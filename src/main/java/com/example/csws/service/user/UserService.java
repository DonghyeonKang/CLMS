package com.example.csws.service.user;

import com.example.csws.common.exception.EntityNotFoundException;
import com.example.csws.common.exception.ErrorCode;
import com.example.csws.entity.user.User;
import com.example.csws.repository.instance.InstanceRepository;
import com.example.csws.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component  // component scan 방식으로 빈 등록. Repository 해도 상관없음
@RequiredArgsConstructor // private 필드를 생성자 주입으로 DI 구현
public class UserService {  // 사용자 회원가입, 사용자 정보 불러오기

    private final UserRepository userRepository;
    private final InstanceRepository instanceRepository;

    // 회원 조회
    public User getUser(String email) {
        return userRepository.findByUsername(email)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    // 회원 탈퇴
    public void deleteUser(String email) {
        userRepository.deleteByUsername(email);
    }

    // 비밀번호 재설정
    public void resetPassword(String username, String password) {
        User userToUpdate = getUser(username);
        userToUpdate.setPassword(password);
        userRepository.save(userToUpdate);
    }
}
