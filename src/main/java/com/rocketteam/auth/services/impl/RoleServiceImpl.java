package com.rocketteam.auth.services.impl;

import com.rocketteam.auth.domain.DefinedRoles;
import com.rocketteam.auth.domain.Role;
import com.rocketteam.auth.domain.User;
import com.rocketteam.auth.repository.RoleRepository;
import com.rocketteam.auth.services.RoleService;
import com.rocketteam.auth.web.models.ErrorDesc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import javax.persistence.EntityNotFoundException;

@Slf4j
@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    /**
     * RoleRepository injection
     */
    private final RoleRepository roleRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public User assignRole(User user, DefinedRoles givenRole) {
        Role role = roleRepository.findByRoleName(givenRole.getName())
                .orElseThrow(() -> {
                   log.warn("Role with given id does not exists [roleName: {}]", givenRole.getName());
                   throw new EntityNotFoundException(ErrorDesc.ROLE_NOT_FOUND.getDesc());
                });

        user.setRole(role);

        return user;
    }
}
