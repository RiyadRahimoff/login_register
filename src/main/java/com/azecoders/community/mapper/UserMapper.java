package com.azecoders.community.mapper;

import com.azecoders.community.dao.entity.UserEntity;
import com.azecoders.community.model.enums.UserRole;
import com.azecoders.community.model.enums.UserStatus;
import com.azecoders.community.model.reponse.UserResponse;
import com.azecoders.community.model.request.create.CreateRegisterRequest;

public class UserMapper {

    public static UserEntity toEntity(CreateRegisterRequest request, String encodedPassword, String verificationCode) {
        return UserEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(encodedPassword)
                .userStatus(UserStatus.PENDING)
                .userRole(UserRole.USER)
                .isEnabled(false)
                .isVerified(false)
                .isPremium(false)
                .verificationToken(verificationCode)
                .build();
    }

    public static UserResponse toResponse(UserEntity user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .userStatus(user.getUserStatus())
                .isVerified(user.isVerified())
                .isPremium(user.isPremium())
                .build();
    }
}
