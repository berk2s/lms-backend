package com.rocketteam.auth.services;

import com.rocketteam.auth.domain.DefinedRoles;
import com.rocketteam.auth.domain.User;

public interface RoleService {

    /**
     * Assigns a role to given User
     * @param user
     * @param givenRole
     * @return The object that assigned role
     */
    User assignRole(User user, DefinedRoles givenRole);

}
