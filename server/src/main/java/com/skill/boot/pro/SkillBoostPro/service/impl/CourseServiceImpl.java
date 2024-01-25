package com.skill.boot.pro.SkillBoostPro.service.impl;

import com.skill.boot.pro.SkillBoostPro.entity.Course;
import com.skill.boot.pro.SkillBoostPro.entity.User;
import com.skill.boot.pro.SkillBoostPro.exception.ResourceNotFoundException;
import com.skill.boot.pro.SkillBoostPro.mapper.CourseMapper;
import com.skill.boot.pro.SkillBoostPro.payload.CourseRequest;
import com.skill.boot.pro.SkillBoostPro.payload.CourseResponse;
import com.skill.boot.pro.SkillBoostPro.repository.CourseRepository;
import com.skill.boot.pro.SkillBoostPro.repository.UserRepository;
import com.skill.boot.pro.SkillBoostPro.service.CourseService;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseMapper mapper;
    private final CourseRepository courseRepository;

    private final UserRepository userRepository;

    public CourseServiceImpl(CourseMapper mapper, CourseRepository courseRepository, UserRepository userRepository) {
        this.mapper = mapper;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CourseResponse createCourse(String username, CourseRequest courseRequest) {

        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User", "email", username));

        Course course =  courseRepository.save(mapper.fromRequest(user, courseRequest));

        return mapper.fromEntity(course);
    }
}
