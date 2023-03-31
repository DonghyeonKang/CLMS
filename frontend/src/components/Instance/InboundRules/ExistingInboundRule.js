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

export default ExistingInboundRule;

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