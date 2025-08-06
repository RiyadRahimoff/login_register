package com.azecoders.community.service.abstraction;

import com.azecoders.community.model.request.create.CreateCompleteProfileRequest;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    void completeProfile(CreateCompleteProfileRequest profileRequest, String email);

    void completePhoto(MultipartFile profilePhoto, String email);
}
