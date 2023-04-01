import Container from '@mui/material/Container';
import MyBox from '../FindPw/MyBox';

const MyContainer = ({ children }) => {
  return (
    <Container component="main" maxWidth="xs">
      <MyBox>
        {children}
      </MyBox>
    </Container>
  );
}

export default MyContainer;
