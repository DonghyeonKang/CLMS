package com.example.csws.service.user;

import com.example.csws.common.exception.ErrorCode;
import com.example.csws.common.exception.RegisterException;
import com.example.csws.entity.department.Department;
import com.example.csws.entity.university.University;
import com.example.csws.entity.user.User;
import com.example.csws.entity.user.UserDto;
import com.example.csws.repository.department.DepartmentRepository;
import com.example.csws.repository.university.UniversityRepository;
import com.example.csws.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Slf4j  // logging framework
@Component  // 컴포넌트 스캔 방식으로 빈 등록
@RequiredArgsConstructor // private field 생성자 주입으로 DI 구현
public class RegisterService {
    final private UserRepository userRepository;
    final private DepartmentRepository departmentRepository;
    final private UniversityRepository universityRepository;

    @Transactional
    public User register(UserDto userDto, String role) {
        System.out.println("Register Service start");
        // 회원가입 검증절차
        validateRegisterRequest(userDto);
        // 로그 남기기
        log.debug("user = {}", userDto);
        // department, university 객체 가져오기
        Department department = departmentRepository.getReferenceById(userDto.getDepartmentId());
        University university = universityRepository.getReferenceById(userDto.getUniversityId());

        // dto to entity
        User user = new User();
        if(role.equals("USER")) {
            user = userDto.toUserEntity(department, university);
        } else {
            user = userDto.toManagerEntity(department, university);
        }

        // jpa의 save
        return userRepository.save(user);
    }

    private void validateRegisterRequest(UserDto userDto) { // 회원가입 검증
        System.out.println("validateRegisterRequest start");
        // 이메일 중복 검증
        validateDuplicateEmail(userDto.getUsername());
    }

    private void validateDuplicateEmail(String email) {
        System.out.println("validateDuplicateEmail start");
        if (userRepository.findByUsername(email).isPresent()) { // isPresent value 가 있으면 true, 없으면 false return
            throw new RegisterException(ErrorCode.DUPLICATED_EMAIL);
        }
    }

    // 회원가입 인증번호 확인
    public boolean checkVerificationNumber(String sessionAuthNumber, String requestAuthNumber) {
        if(sessionAuthNumber.equals(requestAuthNumber)) {
            return true;
        } else {
            return false;
        }
    }
}
