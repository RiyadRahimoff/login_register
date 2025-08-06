package com.azecoders.community.service.abstraction;

import com.azecoders.community.model.reponse.UserResponse;
import com.azecoders.community.model.request.create.CreateRegisterRequest;

public interface RegisterService {
    UserResponse registerUser(CreateRegisterRequest registerRequest);
    boolean verifyuser(String verificationCode);
}
