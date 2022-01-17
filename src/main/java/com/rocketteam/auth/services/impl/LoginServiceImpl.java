package com.rocketteam.auth.services.impl;

import com.rocketteam.auth.config.ServerConfiguration;
import com.rocketteam.auth.security.SecurityUser;
import com.rocketteam.auth.services.*;
import com.rocketteam.auth.web.exceptions.InvalidGrantException;
import com.rocketteam.auth.web.models.ErrorDesc;
import com.rocketteam.auth.web.models.LoginRequestDto;
import com.rocketteam.auth.web.models.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {

    /**
     * AuthenticationService injection that returns AuthenticationServiceImpl
     */
    private final AuthenticationService authenticationService;

    /**
     * AccessTokenService injection
     */
    private final AccessTokenService accessTokenService;

    /**
     * RefreshTokenService injection
     */
    private final RefreshTokenService refreshTokenService;

    /** ServerConfiguration injection **/
    private final ServerConfiguration serverConfiguration;

    /**
     * ScopeService injection
     */
    private final ScopeService scopeService;

    /**
     * {@inheritDoc}
     */
    @Override
    public TokenResponseDto login(LoginRequestDto loginRequestDto) {
        SecurityUser securityUser = authenticationService.authenticate(loginRequestDto.getIdentifier(),
                loginRequestDto.getPassword());

        List<String> givenScopes = new ArrayList<>(Arrays.asList(loginRequestDto.getScopes().trim().split(" ")));

        if(!scopeService.checkScopes(securityUser.getAuthorities(), givenScopes)) {
            log.warn("Some of or all of them of scopes are not permitted to the User [identifier: {}, scopes: {}]",
                    loginRequestDto.getIdentifier(), loginRequestDto.getScopes());
            throw new InvalidGrantException(ErrorDesc.SCOPES_NOT_PERMITTED.getDesc());
        }

        String accessToken = accessTokenService.createAccessToken(securityUser, loginRequestDto.getScopes());
        String refreshToken = refreshTokenService.createRefreshToken(securityUser.getId());

        return TokenResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(serverConfiguration.getAccessToken().getLifetime().getSeconds())
                .scopes(loginRequestDto.getScopes())
                .build();
    }
}
