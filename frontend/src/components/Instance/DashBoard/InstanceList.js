import axios from "axios";
import { useEffect } from "react";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import { useRecoilState } from "recoil";
import { baseUrl } from "../../../Atoms";

//관리자일때랑 학생일때 구분해서 API 요청 만들기
const InstanceList = () => {
  const [BASEURL,] = useRecoilState(baseUrl);
  const navigate = useNavigate();
  const [list,setList] = useState();
  //인스턴스 리스트
  useEffect(()=>{
    try {
      axios.get(BASEURL + '/instances/list/my').then((response)=> setList(response.data.instances));
    } catch (error) {
      console.error(error);
    }
  },[BASEURL]);
    
    return (
        <>
            <InstanceTable>
                <thead>
                  <tr>
                      <InstanceHeader style={{minWidth:'120px'}}>이름</InstanceHeader>
                      <InstanceHeader style={{minWidth:'200px'}}>인스턴스 ID</InstanceHeader>
                      <InstanceHeader style={{minWidth:'60px'}}>상태</InstanceHeader>
                      <InstanceHeader style={{minWidth:'60px'}}>용량</InstanceHeader>
                      <InstanceHeader style={{minWidth:'60px'}}>포트</InstanceHeader>
                      <InstanceHeader style={{minWidth:'120px'}}>키 이름</InstanceHeader>
                  </tr>
                </thead>
                <tbody>
                  {list?.map((i)=>{return (
                  <tr key={i.instanceId}>
                    <InstanceBody>{i.name}</InstanceBody>
                    <InstanceId onClick={() => navigate(`${i.instanceId}`)}>{i.instanceId}</InstanceId>
                    <InstanceBody>{i.status}</InstanceBody>
                    <InstanceBody>{i.storage}</InstanceBody>
                    <InstanceBody>{i.port}</InstanceBody>
                    <InstanceBody>{i.keyName}</InstanceBody>
                  </tr>)})}
                </tbody>
            </InstanceTable>
        </>
    );
};

export default InstanceList;

const InstanceTable = styled.table`
  width: 100%;
  border-spacing: 0;
`;

const InstanceHeader = styled.th`
  background-color: #f2f3f3;
  text-align: left;
  padding: 3px 0;
  padding-left: 10px;
  border: 1px solid #eaeded;
`;

const InstanceBody = styled.td`
  text-align: left;
  padding: 5px 0;
  padding-left: 10px;
  background-color: #fafafa;
`;

const InstanceId = styled(InstanceBody)`
  color: #0073bb;
  &:hover{
    cursor: pointer;
    text-decoration: underline;
  }
`;