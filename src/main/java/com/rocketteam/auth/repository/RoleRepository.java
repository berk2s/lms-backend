package com.rocketteam.auth.repository;

import com.rocketteam.auth.domain.Role;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {

    Optional<Role> findByRoleName(String roleName);

}
