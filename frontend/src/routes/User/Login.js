import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Link from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Container from '@mui/material/Container';
import MyBox from '../../components/User/MUI/MyBox';
import InputAdornment from '@mui/material/InputAdornment';
import { useRecoilState } from "recoil";
import { baseUrl } from "../../Atoms"
import { loginState } from "../../Atoms";
import VisibilityIcon from '@mui/icons-material/Visibility';
import VisibilityOffIcon from '@mui/icons-material/VisibilityOff';
import MyButton from "../../components/User/MUI/MyButton";
import MyTypography from '../../components/User/MUI/MyTypography';
import MyAvatar from '../../components/User/MUI/MyAvatar';
import MyTextFieldID from '../../components/User/MUI/MyTextFieldID';
import MyTextFieldPW from '../../components/User/MUI/MyTextFieldPW';
import axios from 'axios';
import Header from'../../components/Header';

const Login = () => {
  const [loginStatus, setLoginStatus] = useRecoilState(loginState);
  const [BASEURL,] = useRecoilState(baseUrl);
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [pw, setPw] = useState('');
  const [emailValid, setEmailValid] = useState(false);
  const [pwValid, setPwValid] = useState(false);
  const [notAllow, setNotAllow] = useState(true);
  const [passwordType, setPasswordType] = useState({
    type: 'password',
    visible: false
  })

  useEffect(() => {
    if (emailValid && pwValid) {
      setNotAllow(false);
    } else {
      setNotAllow(true);
    }
  }, [emailValid, pwValid]);

  const handleEmail = (e) => {
    setEmail(e.target.value);
    if (e.target.value.length > 0) {
      setEmailValid(true);
    } else {
      setEmailValid(false);
    }
  };

  const handlePw = (e) => {
    setPw(e.target.value);
    if (e.target.value.length > 0) {
      setPwValid(true);
    } else {
      setPwValid(false);
    }
  }

  const onClickConfirmButton = () => {
    axios.post(BASEURL + '/login', { username: email, password: pw }, {withCredentials: true})
      .then(response => {
        const { accessToken } = response.data;
        // API 요청하는 콜마다 헤더에 accessToken 담아 보내도록 설정
        axios.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;
        // 토큰을 로컬 스토리지에 저장
        localStorage.setItem('jwt', accessToken);
        setLoginStatus(true);
        navigate('/');
      })
      .catch(error => {
        alert('로그인 실패: ' + error.message);
      });
  };
  

  const onCheckEnter = (e) => {
    if (e.key === 'Enter' && !notAllow) {
      onClickConfirmButton();
    }
  }

  const handlePasswordType = () => {
    setPasswordType(prevState => {
      return {
        type: prevState.visible ? "password" : "text",
        visible: !prevState.visible
      };
    });
  };

  const PasswordIcon = passwordType.visible ? VisibilityIcon : VisibilityOffIcon;

  return (
    <><Header/>
    <Container component="main" maxWidth="xs">
      <MyBox>
        <MyAvatar />
        <MyTypography>
          CSWS
        </MyTypography>
        <MyTextFieldID value={email} onChange={handleEmail} onKeyPress={onCheckEnter} />
        <MyTextFieldPW
          type={passwordType.type}
          value={pw}
          onChange={handlePw}
          onKeyPress={onCheckEnter}
          InputProps={{
            endAdornment: (
              <InputAdornment position="end">
                <PasswordIcon onClick={handlePasswordType} />
              </InputAdornment>
            )
          }} />
        <MyButton
          disabled={notAllow}
          onClick={onClickConfirmButton}>
          로그인
        </MyButton>
        <Grid container>
          <Grid item xs>
            <Link sx={{ fontSize: '1rem' }} href="/login/findpw">비밀번호 찾기</Link>
          </Grid>
          <Grid item xs>
            <Link sx={{ fontSize: '1rem' }} href="/login/Signup">회원가입(학생)</Link>
          </Grid>
          <Grid item>
            <Link sx={{ fontSize: '1rem' }} href="/login/SignupAd">회원가입(관리자)</Link>
          </Grid>
        </Grid>
      </MyBox>
    </Container></>
  );
}

export default Login;