import TextField from '@mui/material/TextField';

const MyTextFieldID = (props) => {
  return (
    <TextField
      label="학과 전화번호"
      margin="normal"
      required
      fullWidth
      {...props}
    />
  );
}

export default MyTextFieldID;
