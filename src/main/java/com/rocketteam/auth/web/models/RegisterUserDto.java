package com.rocketteam.auth.web.models;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RegisterUserDto {

    /** Given first name of User */
    @NotNull
    @Size(min = 3, max = 100, message = "First name must be between 3 and 100 characters")
    private String firstName;

    /** Given last name of User */
    @NotNull
    @Size(min = 3, max = 100, message = "Last name must be between 3 and 100 characters")
    private String lastName;

    /** Given password of User */
    @NotNull
    @Size(min = 3, max = 100, message = "Password must be between 3 and 100 characters")
    private String password;

    /** Given email of User */
    @NotNull
    @Size(min = 3, max = 100, message = "Email must be between 3 and 100 characters")
    private String email;

    /** Given phone number of User */
    @NotNull
    @Size(min = 10, max = 11, message = "Phone number must be between 10 and 11 characters")
    private String phoneNumber;

}
