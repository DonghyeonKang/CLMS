import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import MyTextFieldID from '../../components/User/MUI/MyTextFieldID';
import MyButton from "../../components/User/MUI/MyButton";
import MyTypography from '../../components/User/MUI/MyTypography';
import Container from '@mui/material/Container';
import MyBox from '../../components/User/MUI/MyBox';
import MyAvatar from '../../components/User/MUI/MyAvatar';
import { useRecoilState } from "recoil";
import { loginState } from "../../Atoms";
import Grid from '@mui/material/Grid';
import MyTextFieldNumber from '../../components/User/MUI/MyTextFieldNumber';
<<<<<<< HEAD
import Header from'../../components/Header';
=======
>>>>>>> parent of 1e11e7d (Revert "Revert "UPDATE Header"")

// 더미데이터
const User = {
  email: 'wkdroal11@gmail.com',
  pw: '12341234'
};

const FindPw = () => {
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [, setUserState] = useRecoilState(loginState);
  const [emailValid, setEmailValid] = useState(false);
  const [notAllow, setNotAllow] = useState(true);
  const [showEmailField, setShowEmailField] = useState(false);
  const [NumberValid, setNumberValid] = useState(false); 
  const [sendButtonDisabled, setSendButtonDisabled] = useState(false);

  // 이메일 인증 번호 실시간 조회
  useEffect(() => {
    if (NumberValid) {
      setNotAllow(false);
    } else {
      setNotAllow(true);
    }
  }, [NumberValid]);


  // 이메일 입력 상태 확인
  const handleEmail = (e) => {
    setEmail(e.target.value);
    if (e.target.value.length > 0) { // 이메일에 한글자로 입력을 했을 시
      setEmailValid(true);
    } else { // 이메일에 아무것도 입력 안 했을 시
      setEmailValid(false);
    }
  };

  // 이메일 인증이 완료되면 다음 비밀번호 변경 페이지로 이동
  const onClickConfirmButton = () => {
    navigate('/login/findpw/ChangePw');
  };

  // 전송 버튼을 클릭했을 때 
  const handleButtonClick = () => {
    if (email === User.email) { // 이메일이 서버에 존재할 때
      setShowEmailField(true); 
      setSendButtonDisabled(true);
    } else { // 존재하지 않을 때
      alert('존재하지 않는 이메일입니다.');
    }
  };

  //Enter가 버튼 클릭 기능으로 구현되도록 설정
  const onCheckEnter = (e) => {
    if (e.key === 'Enter' && !notAllow) {
      onClickConfirmButton();
    }
  };


  //페이지 UI 설정
  return (
    <Container component="main" maxWidth="xs">
      <MyBox>
        <MyAvatar />
        <MyTypography>CSWS</MyTypography>
        <Grid container spacing={2}>
          <Grid item xs={9}>
            <MyTextFieldID
              value={email}
              onChange={handleEmail}
              onKeyPress={onCheckEnter}
              disabled={showEmailField}
            />
          </Grid>
          <Grid item xs={3}>
            <MyButton onClick={handleButtonClick} disabled={!emailValid || sendButtonDisabled}>전송</MyButton>
          </Grid>
        </Grid>
        {showEmailField && <MyTextFieldNumber onNumberValidChange={setNumberValid} />}
        <MyButton disabled={notAllow} onClick={onClickConfirmButton}>
          비밀번호 변경
        </MyButton>
      </MyBox>
<<<<<<< HEAD
    </Container></>
=======
    </Container>
>>>>>>> parent of 1e11e7d (Revert "Revert "UPDATE Header"")

    
  );
};

export default FindPw;