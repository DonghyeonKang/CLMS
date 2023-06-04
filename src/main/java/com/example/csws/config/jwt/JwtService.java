package com.example.csws.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.csws.common.exception.CustomJwtException;
import com.example.csws.common.exception.EntityNotFoundException;
import com.example.csws.common.exception.ErrorCode;
import com.example.csws.common.exception.JwtErrorCode;
import com.example.csws.entity.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;

import javax.persistence.EntityManager;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@Getter
@RequiredArgsConstructor
@Transactional
public class JwtService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final EntityManager entityManager;
    // token 으로 사용자 조회
    @Transactional()
    public User getUserByRefreshToken(String token) {
        System.out.println(token);
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));  // 여기 에러 수정해야함
        return refreshToken.getUser();
    }

    // access token 생성(로그인 시 사용)
    public String createAccessToken(Long id, String username) {
        return JWT.create()
                .withSubject(JwtProperties.ACCESS_TOKEN)  // token 이름
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.ACCESS_EXPIRATION_TIME)) // 만료시간
                .withClaim("userid", String.valueOf(id))    // token payload 에서 private 추가
                .withClaim("username", username)
                .sign(Algorithm.HMAC512(JwtProperties.SECRET)); // 해싱 알고리즘 선택, 시크릿 키
    }

    // refresh token 생성(로그인 시 사용)
    public String createRefreshToken() {
        return JWT.create()
                .withSubject(JwtProperties.REFRESH_TOKEN) // token 이름
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.REFRESH_EXPIRATION_TIME)) // 만료시간
                .sign(Algorithm.HMAC512(JwtProperties.SECRET)); // 해싱 알고리즘 선택 시크릿 키
    }

    // refresh token db 에 저장(로그인 시 사용, 갱신 시 사용)
    public void setRefreshToken(String token, User user) {
        if(refreshTokenRepository.existsByUser_Id(user.getId())) { // 이미 token 이 존재하면 업데이트
            updateRefreshToken(token, user);
        } else {    // token 이 존재하지 않으면 생성
            saveRefreshToken(token, user);
        }
    }

    @Transactional
     private void updateRefreshToken(String token, User user) {
        RefreshToken refreshToken = refreshTokenRepository.findByUser_Id(user.getId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));

        // 엔티티 영속성 설정
        entityManager.persist(refreshToken);

        // dirty check 로 업데이트
        refreshToken.updateToken(token);
    }

    @Transactional
    private void saveRefreshToken(String token, User user) {
        // token 과 user 를 받아서 refreshToken entity 객체 생성
        RefreshToken refreshToken = RefreshToken.builder()
                .token(token)
                .user(user)
                .build();
        // 저장
        refreshTokenRepository.save(refreshToken);
    }

    // refresh token 삭제(로그아웃 시 사용)
    @Transactional
    public void removeRefreshToken(String token) {
        // token 으로 찾아온 다음
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow();
        // 지운다.
        refreshTokenRepository.delete(refreshToken);
    }

    // 로그아웃
    public void logout(HttpServletRequest req) {
        try {
            // header 에 access, refresh token 이 있는지 확인
            checkHeaderValid(req);
        } catch (Exception e) {
            throw new CustomJwtException(JwtErrorCode.JWT_REFRESH_NOT_VALID);
        }
    }

    // header 에 access, refresh token 있는지 검증
    public Map checkHeaderValid(HttpServletRequest req) {
        Map<String, Object> map = new HashMap<String,Object>();

        String accessToken = req.getHeader(JwtProperties.ACCESS_HEADER_PREFIX)
                .replace(JwtProperties.TOKEN_PREFIX, "");
        map.put("accessToken", accessToken);

        // accessToken 과 refreshToken 이 항상 같이 오는 구조 -> 수정 해야함
        if(accessToken == null) {
            throw new CustomJwtException(JwtErrorCode.JWT_ACCESS_NOT_EXIST);
        }
        return map;
    }

    // 토큰 유효성 체크(복호화)
    public DecodedJWT checkTokenValid(String token) {
        try {
            return JWT.require(Algorithm.HMAC512(JwtProperties.SECRET))
                    .build()
                    .verify(token);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    // 토큰 만료 체크(만료 시간)
    public boolean isExpiredToken(String token) {
        try {
            JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token);
        } catch (TokenExpiredException e) {
            log.info("만료된 토큰입니다. ");
            return true;
        }
        return false;
    }

    // refresh token 업데이트 필요한지 체크
    public boolean isNeedToUpdate(String token) {
        try {
            Date expiresAt = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET))
                    .build()
                    .verify(token)
                    .getExpiresAt();

            Date current = new Date(System.currentTimeMillis());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(current);
            calendar.add(Calendar.DATE, 7);

            Date after7dayFromToday = calendar.getTime();

            // 7일 이내 만료
            if(expiresAt.before(after7dayFromToday)) {
                log.info("리프레시 토큰이 7일 이내 만료됩니다. ");
                return true;
            }
        } catch (TokenExpiredException e) {
            return true;
        }
        return false;
    }
}
