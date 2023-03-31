import { useState } from "react";
import styled from "styled-components";

// type, CIDR 목록 넣어야 함

const NewInboundRule = ({data, setData, i}) => {
    const obj = {
        Id:i?.Id,
        type:i?.type,
        protocol:i?.protocol,
        port:i?.port,
        CIDR:i?.CIDR,
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
            <tr key={i.Id}>
                <td style={{width:'12vw', minWidth:'150px'}}>{i?.Id}</td>
                <td>
                    <select name="type" style={{width:'12vw', minWidth:'150px'}} defaultValue={i?.type} onChange={handleType}>
                        <option value='1'>1</option>
                        <option value='2'>2</option>
                        <option value='3'>3</option>
                    </select>
                </td>
                <td><input style={{width:'5vw', minWidth:'80px'}} defaultValue={i?.protocol} onChange={handleProtocol}/></td>
                <td><input style={{width:'6vw', minWidth:'100px'}} defaultValue={i?.port} onChange={handlePort}/></td>
                <td>
                    <select name="CIDR" style={{width:'12vw', minWidth:'150px'}} defaultValue={i?.CIDR} onChange={handleCIDR}>
                        <option value='1'>1</option>
                        <option value='2'>2</option>
                        <option value='3'>3</option>
                    </select>
                </td>
                <DeleteRule onClick={()=>{setData(data.filter((item)=>item?.Id !== i?.Id))}}>삭제</DeleteRule>
            </tr>
        </>
    );
};

export default NewInboundRule;

const DeleteRule = styled.td`
  cursor: pointer;
  background-color: white;
  border: 1px solid black;
  text-align: center;
  width: 50px;
  &:hover{
    background-color: #fafafa;
    color: black;
  }
`;