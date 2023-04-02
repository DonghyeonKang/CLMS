import { MenuItem, Select, TextField } from "@mui/material";
import { useState } from "react";
import styled from "styled-components";

// 존재하는 규칙 배열 수정하고, 삭제시 ID 값 저장시켰다가 DELETE
// type, CIDR 목록 넣어야 함

const ExistingInboundRule = ({data, setData, i}) => {
    const obj = {
        Id:i.Id,
        type:data[i?.Id]?.type,
        protocol:data[i?.Id]?.protocol,
        port:data[i?.Id]?.port,
        CIDR:data[i?.Id]?.CIDR,
    };
    const [rule,setRule] = useState(obj);

    const handleType = async(e) => {
        setRule({...rule, type: e.target.value});
        const newObj = {...rule, type: e.target.value};
        const rest = await data.filter((item)=>item?.Id !== i?.Id);
        const newData = [...rest, newObj].sort((a,b)=>a.Id-b.Id);
        await setData(newData);
    };

    const handleProtocol = async(e) => {
        setRule({...rule, protocol: e.target.value});
        const newObj = {...rule, protocol: e.target.value};
        const rest = await data.filter((item)=>item?.Id !== i?.Id);
        const newData = [...rest, newObj].sort((a,b)=>a.Id-b.Id);
        await setData(newData);
    };

    const handlePort = async(e) => {
        setRule({...rule, port: e.target.value});
        const newObj = {...rule, port: e.target.value};
        const rest = await data.filter((item)=>item?.Id !== i?.Id);
        const newData = [...rest, newObj].sort((a,b)=>a.Id-b.Id);
        await setData(newData);
    };

    const handleCIDR = async(e) => {
        setRule({...rule, CIDR: e.target.value});
        const newObj = {...rule, CIDR: e.target.value};
        const rest = await data.filter((item)=>item?.Id !== i?.Id);
        const newData = [...rest, newObj].sort((a,b)=>a.Id-b.Id);
        await setData(newData);
    };

    return (
        <>
            <tr key={data[i?.Id]?.Id}>
                <td style={{width:'12vw', minWidth:'150px'}}>{i?.Id}</td>
                <td>
                    <Select label="type" style={{width:'12vw', minWidth:'150px'}} defaultValue={i?.type} onChange={handleType} size="small">
                        <MenuItem value='1'>1</MenuItem>
                        <MenuItem value='2'>2</MenuItem>
                        <MenuItem value='3'>3</MenuItem>
                    </Select>
                </td>
                <td><TextField style={{width:'5vw', minWidth:'80px'}} defaultValue={i?.protocol} onChange={handleProtocol} size="small"/></td>
                <td><TextField style={{width:'6vw', minWidth:'100px'}} defaultValue={i?.port} onChange={handlePort} size="small"/></td>
                <td>
                    <Select label="CIDR" style={{width:'12vw', minWidth:'150px'}} defaultValue={i?.CIDR} onChange={handleCIDR} size="small">
                        <MenuItem value='1'>1</MenuItem>
                        <MenuItem value='2'>2</MenuItem>
                        <MenuItem value='3'>3</MenuItem>
                    </Select>
                </td>
                <DeleteRule onClick={()=>{setData(data.filter((item)=>item?.Id !== i?.Id))}}>삭제</DeleteRule>
            </tr>
        </>
    );
};

export default ExistingInboundRule;

const DeleteRule = styled.td`
  cursor: pointer;
  background-color: white;
  border: 1px solid black;
  text-align: center;
  width: 50px;
  padding: 0 8px;
  font-weight: 600;
  &:hover{
    background-color: #fafafa;
    color: black;
  }
`;