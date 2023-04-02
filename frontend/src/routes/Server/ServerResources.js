import { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import Header from "../../components/Header";
import ServerTabs from "../../components/Server/ServerResources/ServerTabs";
import ServerResource from "../../components/Server/ServerResources/ServerResource";

const ServerResources = () => {
    //서버 리스트 불러오기, 서버 별 서버 리소스 객체로 저장하기
    const serverList = ['서버1','서버2','서버3'];
    const [server,setServer] = useState('서버1');
    const navigate = useNavigate();
    const studentList = ['학생1', '학생2', '학생3', '학생4', '학생2', '학생3', '학생4', '학생2', '학생3', '학생4', '학생2', '학생3', '학생4'];
    //서버 별로 객체 배열 만들어서 filter
    return (
        <>
            <Header/>
            <Content>
                <ServerTabs serverList={serverList} server={server} setServer={setServer}/>
                <ServerResource server={server} studentList={studentList} navigate={navigate}/>
            </Content>
        </>
    );
};

export default ServerResources;

const Content = styled.div`
    padding-left: 5%;
    padding-top: 50px;
    width: 95%;
`;