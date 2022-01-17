package com.rocketteam.auth.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rocketteam.auth.domain.Authority;
import com.rocketteam.auth.domain.Role;
import com.rocketteam.auth.domain.User;
import com.rocketteam.auth.repository.AuthorityRepository;
import com.rocketteam.auth.repository.RoleRepository;
import com.rocketteam.auth.repository.UserRepository;
import com.rocketteam.auth.web.models.ErrorDesc;
import com.rocketteam.auth.web.models.ErrorType;
import com.rocketteam.auth.web.models.LoginRequestDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    User user;
    Authority authority;
    Role role;
    LoginRequestDto loginRequestDto;
    String pwd;

    @BeforeEach
    void setUp() {
        pwd = RandomStringUtils.random(10, true, true);

        authority = new Authority();
        authority.setAuthorityName(RandomStringUtils.random(10, true, false));

        authorityRepository.save(authority);

        role = new Role();
        role.setRoleName(RandomStringUtils.random(10, true, false));
        role.getAuthorities().add(authority);

        roleRepository.save(role);

        user = new User();
        user.setEmail(RandomStringUtils.random(10, true, true));
        user.setPhoneNumber(RandomStringUtils.random(10, true, true));
        user.setPassword(passwordEncoder.encode(pwd));
        user.setRole(role);

        userRepository.save(user);

        loginRequestDto = new LoginRequestDto();
        loginRequestDto.setIdentifier(user.getEmail());
        loginRequestDto.setPassword(pwd);
        loginRequestDto.setScopes(authority.getAuthorityName());
    }

    @DisplayName("Login User Via Email Successfully")
    @Test
    void loginUserViaEmailSuccessfully() throws Exception {

        mockMvc.perform(post(LoginController.ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDto)))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @DisplayName("Login User Via Phone Number Successfully")
    @Test
    void loginUserViaPhoneNumberSuccessfully() throws Exception {

        loginRequestDto.setIdentifier(user.getPhoneNumber());

        mockMvc.perform(post(LoginController.ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token", matchesPattern("^[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.?[A-Za-z0-9-_.+/=]*$")))
                .andExpect(jsonPath("$.refresh_token", hasLength(48)));

    }

    @DisplayName("Login with Invalid Credentials")
    @Test
    void loginWithInvalidCredentials() throws Exception {

        loginRequestDto.setIdentifier(RandomStringUtils.random(10, true, false));

        mockMvc.perform(post(LoginController.ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDto)))
                .andDo(print())
                .andExpect(status().isForbidden());

    }

    @DisplayName("Login User Via Invalid Scopes")
    @Test
    void loginUserViaInvalidScopes() throws Exception {
        loginRequestDto.setScopes(RandomStringUtils.random(10, true, false));
        mockMvc.perform(post(LoginController.ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDto)))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error", is(ErrorType.INVALID_REQUEST.getError())))
                .andExpect(jsonPath("$.error_description", is(ErrorDesc.SCOPES_NOT_PERMITTED.getDesc())));
    }

}
