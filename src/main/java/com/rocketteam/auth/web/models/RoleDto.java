package com.rocketteam.auth.web.models;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class RoleDto {

    /** ID of Role */
    private Long roleId;

    /** Name of Role */
    private String roleName;

    /** Assigned authorities of Role */
    private List<AuthorityDto> authorities;

    /** Creation timestamp of Role */
    private Timestamp createdAt;

    /** Updated timestamp of Role */
    private Timestamp lastModifiedAt;

}
