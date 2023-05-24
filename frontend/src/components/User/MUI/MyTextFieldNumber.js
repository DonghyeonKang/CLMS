import React, { useState, useEffect } from 'react';
import Grid from '@mui/material/Grid';
import TextField from '@mui/material/TextField';
import MyButton from "./MyButton";
import Box from '@mui/material/Box';
import Link from '@mui/material/Link';
import Timer from '../VerifyEmail/Timer';

const MyTextFieldNumber = ({ onNumberValidChange }) => {
  const number = 123456;
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

    }
  };

  //이메일 인증 버튼 눌렀을 시
  const showAlert = () => {
    if (timerExpired) {
      window.alert("인증 시간 초과!");
    } else if (number === parseInt(textFieldValue)) {
      window.alert("인증 완료 되었습니다!");
      setShowEmailField(true);
      setNumberValid(true);
      setTimerRunning(false); // 타이머 중지
    } else {
      window.alert("번호가 틀렸습니다!");
      setNumberValid(false);
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
          <Link onClick={handleResetTimer}>
            인증번호 재전송
          </Link>
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
