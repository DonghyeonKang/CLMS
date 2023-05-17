import { useNavigate } from "react-router-dom";
import styled from "styled-components";

const DashBoardHeader = () => {
    const navigate = useNavigate();
    return(
        <ContentHeader>
            <Title>인스턴스</Title>
            <InstanceCreate onClick={() => navigate('createInstance')}>인스턴스 생성</InstanceCreate>
          </ContentHeader>
    );
};
export default DashBoardHeader;

const ContentHeader = styled.div`
  display: flex;
  justify-content: space-between;
  min-width: 380px;
  height: 25px;
  margin: 2% 0;
`;
const Title = styled.div`
  font-size: 20px;
  font-weight: 600;
`;

const InstanceCreate = styled.div`
  cursor: pointer;
  padding: 4px 15px;
  height: 25px;
  background-color: #ec7211;
  color: white;
  font-weight: 600;
  &:hover{
    background-color: #eb5f07;
  }
`