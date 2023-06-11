import React, { useEffect, useState } from "react";
import Container from '@mui/material/Container';
import MyBox from '../../components/User/MUI/MyBox';
import MyAvatar from '../../components/User/MUI/MyAvatar'; 
import MyTypography from '../../components/User/MUI/MyTypography';
import MyTextFieldID from "../../components/User/SignUpAd/MyTextFieldID";
import MyTextFieldPW from '../../components/User/MUI/MyTextFieldPW';
import MyTextFieldPW2 from '../../components/User/MUI/MyTextFieldPW2';
import MyTextFieldTel from "../../components/User/SignUpAd/MyTextFieldTel";
import MyButton from "../../components/User/SignUpAd/MyButton";
import axios from 'axios';
import Grid from '@mui/material/Grid';
import MyTextFieldNumber from '../../components/User/MUI/MyTextFieldNumber';
import {baseUrl} from "../../Atoms"
import styled from 'styled-components'
import { useNavigate } from "react-router-dom";
import { useRecoilState } from "recoil";
import MyTextFieldUnivStu from '../../components/User/MUI/MyTextFieldUnivStu';
import MyTextFieldDeptStu from '../../components/User/MUI/MyTextFieldDeptStu';
import Header from'../../components/Header';

const StyledText = styled.div`
color:red;
font-size:0.8rem;
`;

const User = {
    email: 'wkdroal11@gmail.com',
    pw: 'hyuk0229'
  }

  
const SignUpAd = () => {
    const [BASEURL,] = useRecoilState(baseUrl);
    const [NumberValid, setNumberValid] = useState(false);
    const navigate = useNavigate();
    const [email, setEmail] = useState('');
    const [pw, setPw] = useState('');
    const [pw2, setPw2] = useState('');
    const [emailValid, setEmailValid] = useState(false);
    const [pwValid, setPwValid] = useState(false);
    const [pw2Valid, setPw2Valid] = useState(false);
    const [TelValid, setTelValid] = useState(false);
    const [notAllow,setNotAllow] = useState(true);
    const [sendButtonDisabled, setSendButtonDisabled] = useState(false);
    const [showEmailField, setShowEmailField] = useState(false);
    const [UnivStu, setUnivStu] = useState('');
    const [DeptStu, setDeptStu] = useState('');
    const [Tel, setTel] = useState('');

    //
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
      if(pw!==pw2){
        alert('비밀번호를 다시 확인해주세요.');
      }
      else {
        axios.post(BASEURL+'/register/manager', { username: email, password: pw, universityId: UnivStu , departmentId: DeptStu, phone: Tel})
        .then(response => {
          navigate('/login');
        })
        .catch(error => {
          console.error(error);
        });
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
      if(NumberValid && pwValid && pw2Valid && UnivStu && DeptStu && TelValid ){
        setNotAllow(false);
        return;
      }
      setNotAllow(true);
    },[NumberValid,pwValid,pw2Valid,UnivStu,DeptStu,TelValid]);

    //
    const handleButtonClick = () => {
      if (email === User.email) {
        alert('존재하는 메일입니다.');
      } else {
        setShowEmailField(true);
        setSendButtonDisabled(true);
        axios.get(BASEURL+'/register/verification', { params: { email: email } })
          .then(response => {
          })
          .catch(error => {
            console.error(error);
          });
      }
    };

    const aa = () => {
      console.log(Tel)
    }

    const handleTel = (e) => {
      setTel(e.target.value);
      if (e.target.value.length > 0) { // 전화번호 한글자로 입력을 했을 시
          setTelValid(true);
        } else { // 전화번호 아무것도 입력 안 했을 시
          setTelValid(false);
        }
      };

      
    return (
    <><Header/>
    <Container component="main" maxWidth="xs">
        <MyBox>
            <MyAvatar/>
            <MyTypography>관리자 회원가입</MyTypography>
            <Grid container spacing={2}>
          <Grid item xs={9}>
            <MyTextFieldID
              value={email}
              onChange={handleEmail}
              onKeyPress={onCheckEnter}
              disabled={showEmailField}/>
            <div>
              {
                !emailValid && email.length > 0 && (
                  <StyledText>올바른 이메일 형식을 입력해주세요</StyledText>
                )}
            </div></Grid>
        <Grid item xs={3}>
          <MyButton onClick={handleButtonClick} disabled={!emailValid || sendButtonDisabled} >
            전송
          </MyButton>
          </Grid></Grid>
          {showEmailField && <MyTextFieldNumber email={email} onNumberValidChange={setNumberValid}/>}
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
            <MyTextFieldUnivStu setUnivStu={setUnivStu}/>
            <MyTextFieldDeptStu universityId={UnivStu} setDeptStu={setDeptStu}/>  
            <MyTextFieldTel value={Tel}  onChange={handleTel}/>
            <MyButton disabled={notAllow} OnClick={onClickConfirmButton}/>
        </MyBox>
    </Container></>
    );
}

export default SignUpAd;