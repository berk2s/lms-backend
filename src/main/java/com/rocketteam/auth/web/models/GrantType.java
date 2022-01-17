package com.rocketteam.auth.web.models;

import lombok.Getter;

@Getter
public enum GrantType {
    REFRESH_TOKEN("refresh_token"),
    REVOKE("revoke");

    final String type;

    GrantType(String type) {
        this.type = type;
    }
}
