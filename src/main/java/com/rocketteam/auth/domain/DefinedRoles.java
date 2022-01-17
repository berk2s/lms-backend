package com.rocketteam.auth.domain;

import lombok.Getter;

@Getter
public enum DefinedRoles {
    CUSTOMER("CUSTOMER");

    private final String name;

    DefinedRoles(String name) {
        this.name = name;
    }
}
