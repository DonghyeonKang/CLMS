import React from "react";
import MyButton from "../MUI/MyButton";
import { useRecoilState } from "recoil";
import { loginState } from "../../../Atoms";

//더미데이터
const User = {
  email: 'wkdroal11@gmail.com',
  pw: '12341234'
}

const ConfirmButton = ({ email, navigate }) => {
  const [, setUserState] = useRecoilState(loginState);

  const onClickConfirmButton = () => {
    if (email === User.email) {
      setUserState(1);
      navigate('/login/findpw/ChangePw');
    } else {
      alert('존재하지 않는 이메일입니다.');
    }
  };

  return (
    <MyButton onClick={onClickConfirmButton}>비밀번호 찾기</MyButton>
  );
};

export default ConfirmButton;
