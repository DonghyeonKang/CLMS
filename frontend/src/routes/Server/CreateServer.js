import { useNavigate } from "react-router-dom";
import { Button, TextField } from "@mui/material";
import styled from "styled-components";
import Header from "../../components/Header";
import axios from "axios";
import { useState } from "react";

//post 요청 구현하고 페이지 전환 기능 추가하기
const CreateServer = () => {
    const navigate = useNavigate();
    const [serverData,setServerData] = useState({serverUsername:"pika"});

    const handleServerIP = (e) => {
        setServerData({...serverData, Ipv4:e.target.value});
    }

    const handleServerNickName = (e) => {
        setServerData({...serverData, serverName:e.target.value});
    }
    
    const registerServer = () => {
        try{
            axios.post('http://203.255.3.23:5000/servers/register/new',serverData).then((response)=>console.log(response));
          } catch (error) {
            console.error(error);
          };
    }
    return (
        <>
            <Header/>
            <Content>
                <ContentBody>
                    <Title>서버 등록</Title>
                    <ServerIP>
                        <TextField label="등록할 서버의 고정 IP 주소" onChange={(e)=>handleServerIP(e)} fullWidth variant="standard"/>
                    </ServerIP>
                    <ServerNickname>
                        <TextField label="서버 별명 입력" onChange={(e)=>handleServerNickName(e)} fullWidth variant="standard"/>
                    </ServerNickname>
                    <DownloadFile href=" " download>서버화 위한 다운로드 파일</DownloadFile>
                    <Button variant="contained" onClick={()=>registerServer()}>서버 등록</Button>
                    {/*<Button variant="contained" onClick={()=>navigate('/serverResources')}>서버 등록</Button>*/}
                </ContentBody>
            </Content>
        </>
    );
};

export default CreateServer;

const Content = styled.div`
  padding: 0 5%;
  width: 90%;
  margin-top: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const ContentBody = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    border: 1px solid black;
    padding: 3%;
    background-color: white;
`;

const Title = styled.div`
    font-size: 24px;
    font-weight: 600;
`;

const ServerIP = styled.div`
    width: 400px;
    font-size: 28px;
    margin: 30px;
`;

const ServerNickname = styled.div`
    width: 400px;
    font-size: 28px;
    margin: 30px;
`;

const DownloadFile = styled.a`
    margin: 30px;
    font-size: 24px;
`;