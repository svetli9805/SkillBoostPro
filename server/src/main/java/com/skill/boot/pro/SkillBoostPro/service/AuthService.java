package com.skill.boot.pro.SkillBoostPro.service;

import com.skill.boot.pro.SkillBoostPro.payload.LoginRequest;
import com.skill.boot.pro.SkillBoostPro.payload.RegisterRequest;

public interface AuthService {
    String login(LoginRequest request);

    String register(RegisterRequest request);
}
