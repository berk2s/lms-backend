package com.rocketteam.auth.web.controllers;

import com.rocketteam.auth.config.JwtPkiConfiguration;
import com.rocketteam.auth.web.models.ErrorResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "JWK Set Controller", description = "Exposes JWK Set Endpoint")
@RequiredArgsConstructor
@RequestMapping(JWKSetController.ENDPOINT)
@RestController
public class JWKSetController {

    /** Defined endpoint of the Controller */
    public static final String ENDPOINT = "/jwks";

    /** JwtPkiConfiguration injection */
    private final JwtPkiConfiguration jwtPkiConfiguration;

    /**
     * Generates public key for JWT validations
     * @return The object that contains public key
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
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getJWKSet() {
        return jwtPkiConfiguration.getJwkSet().toJSONObject();
    }

}
