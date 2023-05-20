import styled from "styled-components";
import { useNavigate } from "react-router-dom";
import { List, ListItemButton } from "@mui/material";
import { useEffect, useState } from "react";
import axios from "axios";
import { useRecoilState } from "recoil";
import { baseUrl } from "../../../Atoms";

//아직 API에 serverId 쿼리 적용 안한거 같음
const ServerResource = ({server,studentList,serverId}) => {
    const [BASEURL,] = useRecoilState(baseUrl);
    const navigate = useNavigate();
    const [resources,setResources] = useState();
    //서버 리소스
    useEffect(()=>{
        try {
          axios.get(BASEURL + `/servers/management/resources?serverId=${serverId}`).then((response)=> setResources(response.data));
        } catch (error) {
          console.error(error);
        }
      },[serverId, BASEURL]);
    return (
        <>
        <ServerAddress>{server}</ServerAddress>
        <ServerDetail>
            <StudentList>
                <Title>학생 리스트</Title>
                <List style={{maxHeight: 300, overflow: 'auto', border:'1px solid #eaeded'}}>
                {studentList?.map((item)=>{
                        return(
                        <ListItemButton component='div' onClick={()=>navigate('/dashboard')} key={item?.username}>
                           {item?.username}
                        </ListItemButton>
                    )})}
                </List>
            </StudentList>
            <ResourceList>
                <Title>서버 리소스</Title>
                <Resources>
                    <Resource>
                        <GridTitle>서버 램 사용량</GridTitle>
                        <GridContent>{resources?.ram}</GridContent>
                    </Resource>
                    <Resource>
                        <GridTitle>서버 디스크 사용량</GridTitle>
                        <GridContent>{resources?.disk}</GridContent>
                    </Resource>
                    <Resource>
                        <GridTitle>서버와 CSWS 연결 상태</GridTitle>
                        <GridContent>O</GridContent>
                     </Resource>
                </Resources>
            </ResourceList>
        </ServerDetail>
        </>
    );
};

export default ServerResource;


const ServerAddress = styled.div`
    background-color: white;
    width: 95%;
    text-align: center;
    font-size: 30px;
    font-weight: 600;
    padding: 10px 0;
    background-color: #fafafa;
    border: 1px solid #eaeded;
`;

const ServerDetail = styled.div`
    background-color: white;
    width: 95%;
    display: flex;
`;

const StudentList = styled.div`
    width: 60%;
    padding: 2%;
    display: flex;
    flex-direction: column;
    border: 1px solid #eaeded;
`;

const ResourceList = styled.div`
    width: 100%;
    padding: 2%;
    display: flex;
    flex-direction: column;
    border: 1px solid #eaeded;
`;

const Resources = styled.div`
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
`;

const Resource = styled.div`
    width: 40%;
    min-width: 250px;
    padding: 5px 12px;
    margin: 30px 0;
    height: 80px;
    border: 1px solid #eaeded;
    display: flex;
    flex-direction: column;
    background-color: white;
`;

const Title = styled.div`
    width: 100%;
    font-size: 24px;
    font-weight: 600;
    padding: 10px 0;
    background-color: #fafafa;
    text-align: center;
`;


const GridTitle = styled.div`
  margin: 3%;
  font-size: 16px;
  font-weight: 100;
`;

const GridContent = styled.div`
  margin-left: 5%;
  font-size: 20px;
`;