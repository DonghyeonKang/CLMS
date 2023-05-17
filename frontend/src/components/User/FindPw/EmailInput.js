import React from 'react';
import TextField from '@mui/material/TextField';

const EmailInput = ({ setEmail, setEmailValid }) => {
  const handleEmail = (e)=> {
    setEmail(e.target.value);
    if(e.target.value.length > 0) {
      setEmailValid(true);
    } else {
      setEmailValid(false);
    }
  };

  return (
    <TextField
      margin="normal"
      label="이메일"
      name="email"
      placeholder="이메일을 입력해주세요"
      required
      fullWidth
      autoComplete="email"
      autoFocus
      onChange={handleEmail}
    />
  );
}

export default EmailInput;
