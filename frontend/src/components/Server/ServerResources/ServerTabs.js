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
    background-color: #fafafa;
    border: 1px solid #eaeded;
    padding: 5px 10px;
    &:hover{
    color: #0073bb;
  }
`;

const SelectedServerTab = styled(ServerTab)`
    border-bottom: 2px solid  #0073bb;
    color:  #0073bb;
`;

const CreateServer = styled.div`
    cursor: pointer;
    padding: 4px 15px;
    height: 25px;
    background-color: #ec7211;
    margin-left: 20px;
    color: white;
    font-weight: 600;
    &:hover{
        background-color: #eb5f07;
    }
`;