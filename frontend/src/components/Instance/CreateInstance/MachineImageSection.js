import styled from "styled-components";

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
              <OSTab onClick={setUbuntu}>우분투</OSTab>
              <OSTab onClick={setCentOS}>CentOS</OSTab>
            </OSTabs>
            <select>
                <option>운영체제 버전</option>
            </select>
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
`;

const OSTabs = styled.div`
  display: flex;
  justify-content: space-evenly;
  margin: 4% 0;
  width: 100%;
`;

const OSTab = styled.div`
  border: 1px solid black;
  width: 5vw;
  min-width: 75px;
  height: 10vh;
  min-height: 60px;
  display: flex;
  justify-content: center;
  align-items: center;
`;