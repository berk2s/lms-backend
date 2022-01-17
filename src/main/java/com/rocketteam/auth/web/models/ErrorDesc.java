package com.rocketteam.auth.web.models;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum ErrorDesc {

    USERNAME_NOT_FOUND("User not found by given username", 1),
    BAD_CREDENTIALS("Bad credentials",2),
    INVALID_TOKEN("Invalid token", 3),
    USER_HAS_NOT_SCOPE("Invalid scope requested for user", 4),
    BAD_REQUEST("Bad request", 5),
    USER_TAKEN("Email or phone number already taken", 6),
    ROLE_NOT_FOUND("Role not found", 7),
    SERVER_ERROR("Server error", 8),
    USER_NOT_FOUND("User not found", 9),
    SCOPES_NOT_PERMITTED("Some of or all of Scopes are not permitted for the User", 10),
    REFRESH_TOKEN_NOT_FOUND("Refresh token was not found", 11),
    UNAUTHORIZED("Unauthorized", 12);

    private final String desc;
    private final Integer code;
    private static final Map<String, Integer> errorMap =  new HashMap<>();

    ErrorDesc(String desc, Integer code) {
        this.desc = desc;
        this.code = code;
    }

    static {
        for (ErrorDesc errorDesc : ErrorDesc.values()) {
            errorMap.put(errorDesc.getDesc(), errorDesc.getCode());
        }
    }

    static public Integer getCodeFormDesc(String desc) {
        return errorMap.get(desc);
    }
}
