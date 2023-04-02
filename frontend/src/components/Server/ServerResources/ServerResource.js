import styled from "styled-components";
import { List, ListItemButton } from "@mui/material";

const ServerResource = ({server,studentList,navigate}) => {
    return (
        <>
        <ServerAddress>{server}</ServerAddress>
        <ServerDetail>
            <StudentList>
                <Title>학생 리스트</Title>
                <List style={{maxHeight: 300, overflow: 'auto', border:'1px solid #eaeded'}}>
                {studentList?.map((item)=>{
                        return(
                        <ListItemButton component='div' onClick={()=>navigate('/dashboard')}>
                           {item}
                        </ListItemButton>
                    )})}
                </List>
            </StudentList>
            <ResourceList>
                <Title>서버 리소스</Title>
                <Resources>
                    <Resource>
                        서버 램 사용량
                    </Resource>
                    <Resource>
                        서버 디스크 사용량
                    </Resource>
                    <Resource>
                        서버와 CSWS 연결 상태
                     </Resource>
                </Resources>
            </ResourceList>
        </ServerDetail>
        </>
    );
};

export default ServerResource;


const ServerAddress = styled.div`
    background-color: white;
    width: 95%;
    text-align: center;
    font-size: 30px;
    font-weight: 600;
    padding: 10px 0;
    background-color: #fafafa;
    border: 1px solid #eaeded;
`;

const ServerDetail = styled.div`
    background-color: white;
    width: 95%;
    display: flex;
`;

const StudentList = styled.div`
    width: 60%;
    padding: 2%;
    display: flex;
    flex-direction: column;
    border: 1px solid #eaeded;
`;

const ResourceList = styled.div`
    width: 100%;
    padding: 2%;
    display: flex;
    flex-direction: column;
    border: 1px solid #eaeded;
`;

const Resources = styled.div`
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
`;

const Resource = styled.div`
    width: 40%;
    min-width: 250px;
    padding: 5px 12px;
    margin: 30px 0;
    height: 80px;
    border: 1px solid #eaeded;
`;

const Title = styled.div`
    width: 100%;
    font-size: 24px;
    font-weight: 600;
    padding: 10px 0;
    background-color: #fafafa;
    text-align: center;
`;
