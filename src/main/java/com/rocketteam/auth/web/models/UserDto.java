package com.rocketteam.auth.web.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.sql.Timestamp;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    /** ID of User */
    private String userId;

    /** First name of User */
    private String firstName;

    /** Last name of User */
    private String lastName;

    /** Phone number of User */
    private String phoneNumber;

    /** Unique Email of User */
    private String email;

    /** Role of User */
    private RoleDto role;

    /** Account locked status of User */
    private Boolean isAccountNonLocked;

    /** Account expired status of User */
    private Boolean isAccountNonExpired;

    /** Credentials expired status of User */
    private Boolean isCredentialsNonExpired;

    /** Account enabled status of User */
    private Boolean isEnabled;

    /** Creation timestamp of User */
    private Timestamp createdAt;

    /** Updated timestamp of User */
    private Timestamp lastModifiedAt;

}
