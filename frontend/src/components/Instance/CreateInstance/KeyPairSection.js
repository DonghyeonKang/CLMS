import { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import KeyPairModal from "./KeyPairModal";
import { TextField } from "@mui/material";
import axios from "axios";

//인스턴스 생성 버튼 누르면 API 요청 하게 함수 수정하기
const KeyPairSection = ({setData, data}) => {
  const navigate = useNavigate();
  const [modalOpen, setModalOpen] = useState(false);
  const keyPairHandler = (event) => {
    setData({...data, keyPair: event.target.value});
  }
  //인스턴스 생성
  const createInstance = () => {
    try{
      axios.post('http://203.255.3.23:5000/instances/creation',data).then((response)=>console.log(response));
    } catch (error) {
      console.error(error);
    };
  }
    return (
      <>
        <KeyPair>
            <Title>키 페어</Title>
            <div style={{display:'flex', justifyContent:'space-between', alignItems:'center'}}>
              <TextField label='키 페어' onChange={keyPairHandler} style={{width:'60%'}} size="small"/>
              <CreateKeyPair onClick={()=>setModalOpen(true)}>새 키 페어 생성</CreateKeyPair>
            </div>
        </KeyPair>
        <Btn>
            <Cancel onClick={() => navigate("/dashboard")}>취소</Cancel>
            <Create onClick={() => createInstance()}>인스턴스 생성</Create> 
        </Btn>
        {modalOpen ? <KeyPairModal setModalOpen={setModalOpen} /> : <></>}
      </> 
    );
};

export default KeyPairSection;

const KeyPair = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 40%;
  min-width: 400px;
  padding: 2%;
  height: 50%;
  margin-bottom: 13%;
  box-shadow: 2px 2px #dbdfe0;
  background-color: #fafafa;
`;

const Title = styled.div`
  margin-bottom: 5%;
  font-weight: 600;
`;

const Btn = styled.div`
  width: 100vw;
  height: 5vh;
  display: flex;
  justify-content: end;
  align-items: flex-end;
  margin-top: -8%;
`;

const CreateKeyPair = styled.span`
  cursor: pointer;
  &:hover{
    text-decoration: underline;
  }
`;

const Create = styled.div`
  cursor: pointer;
  padding: 4px 15px;
  height: 25px;
  background-color: #ec7211;
  margin-left: 20px;
  color: white;
  font-weight: 600;
  &:hover{
    background-color: #eb5f07;
  }
`;

const Cancel = styled.div`
  cursor: pointer;
  padding: 4px 15px;
  &:hover{
    background-color: white;
    color: black;
  }
`;