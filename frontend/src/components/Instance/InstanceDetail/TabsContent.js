import { Button, TextField } from "@mui/material";
import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import styled from "styled-components";
import { useRecoilState } from "recoil";
import { baseUrl } from "../../../Atoms";

const TabsContent = ({data, domainName}) => {
  const [BASEURL,] = useRecoilState(baseUrl);
  const navigate = useNavigate();
  const [list,setList] = useState('detail');
  const [inboundRules, setInboundRules] = useState([]);
  const [newDomain,setNewDomain] = useState('');
  const [owner,setOwner] = useState('');
  const {instanceId} = useParams();
  const [domainValidate,setDomainValidate] = useState(false);
  const [ownerValidate,setOwnerValidate] = useState(false);

  const domainValidation = (str) => {
    const reg = /([a-z0-9\w]+\.*)+[a-z0-9]{2,4}/gi;
    return reg.test(str);
  };
  const ownerValidation = (str) => {
    const reg = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
    return reg.test(str);
  };
 

  const loadInboundRules = () => {
      try {
        axios.get(BASEURL + `/instances/inbounds/list?instanceId=${instanceId}`).then((response)=> setInboundRules(response.data.inbounds));
    } catch (error) {
      console.error(error);
    }
  };
  
  const domainHandler = (event) => {
    const value = event.target.value;
    setNewDomain(value);
    if(domainValidation(value)){
      //정규표현식 만족하면
      setDomainValidate(true);
      if(value.length <= 8 || value.length >= 250){
        //글자수 만족 못하면
        setDomainValidate(false);
      }
    }else {
      setDomainValidate(false);
    }
  };

  const ownerHandler = (event) => {
    const value = event.target.value;
    setOwner(value);
    if(ownerValidation(value)){
      //정규표현식 만족하면
      setOwnerValidate(true);
      if(value.length < 8 || value.length > 253){
        //글자수 만족 못하면
        setOwnerValidate(false);
      }
    }else {
      setOwnerValidate(false);
    }
  };

  //요청 보낸 다음 기능 추가하기
  //도메인 변경
  const saveDomain = () => {
      if(domainValidate){
        try {
        axios.post(BASEURL + `/instances/domain`,{
          data : {instanceId, domainName: newDomain}
        }).then((response)=> console.log(response));
      } catch (error) {
        console.error(error);
      }
    } else {
      alert('도메인 주소를 입력해 주세요.')
    }
  };
  //도메인 삭제
  const deleteDomain = () => {
      try {
        axios.delete(BASEURL + `/instances/domain`,{
        data: {instanceId}
      }).then((response)=> console.log(response));
    } catch (error) {
      console.error(error);
    }
  };
//소유자 변경 API
  const changeOwner = () => {
      if(ownerValidate){
        try {
        axios.patch(BASEURL + `/instances/owner`,{
          data: {username: owner, instanceId}
          }).then((response)=> console.log(response));
      } catch (error) {
        console.error(error);
      }
    } else {
      alert('사용자 이름(이메일)을 입력해 주세요.');
    }
  };
  //인바운드 규칙 리스트 불러오기
  useEffect(()=>{
    loadInboundRules();
  },[BASEURL, instanceId]);

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
          {list === 'owner' ? /* 관리자 아니면 안보이게 */
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
                  <GridContent>{data?.userId}</GridContent>
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
                    {(domainName === '' || domainName === undefined) ? (
                      <InputGrid>
                        <TextField label="도메인 입력" onChange={(i)=>domainHandler(i)} error={!domainValidate} size="small" style={{marginRight:'5%'}}/>
                        <Button onClick={()=>saveDomain()} variant="outlined" style={{marginRight:'2%'}}>도메인 적용</Button>
                        <Button onClick={()=>deleteDomain()} variant="outlined" disabled color="error">도메인 삭제</Button>
                    </InputGrid>
                    ) : (
                    <InputGrid>
                      <TextField label="도메인 입력" onChange={(i)=>domainHandler(i)} error={!domainValidate} disabled size="small" style={{marginRight:'5%'}}/>
                      <Button onClick={()=>saveDomain()} variant="outlined" disabled style={{marginRight:'2%'}}>도메인 적용</Button>
                      <Button onClick={()=>deleteDomain()} variant="outlined" color="error">도메인 삭제</Button>
                    </InputGrid>
                      ) }
                </>
                :
                <>
                  <DetailGrid>
                    <GridTitle>소유자</GridTitle>
                    <GridContent>{data?.userId}</GridContent>
                  </DetailGrid>
                  <InputGrid>
                    <TextField label="인스턴스 소유자 변경" onChange={(i)=>ownerHandler(i)} error={!ownerValidate} size="small" style={{marginRight:'5%'}}/>
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
  margin-right: 20px;
  &:hover{
    color: #3eb5c4;
    border-bottom: 2px solid  #3eb5c4;
  }
`;
const SelectedTab = styled(DetailTab)`
  border-bottom: 2px solid  #3eb5c4;
  color:  #3eb5c4;
`;

const DetailContent = styled.div`
  display: grid;
  grid-template-columns: repeat(3,33%);
  grid-auto-flow: row;
  width: 100%;
  min-width: 900px;
  padding-left: 1%;
  margin: 3% 0;
  background-color: #ffffff;
  border: 2px solid #f2f3f3;
  border-radius: 20px;
`;

const DetailGrid = styled.div`
  width: 100%;
  height: 100px;
`;

const Box = styled.div`
  grid-column: 1 / 4;
  width: 95%;
  padding: 2%;
  min-width: 1150px;
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
  border: 2px solid #3eb5c4;
  padding: 4px 15px;
  font-weight: 600;
  border-radius: 20px;
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