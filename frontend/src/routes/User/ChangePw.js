import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Container from '@mui/material/Container';
import MyTextFieldPW from '../../components/User/MUI/MyTextFieldPW';
import MyTextFieldPW2 from '../../components/User/MUI/MyTextFieldPW2';
import MyButton from "../../components/User/MUI/MyButton";
import MyTypography from '../../components/User/MUI/MyTypography';
import MyBox from '../../components/User/MUI/MyBox';
import MyAvatar from '../../components/User/MUI/MyAvatar'; 
import styled from 'styled-components'


// 비밀번호 양식 표시 TEXT스타일
const StyledText = styled.div`
color:red;
font-size:0.8rem;
`;

const ChangePw = () => {
    const navigate = useNavigate(); 
    const [pw, setPw] = useState(''); 
    const [pw2, setPw2] = useState('');
    const [pwValid, setPwValid] = useState(false);
    const [pw2Valid, setPw2Valid] = useState(false);
    const [notAllow,setNotAllow] = useState(true);


    // 비밀번호가 비밀번호 양식에 맞는지 확인
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

    // 비밀번호 확인이 비밀번호 양식에 맞는지 확인
    const handlePw2 = (e)=> {
      setPw2(e.target.value);
      const regex =pw;
      if(regex===e.target.value) {
        setPw2Valid(true);
      } else {
        setPw2Valid(false);
      }
    }
    
    // 비밀번호 변경 버튼 눌렀을 때
    const onClickConfirmButton =() =>{
      if(pw!==pw2){ // 비밀번호와 비밀번호가 일치하지 않을 때
        alert('비밀번호를 다시 확인해주세요.');
      }
      else {
        alert('비밀번호 변경완료!'); // 비밀번호와 비밀번호가 일치할 때
        navigate('/login');// login 페이지로 이동
      }
    }

    //Enter가 버튼 클릭 기능으로 구현되도록 설정
    const onCheckEnter = (e) => {
      if(e.key === 'Enter' && notAllow===false ) {
        onClickConfirmButton()
      }
    }

    //버튼 활성화 실시간으로
    useEffect(() =>{
      if(pwValid && pw2Valid){
        setNotAllow(false);
        return;
      }
      setNotAllow(true);
    },[pwValid,pw2Valid]);
    
    //페이지 UI 설정
    return (
      <Container component="main" maxWidth="xs">
      <MyBox>
        <MyAvatar/>
        <MyTypography>CSWS</MyTypography>
        <MyTextFieldPW
          value={pw}
          onChange={handlePw}
          onKeyPress={onCheckEnter}
        />
        <div>
          {
            !pwValid && pw.length > 0 && (
              <StyledText>특문자 제외 영문자 숫자로 8자 이상 20자 미만으로 입력해주세요</StyledText>
            )}
        </div>
        <MyTextFieldPW2
          value={pw2}
          onChange={handlePw2}
          onKeyPress={onCheckEnter}
        />
        <div>
          {
            !pw2Valid && pw2.length > 0 && (
              <StyledText>비밀번호가 일치하지 않습니다</StyledText>
            )}
        </div>        
        <MyButton
          disabled={notAllow}
          onClick={onClickConfirmButton}>
          비밀번호 변경
        </MyButton>
      </MyBox>
    </Container>
    );
    
};

export default ChangePw;