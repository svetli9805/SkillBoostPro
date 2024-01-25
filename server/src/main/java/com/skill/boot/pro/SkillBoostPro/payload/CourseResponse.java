package com.skill.boot.pro.SkillBoostPro.payload;

import com.skill.boot.pro.SkillBoostPro.entity.Lecture;
import com.skill.boot.pro.SkillBoostPro.entity.User;
import lombok.Data;

import java.util.Set;

@Data
public class CourseResponse {

    private Long courseId;

    private String title;

    private String description;

    private User userId;

    private Set<Lecture> lectures;
}
