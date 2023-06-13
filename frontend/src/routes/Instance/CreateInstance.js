import { useState } from "react";
import styled from "styled-components";
import Header from "../../components/Header";
import InstanceNameSection from "../../components/Instance/CreateInstance/InstanceNameSection";
import KeyPairSection from "../../components/Instance/CreateInstance/KeyPairSection";
import MachineImageSection from "../../components/Instance/CreateInstance/MachineImageSection";
import StorageSection from "../../components/Instance/CreateInstance/StorageSection";
import Navigation from "../../components/Navigation";
import SelectServer from "../../components/Instance/CreateInstance/SelectServer";
import CreateInstanceButtons from "../../components/Instance/CreateInstance/CreateInstanceButtons";
import { useLocation } from "react-router-dom";

const CreateInstance = () => {
  const {state} = useLocation();
  const [data,setData] = useState({
    name: '',
    storage: '1G',
    os: '',
    keyName: '',
    serverId: '',
    userId: state.userId,
    address: state.address,
  });
  const [nameValidate,setNameValidate] = useState(false);
  const [keyPairValidate,setKeyPairValidate] = useState(false);
  const [hostname,setHostname] = useState('');
    return (
      <>
        <Header/>
        <Content>
          <Navigation/>
          <Box>
            <InstanceNameSection setData={setData} data={data} validate={nameValidate} setValidate={setNameValidate}/>
            <StorageSection setData={setData} data={data}/>
            <SelectServer setData={setData} data={data} setHostname={setHostname}/>
            <KeyPairSection setData={setData} data={data} validate={keyPairValidate} setValidate={setKeyPairValidate} hostname={hostname}/>
            <MachineImageSection setData={setData} data={data}/>
            <CreateInstanceButtons data={data} validate={nameValidate&&keyPairValidate}/>
          </Box>
        </Content>  
      </>
    );
};
export default CreateInstance;

const Content = styled.div`
  padding: 0 5%;
  width: 90%;
  min-height: 80vh;
  margin-bottom: 120px;
`;
const Box = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
  margin: 5% 0 ;
`;
