import { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import KeyPairModal from "./KeyPairModal";

const KeyPairSection = ({setData, data}) => {
  const navigate = useNavigate();
  const [modalOpen, setModalOpen] = useState(false);
  const keyPairHandler = (event) => {
    setData({...data, keyPair: event.target.value});
  }
    return (
      <>
        <KeyPair>
            <Title>키 페어</Title>
            <div style={{display:'flex', justifyContent:'space-between'}}>
              <input style={{width:'60%'}} onChange={keyPairHandler}/>
              <CreateKeyPair onClick={()=>setModalOpen(true)}>새 키 페어 생성</CreateKeyPair>
            </div>
        </KeyPair>
        <Btn>
            <Cancel onClick={() => console.log(data)}>취소</Cancel>
            <Create onClick={() => navigate("/dashboard")}>인스턴스 생성</Create> 
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