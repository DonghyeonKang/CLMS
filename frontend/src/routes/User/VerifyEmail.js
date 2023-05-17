import React, { useState} from 'react';
import MyTextFieldEmail from '../../components/User/MUI/MyTextFieldEmail';
import MyButton from "../../components/User/MUI/MyButton";
import MyTypography from '../../components/User/MUI/MyTypography';
import Container from '@mui/material/Container';
import MyBox from '../../components/User/MUI/MyBox';
import MyAvatar from '../../components/User/MUI/MyAvatar';
import Grid from '@mui/material/Grid';
import Link from '@mui/material/Link';
import Timer from '../../components/User/VerifyEmail/Timer';


const VerifyEmail = () => {
  const [resetKey, setResetKey] = useState(0);

  const handleResetTimer = () => {
    setResetKey(resetKey + 1);
  };

  return (
    <Container component="main" maxWidth="xs">
      <MyBox>
        <MyAvatar/>
        <MyTypography>CSWS</MyTypography>
        <MyTextFieldEmail/>
        <Grid container>
          <Grid item xs>
            <Link onClick={handleResetTimer}>인증번호 재전송</Link>
          </Grid>
          <Grid item>
            <Timer key={resetKey} onReset={handleResetTimer} />
          </Grid>
        </Grid>
        <MyButton>인증번호 확인</MyButton>
      </MyBox>
    </Container>
  );
}

export default VerifyEmail;
