package com.rocketteam.auth.web.models;

import lombok.Data;

@Data
public class CourseDto {

    private Long courseId;

    private String courseName;

    private CourseCategoryDto courseCategory;

}
