package com.azecoders.community.service.concrete;

import com.azecoders.community.dao.entity.UserEntity;
import com.azecoders.community.dao.repository.UserRepository;
import com.azecoders.community.model.request.create.CreateCompleteProfileRequest;
import com.azecoders.community.service.abstraction.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserServiceHandler implements UserService {

    private final UserRepository userRepository;
    private final MinioServiceHandler minioServiceHandler;

    @Override
    public void completeProfile(CreateCompleteProfileRequest profileRequest, String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        user.setBio(profileRequest.getBio());
        user.setProfession(profileRequest.getProfession());
        user.setProfileCompleted(true);

        userRepository.save(user);
    }

    @Override
    public void completePhoto(MultipartFile profilePhoto, String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        if (profilePhoto != null && !profilePhoto.isEmpty()) {
            checkFileExtension(profilePhoto);
            String photoUrl = minioServiceHandler.upload(profilePhoto, "profile-photos");
            user.setProfilePhoto(photoUrl);
            userRepository.save(user);
        }
    }

    private void checkFileExtension(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (filename == null || !(filename.endsWith(".png") || filename.endsWith(".jpg") || filename.endsWith(".jpeg"))) {
            throw new IllegalArgumentException("Only PNG, JPG, and JPEG files are allowed.");
        }
    }
}
