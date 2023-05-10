import { TextField } from "@mui/material";
import axios from "axios";
import { useState } from "react";
import styled from "styled-components";

//키페어 생성 요청 보내고 나서 수행할 기능 만들기
const KeyPairModal = ({setModalOpen}) => {
    const [keyPairData, setKeyPairData] = useState({});
    const [, setKeyPairType] = useState('RSA');
    const [, setPrivateKeyFileFormat] = useState('.pem');
    const keyPairNameHandler = (e) => {
        setKeyPairData({
            'instanceId': 1,
            'name': e.target.value
        });
    }
    //키 페어 생성
    const createKeyPair = () => {
        try{
            axios.post('http://203.255.3.23:5000/instances/keypair',{keyPairData}).then((response)=>console.log(response));
          } catch (error) {
            console.error(error);
          };
    }
    return (
        <CreateKeyPair>
            <ModalContent>
                <ModalHeader>
                    <HeaderContent>
                        <Title>키 페어 생성</Title>
                        <Cancel onClick={()=>setModalOpen(false)}>X</Cancel>
                    </HeaderContent>
                </ModalHeader>

                <ModalBody>
                    <BodyContent>
                        <KeyPairName>
                            <div style={{marginBottom:'5px'}}>키 페어 이름</div>
                            <TextField label="키페어 이름" onChange={keyPairNameHandler} fullWidth variant="outlined" size="small"/>
                        </KeyPairName>
                        <KeyPairType>
                            <div style={{marginBottom:'5px'}}>키페어 유형</div>
                            <div>
                                <label><input type='radio' name="type" onClick={()=>setKeyPairType('RSA')}/>  RSA</label>
                            </div>
                        </KeyPairType>
                        <PrivateKeyFileFormat>
                            <div style={{marginBottom:'5px'}}>개인 키 파일 형식</div>
                            <div>
                                <label><input type='radio' name="format" onClick={()=>setPrivateKeyFileFormat('.pem')}/>  .pem</label>
                            </div>
                        </PrivateKeyFileFormat>
                    </BodyContent>
                </ModalBody>

                <ModalBtn>
                    <Btn>
                        <Cancel onClick={()=>setModalOpen(false)}>취소</Cancel>
                        <Create onClick={()=>createKeyPair()}>키 페어 생성</Create>
                    </Btn>
                </ModalBtn>
            </ModalContent>
            <ModalBackground onClick={()=>setModalOpen(false)}/>
        </CreateKeyPair>
        
    );
};

export default KeyPairModal;

const CreateKeyPair = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
`;

const ModalContent = styled.div`
    position: absolute;
    width: 35%;
    min-width: 350px;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    z-index: 5;
    box-shadow: 2px 2px #dbdfe0;
    background-color: #ffffff;
`;

const ModalHeader = styled.div`
    background-color: #fafafa;
    border: 1px solid #eaeded;
`;

const HeaderContent = styled.div`
    display: flex;
    justify-content: space-between;
    padding: 3%;
`;

const Title = styled.div`
  font-size: 20px;
  font-weight: 600;
`;

const ModalBody = styled.div`
    background-color: #ffffff;
`;

const KeyPairName = styled.div`
    margin: 5% 0;
    font-size: 16px;
`;

const KeyPairType = styled.div`
    cursor: default;
    margin: 5% 0;
    font-size: 16px;
`;

const PrivateKeyFileFormat = styled.div`
    cursor: default;
    margin: 5% 0;
    font-size: 16px;
`;

const BodyContent = styled.div`
    padding: 3%;
`;

const ModalBtn = styled.div`
    padding: 3%;
`;

const Btn = styled.div`
    display: flex;
    width: 100%;
    justify-content: flex-end;
`;

const Create = styled.div`
  cursor: pointer;
  margin-left: 20px;
  padding: 4px 15px;
  background-color: #ec7211;
  color: white;
  &:hover{
    background-color: #eb5f07;
  }
`;

const Cancel = styled.div`
  cursor: pointer;
  padding: 4px 15px;
  &:hover{
    background-color: #fafafa;
    color: black;
  }
`;

const ModalBackground = styled.div`
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: 3;
    opacity: 0.5;
    background-color: #f2f3f3;
`;