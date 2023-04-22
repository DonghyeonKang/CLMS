package com.example.csws.service.user;

import com.example.csws.common.exception.EntityNotFoundException;
import com.example.csws.common.exception.ErrorCode;
import com.example.csws.entity.user.Approval;
import com.example.csws.entity.user.ApprovalDto;
import com.example.csws.entity.user.User;
import com.example.csws.repository.user.ApprovalRepository;
import com.example.csws.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Component  // component scan 방식으로 빈 등록. Repository 해도 상관없음
@RequiredArgsConstructor // private 필드를 생성자 주입으로 DI 구현
public class UserService {  // 사용자 회원가입, 사용자 정보 불러오기
    private final EntityManager entityManager;
    private final UserRepository userRepository;
    private final ApprovalRepository approvalRepository;

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

    // 관리자 인증 요청하기
    public void managerVerificationRequest(ApprovalDto approvalDto, Long userId) {
        approvalRepository.save(approvalDto.toApproval(userId));
    }

    // 관리자 인증 요청 리스트 조회
    public List<Approval> getManagerVerificationList() {
        return approvalRepository.findAll();
    }

    // 관리자 인증 요청 승인
    @Transactional
    public void approveManagerVerification(String username) {

        // username 으로 user 조회
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND)); // 예외처리
        System.out.println(user.getId());

        // userId로 approval 조회
        Approval approval = approvalRepository.findById(Long.valueOf(user.getId()))// long 이어야 한다고 에러나서 형변환 해줌
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));

        // 엔티티 영속성 설정
        entityManager.persist(approval);

        // 조회한 approval accept. entity 의 값을 바꾸면 추적해서 업데이트 하는 거 아니었나?
        approval.updateApprovalStatus();
    }

    // 학생 목록 조회
    public List<String> getStudentList(int departmentId) {
        return userRepository.findUsernameByDepartmentId(departmentId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
    }
}
