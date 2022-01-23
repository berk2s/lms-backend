package com.rocketteam.auth.web.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CourseControllerTest {

    @Autowired
    MockMvc mockMvc;

    @DisplayName("Getting Courses")
    @Nested
    class GettingCourses {

        @DisplayName("Get Course Successfully")
        @Test
        void getCourseSuccessfully() throws Exception {
            mockMvc.perform(get(CourseController.ENDPOINT))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(jsonPath("$.content..courseId").isNotEmpty())
                    .andExpect(jsonPath("$.content..courseName").isNotEmpty())
                    .andExpect(jsonPath("$.content..courseCategory").isNotEmpty())
                    .andExpect(jsonPath("$.content..publishers").isNotEmpty())
                    .andExpect(jsonPath("$.content..createdAt").isNotEmpty())
                    .andExpect(jsonPath("$.content..lastModifiedAt").isNotEmpty());
        }

    }

}
