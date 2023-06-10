import axios from "axios";
import { MenuItem, Select } from "@mui/material";
import { useEffect, useState } from "react";
import styled from "styled-components";
import { useRecoilState } from "recoil";
import { baseUrl } from "../../../Atoms";

const SelectServer = ({data, setData, setHostname}) => {
    const [BASEURL,] = useRecoilState(baseUrl);
    const departmentId = localStorage.getItem('departmentId');
    const [serverList,setServerList] = useState([]);
    const serverIdHandler = (event) => {
      const value = event.target.value;
      setData({...data, serverId: value});
      const hostname = serverList?.filter((i)=>i?.serverId === value)[0]?.hostname;
      setHostname(hostname);
      };
      
    const loadServerList = () => {
        try {
          axios.get(BASEURL + `/servers/management/list?departmentId=${departmentId}`).then((response)=> setServerList(response.data.servers));
        } catch (error) {
          console.error(error);
        }
    }
    console.log(serverList);
    useEffect(()=>{
        loadServerList();
    },[BASEURL]);

    return (
    <Content>
        <Title>서버 선택</Title>
        <Select labelId="server" onChange={serverIdHandler} value={data.serverId ?? ''} size='small'>
            {serverList.map((i)=><MenuItem value={i?.serverId} key={i?.serverId}>{i?.name}</MenuItem>)}
        </Select>
    </Content>);
}

export default SelectServer;

const Content = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 40%;
  min-width: 400px;
  padding: 2%;
  margin-bottom: 5%;
  box-shadow: 2px 2px #dbdfe0;
  background-color: #ffffff;
  border: 3px solid #f2f3f3;
  border-radius: 20px;
`;

const Title = styled.div`
  margin-bottom: 5%;
  font-weight: 600;
`;