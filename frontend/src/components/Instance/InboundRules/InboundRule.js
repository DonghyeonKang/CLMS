import { TextField } from "@mui/material";
import { useState } from "react";
import styled from "styled-components";

const ExistingInboundRule = ({data, setData, i}) => {
    const [rule,setRule] = useState({
        Id:i.id,
        port:i.port,
    });

    const handlePort = async(e) => {
        setRule({...rule, port: e.target.value});
        const newObj = {...rule, port: e.target.value};
        const rest = await data.filter((item)=>item?.id !== i?.id);
        const newData = [...rest, newObj].sort((a,b)=>a.id-b.id);
        await setData(newData);
    };

    return (
        <>
            <tr key={i.id}>
                <td style={{width:'20vw', minWidth:'150px'}}>{i?.id}</td>
                <td style={{width:'20vw', minWidth:'80px'}} size="small">TCP</td>
                <td><TextField style={{width:'20vw', minWidth:'100px'}} defaultValue={i?.port} onChange={handlePort} size="small"/></td>
                <DeleteRule onClick={()=>{setData(data.filter((item)=>item?.id !== i?.id))}}>삭제</DeleteRule>
            </tr>
        </>
    );
};

export default ExistingInboundRule;

const DeleteRule = styled.td`
  cursor: pointer;ㄴ
  background-color: white;
  border: 3px solid #3eb5c4;
  border-radius: 20px;
  text-align: center;
  padding: 0 8px;
  font-weight: 600;
  &:hover{
    background-color: #fafafa;
    color: black;
  }
`;