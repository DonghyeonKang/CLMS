import styled from "styled-components";
import ubuntu from '../../../img/ubuntu.png'

const MachineImageSection = ({setData, data}) => {
    const setUbuntu = () => {
      setData({...data, machineImage: 'ubuntu:latest'});
    };
    return (
        <Content>
            <Title>운영체제 종류</Title>
            <OSTabs>
              {data.machineImage === 'ubuntu:latest' ? 
              (<OSTabSelected>
                <div>ubuntu:latest</div>
                <OSImg src={ubuntu} onClick={setUbuntu}/>
              </OSTabSelected>) : 
              (<OSTab>
                <div>ubuntu:latest</div>
                <OSImg src={ubuntu} onClick={setUbuntu}/>
              </OSTab>)}
            </OSTabs>
        </Content>
    );
};

export default MachineImageSection;

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

const Title = styled.div`
  margin-bottom: 5%;
  font-weight: 600;
`;

const OSTabs = styled.div`
  display: flex;
  justify-content: space-evenly;
  margin-bottom: 4%;
  width: 100%;
`;

const OSTab = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  border: 2px solid #eaeded;
  border-radius: 10px;
  padding: 1%;
  margin: -20px;
`;

const OSTabSelected = styled(OSTab)`
  border: 2px solid #3eb5c4;
  background-color: #f1faff;
`;

const OSImg = styled.img`
  width: 8vw;
  min-width: 100px;
  height: 15vh;
  min-height: 60px;
  display: flex;
  margin-top: 30px;
  justify-content: center;
  align-items: center;
`;