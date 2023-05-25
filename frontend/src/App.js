import { QueryClient, QueryClientProvider } from 'react-query';
import GlobalStyle from './GlobalStyle';
import Router from './Router';
import { useRecoilState } from 'recoil';
import { useEffect } from 'react';
import { loginState, tokenState } from './Atoms';

const queryClient = new QueryClient();

function App() {
  const [, setLoginStatus] = useRecoilState(loginState);
  const [, setToken] = useRecoilState(tokenState);

  useEffect(() => {
    const jwtToken = localStorage.getItem('jwt');
    if (jwtToken) {
      setToken(jwtToken);
      setLoginStatus(true);
    }
  }, []);

  return (
    <QueryClientProvider client={queryClient}>
      <GlobalStyle/>
      <Router/>
    </QueryClientProvider>
  );
}

export default App;
