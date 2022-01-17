package com.rocketteam.auth.services;

import com.rocketteam.auth.security.SecurityUser;

public interface AuthenticationService {

    /**
     * Aggregates and connects AuthenticationProvider and UserDetailsService
     * @param identifier points to email or phone number
     * @param password
     * @return The SecurityUser that contains information of authenticated User
     */
    SecurityUser authenticate(String identifier, String password);

}
