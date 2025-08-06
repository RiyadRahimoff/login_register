package com.azecoders.community.model.reponse;

import com.azecoders.community.model.enums.UserRole;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginResponse {
    String id;
    String firstName;
    String lastName;
    String email;
    boolean isProfileCompleted;
    UserRole userRole;
    String accessToken;
    String refreshToken;
}
