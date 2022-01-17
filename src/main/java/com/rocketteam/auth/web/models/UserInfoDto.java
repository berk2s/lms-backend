package com.rocketteam.auth.web.models;

import lombok.Data;

@Data
public class UserInfoDto {

    private String userId;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

}
