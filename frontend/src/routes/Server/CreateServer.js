import { useNavigate } from "react-router-dom";
import { Button, TextField } from "@mui/material";
import styled from "styled-components";
import Header from "../../components/Header";
import axios from "axios";
import { useState } from "react";
import { useRecoilState } from "recoil";
import { baseUrl, tokenState } from "../../Atoms";

//post 요청 구현하고 페이지 전환 기능 추가하기
const CreateServer = () => {
    const [BASEURL,] = useRecoilState(baseUrl);
    const [token,] = useRecoilState(tokenState); 
    const navigate = useNavigate();
    //departmentId 값 받아오면 넣기
    const [serverData,setServerData] = useState({departmentId: 1});

    const [IPValidate,setIPValidate] = useState(false);
    const IPValidation = (str) => {
      const reg = /^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
      return reg.test(str);
    };
    const handleServerIP = (event) => {
        const value = event.target.value;
        setServerData({...serverData, Ipv4:value});
        if(IPValidation(value)){
            setIPValidate(true);
        }else {
            setIPValidate(false);
        }
    };
    
    const [serverNameValidate,setServerNameValidate] = useState(false);
    const serverNameValidation = (str) => {
      const reg = /[a-zA-Zㄱ-ㅎ가-힣0-9]+/gim;
      return reg.test(str);
    };
    const handleServerName = (event) => {
        const value = event.target.value;
        setServerData({...serverData, serverName:value});
        if(value.length >= 1 && value.length <= 250){
            for(let i=0;i<value.length;i++){
              if(serverNameValidation(value[i])){
                setServerNameValidate(true);
              }else {
                setServerNameValidate(false);
                break;
              }
            }
          } else {
            setServerNameValidate(false);
          }
    };

    const [serverUserNamevalidate,setServerUserNameValidate] = useState(false);
    const serverUserNamevalidation = (str) => {
      const reg = /[a-zA-Zㄱ-ㅎ가-힣0-9]+/gim;
      return reg.test(str);
    };
  
    const handleServerUserName = (event) => {
        const value = event.target.value;
        setServerData({...serverData, serverUsername:value});
        if(value.length >= 1 && value.length <= 250){
            for(let i=0;i<value.length;i++){
              if(serverUserNamevalidation(value[i])){
                setServerUserNameValidate(true);
              }else {
                setServerUserNameValidate(false);
                break;
              }
            }
          } else {
            setServerUserNameValidate(false);
          }
    };
    //서버 생성
    const registerServer = () => {
      if(token){
        if(IPValidate && serverNameValidate && serverUserNamevalidate){
          try{
          axios.post(BASEURL + '/servers/register/new',{
            data: serverData,
            headers: {
              'Authorization': `Bearer ${token}`
            }
          }).then((response)=>console.log(response));
          navigate('/serverResources');
        } catch (error) {
          console.error(error);
        };
      } else {
        alert('입력이 올바르지 않습니다.');
      }
    }
    }
    return (
        <>
            <Header/>
            <Content>
                <ContentBody>
                    <Title>서버 등록</Title>
                    <Input>
                        <TextField 
                        label="등록할 서버의 고정 IP 주소" 
                        onChange={(e)=>handleServerIP(e)} 
                        error={!IPValidate}
                        helperText='IP주소를 입력해주세요'
                        fullWidth variant="standard"/>
                    </Input>
                    <Input>
                        <TextField 
                        label="서버 별명 입력" 
                        onChange={(e)=>handleServerName(e)} 
                        error={!serverNameValidate} 
                        helperText='사용할 서버의 이름을 입력해주세요' 
                        fullWidth variant="standard"/>
                    </Input>
                    <Input>
                        <TextField 
                        label="유저 이름 입력" 
                        onChange={(e)=>handleServerUserName(e)} 
                        error={!serverUserNamevalidate} 
                        helperText='리눅스 계정명을 입력해주세요' 
                        fullWidth variant="standard"/>
                    </Input>
                    <Button variant="contained" onClick={()=>registerServer()}>서버 등록</Button>
                </ContentBody>
            </Content>
        </>
    );
};

export default CreateServer;

const Content = styled.div`
  padding: 0 5%;
  width: 90%;
  min-height: 80vh;
  margin-bottom: 120px;
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
    border-radius: 20px;
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