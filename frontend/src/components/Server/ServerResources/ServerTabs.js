import styled from "styled-components";

const ServerTabs = ({serverList,server,setServer}) => {
    return(
    <div>
        {serverList?.map((item)=>{
            return(
            server === item ? 
            <SelectedServerTab onClick={()=>setServer(item)}>{item}</SelectedServerTab> :
            <ServerTab onClick={()=>setServer(item)}>{item}</ServerTab>)
        })}
    </div>);
};

export default ServerTabs;

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