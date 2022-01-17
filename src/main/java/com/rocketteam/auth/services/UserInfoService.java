package com.rocketteam.auth.services;

import com.rocketteam.auth.web.models.UserInfoDto;

public interface UserInfoService {

    /**
     * Generates User info endpoints
     * Method takes User id from the authentication object
     * @return The object that contains information about the User
     */
    UserInfoDto getUserInfo();

}
