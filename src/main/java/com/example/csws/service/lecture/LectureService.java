package com.example.csws.service.lecture;

import com.example.csws.entity.lecture.ClassRegistrationDto;
import com.example.csws.entity.lecture.CreateLectureRequest;
import com.example.csws.entity.lecture.LectureDto;
import com.example.csws.entity.lecture.StudentDto;
import com.example.csws.entity.user.User;

import java.util.List;

public interface LectureService {
    public void createLecture(CreateLectureRequest createLectureRequest);
    public List<LectureDto> getLectureList(Long departmentId);
    public LectureDto getLectureDetail(Long lectureId);
    public void deleteLecture(Long lectureId);
    public List<StudentDto> getStudentList(Long lectureId);
    public void signUpClass(ClassRegistrationDto classRegistrationDto);
    public void approveRegistration(ClassRegistrationDto classRegistrationDto);
    public void refuseRegistration(ClassRegistrationDto classRegistrationDto);
}
