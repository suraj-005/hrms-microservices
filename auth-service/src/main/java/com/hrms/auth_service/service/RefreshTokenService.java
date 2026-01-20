package com.hrms.auth_service.service;

import com.hrms.auth_service.entity.RefreshToken;
import com.hrms.auth_service.entity.User;
import com.hrms.auth_service.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExp;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken createRefreshToken(User user){
        RefreshToken refreshToken=refreshTokenRepository.findByUser(user).orElseGet(RefreshToken::new);
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenExp));
        refreshToken.setToken(UUID.randomUUID().toString());
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken findByToken(String refreshTokenValue) {
        return refreshTokenRepository.findByToken(refreshTokenValue).orElseThrow(()->new RuntimeException("Token not found"));
    }

    public RefreshToken verifyAndRotate(RefreshToken refreshToken) {
        User user=refreshToken.getUser();

        if(refreshToken.getExpiryDate().isBefore(Instant.now())){
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("refresh token is expired");
        }

        refreshTokenRepository.delete(refreshToken);
        return createRefreshToken(user);
    }

    public String logout(String refreshTokenValue) {
        RefreshToken refreshToken=refreshTokenRepository.findByToken(refreshTokenValue).orElseThrow(()->new RuntimeException("Token not found"));
        refreshTokenRepository.delete(refreshToken);
        return "logged out successfully";
    }
}
