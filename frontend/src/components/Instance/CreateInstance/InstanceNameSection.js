import { TextField } from "@mui/material";
import styled from "styled-components";

const InstanceNameSection = ({setData, data}) => {
    const nameHandler = (event) => {
      setData({...data, name:event.target.value});
    };
    return (
        <Name>
            <Title>인스턴스 이름</Title>
            <TextField label='인스턴스 이름' onChange={nameHandler} size="small"/>
        </Name>
    );
};

export default InstanceNameSection;

const Name = styled.div`
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