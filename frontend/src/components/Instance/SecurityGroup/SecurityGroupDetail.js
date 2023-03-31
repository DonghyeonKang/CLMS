import styled from "styled-components";

const SecurityGroupDetail = () => {
    return (
        <>
            <Title>세부정보</Title>
            <DetailContent>
                <DetailGrid>보안 그룹 이름</DetailGrid>
                <DetailGrid>보안 그룹 ID</DetailGrid>
                <DetailGrid>설명(이름, 생성일자)</DetailGrid>
                <DetailGrid>소유자</DetailGrid>
                <DetailGrid>인바운드 규칙 수</DetailGrid>
                <DetailGrid>아웃바운드 규칙 수</DetailGrid>
            </DetailContent>
        </>
        
    );
};

export default SecurityGroupDetail;

const Title = styled.div`
  background-color: #fafafa;
  min-width: 900px;
  padding: 1%;
  border: 1px solid #eaeded;
  font-size: 20px;
  font-weight: 600;
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
  min-width: 300px;
  height: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: white;
`;