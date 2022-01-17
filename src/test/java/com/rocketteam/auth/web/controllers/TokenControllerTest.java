package com.rocketteam.auth.web.controllers;

import com.rocketteam.auth.config.ServerConfiguration;
import com.rocketteam.auth.domain.Authority;
import com.rocketteam.auth.domain.Role;
import com.rocketteam.auth.domain.User;
import com.rocketteam.auth.repository.AuthorityRepository;
import com.rocketteam.auth.repository.RefreshTokenRepository;
import com.rocketteam.auth.repository.RoleRepository;
import com.rocketteam.auth.repository.UserRepository;
import com.rocketteam.auth.services.AccessTokenService;
import com.rocketteam.auth.services.RefreshTokenService;
import com.rocketteam.auth.web.models.ErrorDesc;
import com.rocketteam.auth.web.models.ErrorType;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TokenControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccessTokenService accessTokenService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    ServerConfiguration serverConfiguration;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AuthorityRepository authorityRepository;

    User user;
    String refreshToken;
    Authority authority;
    Role role;

    @BeforeEach
    void setUp() {
        authority = new Authority();
        authority.setAuthorityName(RandomStringUtils.random(10, true, false));

        authorityRepository.save(authority);

        role = new Role();
        role.setRoleName(RandomStringUtils.random(10, true, false));
        role.getAuthorities().add(authority);

        roleRepository.save(role);

        user = new User();
        user.setFirstName(RandomStringUtils.random(10, true, false));
        user.setLastName(RandomStringUtils.random(10, true, false));
        user.setEmail(RandomStringUtils.random(10, true, false));
        user.setPhoneNumber(RandomStringUtils.random(10, true, false));
        user.setRole(role);

        userRepository.save(user);

        refreshToken = refreshTokenService.createRefreshToken(user.getId());
    }

    @DisplayName("Revoking Refresh Token")
    @Nested
    class RevokeRefreshToken {

        @Autowired
        RefreshTokenRepository refreshTokenRepository;

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        @BeforeEach
        void setUp() {
            params.set("client_id", "clientId");
            params.set("grant_type", "revoke");
            params.set("refresh_token", refreshToken);
        }

        @DisplayName("Revoke Refresh Token Successfully")
        @Test
        void revokeRefreshTokenSuccessfully() throws Exception {

            mockMvc.perform(post(TokenController.ENDPOINT)
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                            .params(params))
                    .andDo(print())
                    .andExpect(status().isOk());

            boolean isRefreshTokenExists = refreshTokenRepository.existsByToken(refreshToken);

            assertThat(isRefreshTokenExists)
                    .isEqualTo(false);
        }

        @DisplayName("Revoke Refresh Token Not Found Error")
        @Test
        void revokeRefreshTokenNotFoundError() throws Exception {
            params.set("refresh_token", RandomStringUtils.random(48, true, false));

            mockMvc.perform(post(TokenController.ENDPOINT)
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                            .params(params))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.error", is(ErrorType.INVALID_REQUEST.getError())))
                    .andExpect(jsonPath("$.error_description", is(ErrorDesc.REFRESH_TOKEN_NOT_FOUND.getDesc())));
        }

    }

    @DisplayName("Refreshing a Token")
    @Nested
    class RefreshingToken {

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        @BeforeEach
        void setUp() {
            params.set("client_id", "clientId");
            params.set("grant_type", "refresh_token");
            params.set("refresh_token", refreshToken);
            params.set("scopes", authority.getAuthorityName());
        }

        @DisplayName("Refresh Access Token Successfully")
        @Test
        void refreshAccessTokenSuccessfully() throws Exception {
            mockMvc.perform(post(TokenController.ENDPOINT)
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .params(params))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.access_token", matchesPattern("^[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.?[A-Za-z0-9-_.+/=]*$")))
                    .andExpect(jsonPath("$.refresh_token", is(refreshToken)))
                    .andExpect(jsonPath("$.scopes", is(authority.getAuthorityName())))
                    .andExpect(jsonPath("$.expires_in", is((int) serverConfiguration.getAccessToken().getLifetime().getSeconds())));

        }

    }

}
