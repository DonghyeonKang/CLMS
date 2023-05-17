import React from "react";
import Container from '@mui/material/Container';
import MyBox from '../../components/User/MUI/MyBox';
import MyAvatar from '../../components/User/MUI/MyAvatar'; 
import MyTypography from '../../components/User/MUI/MyTypography';
import MyTextFieldID from "../../components/User/SignUpAd/MyTextFieldID";
import MyTextFieldPw from "../../components/User/SignUpAd/MyTextFieldPw";
import MyTextFieldPw2 from "../../components/User/SignUpAd/MyTextFieldPw2";
import MyTextFieldUniv from "../../components/User/SignUpAd/MyTextFieldUniv";
import MyTextFieldDept from "../../components/User/SignUpAd/MyTextFieldDept";
import MyTextFieldTel from "../../components/User/SignUpAd/MyTextFieldTel";
import MyButton from "../../components/User/SignUpAd/MyButton";

const SignUpAd = () => {
    return (
    
    <Container component="main" maxWidth="xs">
        <MyBox>
            <MyAvatar/>
            <MyTypography>관리자 회원가입</MyTypography>
            <MyTextFieldID/>
            <MyTextFieldPw/>
            <MyTextFieldPw2/>
            <MyTextFieldUniv/>
            <MyTextFieldDept/>
            <MyTextFieldTel/>
            <MyButton/>
        </MyBox>
    </Container>
    );
}

export default SignUpAd;