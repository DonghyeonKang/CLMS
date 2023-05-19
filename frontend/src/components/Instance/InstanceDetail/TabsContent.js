import { Button, TextField } from "@mui/material";
import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import styled from "styled-components";
import { useRecoilState } from "recoil";
import { baseUrl } from "../../../Atoms";

//post랑 delete 기능 구현하기
const TabsContent = ({data, domainName}) => {
  const [BASEURL,] = useRecoilState(baseUrl);
  const navigate = useNavigate();
  const [list,setList] = useState('detail');
  const [inboundRules, setInboundRules] = useState();
  const [newDomain,setNewDomain] = useState('');
  const [owner,setOwner] = useState('');
  const {instanceId} = useParams();
  //인바운드 리스트 조회 instanceId 별로 조회하게 수정하기
  //post, delete 요청 기능 구현하기
  //도메인 변경
  const saveDomain = () => {
    try {
      axios.post(BASEURL + `/instances/domain`,{instanceId, newDomain}).then((response)=> console.log(response));
    } catch (error) {
      console.error(error);
    }
  };
  //도메인 삭제
  const deleteDomain = () => {
    try {
      axios.delete(BASEURL + `/instances/domain`,{instanceId}).then((response)=> console.log(response));
    } catch (error) {
      console.error(error);
    }
  };
//소유자 변경 API
  const changeOwner = () => {
    try {
      axios.patch(BASEURL + `/instances/owner`,{owner, instanceId}).then((response)=> console.log(response));
    } catch (error) {
      console.error(error);
    }
  };
  //인바운드 리스트 불러오기
  useEffect(()=>{
    try {
      axios.get(BASEURL + `/instances/inbounds/list`).then((response)=> setInboundRules(response.data.inbounds));
    } catch (error) {
      console.error(error);
    }
  },[BASEURL]);

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
          {list === 'owner' ? 
          <SelectedTab onClick={()=>setList('owner')}>인스턴스 소유자 변경</SelectedTab> : 
          <DetailTab onClick={()=>setList('owner')}>인스턴스 소유자 변경</DetailTab>}

            <DetailContent>
                {(list === 'detail') ? 
                <>
                <DetailGrid>
                  <GridTitle>플랫폼(OS)</GridTitle>
                  <GridContent>{data?.os}</GridContent>
                </DetailGrid>
                <DetailGrid>
                  <GridTitle>시작 시간</GridTitle>
                  <GridContent>{data?.created}</GridContent>
                </DetailGrid>
                <DetailGrid>
                  <GridTitle>키 페어 이름</GridTitle>
                  <GridContent>{data?.keyName}</GridContent>
                </DetailGrid>
                <DetailGrid>
                  <GridTitle>소유자</GridTitle>
                  <GridContent>123</GridContent>
                </DetailGrid>
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
                        <RulesHeader style={{minWidth:'180px'}}>
                        ID
                        </RulesHeader>
                        <RulesHeader style={{minWidth:'120px'}}>
                        프로토콜
                        </RulesHeader>
                        <RulesHeader style={{minWidth:'120px'}}>
                        포트범위
                        </RulesHeader>
                        <RulesHeader style={{minWidth:'120px'}}>
                        소스
                        </RulesHeader>
                      </tr>
                    </thead>
                    <tbody>
                      {inboundRules.map((i)=>{
                        return(<tr>
                          <RulesBody style={{minWidth:'180px'}}>{i?.id}</RulesBody>
                          <RulesBody style={{minWidth:'120px'}}>TCP</RulesBody>
                          <RulesBody style={{minWidth:'120px'}}>{i?.port}</RulesBody>
                          <RulesBody style={{minWidth:'120px'}}>0.0.0.0/0</RulesBody>
                        </tr>)
                        })}
                      
                    </tbody>
                  </Rules>
                </Box>
                : (list === 'domain') ? 
                <> 
                  <DetailGrid> 
                    <GridTitle>도메인</GridTitle>
                    <GridContent>{domainName}</GridContent>
                  </DetailGrid>
                  <InputGrid>
                  <TextField label="도메인 입력" onChange={(i)=>setNewDomain(i.target.value)} size="small" style={{marginRight:'5%'}}/>
                  <Button onClick={()=>saveDomain()} variant="outlined" style={{marginRight:'2%'}}>도메인 적용</Button>
                  <Button onClick={()=>deleteDomain()} variant="outlined" color="error">도메인 삭제</Button>
                  </InputGrid>
                </>
                :
                <>
                  <DetailGrid>
                    <GridTitle>소유자</GridTitle>
                    <GridContent>123</GridContent>
                  </DetailGrid>
                  <InputGrid>
                    <TextField label="인스턴스 소유자 변경" onChange={(i)=>setOwner(i.target.value)} size="small" style={{marginRight:'5%'}}/>
                    <Button onClick={()=>changeOwner()} variant="outlined">소유자 변경</Button>
                  </InputGrid>
                </>
                }
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
  min-width: 900px;
  margin-bottom: 5%;
  background-color: white;
`;

const DetailGrid = styled.div`
  width: 100%;
  height: 100px;
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

const InputGrid = styled(DetailGrid)`
  grid-column: 2 / 4;
  display: flex;
  align-items: center;
`;

const GridTitle = styled.div`
  margin: 3%;
  font-size: 16px;
  font-weight: 100;
`;

const GridContent = styled.div`
  margin-left: 5%;
  font-size: 20px;
`;