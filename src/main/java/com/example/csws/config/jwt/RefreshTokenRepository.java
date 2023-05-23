package com.example.csws.config.jwt;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    Optional<RefreshToken> findByUser_Id(Long userId);
    Optional<RefreshToken> findByToken(String token);
    boolean existsByUser_Id(Long userId);
}
