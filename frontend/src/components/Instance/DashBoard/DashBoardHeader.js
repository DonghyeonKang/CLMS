import { useNavigate } from "react-router-dom";
import styled from "styled-components";

const DashBoardHeader = ({userId, address}) => {
    const navigate = useNavigate();
    return(
        <ContentHeader>
            <Title>인스턴스</Title>
            <InstanceCreate onClick={() => navigate('createInstance',{state: {userId, address}})}>인스턴스 생성</InstanceCreate>
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
  padding: 6px 15px;
  height: 25px;
  background-color: #3eb5c4;
  border-radius: 20px;
  color: white;
  font-weight: 600;
  &:hover{
    background-color: #2da4b3;
  }
`