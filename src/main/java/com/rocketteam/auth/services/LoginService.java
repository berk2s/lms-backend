package com.rocketteam.auth.services;

import com.rocketteam.auth.web.models.LoginRequestDto;
import com.rocketteam.auth.web.models.TokenResponseDto;

public interface LoginService {

    /**
     * Performs Login Action
     * @param loginRequestDto contains login information (username and password)
     * @return The token response contains access token, refresh token, scopes, and expires in
     **/
    TokenResponseDto login(LoginRequestDto loginRequestDto);

}
