import styled from "styled-components";
import DashBoardHeader from "../../components/Instance/DashBoard/DashBoardHeader";
import Header from "../../components/Header";
import InstanceList from "../../components/Instance/DashBoard/InstanceList";
import Navigation from "../../components/Navigation";
import { useState } from "react";
import Footer from "../../components/Footer";

const DashBoard = () => {
  const [userId, setUserId] = useState();
  const [address, setAddress] = useState();
    return (
      <>
        <Header/>
        <Content>
          <Navigation/>
          <DashBoardHeader userId={userId} address={address}/>
          <InstanceList setUserId={setUserId} setAddress={setAddress}/>
        </Content>
        <Footer/>
      </>
    );
};

export default DashBoard;

const Content = styled.div`
  padding: 0 5%;
  min-height: 80vh;
  margin-bottom: 120px;
`;

