package com.example.csws.controller.user;

import com.example.csws.entity.user.*;
import com.example.csws.service.department.DepartmentService;
import com.example.csws.service.university.UniversityService;
import com.example.csws.service.user.EmailService;
import com.example.csws.service.user.ManagerAuthoritiesService;
import com.example.csws.service.user.RegisterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
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
    private final UniversityService universityService;
    private final DepartmentService departmentService;
    private final ManagerAuthoritiesService managerAuthoritiesService;

    // 모든 학교 조회
    @GetMapping("/universities")
    public JSONObject getUniversities() {
        JSONObject obj = new JSONObject();
        obj.put("universities", universityService.findAllUniversity());
        return obj;
    }

    // universityId 로 모든 학과 조회
    @GetMapping("/departments")
    public JSONObject getDepartments(HttpServletRequest req) {
        JSONObject obj = new JSONObject();
        obj.put("departments", departmentService.findAllDepartment(Integer.parseInt(req.getParameter("universityId"))));
        return obj;
    }

    // 학생 회원가입
    @PostMapping("/student")
    public Object registerStudent(@RequestBody RegisterStudentRequest model) {
        try {
            model.setPassword(passwordEncoder.encode(model.getPassword()));  // password encoding
            // request -> userDto
            UserDto userDto = model.toUserDto();

            // setting for student ,,,,,,, 나중에 수정해야함
            userDto.setRole("ROLE_USER");

            // register
            registerService.register(userDto, "USER");
            return Map.of("result", "성공");  // 성공 리턴
        } catch (Exception e) {
            throw e;
        }
    }

    // 관리자 회원가입
    @PostMapping("/manager")
    public Object registerManager(@RequestBody RegisterManagerRequest model) {
        try {
            model.setPassword(passwordEncoder.encode(model.getPassword()));  // password encoding
            // request -> userDto
            UserDto userDto = model.toUserDto();

            // setting for student ,,,,,,, 나중에 수정해야함
            userDto.setRole("ROLE_USER");

            // register
            User user = registerService.register(userDto, "MANAGER");

            // 관리자 인증 요청 등록
            ManagerAuthorityDto approvalDto = new ManagerAuthorityDto();
            approvalDto.setStatus("waiting");
            approvalDto.setUsername(model.getUsername());
            managerAuthoritiesService.managerVerificationRequest(approvalDto, user.getId());

            return Map.of("result", "성공");  // 성공 리턴
        } catch (Exception e) {
            throw e;
        }
    }

    // 회원가입 메일 인증번호 요청
    @GetMapping("/verification")
    public void getVerificationNumber(HttpServletRequest req) throws MessagingException, UnsupportedEncodingException {
        // 인증번호 생성 및 전송
        String authNum = emailService.sendEmail(req.getParameter("email"));

        // 인증번호 저장 - 따닥 방지 필요
        emailService.saveAuthNum(req.getParameter("email"), authNum);
    }

    // 회원가입 인증번호 확인
    @PostMapping("/verification")
    public JSONObject checkVerificationNumber(@RequestBody VerificationRequest model) {
        JSONObject obj = new JSONObject();
        obj.put("success", emailService.checkAuthNum(model.getEmail(), model.getAuthNumber()));
        return obj;
    }
}
