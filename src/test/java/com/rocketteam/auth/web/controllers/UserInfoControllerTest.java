package com.rocketteam.auth.web.controllers;

import com.rocketteam.auth.domain.Authority;
import com.rocketteam.auth.domain.Role;
import com.rocketteam.auth.domain.User;
import com.rocketteam.auth.repository.AuthorityRepository;
import com.rocketteam.auth.repository.RoleRepository;
import com.rocketteam.auth.repository.UserRepository;
import com.rocketteam.auth.security.SecurityUser;
import com.rocketteam.auth.services.AccessTokenService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserInfoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccessTokenService accessTokenService;

    User user;
    String accessToken;

    @Transactional
    @BeforeEach
    void setUp() {

        user = new User();
        user.setFirstName(RandomStringUtils.random(10, true, false));
        user.setLastName(RandomStringUtils.random(10, true, false));
        user.setEmail(RandomStringUtils.random(10, true, false));
        user.setPhoneNumber(RandomStringUtils.random(10, true, false));

        userRepository.save(user);

        accessToken = accessTokenService.createAccessToken(new SecurityUser(user), "");
    }

    @DisplayName("Get User Info Successfully")
    @Test
    void getUserInfoSuccessfully() throws Exception {
        mockMvc.perform(get(UserInfoController.ENDPOINT)
                .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is(user.getId().toString())))
                .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(user.getLastName())))
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.phoneNumber", is(user.getPhoneNumber())));
    }

}
