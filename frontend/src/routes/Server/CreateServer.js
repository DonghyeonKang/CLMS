import { Button, TextField } from "@mui/material";
import styled from "styled-components";
import Header from "../../components/Header";

const CreateServer = () => {
    return (
        <>
            <Header/>
            <Content>
                <ContentBody>
                    <Title>서버 등록</Title>
                    <ServerIP>
                        <TextField label="등록할 서버의 고정 IP 주소" fullWidth variant="standard"/>
                    </ServerIP>
                    <ServerNickname>
                        <TextField label="서버 별명 입력" fullWidth variant="standard"/>
                    </ServerNickname>
                    <DownloadFile href=" " download>서버화 위한 다운로드 파일</DownloadFile>
                    <Button variant="contained">서버 등록</Button>
                </ContentBody>
            </Content>
        </>
    );
};

export default CreateServer;

const Content = styled.div`
  padding: 0 5%;
  width: 90%;
  height: 80vh;
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