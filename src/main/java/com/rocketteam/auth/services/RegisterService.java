package com.rocketteam.auth.services;

import com.rocketteam.auth.web.models.RegisterUserDto;
import com.rocketteam.auth.web.models.UserDto;

public interface RegisterService {

    /**
     * Gets an argument that contains information about user that will be registered
     * @param registerUserDto Contains information about user that will be registered
     * @return The object that contains information about registered user.
     */
    UserDto register(RegisterUserDto registerUserDto);

}
