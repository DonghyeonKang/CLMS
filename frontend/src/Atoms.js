import { atom } from "recoil";

export const loginState = atom({
    key: 'isLoggedIn',
    default: 0,
});
// 0 : 로그인 안됨, 1 : 학생, 2 : 관리자, 3 : 어드민