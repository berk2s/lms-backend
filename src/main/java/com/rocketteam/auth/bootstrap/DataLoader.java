package com.rocketteam.auth.bootstrap;

import com.rocketteam.auth.domain.Authority;
import com.rocketteam.auth.domain.DefinedRoles;
import com.rocketteam.auth.domain.Role;
import com.rocketteam.auth.repository.AuthorityRepository;
import com.rocketteam.auth.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {

    /** RoleRepository injection */
    private final RoleRepository roleRepository;

    /** AuthorityRepository injection */
    private final AuthorityRepository authorityRepository;

    /**
     * Loads initial data while application is starting to run
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        loadRoles();
    }

    private void loadRoles() {
        List<Authority> authorities = new ArrayList<>();

        List<String> authorityNames = new ArrayList<>();
        authorityNames.add("profile:manage");
        authorityNames.add("common:courses");

        authorityNames.forEach(authorityName -> {
            Authority authority = new Authority();
            authority.setAuthorityName(authorityName);

            authorityRepository.save(authority);

            authorities.add(authority);
        });

        Role role = new Role();
        role.setRoleName(DefinedRoles.CUSTOMER.getName());

        authorities.forEach(authority -> {
            role.getAuthorities().add(authority);
        });

        roleRepository.save(role);
    }
}
