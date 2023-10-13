package com.example.csws.service.lecture;

import com.example.csws.entity.lecture.*;
import com.example.csws.entity.user.User;

import java.util.List;

public interface LectureService {

    public void createLecture(CreateLectureRequest createLectureRequest);
    public List<LectureDto> getLectureList(Long departmentId);
    public List<LectureDto> getMyLectureList(Long userId);
    public LectureDto getLectureDetail(Long lectureId);
    public void deleteLecture(Long lectureId);
    public List<StudentDto> getStudentList(Long lectureId);
    public void signUpClass(ClassRegistrationDto classRegistrationDto);
    public void approveRegistration(ClassRegistrationDto classRegistrationDto);
    public void refuseRegistration(ClassRegistrationDto classRegistrationDto);
    public LectureDto findById(Long lectureId);
}
