import styled from "styled-components";
import logo from '../img/Logo.png'
import icons from '../img/icons.png';

const Footer = () => {
    return (
        <FooterContent>
            <Logo src={logo}/>
            <Contents>
                <Content>
                    <Title>CSWS</Title>
                    <List>Email : donghyeon009@gmail.com</List>
                    <List>
                        <a href='https://github.com/DonghyeonKang/CSWS' target="_blank" style={{color:'#b2bec3', textDecoration:'none'}}>Github : https://github.com/DonghyeonKang/CSWS</a>
                    </List>
                </Content>
                <Content>
                    <Title>Creators</Title>
                    <List>강동현</List>
                    <List>이건호</List>
                    <List>임채준</List>
                    <List>장우혁</List>
                    <List>정수진</List>
                </Content>
                <Content>
                    <Login>콘솔에 로그인</Login>
                    <Icons src={icons}/>
                </Content>
            </Contents>
        </FooterContent>
    );
}

export default Footer;

const FooterContent = styled.footer`
    width: 100%;
    height: 200px;
    background-color: #232f3e;
    padding: 2%;
    display: flex;
    align-items: center;
    font-family: Malgun Gothic,sans-serif;
`;

const Logo = styled.img`
    height: 130px;
    margin-left: 20px;
`;

const Contents = styled.div`
    display: flex;
    width: 100%;
`;

const Content = styled.div`
    margin-left: 100px;
`;

const Title = styled.div`
    margin-bottom: 20px;
    font-size: 32px;
    font-weight: 600;
    color: white;
`;

const List = styled.div`
    color: #b2bec3;
`;

const Login = styled.div`
  cursor: pointer;
  padding: 6px 35px;
  height: 25px;
  background-color: #3eb5c4;
  text-align: center;
  border-radius: 20px;
  margin-top: 50px;
  color: white;
  font-weight: 600;
  &:hover{
    background-color: #2da4b3;
  }
`;

const Icons = styled.img`
    width: 250px;
`;