package com.rocketteam.auth.services.impl;

import com.rocketteam.auth.services.ScopeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ScopeServiceImpl implements ScopeService {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkScopes(Collection<? extends GrantedAuthority> grantedAuthorities,
                               List<String> scopes) {
        return scopes.stream().allMatch(scope -> grantedAuthorities.stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().toUpperCase().equalsIgnoreCase(scope)));
    }
}
