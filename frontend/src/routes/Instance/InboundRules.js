import styled from "styled-components";
import Header from "../../components/Header";
import Navigation from "../../components/Navigation";
import EditInboundRules from "../../components/Instance/InboundRules/EditInboundRules";

const InboundRules = () => {
  
    return (
      <>
        <Header/>
        <Content>
          <Navigation/>
          <EditInboundRules/>
        </Content>
      </>
    );
};
export default InboundRules;

const Content = styled.div`
  padding: 0 5%;
  width: 90%;
`;