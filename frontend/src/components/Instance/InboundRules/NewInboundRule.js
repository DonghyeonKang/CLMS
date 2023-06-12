import { TextField } from "@mui/material";
import { useState } from "react";
import { useParams } from "react-router-dom";
import styled from "styled-components";

const NewInboundRule = ({newData, setNewData, i}) => {
    const {instanceId} = useParams();
    const [rule,setRule] = useState({
        id: i.id,
        hostPort: i?.hostPort,
        instancePort: Number(i.instancePort),
        instanceId: Number(instanceId),
        number: Number(i.number),
    });

    const [validate, setValidate] = useState(false);
    const validation = (str) => {
        const reg = /^(6553[0-5]|655[0-2]\d|65[0-4]\d{2}|6[0-4]\d{3}|5\d{4}|4\d{4}|3\d{4}|2\d{4}|1\d{4}|[1-9]\d{0,3}|[0])$/;
        return reg.test(Number(str));
      }

    const handleInstancePort = async(e) => {
        const value = e.target.value;
        if(validation(value)){
            setValidate(true);
          } else {
            setValidate(false);
          }
        setRule({...rule, instancePort: Number(value)});
        const newObj = {...rule, instancePort: Number(value)};
        const rest = await newData.filter((item)=>item?.number !== i?.number);
        await setNewData([...rest, newObj].sort((a,b)=>a.number-b.number));
    };

    return (
        <>
            <tr key={i.number}>
                <td style={{width:'200px'}}>{i?.id === -1 ? '' : i?.id}</td>
                <td style={{width:'200px'}} size="small">TCP</td>
                <td style={{width:'300px'}} size="small">{i?.hostPort === -1 ? '' : i?.hostPort}</td>
                <td style={{width:'300px'}}><TextField defaultValue={i?.instancePort} error={!validate} onChange={(e)=>handleInstancePort(e)} size="small"/></td>
                <DeleteRule onClick={()=>{setNewData(newData.filter((item)=>item?.number !== i?.number))}}>삭제</DeleteRule>
            </tr>
        </>
    );
};

export default NewInboundRule;

const DeleteRule = styled.td`
  cursor: pointer;
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