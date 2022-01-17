package com.rocketteam.auth.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rocketteam.auth.domain.User;
import com.rocketteam.auth.repository.UserRepository;
import com.rocketteam.auth.web.models.ErrorDesc;
import com.rocketteam.auth.web.models.ErrorType;
import com.rocketteam.auth.web.models.RegisterUserDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RegisterControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    RegisterUserDto registerUserDto;

    @BeforeEach
    void setUp() {
        registerUserDto = new RegisterUserDto();
        registerUserDto.setEmail(RandomStringUtils.random(10, true, false));
        registerUserDto.setFirstName(RandomStringUtils.random(10, true, false));
        registerUserDto.setLastName(RandomStringUtils.random(10, true, false));
        registerUserDto.setPhoneNumber(RandomStringUtils.random(10, true, false));
        registerUserDto.setPassword(RandomStringUtils.random(10, true, false));
    }

    @DisplayName("Register User Successfully")
    @Test
    void registerUserSuccessfully() throws Exception {

        mockMvc.perform(post(RegisterController.ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(registerUserDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").isNotEmpty())
                .andExpect(jsonPath("$.firstName", is(registerUserDto.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(registerUserDto.getLastName())))
                .andExpect(jsonPath("$.phoneNumber", is(registerUserDto.getPhoneNumber())))
                .andExpect(jsonPath("$.email", is(registerUserDto.getEmail())))
                .andExpect(jsonPath("$.role.roleId").isNotEmpty())
                .andExpect(jsonPath("$.role.roleName").isNotEmpty())
                .andExpect(jsonPath("$.isAccountNonLocked", is(true)))
                .andExpect(jsonPath("$.isAccountNonExpired", is(true)))
                .andExpect(jsonPath("$.isCredentialsNonExpired", is(true)))
                .andExpect(jsonPath("$.isEnabled", is(true)))
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.lastModifiedAt").isNotEmpty());

    }

    @DisplayName("Register User with Taken Email")
    @Test
    void registerUserWithTakenEmail() throws Exception {

        User user = new User();
        user.setEmail(RandomStringUtils.random(10, true, false));

        userRepository.save(user);

        registerUserDto.setEmail(user.getEmail());

        mockMvc.perform(post(RegisterController.ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(registerUserDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is(ErrorType.INVALID_REQUEST.getError())))
                .andExpect(jsonPath("$.error_description", is(ErrorDesc.USER_TAKEN.getDesc())));

    }

    @DisplayName("Register User with Taken Phone Number")
    @Test
    void registerUserWithTakenPhoneNumber() throws Exception {

        User user = new User();
        user.setPhoneNumber(RandomStringUtils.random(10, true, false));

        userRepository.save(user);

        registerUserDto.setPhoneNumber(user.getPhoneNumber());

        mockMvc.perform(post(RegisterController.ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(registerUserDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is(ErrorType.INVALID_REQUEST.getError())))
                .andExpect(jsonPath("$.error_description", is(ErrorDesc.USER_TAKEN.getDesc())));

    }

}
