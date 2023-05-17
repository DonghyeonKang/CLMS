import { useState } from "react";
import styled from "styled-components";

//인스턴스 상태 API 만들어지면 추가하기
const InstanceDescription = ({data, domainName}) => {
  const [IOption, setIOption] = useState(false);
    return (
        <>
            <DetailHeader>
              <Title>인스턴스 요약</Title>
              <InstanceState>
                <State onClick={()=>{setIOption((prev)=>!prev)}}>{IOption ? '인스턴스 상태 ▲' : '인스턴스 상태 ▼'}</State>
                {IOption ? (
                  <div style={{position:'absolute', backgroundColor:'white'}}>
                    <SetState onClick={()=>{setIOption((prev)=>!prev)}}>인스턴스 시작</SetState>
                    <SetState onClick={()=>{setIOption((prev)=>!prev)}}>인스턴스 중지</SetState>
                    <SetState onClick={()=>{setIOption((prev)=>!prev)}}>인스턴스 재부팅</SetState>
                    <SetState onClick={()=>{setIOption((prev)=>!prev)}}>인스턴스 종료</SetState>
                  </div>) : (<></>)}
              </InstanceState>
            </DetailHeader>
            
            <DescriptionContent>
                <DescriptionGrid>
                  <GridTitle>인스턴스 ID</GridTitle>
                  <GridContent>{data?.instanceId}</GridContent>
                </DescriptionGrid>
                <DescriptionGrid>
                  <GridTitle>퍼블릭 IPv4 주소</GridTitle>
                  <GridContent>{data?.address}</GridContent>
                </DescriptionGrid>
                <DescriptionGrid>
                  <GridTitle>인스턴스 상태</GridTitle>
                  <GridContent>{data?.status}</GridContent>
                </DescriptionGrid>
                <DescriptionGrid>
                  <GridTitle>퍼블릭 IPv4 DNS</GridTitle>
                  <GridContent>{domainName}</GridContent>
                </DescriptionGrid>
                <DescriptionGrid>
                  <GridTitle>자동 할당 IP 주소(퍼블릭 IP)</GridTitle>
                  <GridContent>{data?.address}</GridContent>
                </DescriptionGrid>
            </DescriptionContent>
        </>
        
    );
};

export default InstanceDescription;

const DetailHeader = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  background-color: #fafafa;
  border: 1px solid #eaeded;
  width: 100%;
  min-width: 900px;
`;

const Title = styled.div`
  width: 50%;
  min-width: 500px;
  padding: 1%;
  font-size: 20px;
  font-weight: 600;
`;

const InstanceState = styled.div`
  cursor: pointer;
  border: 0.5px solid #879596;
  margin-right: 2%;
  min-width: 190px;
`;

const State = styled.div`
  padding: 4px 15px;
  font-weight: 600;
  background-color: white;
  &:hover{
    background-color: #fafafa;
    color: black;
  }
`;

const SetState = styled.div`
  padding: 2px 12px;
  border: 0.5px solid #879596;
  font-weight: 600;
  min-width: 150px;
  &:hover{
    background-color: #fafafa;
    border: 2px solid #879596;
    color: black;
  }
`;

const DescriptionContent = styled.div`
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

const DescriptionGrid = styled.div`
  width: 100%;
  height: 100px;
  display: flex;
  flex-direction: column;
  background-color: white;
`

const GridTitle = styled.div`
  margin: 3%;
  font-size: 16px;
  font-weight: 100;
`;

const GridContent = styled.div`
  margin-left: 5%;
  font-size: 20px;
`;