package com.rocketteam.auth.services;

import com.rocketteam.auth.web.models.TokenResponseDto;

import java.util.UUID;

public interface RefreshTokenService {

    /**
     * Creates refresh token for the given user
     * @param userId
     * @return The opaque refresh token
     */
    String createRefreshToken(UUID userId);

    /**
     * Refreshes access token by given refresh token
     * @param refreshToken
     * @return The response that contain tokens
     */
    TokenResponseDto refreshToken(String refreshToken,
                                  String scopes);

    /**
     * Revokes a refresh token
     * @param token The token that will be revoked
     */
    void revokeToken(String token);

}
