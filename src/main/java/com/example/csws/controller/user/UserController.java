package com.example.csws.controller.user;

import com.example.csws.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController // restful api 구성시 필요
@RequiredArgsConstructor    // final 로 선언된 필드 생성자 주입방식으로 DI 하게 해줌. DI 안 해주면 Nullporinter exception 나옴
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    // 회원 탈퇴
    @DeleteMapping()
    public void deleteUser() {

    }

    // 비밀번호 재설정
    @PutMapping()
    public void resetPassword() {

    }

    // 관리자 인증 요청 리스트 조회
    @GetMapping("/manager/verification")
    public void getManagerVerificationList() {

    }

    // 관리자 인증 요청 승인
    @PostMapping("/manager/verification")
    public void approveManagerVerification() {

    }
    // 학생 목록 조회
    @GetMapping("/student/list")
    public void getStudentList() {

    }
}
