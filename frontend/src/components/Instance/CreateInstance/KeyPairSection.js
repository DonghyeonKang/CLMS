import { useState } from "react";
import styled from "styled-components";
import KeyPairModal from "./KeyPairModal";
import { TextField } from "@mui/material";


const KeyPairSection = ({setData, data}) => {
  const [modalOpen, setModalOpen] = useState(false);
  const [validate,setValidate] = useState(false);
  const validation = (str) => {
    const reg = /[a-zA-Zㄱ-ㅎ가-힣0-9]+/gim;
    return reg.test(str);
  };

  const keyPairHandler = (event) => {
    const value = event.target.value;
    setData({...data, keyPair: value});
    if(value.length >= 1 && value.length <= 250){
      for(let i=0;i<value.length;i++){
        if(validation(value[i])){
          setValidate(true);
        }else {
          setValidate(false);
          break;
        }
      }
    } else {
      setValidate(false);
    }
    
  }
    return (
      <Content>
        <KeyPair>
            <Title>키 페어</Title>
            <div style={{display:'flex', justifyContent:'space-between', alignItems:'center'}}>
              <TextField label='키 페어' onChange={keyPairHandler} error={!validate} helperText='키 페어 이름을 입력해주세요' style={{width:'60%'}} size="small"/>
              <CreateKeyPair onClick={()=>setModalOpen(true)}>새 키 페어 생성</CreateKeyPair>
            </div>
        </KeyPair>
        {modalOpen ? <KeyPairModal setModalOpen={setModalOpen} /> : <></>}
      </Content> 
    );
};

export default KeyPairSection;

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

const KeyPair = styled.div`
`;

const Title = styled.div`
  margin-bottom: 5%;
  font-weight: 600;
`;

const CreateKeyPair = styled.span`
  cursor: pointer;
  &:hover{
    text-decoration: underline;
  }
`;
