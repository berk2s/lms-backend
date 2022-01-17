package com.rocketteam.auth.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rocketteam.auth.services.RefreshTokenService;
import com.rocketteam.auth.web.models.ErrorResponseDto;
import com.rocketteam.auth.web.models.GrantType;
import com.rocketteam.auth.web.models.TokenRequestDto;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "Token Controller", description = "Exposes token endpoint in order to refresh or revoke a token")
@RequiredArgsConstructor
@RequestMapping(TokenController.ENDPOINT)
@RestController
public class TokenController {

    /** Defined endpoint of the Controller */
    public static final String ENDPOINT = "/token";

    /** ObjectMapper injection in order to convert params to DTO */
    private final ObjectMapper objectMapper;

    /** RefreshTokenService injection */
    private final RefreshTokenService refreshTokenService;

    /**
     * Performs token actions
     * @param params contains information action
     * @return The object that may contain tokens
     */
    @Operation(summary = "Perform Token Action")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation is succeed"),
            @ApiResponse(responseCode = "400", description = "Invalid input or malformed data", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
            }),
            @ApiResponse(responseCode = "500",
                    description = "Server Error while generating Access Token", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
            })
    })
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<TokenResponseDto> tokenRequest(@RequestParam Map<String, String> params) {
        TokenRequestDto tokenRequest = objectMapper.convertValue(params, TokenRequestDto.class);

        if (tokenRequest.getGrantType().equalsIgnoreCase(GrantType.REFRESH_TOKEN.getType())) {
            return new ResponseEntity<>(refreshTokenService
                    .refreshToken(tokenRequest.getRefreshToken(), tokenRequest.getScopes()),
                    HttpStatus.OK);
        } else if (tokenRequest.getGrantType().equalsIgnoreCase(GrantType.REVOKE.getType())) {
            refreshTokenService.revokeToken(tokenRequest.getRefreshToken());
            return new ResponseEntity<>(new TokenResponseDto(), HttpStatus.OK);
        }

        return null;
    }


}
