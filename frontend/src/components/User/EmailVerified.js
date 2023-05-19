import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import MyTextFieldID from '../../components/User/MUI/MyTextFieldID';
import MyButton from "../../components/User/MUI/MyButton";
import MyBox from '../../components/User/MUI/MyBox';
import Grid from '@mui/material/Grid';
import MyTextFieldNumber from './MyTextFieldNumber';

const User = {
  email: 'wkdroal11@gmail.com',
  pw: '12341234'
};

const EmailVerified = () => {
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [emailValid, setEmailValid] = useState(false);
  const [notAllow, setNotAllow] = useState(true);
  const [showEmailField, setShowEmailField] = useState(false);
  const [NumberValid, setNumberValid] = useState(false); 

  useEffect(() => {
    if (NumberValid) {
      setNotAllow(false);
    } else {
      setNotAllow(true);
    }
  }, [NumberValid]);

  const handleEmail = (e) => {
    setEmail(e.target.value);
    if (e.target.value.length > 0) {
      setEmailValid(true);
    } else {
      setEmailValid(false);
    }
  };

  const onClickConfirmButton = () => {
    navigate('/login/findpw/ChangePw');
  };

  const handleButtonClick = () => {
    if (email === User.email) {
      setShowEmailField(true);
    } else {
      alert('존재하지 않는 이메일입니다.');
    }
  };

  const onCheckEnter = (e) => {
    if (e.key === 'Enter' && !notAllow) {
      onClickConfirmButton();
    }
  };

  return (
      <MyBox>
        <Grid container spacing={2}>
          <Grid item xs={9}>
            <MyTextFieldID
              value={email}
              onChange={handleEmail}
              onKeyPress={onCheckEnter}
              disabled={showEmailField}
            />
          </Grid>
          <Grid item xs={3}>
            <MyButton onClick={handleButtonClick}>전송</MyButton>
          </Grid>
        </Grid>
        {showEmailField && <MyTextFieldNumber onNumberValidChange={setNumberValid} />}
      </MyBox>
  );
};

export default EmailVerified;