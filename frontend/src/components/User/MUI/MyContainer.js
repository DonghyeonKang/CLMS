import Container from '@mui/material/Container';
import MyBox from './MyBox';

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
