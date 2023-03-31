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
          {list === 'networking' ? 
          <SelectedTab onClick={()=>setList('networking')}>네트워킹</SelectedTab> : 
          <DetailTab onClick={()=>setList('networking')}>네트워킹</DetailTab>}
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
                <>
                <DetailGrid>소유자 ID</DetailGrid>
                <DetailGrid>시작 시간</DetailGrid>
                <DetailId onClick={() => navigate(`${Date.now()}`)}>보안 그룹 ID</DetailId>
                <DetailGrid>인바운드 규칙 목록</DetailGrid>
                <DetailGrid>아웃바운드 규칙 목록</DetailGrid>
                </>
                : (list === 'networking') ? 
                <>
                <DetailGrid>퍼블릭 IPv4 주소</DetailGrid>
                <DetailGrid>프라이빗 IPv4 주소</DetailGrid>
                <DetailGrid>퍼블릭 IPv4 DNS</DetailGrid>
                <DetailGrid>프라이빗 IP DNS 이름</DetailGrid>
                <DetailGrid>네트워크 인터페이스</DetailGrid>
                </>
                : <DetailGrid>
                <DomainInput placeholder="도메인 입력" onChange={(i)=>setDomain(i)}/>
                <button onClick={()=>console.log(domain.target.value)}>도메인 적용</button>
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
  height: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: white;
`;

const DetailId = styled(DetailGrid)`
  cursor: pointer;
  color: #0073bb;
  &:hover{
    text-decoration: underline;
  }
`;

const DomainInput = styled.input`
  margin-right: 10%;
`;