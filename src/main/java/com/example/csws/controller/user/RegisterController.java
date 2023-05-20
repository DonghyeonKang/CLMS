package com.example.csws.controller.user;

import com.example.csws.entity.user.*;
import com.example.csws.service.user.EmailService;
import com.example.csws.service.user.RegisterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@Slf4j  // logging framework in lombok
@RestController // restful api 구성시 필요
@RequiredArgsConstructor    // final 로 선언된 필드 생성자 주입방식으로 DI 하게 해줌. DI 안 해주면 Nullporinter exception 나옴
@RequestMapping("/register")
public class RegisterController {
    private final RegisterService registerService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    // 학생 회원가입
    @PostMapping("/student")
    public Object registerStudent(@ModelAttribute RegisterStudentRequest model) {
        try {
            model.setPassword(passwordEncoder.encode(model.getPassword()));  // password encoding
            // request -> userDto
            UserDto userDto = model.toUserDto();

            // setting for student ,,,,,,, 나중에 수정해야함
            userDto.setRole("ROLE_USER");

            // register
            registerService.register(userDto);
            return Map.of("result", "성공");  // 성공 리턴
        } catch (Exception e) {
            throw e;
        }
    }

    // 관리자 회원가입
    @PostMapping("/manager")
    public Object registerManager(@ModelAttribute RegisterManagerRequest model) {
        try {
            model.setPassword(passwordEncoder.encode(model.getPassword()));  // password encoding
            // request -> userDto
            UserDto userDto = model.toUserDto();

            // setting for student ,,,,,,, 나중에 수정해야함
            userDto.setRole("ROLE_USER");

            // register
            registerService.register(userDto);
            return Map.of("result", "성공");  // 성공 리턴
        } catch (Exception e) {
            throw e;
        }
    }

    // 회원가입 메일 인증번호 요청
    @GetMapping("/verification")
    public void getVerificationNumber(HttpServletRequest req) throws MessagingException, UnsupportedEncodingException {
        String authNum = emailService.sendEmail(req.getParameter("email"));
        HttpSession session = req.getSession();
        session.setAttribute("authNum" + req.getParameter("email"), authNum);
        session.setMaxInactiveInterval(1800);   // session timeout 30분
    }

    // 회원가입 인증번호 확인
    @PostMapping("/verification")
    public boolean checkVerificationNumber(@RequestBody VerificationRequest model, HttpServletRequest req) {
        HttpSession session = req.getSession();
        return registerService.checkVerificationNumber(session.getAttribute("authNum" + model.getEmail()).toString(), model.getAuthNumber());
    }
}
