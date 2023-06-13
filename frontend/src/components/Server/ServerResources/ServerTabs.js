import { useNavigate } from "react-router-dom";
import styled from "styled-components";

const ServerTabs = ({serverList,server,setServer}) => {
    const navigate = useNavigate();
    
    return(
    <Header>
        <div>
            {serverList?.map((item)=>{
                return(
                server === item ? 
                <SelectedServerTab onClick={()=>setServer(item)} key={item}>{item}</SelectedServerTab> :
                <ServerTab onClick={()=>setServer(item)} key={item}>{item}</ServerTab>)
            })}
        </div>
        <CreateServer onClick={()=>navigate('/createServer')}>서버 등록</CreateServer>
    </Header>
    );
};

export default ServerTabs;

const Header = styled.div`
    width: 95%;
    display: flex;
    justify-content: space-between;
`;

const ServerTab = styled.div`
    cursor: pointer;
    display: inline-block;
    padding: 5px 15px;
    margin-right: 20px;
    &:hover{
        color: #3eb5c4;
        border-bottom: 2px solid  #3eb5c4;
  }
`;

const SelectedServerTab = styled(ServerTab)`
    border-bottom: 2px solid  #3eb5c4;
    color:  #3eb5c4;
`;

const CreateServer = styled.div`
    cursor: pointer;
    padding: 5px 15px;
    height: 25px;
    background-color: #3eb5c4;
    border-radius: 20px;
    margin-left: 20px;
    color: white;
    font-weight: 600;
    &:hover{
        background-color: #2da4b3;
    }
`;