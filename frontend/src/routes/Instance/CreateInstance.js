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

const CreateInstance = () => {
  const [data,setData] = useState({
    name: '',
    storage: '1G',
    machineImage: '',
    keyPair: '',
    serverId: '',
  });
  const [nameValidate,setNameValidate] = useState(false);
  const [keyPairValidate,setKeyPairValidate] = useState(false);
    return (
      <>
        <Header/>
        <Content>
          <Navigation/>
          <Box>
            <InstanceNameSection setData={setData} data={data} validate={nameValidate} setValidate={setNameValidate}/>
            <StorageSection setData={setData} data={data}/>
            <SelectServer setData={setData} data={data}/>
            <KeyPairSection setData={setData} data={data} validate={keyPairValidate} setValidate={setKeyPairValidate}/>
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
`;
const Box = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
  margin: 5% 0 ;
`;
