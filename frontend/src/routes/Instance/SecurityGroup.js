import styled from "styled-components";
import Header from "../../components/Header";
import BoundRules from "../../components/Instance/SecurityGroup/BoundRules";
import SecurityGroupDetail from "../../components/Instance/SecurityGroup/SecurityGroupDetail";
import Navigation from "../../components/Navigation";

const SecurityGroup = () => {
    return (
      <>
        <Header/>
        <Content>
          <Navigation/>
          <SecurityGroupDetail/>
          <BoundRules/>
        </Content>
        
      </>
    );
};

export default SecurityGroup;

const Content = styled.div`
  padding: 0 5%; 
`;