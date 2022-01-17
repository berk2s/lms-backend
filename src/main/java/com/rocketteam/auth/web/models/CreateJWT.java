package com.rocketteam.auth.web.models;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CreateJWT {

    /** ID of User */
    private UUID userId;

    /** Username of User */
    private String username;

    /** Scopes of User */
    private String scopes;

}
