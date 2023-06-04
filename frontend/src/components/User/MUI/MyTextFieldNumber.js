import React, { useState, useEffect } from 'react';
import Grid from '@mui/material/Grid';
import TextField from '@mui/material/TextField';
import MyButton from "./MyButton";
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Timer from '../VerifyEmail/Timer';
import { useRecoilState } from "recoil";
import {baseUrl} from "../../../Atoms"
import axios from 'axios';

const MyTextFieldNumber = ({ onNumberValidChange, email }) => {
  const [BASEURL,] = useRecoilState(baseUrl);
  const [showEmailField, setShowEmailField] = useState(false);
  const [resetKey, setResetKey] = useState(0);
  const [textFieldValue, setTextFieldValue] = useState('');
  const [numberValid, setNumberValid] = useState(false);
  const [timerExpired, setTimerExpired] = useState(false);
  const [timerRunning, setTimerRunning] = useState(true);


  //이메일 재전송
  const handleResetTimer = () => {
    if (!numberValid) {
      setResetKey(resetKey + 1);
      setTimerExpired(false);
      window.alert("메일을 다시 전송하였습니다!");
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

// 이메일 인증 버튼 눌렀을 시
const showAlert = () => {
  if (timerExpired) {
    window.alert("인증 시간 초과!");
  } else if (textFieldValue.trim() === '') {
    window.alert("인증번호를 입력해주세요!");
  } else {
    // 이메일 인증 요청 보내기
    const verificationData = {
      authNumber: textFieldValue,
      email: email
    };

    axios.post(BASEURL+'/register/verification', verificationData)
      .then(response => {
        if (response.data) {
          window.alert("인증이 완료되었습니다!");
          setShowEmailField(true);
          setNumberValid(true);
          setTimerRunning(false); // 타이머 중지
        } else {
          window.alert("인증번호가 잘못되었습니다!");
        }
      })
      .catch(error => {
        console.error(error);
      });
  }
};

  const handleTextFieldChange = (event) => {
    setTextFieldValue(event.target.value);
  };


  useEffect(() => {
    onNumberValidChange(numberValid);
  }, [numberValid, onNumberValidChange]);

  //빈칸일 때 버튼 비활성화
  const isTextFieldEmpty = textFieldValue === '';

  return (
    <Box display="contents">
      <Grid container spacing={2}>
        <Grid item xs={9}>
          <TextField
            margin="normal"
            required
            fullWidth
            value={textFieldValue}
            onChange={handleTextFieldChange}
            disabled={showEmailField}
          />
        </Grid>
        <Grid item xs={3}>
          <MyButton onClick={showAlert} disabled={isTextFieldEmpty || numberValid}>
            인증
          </MyButton>
        </Grid>
      </Grid>
      <Grid container>
        <Grid item xs>
          <Button  onClick={handleResetTimer} disabled={numberValid} >
            인증번호 재전송
          </Button >
        </Grid>
        <Grid item>
          {timerExpired ? '00:00' : (
            <Timer key={resetKey} onTimerExpired={() => setTimerExpired(true)} timerRunning={timerRunning} />
          )}
        </Grid>
      </Grid>
    </Box>
  );
};

export default MyTextFieldNumber;
