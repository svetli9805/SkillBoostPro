package com.skill.boot.pro.SkillBoostPro.controller;

import com.skill.boot.pro.SkillBoostPro.payload.JWTAuthResponse;
import com.skill.boot.pro.SkillBoostPro.payload.LoginRequest;
import com.skill.boot.pro.SkillBoostPro.payload.RegisterRequest;
import com.skill.boot.pro.SkillBoostPro.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginRequest loginDto){
        String token = authService.login(loginDto);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }


    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerDto){
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
