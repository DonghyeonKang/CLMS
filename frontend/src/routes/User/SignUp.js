import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Container from '@mui/material/Container';
import styled from 'styled-components'
import MyTextFieldID from '../../components/User/MUI/MyTextFieldID';
import MyTextFieldPW from '../../components/User/MUI/MyTextFieldPW';
import MyTextFieldPW2 from '../../components/User/MUI/MyTextFieldPW2';
import MyButton from "../../components/User/MUI/MyButton";
import MyTypography from '../../components/User/MUI/MyTypography';
import MyBox from '../../components/User/MUI/MyBox';
import MyAvatar from '../../components/User/MUI/MyAvatar'; 

const StyledText = styled.div`
color:red;
font-size:0.8rem;
`;

//더미데이터
const User = {
  email: 'wkdroal11@gmail.com',
  pw: 'hyuk0229'
}

const SignUp = () => {
    const navigate = useNavigate();
    //이메일, 비밀번호, 비밀번호 확인
    const [email, setEmail] = useState('');
    const [pw, setPw] = useState('');
    const [pw2, setPw2] = useState('');
    //유효성 검사
    const [emailValid, setEmailValid] = useState(false);
    const [pwValid, setPwValid] = useState(false);
    const [pw2Valid, setPw2Valid] = useState(false);
    const [notAllow,setNotAllow] = useState(true);
    //이메일 오류메세지
    const handleEmail = (e)=> {
      setEmail(e.target.value);
      const regex =
        /^(([^<>()\].,;:\s@"]+(\.[^<>()\].,;:\s@"]+)*)|(".+"))@(([^<>()[\].,;:\s@"]+\.)+[^<>()[\].,;:\s@"]{2,})$/i;
      if (regex.test(e.target.value)) {
        setEmailValid(true);
      } else {
        setEmailValid(false);
      }
    }
    //비밀번호 오류메세지
    const handlePw = (e)=> {
      setPw(e.target.value);
      const regex =
        /^[a-zA-z0-9]{8,20}$/;
      if(regex.test(e.target.value)) {
        setPwValid(true);
      } else {
        setPwValid(false);
      }
    }
    //비밀번호 확인 오류메세지
    const handlePw2 = (e)=> {
      setPw2(e.target.value);
      const regex =pw;
      if(regex===e.target.value) {
        setPw2Valid(true);
      } else {
        setPw2Valid(false);
      }
    }
    //회원가입 버튼 눌렀을 시 메세지
    const onClickConfirmButton =() =>{
      if(email === User.email){
        alert('이미 등록된 이메일입니다.');
      }
      else if(pw!==pw2){
        alert('비밀번호를 다시 확인해주세요.');
      }
      else {
        navigate('/login/VerifyEmail');
      }
    }
    //Enter로 버튼 클릭 가능하게
    const onCheckEnter = (e) => {
      if(e.key === 'Enter' && notAllow===false ) {
        onClickConfirmButton()
      }
    }
    //버튼 활성화 실시간으로
    useEffect(() =>{
      if(emailValid && pwValid && pw2Valid){
        setNotAllow(false);
        return;
      }
      setNotAllow(true);
    },[emailValid,pwValid,pw2Valid]);
    
    return (
      <Container component="main" maxWidth="xs">
      <MyBox>
        <MyAvatar/>
        <MyTypography>CSWS</MyTypography>
        <MyTextFieldID
          value={email}
          onChange={handleEmail}
          onKeyPress={onCheckEnter}/>
        <div>
          {
            !emailValid && email.length > 0 && (
              <StyledText>올바른 이메일 형식을 입력해주세요</StyledText>
            )}
        </div>
        <MyTextFieldPW
          value={pw}
          onChange={handlePw}
          onKeyPress={onCheckEnter}/>
        <div>
          {
            !pwValid && pw.length > 0 && (
              <StyledText >특문자 제외 영문자 숫자로 8자 이상 20자 미만으로 입력해주세요</StyledText>
            )}
        </div>        
        <MyTextFieldPW2
          value={pw2}
          onChange={handlePw2}
          onKeyPress={onCheckEnter}/>
        <div>
          {
            !pw2Valid && pw2.length > 0 && (
              <StyledText>비밀번호가 일치하지 않습니다</StyledText>
            )}
        </div>        
        <MyButton
          disabled={notAllow}
          onClick={onClickConfirmButton}
        >
          가입하기
        </MyButton>
      </MyBox>
    </Container>
    );
};

export default SignUp;