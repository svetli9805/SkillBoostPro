package com.skill.boot.pro.SkillBoostPro.service;

import com.skill.boot.pro.SkillBoostPro.payload.CourseRequest;
import com.skill.boot.pro.SkillBoostPro.payload.CourseResponse;

public interface CourseService {
    CourseResponse createCourse(String email, CourseRequest courseRequest);
}
