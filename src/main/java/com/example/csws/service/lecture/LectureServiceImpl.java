package com.example.csws.service.lecture;

import com.example.csws.common.exception.EntityNotFoundException;
import com.example.csws.common.exception.ErrorCode;
import com.example.csws.entity.department.Department;
import com.example.csws.entity.lecture.*;
import com.example.csws.entity.server.Server;
import com.example.csws.repository.department.DepartmentRepository;
import com.example.csws.repository.lecture.LectureRepository;
import com.example.csws.repository.lecture.LectureUserRepository;
import com.example.csws.repository.server.ServerRepository;
import com.example.csws.entity.user.User;
import com.example.csws.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LectureServiceImpl implements LectureService{
    private final UserRepository userRepository;
    private final LectureRepository lectureRepository;
    private final ServerRepository serverRepository;
    private final LectureUserRepository lectureUserRepository;
    private final DepartmentRepository departmentRepository;

    // 강의 생성
    @Override
    public void createLecture(CreateLectureRequest createLectureRequest) {
        Server server = serverRepository.getReferenceById(createLectureRequest.getServerId());
        Department department = departmentRepository.getReferenceById(createLectureRequest.getDepartmentId());

        Lecture lecture = Lecture.builder()
                .lectureName(createLectureRequest.getLectureName())
                .introducing(createLectureRequest.getIntroducing())
                .server(server)
                .department(department)
                .build();
        lectureRepository.save(lecture);
    }

    // 강의 목록
    @Override
    public List<LectureDto> getLectureList(Long departmentId) {
        List<Lecture> lectureList = lectureRepository.findAllByDepartmentId(departmentId);

        List<LectureDto> result = lectureList.stream()
                .map(m -> m.toDto())
                .collect(Collectors.toList());
        return result;
    }

    // 강의 상세
    @Override
    public LectureDto getLectureDetail(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));

        LectureDto result = lecture.toDto();
        return result;
    }

    // 강의 삭제
    @Override
    public void deleteLecture(Long lectureId) {
        lectureRepository.deleteById(lectureId);
    }

    // 수강 신청 학생 목록
    @Override
    public List<StudentDto> getStudentList(Long lectureId) {
        List<LectureUser> lectureUsers = lectureUserRepository.findAllByLectureId(lectureId);
        List<User> users = lectureUsers.stream().map(LectureUser::getUser).collect(Collectors.toList());


        List<StudentDto> result = users.stream()
                .map(m -> m.toStudentDto())
                .collect(Collectors.toList());
        return result;
    }

    // 수강 신청
    @Transactional
    public void signUpClass(ClassRegistrationDto classRegistrationDto) {
        User user = userRepository.getReferenceById(classRegistrationDto.getUserId());
        Lecture lecture = lectureRepository.getReferenceById(classRegistrationDto.getLectureId());
        System.out.println("---------------------");
        System.out.println(classRegistrationDto.getLectureId());
        // 엔티티 생성
        LectureUser newLectureUser = LectureUser.builder()
                .user(user)
                .lecture(lecture)
                .build();

        // 엔티티 저장
        LectureUser lectureUser = lectureUserRepository.save(newLectureUser);
    }

    // 수강 신청 승인
    @Transactional
    public void approveRegistration(ClassRegistrationDto classRegistrationDto) {
        // 엔티티 탐색
        LectureUser lectureUser = lectureUserRepository.findById(classRegistrationDto.getId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));

        // 수정
        lectureUser.setPermit();
        lectureUserRepository.save(lectureUser);
    }


    // 수강 신청 거절
   @Transactional
    public void refuseRegistration(ClassRegistrationDto classRegistrationDto) {
        // 엔티티 탐색
        LectureUser lectureUser = lectureUserRepository.findById(classRegistrationDto.getId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));

        // 수정
        lectureUser.setRefuse();
        lectureUserRepository.save(lectureUser);
    }

}