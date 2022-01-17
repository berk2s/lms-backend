package com.rocketteam.auth.web.models;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class LoginRequestDto {

    /** Identifier field for who will be authenticated */
    @Size(min = 3, max = 100, message = "Identifier must be between 3 and 100 characters")
    @NotNull(message = "Identifier cannot be null")
    private String identifier;

    /** Password field for who will be authenticated */
    @Size(min = 3, max = 100, message = "Password must be between 3 and 100 characters")
    @NotNull(message = "Password cannot be null")
    private String password;

    /** Requested scopes for who will be authenticated */
    @NotNull
    @Size(max = 300, message = "Scopes must not be longer than 300 character")
    private String scopes;

}
