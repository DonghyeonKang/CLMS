import { useEffect, useState } from "react";
import styled from "styled-components";
import Header from "../../components/Header";
import ServerTabs from "../../components/Server/ServerResources/ServerTabs";
import ServerResource from "../../components/Server/ServerResources/ServerResource";
import axios from "axios";

//serverId 별로 학생 리스트 요청하도록 구현하기
const ServerResources = () => {
    const [data, setData] = useState([]);
    const [serverList,setServerList] = useState([]);
    const [server,setServer] = useState('');
    const [studentList,setStudentList] = useState([]);
    const [serverId,setServerId] = useState(1);

    const loadServerList = () => {
        try {
            axios.get(`http://203.255.3.23:5000/servers/management/list?departmentId=0`).then((response)=> setData(response.data.servers));
          } catch (error) {
            console.error(error);
          }
    }

    const loadStudentList = () => {
        try {
            axios.get(`http://203.255.3.23:5000/user/student/list`).then((response)=> setStudentList(response.data.students));
          } catch (error) {
            console.error(error);
          }
    }
    useEffect(()=>{
        loadServerList();
        loadStudentList();
      },[]);

    useEffect(()=>{
        setServer(data[0]?.name);
        data?.map((i)=> setServerList((prev) => [...prev, i?.name]));
    },[data]);

    useEffect(()=>{
        const switchServer = data?.filter((i)=>i?.name===server);
        setServerId(switchServer[0]?.id);
    },[data,server]);
    return (
        <>
            <Header/>
            <Content>
                <ServerTabs serverList={serverList} server={server} setServer={setServer}/>
                <ServerResource server={server} studentList={studentList} serverId={serverId}/>
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