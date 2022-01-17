package com.rocketteam.auth.services.impl;

import com.rocketteam.auth.security.SecurityUser;
import com.rocketteam.auth.security.SecurityUserDetailsService;
import com.rocketteam.auth.security.UserAuthenticationProvider;
import com.rocketteam.auth.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    /** UserAuthentication injection */
    private final UserAuthenticationProvider userAuthenticationProvider;

    /** UserDetailsService injection */
    private final SecurityUserDetailsService securityUserDetailsService;

    /**
     * {@inheritDoc}
     */
    @Override
    public SecurityUser authenticate(String identifier, String password) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(identifier, password);

        userAuthenticationProvider.authenticate(token);

        SecurityUser securityUser = securityUserDetailsService.loadUserByUsername(identifier);

        return securityUser;
    }
}
