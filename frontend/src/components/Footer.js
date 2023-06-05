import styled from "styled-components";
import logo from '../img/Logo.png'

const Footer = () => {
    return (
        <FooterContent>
            <Logo src={logo}/>
            <Contents>
                <Content>
                    <Title>CSWS</Title>
                    <div>Email : donghyeon009@gmail.com</div>
                    <a href='https://github.com/DonghyeonKang/CSWS' target="_blank" style={{color:'white'}}>Github : https://github.com/DonghyeonKang/CSWS</a>
                </Content>
                <Content>
                    <Title>Creators</Title>
                    <div>강동현</div>
                    <div>이건호</div>
                    <div>임채준</div>
                    <div>장우혁</div>
                    <div>정수진</div>
                </Content>
            </Contents>
        </FooterContent>
    );
}

export default Footer;

const FooterContent = styled.footer`
    height: 200px;
    background-color: #232f3e;
    padding: 2%;
    display: flex;
    align-items: center;
    color: white;
`;

const Logo = styled.img`
    height: 180px;
`;

const Contents = styled.div`
    display: flex;
    width: 100%;
    justify-content: flex-end;
    padding-right: 100px;
`;

const Content = styled.div`
    margin-left: 150px;
`;

const Title = styled.div`
    margin-bottom: 30px;
    font-size: 32px;
    font-weight: 600;
`;