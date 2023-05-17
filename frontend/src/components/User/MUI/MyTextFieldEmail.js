import React from 'react';
import TextField from '@mui/material/TextField';

const MyTextFieldEmail = (props) => {
  const handleKeyPress = (event) => {
    const keyCode = event.keyCode || event.which;
    const keyValue = String.fromCharCode(keyCode);
    const regex = /^[0-9]+$/;

    if (!regex.test(keyValue)) {
      event.preventDefault();
    }
  };

  const handleInput = (event) => {
    const inputValue = event.target.value.replace(/[^0-9]/g, '');
    event.target.value = inputValue.slice(0, 6);
  };

  return (
    <TextField
      label="인증번호"
      margin="normal"
      required
      fullWidth
      {...props}
      InputProps={{
        inputMode: 'numeric',
        pattern: '[0-9]*',
      }}
      onKeyPress={handleKeyPress}
      onInput={handleInput}
    />
  );
}

export default MyTextFieldEmail;
