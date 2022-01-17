package com.rocketteam.auth.services.impl;

import com.nimbusds.jwt.SignedJWT;
import com.rocketteam.auth.security.SecurityUser;
import com.rocketteam.auth.services.AccessTokenService;
import com.rocketteam.auth.services.JwtService;
import com.rocketteam.auth.web.models.CreateJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccessTokenServiceImpl implements AccessTokenService {

    /** JwtService injection */
    private final JwtService jwtService;

    /**
     * {@inheritDoc}
     */
    @Override
    public String createAccessToken(SecurityUser securityUser, String scopes) {
        CreateJWT createJWT = CreateJWT.builder()
                .username(securityUser.getUsername())
                .userId(securityUser.getId())
                .scopes(scopes)
                .build();

        return jwtService.createJwt(createJWT).serialize();
    }
}
