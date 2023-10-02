import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Link from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Container from '@mui/material/Container';
import MyBox from '../../components/User/MUI/MyBox';
import InputAdornment from '@mui/material/InputAdornment';
import { useRecoilState } from "recoil";
import { baseUrl, userState } from "../../Atoms"
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
  const [, setUserState] = useRecoilState(userState);
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
        if (response.data.success) {
          const accessToken = response.headers.Authorization;
          const userRole = response.data.role;
          const deptId = response.data.departmentId;
          localStorage.setItem('accessToken', accessToken);
          localStorage.setItem('userRole', userRole);
          localStorage.setItem('departmentId', deptId);
          setUserState(userRole);
          navigate('/');
        } else {
          alert('이메일 또는 비밀번호가 일치하지 않습니다.');
        }
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

  // API 요청 시 Authorization 헤더에 토큰 추가
  axios.interceptors.request.use(
    config => {
      const accessToken = localStorage.getItem('accessToken');
      if (accessToken) {
        config.headers.Authorization = `Bearer ${accessToken}`;
      }
      return config;
    },
    error => {
      return Promise.reject(error);
    }
  );

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