package com.example.csws.service.user;

import com.example.csws.common.exception.EntityNotFoundException;
import com.example.csws.common.exception.ErrorCode;
import com.example.csws.entity.user.User;
import com.example.csws.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component  // component scan 방식으로 빈 등록. Repository 해도 상관없음
@RequiredArgsConstructor // private 필드를 생성자 주입으로 DI 구현
public class UserService {  // 사용자 회원가입, 사용자 정보 불러오기
    private final UserRepository userRepository;

    // 회원 조회
    public User getUser(String email) {
        return userRepository.findByUsername(email)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    // 회원 탈퇴
    public void deleteUser() {

    }

    // 비밀번호 재설정
    public void resetPassword() {

    }

    // 관리자 인증 요청 리스트 조회
    public void getManagerVerificationList() {

    }

    // 관리자 인증 요청 승인
    public void approveManagerVerification() {

    }
    // 학생 목록 조회
    public void getStudentList() {

    }
}
