package com.skill.boot.pro.SkillBoostPro.mapper;

import com.skill.boot.pro.SkillBoostPro.entity.Course;
import com.skill.boot.pro.SkillBoostPro.entity.User;
import com.skill.boot.pro.SkillBoostPro.payload.CourseRequest;
import com.skill.boot.pro.SkillBoostPro.payload.CourseResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    @Mapping(source = "user", target = "userId")
    Course fromRequest(User user, CourseRequest request);


    CourseResponse fromEntity(Course course);

}
