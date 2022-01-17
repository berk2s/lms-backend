package com.rocketteam.auth.web.controllers;

import com.rocketteam.auth.services.UserInfoService;
import com.rocketteam.auth.web.models.UserInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin( origins = "*")
@Tag(name = "User Info Controller", description = "Exposes user info endpoint")
@RequiredArgsConstructor
@RequestMapping(UserInfoController.ENDPOINT)
@RestController
public class UserInfoController {

    /** Defined Endpoint of Controller */
    public static final String ENDPOINT = "/userinfo";

    /** UserInfoService injection */
    private final UserInfoService userInfoService;

    /**
     * Generates user info
     * @return The object that contains user information
     */
    @Operation(summary = "Get User Info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User info is generated"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserInfoDto> getUserInfo() {
        return new ResponseEntity<>(userInfoService.getUserInfo(), HttpStatus.OK);
    }
}
