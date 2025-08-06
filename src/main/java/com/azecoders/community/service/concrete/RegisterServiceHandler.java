package com.azecoders.community.service.concrete;

import com.azecoders.community.dao.entity.UserEntity;
import com.azecoders.community.dao.repository.UserRepository;
import com.azecoders.community.exception.MailSendException;
import com.azecoders.community.mapper.UserMapper;
import com.azecoders.community.model.enums.UserStatus;
import com.azecoders.community.model.reponse.UserResponse;
import com.azecoders.community.model.request.create.CreateRegisterRequest;
import com.azecoders.community.service.abstraction.MailService;
import com.azecoders.community.service.abstraction.RegisterService;
import com.azecoders.community.util.VerificationCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RegisterServiceHandler implements RegisterService {
    private final UserRepository userRepository;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse registerUser(CreateRegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new IllegalArgumentException("Bu mail bazada mövcuddur.");
        }
        String encodePassword = passwordEncoder.encode(registerRequest.getPassword());


        String verificationCode = VerificationCodeGenerator.generateCode();

        UserEntity user = UserMapper.toEntity(registerRequest,encodePassword,verificationCode);

        userRepository.save(user);

        try {
            mailService.sendVertificationCode(user.getEmail(), verificationCode,user.getFirstName());
        } catch (MailSendException e) {
            throw new RuntimeException("Təsdiqləmə kodu göndərilə bilinmədi.Yenidən cəhd edin!");
        }

        return UserMapper.toResponse(user);
    }


    @Override
    public boolean verifyuser(String verificationCode) {
        UserEntity user = userRepository.findByVerificationToken(verificationCode)
                .orElseThrow(() -> new MailSendException("Səhv və ya vaxtı bitmiş kod"));

        user.setUserStatus(UserStatus.ACTIVE);
        user.setEnabled(true);
        user.setVerified(true);
        user.setVerificationToken(null);
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);

        mailService.sendAccountConfirmedMessage(user.getEmail(), user.getFirstName());

        return true;
    }
}
