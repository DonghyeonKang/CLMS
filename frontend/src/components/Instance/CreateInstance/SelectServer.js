import axios from "axios";
import { MenuItem, Select } from "@mui/material";
import { useEffect, useState } from "react";
import styled from "styled-components";

const SelectServer = ({data, setData}) => {
    const [serverList,setServerList] = useState([]);
    const serverIdHandler = (event) => {
      setData({...data, serverId: event.target.value});
      };
    const loadServerList = () => {
        try {
            axios.get(`http://203.255.3.23:5000/servers/management/list?departmentId=0`).then((response)=> setServerList(response.data.servers));
          } catch (error) {
            console.error(error);
          }
    }

    useEffect(()=>{
        loadServerList();
    },[data]);

    return (
    <Content>
        <Title>서버 선택</Title>
        <Select label="server" onChange={serverIdHandler} value={data.serverId ?? ''} size='small'>
            {serverList.map((i)=><MenuItem value={i?.id} key={i?.id}>{i?.name}</MenuItem>)}
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
  background-color: #fafafa;
`;

const Title = styled.div`
  margin-bottom: 5%;
  font-weight: 600;
`;