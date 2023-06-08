import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import styled from "styled-components";
import InboundRule from "./InboundRule";
import axios from "axios";
import { useRecoilState } from "recoil";
import { baseUrl, tokenState } from "../../../Atoms";

//instanceId 별로 인바운드 리스트 조회 API 요청 하도록 구현
const EditInboundRules = () => {
    const [BASEURL,] = useRecoilState(baseUrl);
    const [token,] = useRecoilState(tokenState);
    const navigate = useNavigate();
    const {instanceId} = useParams();
    const [data,setData] = useState([]);
    //규칙 추가 함수
    const addData = () => {
        setData((prev) => [...prev,{
        id:-1,
        port:'',
        instanceId
        }]);
    };

    //인바운드 규칙 저장
    const saveInboundRules = () => {
      if(token){
        try {
          axios.put(BASEURL + `/instances/inbounds/setting`,{
          data,
          headers: {
            'Authorization': `Bearer ${token}`
          }
        }).then((response)=> console.log(response));
        navigate(`/dashboard/${instanceId}`);
      } catch (error) {
        console.error(error);
      }
    }
    };
    //인바운드 규칙 리스트 불러오기
    const loadInboundRules = () => {
      if(token){
        try {
          axios.get(BASEURL + `/instances/inbounds/list?instanceId=${instanceId}`,{
            headers: {
              'Authorization': `Bearer ${token}`
            }
          }).then((response)=> setData(response.data.inbounds));
        } catch (error) {
          console.error(error);
        }
      }
    }
    useEffect(()=>{
      loadInboundRules();
    },[BASEURL, token, instanceId]);

    return (
        <>
          <Title>인바운드 규칙 편집</Title>
            <Container>
                <Rules>
                <tbody>
                <tr>
                    <RulesHeader style={{minWidth:'150px'}}>보안 그룹 ID</RulesHeader>
                    <RulesHeader style={{minWidth:'80px'}}>프로토콜</RulesHeader>
                    <RulesHeader style={{minWidth:'100px'}}>포트 범위</RulesHeader>
                    <th> </th>
                </tr>
                {data.map((i)=>{
                    return(<InboundRule data={data} setData={setData} i={i} key={i.id}/>)})
                }
                </tbody>
                </Rules>
            </Container>
            <BtnSection>
            <AddRule onClick={()=>addData()}>규칙 추가</AddRule>
            <div style={{display: 'flex'}}>
                <Cancel onClick={() => navigate(`/dashboard/${instanceId}`)}>취소</Cancel>
                <SaveRules onClick={()=>saveInboundRules()}>인바운드 규칙 저장</SaveRules>
            </div>
          </BtnSection>
        </>
    );
};

export default EditInboundRules;

const Container = styled.div`
display: flex;
width: 100%;
min-width: 900px;
margin: 3% 0;
border: 1px solid #dbdfe0;
border-radius: 20px;
background-color: #ffffff;
`;
const Title = styled.div`
  font-size: 20px;
  font-weight: 600;
`;
const Rules = styled.table`
  width: 100%;
  border-spacing: 3vw;
`;

const RulesHeader = styled.th`
  text-align: left;
  margin-right: 5vw;
  width: 30%;
`;

const BtnSection = styled.div`
  display: flex;
  justify-content: space-between;
`;

const AddRule = styled.div`
  cursor: pointer;
  border: 3px solid #3eb5c4;
  height: 25px;
  padding: 4px 15px;
  background-color: white;
  border-radius: 20px;
  font-weight: 600;
  &:hover{
    background-color: #fafafa;
    border-color: #2da4b3;
    color: black;
  }
`

const SaveRules = styled.div`
  cursor: pointer;
  margin-left: 20px;
  padding: 4px 15px;
  background-color: #3eb5c4;
  border-radius: 20px;
  color: white;
  font-weight: 600;
  &:hover{
    background-color: #2da4b3;
  }
`;

const Cancel = styled.div`
  cursor: pointer;
  padding: 4px 15px;
  border: 3px solid #3eb5c4;
  border-radius: 20px;
  font-weight: 600;
  &:hover{
    background-color: white;
    color: black;
  }
`;