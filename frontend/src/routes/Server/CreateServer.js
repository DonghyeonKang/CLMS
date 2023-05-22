import { useNavigate } from "react-router-dom";
import { Button, TextField } from "@mui/material";
import styled from "styled-components";
import Header from "../../components/Header";
import axios from "axios";
import { useState } from "react";
import { useRecoilState } from "recoil";
import { baseUrl } from "../../Atoms";

//post 요청 구현하고 페이지 전환 기능 추가하기
const CreateServer = () => {
    const [BASEURL,] = useRecoilState(baseUrl);
    const navigate = useNavigate();
    //departmentId 값 받아오면 넣기
    const [serverData,setServerData] = useState({departmentId: 1});

    const handleServerIP = (e) => {
        setServerData({...serverData, Ipv4:e.target.value});
    }

    const handleServerNickName = (e) => {
        setServerData({...serverData, serverName:e.target.value});
    }

    const handleServerUserName = (e) => {
        setServerData({...serverData, serverUsername:e.target.value});
    }
    //서버 생성
    const registerServer = () => {
        try{
            axios.post(BASEURL + '/servers/register/new',serverData).then((response)=>console.log(response));
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
                    <Input>
                        <TextField label="등록할 서버의 고정 IP 주소" onChange={(e)=>handleServerIP(e)} fullWidth variant="standard"/>
                    </Input>
                    <Input>
                        <TextField label="서버 별명 입력" onChange={(e)=>handleServerNickName(e)} fullWidth variant="standard"/>
                    </Input>
                    <Input>
                        <TextField label="유저 이름 입력" onChange={(e)=>handleServerUserName(e)} fullWidth variant="standard"/>
                    </Input>
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

const Input = styled.div`
    width: 400px;
    font-size: 28px;
    margin: 30px;
`;