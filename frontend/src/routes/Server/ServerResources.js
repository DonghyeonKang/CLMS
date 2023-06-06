import { useEffect, useState } from "react";
import styled from "styled-components";
import Header from "../../components/Header";
import ServerTabs from "../../components/Server/ServerResources/ServerTabs";
import ServerResource from "../../components/Server/ServerResources/ServerResource";
import axios from "axios";
import { useRecoilState } from "recoil";
import { baseUrl, tokenState } from "../../Atoms";

//serverId 별로 학생 리스트 요청하도록 구현하기
//serverId 변경되면 학생리스트, 리소스 API 요청 하게 수정하기
const ServerResources = () => {
    const [BASEURL,] = useRecoilState(baseUrl);
    const [token,] = useRecoilState(tokenState);
    const [data, setData] = useState([]);
    const [serverList,setServerList] = useState([]);
    const [server,setServer] = useState('');
    const [studentList,setStudentList] = useState([]);
    const [serverId,setServerId] = useState('');
    //서버 리스트 (학과 ID값 받아와야 함)
    const loadServerList = () => {
        if(token){
            try {
                axios.get(BASEURL + `/servers/management/list?departmentId=0`,{
                    headers: {
                        'Authorization': `Bearer ${token}`
                      }
                }).then((response)=> setData(response.data.servers));
              } catch (error) {
                console.error(error);
              }
        }
    }
    //학생 리스트 (serverId 쿼리로)
    const loadStudentList = () => {
        if(token){
            try {
                axios.get(BASEURL + `/user/student/list`,{
                headers: {
                    'Authorization': `Bearer ${token}`
                  }
            }).then((response)=> setStudentList(response.data.students));
          } catch (error) {
            console.error(error);
          }
        }
    }
    useEffect(()=>{
        loadServerList();
      },[]);

    useEffect(()=>{
        setServer(data[0]?.name);
        data?.map((i)=> setServerList((prev) => [...prev, i?.name]));
    },[data]);

    useEffect(()=>{
        const switchServer = data?.filter((i)=>i?.name===server);
        setServerId(switchServer[0]?.id);
        if(switchServer[0]?.id !== undefined){
            loadStudentList();
        }
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
    min-height: 80vh;
    margin-bottom: 120px;
`;