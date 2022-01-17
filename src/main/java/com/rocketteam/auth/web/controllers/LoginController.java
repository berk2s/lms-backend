package com.rocketteam.auth.web.controllers;

import com.rocketteam.auth.services.LoginService;
import com.rocketteam.auth.web.models.ErrorResponseDto;
import com.rocketteam.auth.web.models.LoginRequestDto;
import com.rocketteam.auth.web.models.TokenResponseDto;
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
@Tag(name = "Login Controller", description = "Exposes login endpoint")
@RequestMapping(LoginController.ENDPOINT)
@RequiredArgsConstructor
@RestController
public class LoginController {

    /** Defined Endpoint of Controller */
    public static final String ENDPOINT = "/login";

    /** LoginService injection that returns LoginServiceImpl */
    private final LoginService loginService;

    /**
     * Login endpoint handler
     * @param loginRequestDto Contains identifier and password in order to authentication process
     * @return THe object of TokenResponseDto that contains access token, refresh token, scopes and expires of access token
     */
    @Operation(summary = "Login User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "403", description = "Username or password are invalid")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenResponseDto> handleLogin(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        return new ResponseEntity<>(loginService.login(loginRequestDto), HttpStatus.OK);
    }

}
