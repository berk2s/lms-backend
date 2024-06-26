package com.rocketteam.auth.web.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class JWKSetControllerTest {

    @Autowired
    MockMvc mockMvc;

    @DisplayName("Get Public Key Successfully")
    @Test
    void getPublicKeySuccessfully() throws Exception {
        mockMvc.perform(get(JWKSetController.ENDPOINT))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
