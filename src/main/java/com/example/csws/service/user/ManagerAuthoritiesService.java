package com.example.csws.service.user;

import com.example.csws.common.exception.EntityNotFoundException;
import com.example.csws.common.exception.ErrorCode;
import com.example.csws.entity.user.ManagerAuthority;
import com.example.csws.entity.user.ManagerAuthorityDto;
import com.example.csws.entity.user.ManagerAuthorityResponse;
import com.example.csws.entity.user.User;
import com.example.csws.repository.user.ManagerAuthoritiesRepository;
import com.example.csws.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ManagerAuthoritiesService {
    private final EntityManager entityManager;
    private final ManagerAuthoritiesRepository managerAuthoritiesRepository;
    private final UserRepository userRepository;

    // 관리자 인증 요청하기
    public void managerVerificationRequest(ManagerAuthorityDto approvalDto, Long userId) {
        managerAuthoritiesRepository.save(approvalDto.toManagerAuthority(userId));
    }

    // 관리자 인증 요청 리스트 조회
    public List<ManagerAuthorityResponse> getManagerVerificationList() {
        List<ManagerAuthority> managerAuthorityList = managerAuthoritiesRepository.findAll();

        List<ManagerAuthorityResponse> managerAuthorityResponses = new ArrayList<>();
        for(ManagerAuthority managerAuthority : managerAuthorityList) {
            User user = userRepository.findById(managerAuthority.getUserId()).get();

            managerAuthorityResponses.add(ManagerAuthorityResponse.builder()
                            .username(user.getUsername())
                            .university(user.getUniversity().getName())
                            .department(user.getDepartment().getName())
                            .phone(user.getPhone())
                            .status(managerAuthority.getStatus())
                            .build());
        }

        return managerAuthorityResponses;
    }

    // 관리자 인증 요청 승인
    @Transactional
    public void approveManagerVerification(String username) {

        // username 으로 user 조회
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND)); // 예외처리

        // userId로 roles 조회
        ManagerAuthority managerAuthority = managerAuthoritiesRepository.findById(Long.valueOf(user.getId()))
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND)); // managerAuthority 가 존제하지 않음 에러로 수정해야함

        // 엔티티 영속성 설정
        entityManager.persist(managerAuthority);
        entityManager.persist(user);

        // 조회한 approval accept.
        managerAuthority.acceptAuthorityStatus();
        user.setManager();
    }

    // 관리자 인증 요청 거절
    @Transactional
    public void denyManagerVerification(String username) {

        // username 으로 user 조회
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND)); // 예외처리

        // userId로 approval 조회
        ManagerAuthority managerAuthority = managerAuthoritiesRepository.findById(Long.valueOf(user.getId()))
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND)); // managerAuthority 가 존제하지 않음 에러로 수정해야함

        // 엔티티 영속성 설정
        entityManager.persist(managerAuthority);

        // 조회한 approval accept.
        managerAuthority.denyAuthorityStatus();
    }

    // 관리자 인증 요청 삭제
    public void deleteManagerVerification(String username) {
        // username 으로 user 조회
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND)); // 예외처리

        // userId로 approval 조회
        ManagerAuthority managerAuthority = managerAuthoritiesRepository.findById(Long.valueOf(user.getId()))
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND)); // managerAuthority 가 존제하지 않음 에러로 수정해야함

        // 삭제
        Long verificationId = managerAuthority.getId();
        managerAuthoritiesRepository.deleteById(verificationId);
    }
}
