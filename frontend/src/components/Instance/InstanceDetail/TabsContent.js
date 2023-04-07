import { Button, TextField } from "@mui/material";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";

const TabsContent = () => {
  const navigate = useNavigate();
  const [list,setList] = useState('detail'); //인스턴스 상세 탭
  const [domain,setDomain] = useState();
    return (
        <>
          {list === 'detail' ? 
          <SelectedTab onClick={()=>setList('detail')}>세부정보</SelectedTab> : 
          <DetailTab onClick={()=>setList('detail')}>세부정보</DetailTab>}
          {list === 'security' ? 
          <SelectedTab onClick={()=>setList('security')}>보안</SelectedTab> : 
          <DetailTab onClick={()=>setList('security')}>보안</DetailTab>}
          {list === 'domain' ? 
          <SelectedTab onClick={()=>setList('domain')}>도메인 적용</SelectedTab> : 
          <DetailTab onClick={()=>setList('domain')}>도메인 적용</DetailTab>}

            <DetailContent>
                {(list === 'detail') ? 
                <>
                <DetailGrid>플랫폼(OS)</DetailGrid>
                <DetailGrid>시작 시간</DetailGrid>
                <DetailGrid>키 페어 이름</DetailGrid>
                <DetailGrid>소유자</DetailGrid>
                </>
                : (list === 'security') ? 
                <Box>
                  <Stripe>
                    <Title>인바운드 규칙</Title>
                    <EditRules onClick={() => navigate('inboundRules')}>인바운드 규칙 편집</EditRules>
                  </Stripe>
                  
                  <Rules> 
                    <thead>
                      <tr>
                        <RulesHeader style={{minWidth:'180px'}}>이름</RulesHeader>
                        <RulesHeader style={{minWidth:'120px'}}>프로토콜</RulesHeader>
                        <RulesHeader style={{minWidth:'120px'}}>포트범위</RulesHeader>
                        <RulesHeader style={{minWidth:'120px'}}>소스</RulesHeader>
                      </tr>
                    </thead>
                    <tbody>
                      <tr>
                        <RulesBody style={{minWidth:'180px'}}>-</RulesBody>
                        <RulesBody style={{minWidth:'120px'}}>-</RulesBody>
                        <RulesBody style={{minWidth:'120px'}}>-</RulesBody>
                        <RulesBody style={{minWidth:'120px'}}>0.0.0.0/0</RulesBody>
                      </tr>
                    </tbody>
                  </Rules>
                </Box>
                : <DetailGrid>
                <TextField label="도메인 입력" onChange={(i)=>setDomain(i)} size="small" style={{marginRight:'10%'}}/>
                <Button onClick={()=>console.log(domain.target.value)} variant="outlined">도메인 적용</Button>
                </DetailGrid>}
            </DetailContent>
        </>
    );
};

export default TabsContent;

const DetailTab = styled.div`
  cursor: pointer;
  display: inline-block;
  padding: 5px;
  border: 1px solid #eaeded;
  background-color: #fafafa;
  &:hover{
    color: #0073bb;
  }
`;
const SelectedTab = styled(DetailTab)`
  border-bottom: 2px solid  #0073bb;
  color:  #0073bb;
`;

const DetailContent = styled.div`
  display: grid;
  grid-template-columns: repeat(3,33%);
  grid-auto-flow: row;
  gap: 0.5%;
  row-gap: 5px;
  width: 100%;
  min-width: 1150px;
  margin-bottom: 5%;
  background-color: white;
`;

const DetailGrid = styled.div`
  width: 100%;
  min-width: 300px;
  min-height: 100px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: white;
`;

const Box = styled.div`
  grid-column: 1 / 4;
  width: 95%;
  padding: 2%;
  min-width: 1150px;
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
  width: 20%;
`;