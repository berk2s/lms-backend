package com.rocketteam.auth.web.controllers;

import com.rocketteam.auth.web.models.CourseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Course Controller", description = "Exposes course endpoints")
@RequiredArgsConstructor
@RequestMapping(CourseController.ENDPOINT)
@RestController
public class CourseController {

    public static final String ENDPOINT = "/courses";

    @Operation(summary = "Get Courses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Courses are listed"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CourseDto> getCourses(@RequestParam(defaultValue = "0") Integer page,
                                                @RequestParam(defaultValue = "5") Integer size,
                                                @RequestParam(defaultValue = "createdAt") String sort,
                                                @RequestParam(defaultValue = "asc") String order,
                                                @RequestParam(defaultValue = "") String search) {

    }


}
