import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Container from '@mui/material/Container';
import Grid from '@mui/material/Grid';
import styled from 'styled-components'
import MyTextFieldID from '../../components/User/MUI/MyTextFieldID';
import MyTextFieldPW from '../../components/User/MUI/MyTextFieldPW';
import MyTextFieldPW2 from '../../components/User/MUI/MyTextFieldPW2';
import MyButton from "../../components/User/MUI/MyButton";
import MyTypography from '../../components/User/MUI/MyTypography';
import MyBox from '../../components/User/MUI/MyBox';
import MyAvatar from '../../components/User/MUI/MyAvatar'; 
import MyTextFieldNumber from '../../components/User/MUI/MyTextFieldNumber';
import MyTextFieldUnivStu from '../../components/User/MUI/MyTextFieldUnivStu';
import MyTextFieldDeptStu from '../../components/User/MUI/MyTextFieldDeptStu';
import { useRecoilState } from "recoil";
import {baseUrl} from "../../Atoms"
import axios from 'axios';


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
  const [BASEURL,] = useRecoilState(baseUrl);
  const [NumberValid, setNumberValid] = useState(false);
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [pw, setPw] = useState('');
  const [pw2, setPw2] = useState('');
  const [emailValid, setEmailValid] = useState(false);
  const [pwValid, setPwValid] = useState(false);
  const [pw2Valid, setPw2Valid] = useState(false);
  const [notAllow,setNotAllow] = useState(true);
  const [sendButtonDisabled, setSendButtonDisabled] = useState(false);
  const [showEmailField, setShowEmailField] = useState(false);
  const [UnivStu, setUnivStu] = useState('');
  const [DeptStu, setDeptStu] = useState('');

  const handleEmail = (e) => {
    setEmail(e.target.value);
    const regex =
      /^(([^<>()\].,;:\s@"]+(\.[^<>()\].,;:\s@"]+)*)|(".+"))@(([^<>()[\].,;:\s@"]+\.)+[^<>()[\].,;:\s@"]{2,})$/i;
    if (regex.test(e.target.value)) {
      setEmailValid(true);
    } else {
      setEmailValid(false);
    }
  }

  const handlePw = (e) => {
    setPw(e.target.value);
    const regex =
      /^[a-zA-z0-9]{8,20}$/;
    if(regex.test(e.target.value)) {
      setPwValid(true);
    } else {
      setPwValid(false);
    }
  }

  const handlePw2 = (e) => {
    setPw2(e.target.value);
    const regex = pw;
    if(regex === e.target.value) {
      setPw2Valid(true);
    } else {
      setPw2Valid(false);
    }
  }


  const onClickConfirmButton = () => {
    if(pw !== pw2){
      alert('비밀번호를 다시 확인해주세요.');
    } else {
      axios.post(BASEURL+'/register/student', { username: email, password: pw, universityId: UnivStu , departmentId: DeptStu,  })
        .then(response => {
          navigate('/login');
        })
        .catch(error => {
          console.error(error);
        });
    }
  }

  const onCheckEnter = (e) => {
    if(e.key === 'Enter' && !notAllow) {
      onClickConfirmButton()
    }
  }

  useEffect(() => {
    if(NumberValid && pwValid && pw2Valid && UnivStu && DeptStu ){
      setNotAllow(false);
    } else {
      setNotAllow(true);
    }
  }, [NumberValid, pwValid, pw2Valid, UnivStu , DeptStu]);

    const handleButtonClick = () => {
      if (email === User.email) {
        alert('존재하는 메일입니다.');
      } else {
        setShowEmailField(true);
        setSendButtonDisabled(true);
        axios.get(BASEURL+'/register/verification',
                    { params: { email: email } },
                    { withCredentials: true })
          .then(response => {
          })
          .catch(error => {
            console.error(error);
          });
      }
    };
  
  return (
    <Container component="main" maxWidth="xs">
      <MyBox>
        <MyAvatar/>
        <MyTypography>CSWS</MyTypography>
        <Grid container spacing={2}>
          <Grid item xs={9}>
            <MyTextFieldID
              value={email}
              onChange={handleEmail}
              onKeyPress={onCheckEnter}
              disabled={showEmailField}
            />
            <div>
              {
                !emailValid && email.length > 0 && (
                  <StyledText>올바른 이메일 형식을 입력해주세요</StyledText>
                )
              }
            </div>
          </Grid>
          <Grid item xs={3}>
            <MyButton onClick={handleButtonClick} disabled={!emailValid || sendButtonDisabled}>
              전송
            </MyButton>
          </Grid>
        </Grid>
        {showEmailField && <MyTextFieldNumber email={email} onNumberValidChange={setNumberValid}/>}
        <MyTextFieldPW
          value={pw}
          onChange={handlePw}
          onKeyPress={onCheckEnter}
        />
        <div>
          {
            !pwValid && pw.length > 0 && (
              <StyledText>특문자 제외 영문자 숫자로 8자 이상 20자 미만으로 입력해주세요</StyledText>
            )
          }
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
            )
          }
        </div>
        <MyTextFieldUnivStu setUnivStu={setUnivStu}/>
        <MyTextFieldDeptStu universityId={UnivStu} setDeptStu={setDeptStu}/>  
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
