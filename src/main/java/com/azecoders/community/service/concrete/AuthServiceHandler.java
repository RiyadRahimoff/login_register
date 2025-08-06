package com.azecoders.community.service.concrete;

import com.azecoders.community.dao.entity.UserEntity;
import com.azecoders.community.dao.repository.UserRepository;
import com.azecoders.community.exception.UserNotFoundException;
import com.azecoders.community.model.enums.UserStatus;
import com.azecoders.community.model.reponse.LoginResponse;
import com.azecoders.community.model.request.create.CreateLoginRequest;
import com.azecoders.community.model.request.create.RefreshTokenRequest;
import com.azecoders.community.service.abstraction.AuthService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceHandler implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, String> redisTemplate;

    private static final String REFRESH_TOKEN_PREFIX = "refresh_token:";

    @Override
    @Transactional
    public LoginResponse login(CreateLoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!user.isVerified() || !user.isEnabled()) {
            throw new IllegalStateException("User is not verified or not enabled.");
        }

        if (user.getUserStatus() == UserStatus.PENDING) {
            throw new IllegalStateException("User is still pending.");
        }

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        String redisKey = REFRESH_TOKEN_PREFIX + user.getEmail();
        redisTemplate.opsForValue().set(redisKey, refreshToken, Duration.ofDays(7));

        if (user.getUserStatus() != UserStatus.ACTIVE) {
            user.setUserStatus(UserStatus.ACTIVE);
            user.setUpdatedAt(LocalDateTime.now());
            userRepository.save(user);
        }

        return LoginResponse.builder()
                .id(String.valueOf(user.getId()))
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .userRole(user.getUserRole())
                .isProfileCompleted(user.isProfileCompleted())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

    }

    @Override
    public LoginResponse refreshToken(RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();
        try {
            String email = jwtService.extractUsername(refreshToken);
            UserEntity user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            String redisKey = REFRESH_TOKEN_PREFIX + email;
            String storedRefreshToken = redisTemplate.opsForValue().get(redisKey);

            if (storedRefreshToken == null || !storedRefreshToken.equals(refreshToken)) {
                throw new IllegalArgumentException("Invalid refresh token");
            }

            if (!jwtService.isTokenValid(refreshToken, user)) {
                throw new IllegalArgumentException("Invalid refresh token");
            }

            String newAccessToken = jwtService.generateToken(user);
            String newRefreshToken = jwtService.generateRefreshToken(user);


            redisTemplate.opsForValue().set(redisKey, newRefreshToken, Duration.ofDays(7));

            return LoginResponse.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(newRefreshToken)
                    .build();

        } catch (ExpiredJwtException e) {
            throw new IllegalArgumentException("Refresh token expired");
        }
    }

    public void logout(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setUserStatus(UserStatus.LOGOUT);
        userRepository.save(user);

        String redisKey = REFRESH_TOKEN_PREFIX + email;
        redisTemplate.delete(redisKey);
    }
}
