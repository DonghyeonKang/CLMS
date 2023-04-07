import { TextField } from "@mui/material";
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

    const handlePort = async(e) => {
        setRule({...rule, port: e.target.value});
        const newObj = {...rule, port: e.target.value};
        const rest = await data.filter((item)=>item?.Id !== i?.Id);
        const newData = [...rest, newObj].sort((a,b)=>a.Id-b.Id);
        await setData(newData);
    };

    return (
        <>
            <tr key={data[i?.Id]?.Id}>
                <td style={{width:'20vw', minWidth:'150px'}}>{i?.Id}</td>
                <td><TextField style={{width:'20vw', minWidth:'80px'}} defaultValue={i?.protocol} size="small"/></td>
                <td><TextField style={{width:'20vw', minWidth:'100px'}} defaultValue={i?.port} onChange={handlePort} size="small"/></td>
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