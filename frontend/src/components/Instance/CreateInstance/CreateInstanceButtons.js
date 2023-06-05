import axios from "axios";
import styled from "styled-components";
import { useNavigate } from "react-router-dom";
import { useRecoilState } from "recoil";
import { baseUrl, tokenState } from "../../../Atoms";

//인스턴스 생성 버튼 누르면 API 요청 하게 함수 수정하기
const CreateInstanceButtons = ({data, validate}) => {
  const navigate = useNavigate();
  const [BASEURL,] = useRecoilState(baseUrl);
  const [token,] = useRecoilState(tokenState);
    //인스턴스 생성
  const createInstance = () => {
    if(validate && token){
      try{
        axios.post(BASEURL + '/instances/creation', data, {
          headers: {
            'Authorization': `Bearer ${token}`
          }
        }).then((response)=>console.log(response));
      } catch (error) {
        console.error(error);
      };
      navigate("/dashboard");
    } else {
      alert('입력이 올바르지 않습니다.');
    }
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
  height: 25px;
  display: flex;
  justify-content: end;
  align-items: flex-end;
  margin-top: -8%;
`;

const Create = styled.div`
  cursor: pointer;
  padding: 6px 15px;
  height: 25px;
  background-color: #3eb5c4;
  border-radius: 20px;
  margin-left: 20px;
  color: white;
  font-weight: 600;
  border: none;
  &:hover{
    background-color: #2da4b3;
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