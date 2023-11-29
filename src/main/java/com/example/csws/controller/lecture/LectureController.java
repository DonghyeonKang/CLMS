package com.example.csws.controller.lecture;

import com.example.csws.config.auth.PrincipalDetails;
import com.example.csws.entity.lecture.*;
import com.example.csws.service.lecture.LectureService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lecture")
public class LectureController {
    private final LectureService lectureService;

    @GetMapping("/my")
    public JSONObject getMyLectureList(Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        List<LectureDto> result = lectureService.getMyLectureList(principalDetails.getUser().getId());
        JSONObject obj = new JSONObject();
        obj.put("lectureList", result);
        return obj;
    }

    @GetMapping
    public JSONObject getLectureList(@RequestParam(value = "departmentId") Long departmentId) {
        List<LectureDto> result = lectureService.getLectureList(departmentId);
        JSONObject obj = new JSONObject();
        obj.put("lectureList", result);
        return obj;
    }

    @GetMapping("/detail")
    public LectureDto getLectureDetail(@RequestParam(value = "id") Long lectureId) {
        LectureDto result = lectureService.getLectureDetail(lectureId);
        return result;
    }

    @PostMapping
    public JSONObject createLecture(@RequestBody CreateLectureRequest createLectureRequest) {
        LectureDto lectureDto = lectureService.createLecture(createLectureRequest);

        // 저장된 도메인 명 응답
        JSONObject obj = new JSONObject();
        obj.put("lecture", lectureDto);
        return obj;
    }

    @DeleteMapping
    public void deleteLecture(@RequestParam(value = "id") Long lectureId) {
        lectureService.deleteLecture(lectureId);
    }

    // 수강 신청된 학생 목록
    @GetMapping("/student")
    public JSONObject getStudentList(@RequestParam(value = "id") Long lectureId) {
        List<StudentDto> result = lectureService.getStudentList(lectureId);

        JSONObject obj = new JSONObject();
        obj.put("studentList", result);
        return obj;
    }

    // 수강 신청한 학생 목록
    @GetMapping("/student/register")
    public JSONObject getStudentListForRegister(@RequestParam(value = "id") Long lectureId) {
        List<StudentDto> result = lectureService.getStudentListForRegister(lectureId);

        JSONObject obj = new JSONObject();
        obj.put("studentList", result);
        return obj;
    }

    // 수강 신청
    @PostMapping("/student")
    public void signUpClass(@RequestBody ClassRegistrationDto classRegistrationDto , Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        classRegistrationDto.setUserId(principalDetails.getUser().getId());
        lectureService.signUpClass(classRegistrationDto);
    }

    // 수강 신청 승인
    @PostMapping("/student/registration")
    public JSONObject approveRegistration(@RequestBody ClassRegistrationDto classRegistrationDto, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        classRegistrationDto.setUserId(principalDetails.getUser().getId());
        List<StudentDto> result = lectureService.approveRegistration(classRegistrationDto);

        JSONObject obj = new JSONObject();
        obj.put("studentList", result);
        return obj;
    }

    // 수강 신청 거절
    @PostMapping("/student/refusal")
    public JSONObject refuseRegistration(@RequestBody ClassRegistrationDto classRegistrationDto, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        classRegistrationDto.setUserId(principalDetails.getUser().getId());
        List<StudentDto> result = lectureService.refuseRegistration(classRegistrationDto);

        JSONObject obj = new JSONObject();
        obj.put("studentList", result);
        return obj;
    }
}
