import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Link from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Container from '@mui/material/Container';
import MyBox from '../../components/User/MUI/MyBox';
import InputAdornment from '@mui/material/InputAdornment';
import { useRecoilState } from "recoil";
import { loginState } from "../../Atoms";
import VisibilityIcon from '@mui/icons-material/Visibility';
import VisibilityOffIcon from '@mui/icons-material/VisibilityOff';
import MyButton from "../../components/User/MUI/MyButton";
import MyTypography from '../../components/User/MUI/MyTypography';
import MyAvatar from '../../components/User/MUI/MyAvatar'; 
import MyTextFieldID from '../../components/User/MUI/MyTextFieldID';
import MyTextFieldPW from '../../components/User/MUI/MyTextFieldPW';



//더미데이터
const User = {
  email: 'wkdroal11@gmail.com',
  pw: '12341234'
}

const Login = () => {
  const navigate = useNavigate(); 
  //이메일,비밀번호
  const [email, setEmail] = useState('');
  const [pw, setPw] = useState('');
  //유효성 검사
  const [,setUserState] = useRecoilState(loginState);
  const [emailValid, setEmailValid] = useState(false);
  const [pwValid, setPwValid] = useState(false);
  //버튼 활성화
  const [notAllow,setNotAllow] = useState(true);
  //비밀번호 보이기,숨기기
  const [passwordType, setPasswordType] = useState({
    type: 'password',
    visible: false})
  //이메일과 비밀번호 동기적 사용
  useEffect(() =>{
    if(emailValid && pwValid){
        setNotAllow(false);
        return;
      }
      setNotAllow(true);
      },[emailValid,pwValid]);
  //
  const handleEmail = (e)=> {
    setEmail(e.target.value);
    if(e.target.value.length > 0) {
      setEmailValid(true);
    } else {
      setEmailValid(false);
    }
  };

  const handlePw = (e)=> {
    setPw(e.target.value);
    if(e.target.value.length > 0) {
      setPwValid(true);
    } else {
      setPwValid(false);
    }
  }


  const onClickConfirmButton =() =>{
    if(email === User.email && pw=== User.pw){
      alert('로그인 성공!.');
      setUserState(1);
      navigate('/');
    } else {
      alert('이메일 또는 비밀번호가 일치하지 않습니다.');
    }
  }

  const onCheckEnter = (e) => {
    if(e.key === 'Enter' && notAllow===false ) {
      onClickConfirmButton()
    }
  
  }
  const handlePasswordType = e => {
    setPasswordType(prevState => {
      return {
        type: prevState.visible ? "password" : "text",
        visible: !prevState.visible
      };
    });
  };

  const PasswordIcon =  passwordType.visible ? VisibilityIcon : VisibilityOffIcon;

  return (
    <Container component="main" maxWidth="xs">
      <MyBox>
        <MyAvatar/>
        <MyTypography>
          CSWS
        </MyTypography>
        <MyTextFieldID
          value={email}
          onChange={handleEmail}
          onKeyPress={onCheckEnter}
        />
        <MyTextFieldPW
          type={passwordType.type}
          value={pw}
          onChange={handlePw}
          onKeyPress={onCheckEnter}
          InputProps={{
            endAdornment: (
              <InputAdornment position="end">
                <PasswordIcon
                onClick={handlePasswordType}
                />
              </InputAdornment>
            )
            }}/>
        <MyButton 
          disabled={notAllow}
          onClick={onClickConfirmButton}>
          로그인
        </MyButton>
        <Grid container>
          <Grid item xs>
            <Link href="login/FindPw">비밀번호 재설정</Link>
          </Grid>
          <Grid item>
            <Link href="login/Signup">회원가입</Link>
          </Grid>
        </Grid>
      </MyBox>
    </Container>
  );
}

export default Login;