import TextField from '@mui/material/TextField';

const MyTextFieldPW = (props) => {
  return (
    <TextField
      margin="normal"
      label="비밀번호"
      type="password"
      name="password"
      placeholder="특수문자 제외, 영문, 숫자 포함 8자 이상"
      required
      fullWidth
      autoComplete="current-password"
      {...props}

    />
  );
}

export default MyTextFieldPW;
