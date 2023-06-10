import { atom } from "recoil";

export const userState = atom({
  key: 'user_role',
  default: '',
});
// '': 로그인 안됨, 'ROLE_USER': 학생, 'ROLE_MANAGER': 관리자, ?: 어드민

export const baseUrl = atom({
  key: `baseurl`,
  default: 'http://localhost:8080',
});