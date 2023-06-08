import axios from "axios";
import { useEffect } from "react";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import { useRecoilState } from "recoil";
import { baseUrl } from "../../../Atoms";
import { tokenState } from '../../../Atoms';

//관리자일때랑 학생일때 구분해서 API 요청 만들기
const InstanceList = ({setUserId, setAddress}) => {
  const [BASEURL,] = useRecoilState(baseUrl);
  const navigate = useNavigate();
  const [list,setList] = useState([]);
  const [token,] = useRecoilState(tokenState);
  //인스턴스 리스트
  useEffect(() => {
    if(token){
      try {
        axios.get(BASEURL + '/instances/list/user', {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      }).then((response) => setList(response.data.instances));
    } catch (error) {
      console.error(error);
    }
  }
  }, [BASEURL, token]); 
    
  useEffect(()=>{
    if(list.length>=1){
      setUserId(list[0]?.userId);
      setAddress(list[0]?.address);
    }
  },[list])
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
                  {list?.map((i)=>{ console.log(i.status); return (
                  <tr key={i.instanceId}>
                    <InstanceBody>{i.name}</InstanceBody>
                    <InstanceId onClick={() => navigate(`${i.instanceId}`)}>{i.instanceId}</InstanceId>
                    <InstanceBody>{i.state}</InstanceBody>
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