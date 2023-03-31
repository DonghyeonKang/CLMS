import { useNavigate } from "react-router-dom";
import styled from "styled-components";

//인바운드 규칙 객체로 받아와서 map으로 테이블에 뿌리기
const BoundRules = () => {
  const navigate = useNavigate();
    return (
        <>
            <Tab>
              <SelectedTab>인바운드 규칙</SelectedTab>
            </Tab>
            <Box>
              <Stripe>
                <Title>인바운드 규칙</Title>
                <EditRules onClick={() => navigate('inboundRules')}>인바운드 규칙 편집</EditRules>
              </Stripe>
              
              <Rules> 
                <thead>
                  <tr>
                    <RulesHeader style={{minWidth:'60px'}}>이름</RulesHeader>
                    <RulesHeader style={{minWidth:'160px'}}>보안 그룹 규칙 ID</RulesHeader>
                    <RulesHeader style={{minWidth:'80px'}}>IP 버전</RulesHeader>
                    <RulesHeader style={{minWidth:'60px'}}>유형</RulesHeader>
                    <RulesHeader style={{minWidth:'120px'}}>프로토콜</RulesHeader>
                    <RulesHeader style={{minWidth:'120px'}}>포트범위</RulesHeader>
                    <RulesHeader style={{minWidth:'60px'}}>소스</RulesHeader>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <RulesBody style={{minWidth:'60px'}}>-</RulesBody>
                    <RulesBody style={{minWidth:'160px'}}>-</RulesBody>
                    <RulesBody style={{minWidth:'80px'}}>-</RulesBody>
                    <RulesBody style={{minWidth:'60px'}}>-</RulesBody>
                    <RulesBody style={{minWidth:'120px'}}>-</RulesBody>
                    <RulesBody style={{minWidth:'120px'}}>-</RulesBody>
                    <RulesBody style={{minWidth:'60px'}}>-</RulesBody>
                  </tr>
                </tbody>
              </Rules>
            </Box>
        </>
    );
};

export default BoundRules;

const Tab = styled.div`
`;
const SelectedTab = styled.div`
  color: #0073bb;
  cursor: default;
  display: inline-block;
  padding: 5px;
  border: 1px solid #eaeded;
  background-color: #fafafa;
  border-bottom: 2px solid #0073bb;
`;

const Box = styled.div`
  border: 1px solid #eaeded;
  padding: 1%;
  min-width: 900px;
  background-color: white;
`;
const Stripe = styled.div`
  display: flex;
  justify-content: space-between;
  margin-bottom: 2%;
`;
const Title = styled.div`
  font-size: 20px;
  font-weight: 600;
`;
const EditRules = styled.div`
  cursor: pointer;
  border: 0.5px solid #879596;
  padding: 2px 12px;
  font-weight: 600;
  background-color: white;
  &:hover{
    background-color: #fafafa;
    color: black;
  }
`;
const Rules = styled.table`
  width: 100%;
  border-spacing: 0;
`;
const RulesHeader = styled.th`
  background-color: #fafafa;
  text-align: left;
  padding: 3px 0;
  padding-left: 10px;
  border: 1px solid #eaeded;
`;
const RulesBody = styled.td`
  text-align: left;
  padding: 5px 0;
  padding-left: 10px;
`;