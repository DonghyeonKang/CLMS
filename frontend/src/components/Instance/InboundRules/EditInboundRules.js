import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import styled from "styled-components";
import InboundRule from "./InboundRule";
import axios from "axios";

//instanceId 별로 인바운드 리스트 조회 API 요청 하도록 구현
//인바운드 규칙 저장 버튼 누르면 PUT 요청 보내게 구현
const EditInboundRules = () => {
    const navigate = useNavigate();
    const {instanceId} = useParams();
    const [id, setId] = useState(3);
    const [data,setData] = useState([{
      id:1,
      port:20,
      instanceId
      },
      {
      id:2,
      port:30,
      instanceId
      }]);
    const addData = () => {
        setData((prev) => [...prev,{
        id:id,
        port:'',
        instanceId
        }]);
        setId((prev)=>prev+1);
    };

/* 인바운드 규칙 리스트
    useEffect(()=>{
      try {
        axios.get(`http://203.255.3.23:5000/instances/inbounds/list`).then((response)=> setData(response.data.inbounds));
      } catch (error) {
        console.error(error);
      }
    },[]);
    
    인바운드 규칙 수정 API 추가해야함
*/


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
            <div>
                <Cancel onClick={() => navigate(`/dashboard/${instanceId}`)}>취소</Cancel>
                <SaveRules onClick={()=>console.log(data)}>인바운드 규칙 저장</SaveRules>
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
box-shadow: 2px 2px #dbdfe0;
background-color: #fafafa;
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

const AddRule = styled.span`
  cursor: pointer;
  border: 0.5px solid #879596;
  height: 25px;
  padding: 4px 15px;
  background-color: white;
  font-weight: 600;
  &:hover{
    background-color: #fafafa;
    color: black;
  }
`

const SaveRules = styled.span`
  cursor: pointer;
  margin-left: 20px;
  padding: 4px 15px;
  background-color: #ec7211;
  color: white;
  font-weight: 600;
  &:hover{
    background-color: #eb5f07;
  }
`;

const Cancel = styled.span`
  cursor: pointer;
  padding: 4px 15px;
  &:hover{
    background-color: white;
    color: black;
  }
`;