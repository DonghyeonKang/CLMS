package com.example.csws.controller.user;

import com.example.csws.config.auth.PrincipalDetails;
import com.example.csws.entity.department.DepartmentResponse;
import com.example.csws.entity.university.UniversityDto;
import com.example.csws.entity.user.ManagerAuthority;
import com.example.csws.entity.user.ManagerAuthorityDto;
import com.example.csws.entity.user.ResetPasswordRequest;
import com.example.csws.service.department.DepartmentService;
import com.example.csws.service.university.UniversityService;
import com.example.csws.service.user.ManagerAuthoritiesService;
import com.example.csws.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController // restful api 구성시 필요
@RequiredArgsConstructor    // final 로 선언된 필드 생성자 주입방식으로 DI 하게 해줌. DI 안 해주면 NullPointer exception 나옴
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final UniversityService universityService;
    private final ManagerAuthoritiesService managerAuthoritiesService;
    private final DepartmentService departmentService;


    // 회원 탈퇴
    @DeleteMapping()
    public void deleteUser(HttpServletRequest req) {
        userService.deleteUser(req.getParameter("username"));
    }

    // 비밀번호 재설정
    @PutMapping()
    public void resetPassword(@RequestBody ResetPasswordRequest req) {
        userService.resetPassword(req.getUsername(), req.getPassword());
    }

    // 관리자 인증 요청 리스트 조회
    @GetMapping("/manager/verification")
    public List<ManagerAuthority> getManagerVerificationList() {
        System.out.println("controller");
        return managerAuthoritiesService.getManagerVerificationList();
    }

    // 관리자 인증 요청하기
    @PostMapping("/manager/verification")
    public void managerVerificationRequest(@RequestBody ManagerAuthorityDto approvalDto, Authentication authentication) {   // dto 에 있는 값을 모두 안 받아도 될까? 그럼 이거도 dto 로 받게 하고 넘기면 될 텐데
        approvalDto.setStatus("waiting");   // 초기값 waiting으로 설정
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();   // authentication 에서 userId를 가져옴
        managerAuthoritiesService.managerVerificationRequest(approvalDto, Long.valueOf(principalDetails.getId()));    // long 으로 형변환 후 매개변수로 넣기
    }

    // 관리자 인증 요청 승인
    @PostMapping("/manager/verification/accept") 
    public void approveManagerVerification(@RequestBody Map<String, String> req) {
        managerAuthoritiesService.approveManagerVerification(req.get("username"));
    }

    // 관리자 인증 요청 거절
    @PostMapping("/manager/verification/deny")
    public void denyManagerVerification(@RequestBody Map<String, String> req) {
        managerAuthoritiesService.denyManagerVerification(req.get("username"));
    }

    // 관리자 인증 요청 삭제
    @PostMapping("/manager/verification/delete")
    public void deleteManagerVerification(@RequestBody Map<String, String> req) {
        managerAuthoritiesService.deleteManagerVerification(req.get("username"));
    }

    // 학생 목록 조회
    @GetMapping("/student/list")
    public List<String> getStudentList(HttpServletRequest req) {
        return userService.getStudentList(Integer.parseInt(req.getParameter("serverId")));  // int 로 형변환해서 조회
    }

    // 모든 학교 조회
    @GetMapping("/university")
    public List<UniversityDto> getUniversities() {
        return universityService.findAllUniversity();
    }

    // universityId 로 모든 학과 조회
    @GetMapping("/departments")
    public List<DepartmentResponse> getDepartments(HttpServletRequest req) {
        return departmentService.findAllDepartment(Integer.parseInt(req.getParameter("universityId")));
    }
}
