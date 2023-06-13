import styled from "styled-components";
import { useNavigate } from "react-router-dom";
import { List, ListItemButton } from "@mui/material";
import { useEffect, useState } from "react";
import axios from "axios";
import { useRecoilState } from "recoil";
import { baseUrl } from "../../../Atoms";
import RamChart from "./RamChart";
import DiskChart from "./DiskChart";

const ServerResource = ({server,studentList,serverId}) => {
    const [BASEURL,] = useRecoilState(baseUrl);
    const navigate = useNavigate();
    const [resources,setResources] = useState({});

    //서버 리소스
    useEffect(()=>{
        if(serverId !== '' && serverId !== undefined){
            try {
              axios.get(BASEURL + `/servers/management/resources?serverId=${serverId}`).then((response)=> setResources(response.data));
            } catch (error) {
              console.error(error);
            }
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
                    {
                        (resources.ram) ? <RamChart dataAvg={resources.ram} dataLabel={'램 사용량'}/> : ''
                    }
                    {
                    (resources.disk) ? <DiskChart dataAvg={resources.disk} dataLabel={'디스크 사용량'}/> : ''
                    }
                    <Resource>서버 연결 상태 {resources?.connection === 'connected' ? '✅' : '❌'}</Resource>
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
    border-radius: 20px;
    margin: 20px 0;
`;

const ServerDetail = styled.div`
    background-color: white;
    width: 95%;
    display: flex;
    min-height: 150px;
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
    width: 52%;
    min-width: 150px;
    margin: 30px 0;
    height: 80px;
    display: flex;
    flex-direction: column;
    align-items: center;
    position: absolute;
`;

const Title = styled.div`
    width: 100%;
    font-size: 24px;
    font-weight: 600;
    padding: 10px 0;
    background-color: #fafafa;
    text-align: center;
`;