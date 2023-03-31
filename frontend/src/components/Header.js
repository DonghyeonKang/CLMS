import styled from "styled-components";
import { useNavigate } from "react-router-dom";
import { useRecoilValue } from "recoil";
import { loginState } from "../Atoms";
import mainlogo from "../img/Logo.png" ;

//학생: 대시보드, 관리자: 서버 리소스, 어드민: 관리자 인증

const Header = () => {
  const isLoggedIn = useRecoilValue(loginState);
  const navigate = useNavigate();
  return (
    <HeaderContent>
      <HeaderLogo src={mainlogo} onClick={() => navigate('/')}/>
      {(isLoggedIn === 0) ? <HeaderBtn onClick={() => navigate('/login')}>로그인</HeaderBtn> : 
      <HeaderBtn onClick={() => navigate('/dashboard')}>대시보드</HeaderBtn>
      }
    </HeaderContent>
  );
};

export default Header;

const HeaderContent = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 96%;
  height: 35px;
  padding: 1% 2%;
  background-color: #232f3e;
  color: white;
`;

const HeaderLogo = styled.img`
  cursor: pointer;
  width: 100px;
  height: 50px;
`;

const HeaderBtn = styled.button`
  cursor: pointer;
  width: 100px;
  height: 35px;
  font-size: 16px;
  color: white;
  background-color: #ec7211;
  border: 2px #ec7211 solid;
  border-radius: 5vh;
  margin-right: 20px;
  &:hover{
    background-color: #eb5f07;
  }
`;