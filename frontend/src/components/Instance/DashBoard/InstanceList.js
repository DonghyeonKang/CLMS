import { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";

//인스턴스 API 객체형태로 받아와서 뿌리기
const InstanceList = () => {
    const navigate = useNavigate();
    const [list,] = useState([
      {
        name: 1,
        ID: 2,
        state: 3,
        storage: 4,
        IP: 5,
        key: 6,
      },
      {
        name: 2,
        ID: 3,
        state: 4,
        storage: 5,
        IP: 6,
        key: 7,
      },
    ]);
    
    return (
        <>
            <InstanceTable>
                <thead>
                  <tr>
                      <InstanceHeader style={{minWidth:'120px'}}>이름</InstanceHeader>
                      <InstanceHeader style={{minWidth:'200px'}}>인스턴스 ID</InstanceHeader>
                      <InstanceHeader style={{minWidth:'60px'}}>상태</InstanceHeader>
                      <InstanceHeader style={{minWidth:'60px'}}>용량</InstanceHeader>
                      <InstanceHeader style={{minWidth:'250px'}}>IPv4 주소 및 포트</InstanceHeader>
                      <InstanceHeader style={{minWidth:'120px'}}>키 이름</InstanceHeader>
                  </tr>
                </thead>
                <tbody>
                  {list.map((i)=>{return (
                  <tr key={i.ID}>
                    <InstanceBody>{i.name}</InstanceBody>
                    <InstanceId onClick={() => navigate(`${Date.now()}`)}>{i.ID}</InstanceId>
                    <InstanceBody>{i.state}</InstanceBody>
                    <InstanceBody>{i.storage}</InstanceBody>
                    <InstanceBody>{i.IP}</InstanceBody>
                    <InstanceBody>{i.key}</InstanceBody>
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
  background-color: #fafafa;
  text-align: left;
  padding: 3px 0;
  padding-left: 10px;
  border: 1px solid #eaeded;
`;

const InstanceBody = styled.td`
  text-align: left;
  padding: 5px 0;
  padding-left: 10px;
  background-color: white;
`;

const InstanceId = styled(InstanceBody)`
  color: #0073bb;
  &:hover{
    cursor: pointer;
    text-decoration: underline;
  }
`;