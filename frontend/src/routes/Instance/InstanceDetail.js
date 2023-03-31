import styled from "styled-components";
import Header from "../../components/Header";
import InstanceDescription from "../../components/Instance/InstanceDetail/InstanceDescription";
import TabsContent from "../../components/Instance/InstanceDetail/TabsContent";
import Navigation from "../../components/Navigation";

const InstanceDetail = () => {
    return (
      <>
        <Header/>
        <Content>
          <Navigation/>
          <InstanceDescription/>
          <TabsContent/>
        </Content>
      </>
    );
};

export default InstanceDetail;
const Content = styled.div`
  padding: 0 5%;
`;