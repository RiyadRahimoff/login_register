package com.azecoders.community.service.concrete;

import com.azecoders.community.dao.entity.UserEntity;
import com.azecoders.community.dao.repository.UserRepository;
import com.azecoders.community.model.enums.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCleanupService {

    private final UserRepository userRepository;


    @Scheduled(fixedRate = 60000)
    public void removeExpiredPendingUsers() {
        LocalDateTime oneMinuteAgo = LocalDateTime.now().minusMinutes(15);
        List<UserEntity> expiredUsers = userRepository
                .findAllByUserStatusAndCreatedAtBefore(UserStatus.PENDING, oneMinuteAgo);

        if (!expiredUsers.isEmpty()) {
            userRepository.deleteAll(expiredUsers);
            System.out.println("Expired PENDING users deleted: " + expiredUsers.size());
        }
    }
}
