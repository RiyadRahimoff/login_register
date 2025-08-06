package com.azecoders.community.controller;

import com.azecoders.community.model.request.create.CreateCompleteProfileRequest;
import com.azecoders.community.service.concrete.UserServiceHandler;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceHandler serviceHandler;


    @PostMapping(value = "/complete/profile")
    @ResponseStatus(HttpStatus.OK)
    public void completeProfile(@ModelAttribute CreateCompleteProfileRequest profileRequest,
                                @AuthenticationPrincipal UserDetails userDetails) {
        serviceHandler.completeProfile(profileRequest, userDetails.getUsername());
    }

    @Operation(summary = "Upload profile photo")
    @PostMapping(value = "/complete/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void completePhoto(@RequestPart("profilePhoto") MultipartFile profilePhoto,
                              @AuthenticationPrincipal UserDetails userDetails) {
        serviceHandler.completePhoto(profilePhoto, userDetails.getUsername());
    }
}
