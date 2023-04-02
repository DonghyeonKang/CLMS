import styled from "styled-components";
import ubuntu from '../../../img/ubuntu.png'
import CentOs from '../../../img/CentOs.png'

const MachineImageSection = ({setData, data}) => {
    const setUbuntu = () => {
      setData({...data, machineImage: 'Ubuntu'});
    };
    const setCentOS = () => {
      setData({...data, machineImage: 'CentOS'});
    };
    return (
        <OS>
            <Title>운영체제 종류</Title>
            <OSTabs>
              {data.machineImage === 'Ubuntu' ? 
              (<OSTabSelected>
                <div>Ubuntu</div>
                <OSImg src={ubuntu} onClick={setUbuntu}/>
              </OSTabSelected>) : 
              (<OSTab>
                <div>Ubuntu</div>
                <OSImg src={ubuntu} onClick={setUbuntu}/>
              </OSTab>)}
              
              {data.machineImage === 'CentOS' ? 
              (<OSTabSelected>
                <div>CentOS</div>
                <OSImg src={CentOs} onClick={setCentOS}/>
              </OSTabSelected>) : 
              (<OSTab>
                <div>CentOS</div>
                <OSImg src={CentOs} onClick={setCentOS}/>
              </OSTab>)}
            </OSTabs>
        </OS>
    );
};

export default MachineImageSection;

const OS = styled.div`
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
  padding: 1%;
`;

const OSTabSelected = styled(OSTab)`
  border: 2px solid #0073bb;
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