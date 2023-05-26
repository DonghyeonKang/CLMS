import { atom } from "recoil";

export const loginState = atom({
  key: 'isLoggedIn',
  default: 0,
});
// 0: 로그인 안됨, 1: 학생, 2: 관리자, 3: 어드민

export const baseUrl = atom({
  key: `baseurl`,
  default: 'http://203.255.3.23:5000',
});

export const tokenState = atom({
  key: 'tokenState',
  default: null,
});
