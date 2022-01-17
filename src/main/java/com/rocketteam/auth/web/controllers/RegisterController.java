package com.rocketteam.auth.web.controllers;

import com.rocketteam.auth.services.RegisterService;
import com.rocketteam.auth.web.models.ErrorResponseDto;
import com.rocketteam.auth.web.models.RegisterUserDto;
import com.rocketteam.auth.web.models.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Register Controller", description = "Exposes registration endpoint")
@RequestMapping(RegisterController.ENDPOINT)
@RestController
public class RegisterController {

    /** Defined Endpoint of Controller */
    public static final String ENDPOINT = "/register";

    private final RegisterService registerService;

    /**
     * Handles registration request and registeres user.
     * @param registerUserDto Contains information about user that will be registered.
     * @return The object that contains information about registered user.
     */
    @Operation(summary = "Register User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User is registered"),
            @ApiResponse(responseCode = "400", description = "Invalid request body or unique fields are taken"),

    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> registerUser(@RequestBody @Valid RegisterUserDto registerUserDto) {
        return new ResponseEntity<>(registerService.register(registerUserDto), HttpStatus.CREATED);
    }

}
