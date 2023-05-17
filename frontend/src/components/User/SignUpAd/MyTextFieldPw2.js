import TextField from '@mui/material/TextField';

const MyTextFieldPW2 = (props) => {
  return (
    <TextField
    margin="normal"
    label="비밀번호 확인"
    type="password"
    name="passwordConfirm"
    required
    fullWidth
    autoComplete="current-password"
    {...props}

    />
  );
}

export default MyTextFieldPW2;
