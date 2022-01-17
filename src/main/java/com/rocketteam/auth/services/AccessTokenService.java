package com.rocketteam.auth.services;

import com.rocketteam.auth.security.SecurityUser;

public interface AccessTokenService {

    /**
     * Creates Access Token which in JWT format
     * @param securityUser Holds User information
     * @param scopes Scopes that requested
     * @return The object that contains plain token
     */
    String createAccessToken(SecurityUser securityUser, String scopes);

}
