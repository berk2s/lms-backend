package com.rocketteam.auth.services.impl;

import com.rocketteam.auth.config.ServerConfiguration;
import com.rocketteam.auth.domain.RefreshToken;
import com.rocketteam.auth.domain.User;
import com.rocketteam.auth.repository.RefreshTokenRepository;
import com.rocketteam.auth.repository.UserRepository;
import com.rocketteam.auth.security.SecurityUser;
import com.rocketteam.auth.services.AccessTokenService;
import com.rocketteam.auth.services.RefreshTokenService;
import com.rocketteam.auth.utils.TokenUtils;
import com.rocketteam.auth.web.exceptions.InvalidGrantException;
import com.rocketteam.auth.web.models.ErrorDesc;
import com.rocketteam.auth.web.models.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Transactional
@Slf4j
@RequiredArgsConstructor
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    /** RefreshTokenRepository injection */
    private final RefreshTokenRepository refreshTokenRepository;

    /** UserRepository injection */
    private final UserRepository userRepository;

    /** ServerConfiguration injection */
    private final ServerConfiguration serverConfiguration;

    /** AccessTokenService injection */
    private final AccessTokenService accessTokenService;

    /**
     * {@inheritDoc}
     * @throws javax.persistence.EntityNotFoundException
     */
    @Override
    public String createRefreshToken(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                   log.warn("User with given id does not exists [userId: {}]", userId);
                   throw new EntityNotFoundException(ErrorDesc.USER_NOT_FOUND.getDesc());
                });

        String generatedToken = RandomStringUtils.random(48, true, true);

        Duration refreshTokenDuration = serverConfiguration.getRefreshToken().getLifetime();

        LocalDateTime issuedAt = LocalDateTime.now();

        LocalDateTime expiryDateTime = LocalDateTime.now()
                .plusMinutes(refreshTokenDuration.toMinutes());

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(generatedToken);
        refreshToken.setIssueTime(issuedAt);
        refreshToken.setExpiryDateTime(expiryDateTime);
        refreshToken.setNotBefore(LocalDateTime.now());
        refreshToken.setUser(user);

        refreshTokenRepository.save(refreshToken);

        return refreshToken.getToken();
    }

    /**
     * {@inheritDoc}
     * @throws javax.persistence.EntityNotFoundException
     * @throws InvalidGrantException
     */
    @Override
    public TokenResponseDto refreshToken(String token, String scopes) {
        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(token)
                .orElseThrow(() -> {
                    log.warn("Refresh token with given id does not exists [token: {}]", token);
                    throw new EntityNotFoundException(ErrorDesc.REFRESH_TOKEN_NOT_FOUND.getDesc());
                });

        if (TokenUtils.isTokenExpired(refreshToken.getExpiryDateTime())) {
            log.warn("Token has been expired [token: {}, tokenId: {}]",
                    token, refreshToken.getId());
            refreshTokenRepository.delete(refreshToken);
            throw new InvalidGrantException(ErrorDesc.UNAUTHORIZED.getDesc());
        }

        String accessToken = accessTokenService.createAccessToken(new SecurityUser(refreshToken.getUser()),
                scopes);

        log.info("Access token has been created successfully");

        return TokenResponseDto.builder()
                .refreshToken(token)
                .accessToken(accessToken)
                .scopes(scopes)
                .expiresIn(serverConfiguration.getAccessToken().getLifetime().getSeconds())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void revokeToken(String token) {
        if (!refreshTokenRepository.existsByToken(token)) {
            log.warn("Refresh token with given id does not exists");
            throw new EntityNotFoundException(ErrorDesc.REFRESH_TOKEN_NOT_FOUND.getDesc());
        }

        refreshTokenRepository
                .deleteByToken(token);

        log.info("Refresh token has been deleted [performedBy: {}]",
                SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
