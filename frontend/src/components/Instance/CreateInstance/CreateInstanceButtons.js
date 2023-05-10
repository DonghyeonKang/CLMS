import axios from "axios";
import styled from "styled-components";
import { useNavigate } from "react-router-dom";

//인스턴스 생성 버튼 누르면 API 요청 하게 함수 수정하기
const CreateInstanceButtons = ({data}) => {
  const navigate = useNavigate();
    //인스턴스 생성
  const createInstance = () => {
    try{
      axios.post('http://203.255.3.23:5000/instances/creation',data).then((response)=>console.log(response));
    } catch (error) {
      console.error(error);
    };
  }
    return (
    <Btn>
        <Cancel onClick={() => navigate("/dashboard")}>취소</Cancel>
        <Create onClick={() => createInstance()}>인스턴스 생성</Create> 
    </Btn>);
  
};
export default CreateInstanceButtons;

const Btn = styled.div`
  width: 100vw;
  height: 5vh;
  display: flex;
  justify-content: end;
  align-items: flex-end;
  margin-top: -8%;
`;

const Create = styled.div`
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

const Cancel = styled.div`
  cursor: pointer;
  padding: 4px 15px;
  &:hover{
    background-color: white;
    color: black;
  }
`;