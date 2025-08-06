package com.azecoders.community.controller;

import com.azecoders.community.model.reponse.UserResponse;
import com.azecoders.community.model.request.create.CreateRegisterRequest;
import com.azecoders.community.service.abstraction.RegisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class RegisterController {

    private final RegisterService registerService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserResponse> register(@RequestBody @Valid CreateRegisterRequest request) {
        UserResponse response = registerService.registerUser(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/verify")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> verify(@RequestParam String code) {
        boolean success = registerService.verifyuser(code);
        if (success) {
            return ResponseEntity.ok("Account Confirmed");
        } else {
            return ResponseEntity.badRequest().body("Wrong or expired code");
        }
    }
}
