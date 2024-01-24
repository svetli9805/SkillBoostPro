package com.skill.boot.pro.SkillBoostPro.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    private String usernameOrEmail;
    private String password;
}
