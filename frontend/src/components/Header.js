import styled from "styled-components";
import { useNavigate } from "react-router-dom";
import mainlogo from "../img/Logo.png" ;

const Header = () => {
  const userRole = localStorage.getItem('userRole');
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('userRole');
    localStorage.removeItem('departmentId');
    navigate('/login');
  };

  return (
    <HeaderContent>
      <HeaderLogo src={mainlogo} onClick={() => navigate('/')}/>
      <div style={{marginRight:'50px'}}>
      {(userRole === null) ? 
        (<HeaderBtn onClick={() => navigate('/login')}>로그인</HeaderBtn>
        ) : (userRole === 'ROLE_MANAGER') ?
        (<ButtonGroup>
          <HeaderBtn onClick={() => navigate('/serverResources')}>서버 리소스</HeaderBtn>
          <HeaderBtn onClick={() => navigate('/dashboard')}>대시보드</HeaderBtn>
          <HeaderBtn onClick={() => handleLogout()}>로그아웃</HeaderBtn>
        </ButtonGroup>) : (<ButtonGroup>
          <HeaderBtn onClick={() => navigate('/dashboard')}>대시보드</HeaderBtn>
          <HeaderBtn onClick={() => handleLogout()}>로그아웃</HeaderBtn>
        </ButtonGroup>)
      }
      </div>
    </HeaderContent>
  );
};

export default Header;

const HeaderContent = styled.header`
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  min-width: 900px;
  height: 40px;
  padding: 2%;
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
  font-weight: 600;
  color: white;
  background-color: #3eb5c4;
  border: 2px #3eb5c4 solid;
  border-radius: 20px;
  margin-right: 20px;
  &:hover{
    background-color: #2da4b3;
  }
`;

const ButtonGroup = styled.div`
  display: flex;
  justify-content: space-between;
`;
