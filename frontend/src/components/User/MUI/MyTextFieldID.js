import TextField from '@mui/material/TextField';

const MyTextFieldID = (props) => {
  return (
    <TextField
      label="이메일"
      placeholder="example@company.com"
      margin="normal"
      required
      fullWidth
      {...props}
    />
  );
}

export default MyTextFieldID;
