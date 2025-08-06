package com.azecoders.community.dao.repository;

import com.azecoders.community.dao.entity.UserEntity;
import com.azecoders.community.model.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    
    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    List<UserEntity> findAllByUserStatusAndCreatedAtBefore(UserStatus status, LocalDateTime time);


    Optional<UserEntity> findByVerificationToken(String token);
}
