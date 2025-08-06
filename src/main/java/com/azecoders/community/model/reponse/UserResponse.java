package com.azecoders.community.model.reponse;

import com.azecoders.community.model.enums.UserStatus;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private UserStatus userStatus;
    private boolean isVerified;
    private boolean isPremium;
}
