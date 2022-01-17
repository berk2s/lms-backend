package com.rocketteam.auth.web.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.Duration;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenResponseDto {

    /** Access Token in JWT format */
    @JsonProperty("access_token")
    private String accessToken;

    /** Refresh Token in Opaque Format stored in database */
    @JsonProperty("refresh_token")
    private String refreshToken;

    /** Scopes which are assigned to access token */
    @JsonProperty("scopes")
    private String scopes;

    /** Expires time of Access Token */
    @JsonProperty("expires_in")
    private long expiresIn;

}
