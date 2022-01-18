package com.rocketteam.auth.web.models;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CourseCategoryDto {

    private Long courseId;

    private String categoryName;

    private Timestamp createdAt;

    private Timestamp lastModifiedAt;

}