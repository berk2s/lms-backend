package com.rocketteam.auth.web.models;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class AuthorityDto {

    /** ID of Authority */
    private Long authorityId;

    /** Name of Authority */
    private String authorityName;

    /** Roles of Authority */
    private List<RoleDto> roles;

    /** Creation timestamp of Authority */
    private Timestamp createdAt;

    /** Updated timestamp of Authority */
    private Timestamp lastModifiedAt;

}
