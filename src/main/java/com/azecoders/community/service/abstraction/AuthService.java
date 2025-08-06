package com.azecoders.community.service.abstraction;

import com.azecoders.community.model.reponse.LoginResponse;
import com.azecoders.community.model.request.create.CreateLoginRequest;
import com.azecoders.community.model.request.create.RefreshTokenRequest;

public interface AuthService {
    LoginResponse login(CreateLoginRequest request);

    LoginResponse refreshToken(RefreshTokenRequest request);

    void logout(String email);
}