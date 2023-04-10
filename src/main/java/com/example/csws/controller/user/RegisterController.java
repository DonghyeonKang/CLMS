package com.example.csws.controller.user;

import com.example.csws.entity.user.RegisterRequest;
import com.example.csws.service.user.RegisterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Slf4j  // logging framework in lombok
@RestController // restful api 구성시 필요
@RequiredArgsConstructor    // final 로 선언된 필드 생성자 주입방식으로 DI 하게 해줌. DI 안 해주면 Nullporinter exception 나옴
@RequestMapping("/register")
public class RegisterController {
    private final RegisterService registerService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    public Object register(@ModelAttribute RegisterRequest request) throws IOException {
        try {
            System.out.println("Controller start");
            System.out.println(request.getUsername());
            System.out.println(request.getPassword());
            request.setPassword(passwordEncoder.encode(request.getPassword()));  // password encoding
            registerService.register(request);
            return Map.of("result", "성공");  // 성공 리턴
        } catch (Exception e) {
            throw e;
        }
    }

    // 학생 회원가입
    @PostMapping("/student")
    public void registerStudent() {

    }

    // 관리자 회원가입
    @PostMapping("/manager")
    public void registerManager() {

    }

    // 회원가입 메일 인증번호 요청
    @GetMapping("/verification")
    public void getVerificationNumber() {

    }

    // 회원가입 인증번호 확인
    @PostMapping("/verification")
    public void checkVerificationNumber() {

    }
}
