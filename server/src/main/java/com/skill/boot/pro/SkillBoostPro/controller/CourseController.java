package com.skill.boot.pro.SkillBoostPro.controller;

import com.skill.boot.pro.SkillBoostPro.payload.CourseRequest;
import com.skill.boot.pro.SkillBoostPro.payload.CourseResponse;
import com.skill.boot.pro.SkillBoostPro.security.JwtTokenProvider;
import com.skill.boot.pro.SkillBoostPro.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/courses")
@Tag(
        name = "CRUD REST APIs for Course Resource"
)
public class CourseController {

    private final CourseService courseService;

    private final JwtTokenProvider jwtTokenProvider;

    public CourseController(CourseService courseService, JwtTokenProvider jwtTokenProvider) {
        this.courseService = courseService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Operation(
            summary = "Create Course REST API",
            description = "Create Course REST API is used to save course into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping()
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<CourseResponse> createCourse(@RequestBody CourseRequest courseRequest,
                                                       @RequestParam("username") String username) {
        return new ResponseEntity<>(courseService.createCourse(username, courseRequest), HttpStatus.CREATED);
    }
}
