import styled from "styled-components";
import { useNavigate } from "react-router-dom";
import { useRecoilState } from "recoil";
import { loginState, tokenState } from "../Atoms";
import mainlogo from "../img/Logo.png" ;

const Header = () => {
  const [isLoggedIn, setIsLoggedIn] = useRecoilState(loginState);
  const [, setToken] = useRecoilState(tokenState);
  const navigate = useNavigate();

  const handleLogout = () => {
    setIsLoggedIn(0);
    setToken(null);
    localStorage.removeItem('jwt');
    navigate('/login');
  };

  return (
    <HeaderContent>
      <HeaderLogo src={mainlogo} onClick={() => navigate('/')}/>
      {isLoggedIn === 0 ? 
        <HeaderBtn onClick={() => navigate('/login')}>로그인</HeaderBtn> : 
        <ButtonGroup>
          <HeaderBtn onClick={() => navigate('/dashboard')}>대시보드</HeaderBtn>
          <HeaderBtn onClick={handleLogout}>로그아웃</HeaderBtn>
        </ButtonGroup>
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
  min-width: 900px;
  height: 40px;
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

const ButtonGroup = styled.div`
  display: flex;
  justify-content: space-between;
`;
