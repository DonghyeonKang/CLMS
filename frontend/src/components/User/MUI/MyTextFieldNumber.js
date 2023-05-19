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

  const handleResetTimer = () => {
    setResetKey(resetKey + 1);
  };

  const showAlert = () => {
    if (number === parseInt(textFieldValue)) {
      window.alert("인증 완료 되었습니다!");
      setShowEmailField(true);
      setNumberValid(true);
    } else {
      window.alert("번호가 틀렸습니다!");
      setNumberValid(false);
    }
  };

  const handleTextFieldChange = (event) => {
    setTextFieldValue(event.target.value);
  };

  // Call the parent component's callback to notify the change in number validity
  useEffect(() => {
    onNumberValidChange(numberValid);
  }, [numberValid, onNumberValidChange]);

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
          <MyButton onClick={showAlert}>인증</MyButton>
        </Grid>
      </Grid>
      <Grid container>
        <Grid item xs>
          <Link onClick={handleResetTimer}>인증번호 재전송</Link>
        </Grid>
        <Grid item>
          <Timer key={resetKey} onReset={handleResetTimer} />
        </Grid>
      </Grid>
    </Box>
  );
};

export default MyTextFieldNumber;
