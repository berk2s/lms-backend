package com.rocketteam.auth.services;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public interface ScopeService {

    /**
     * Checks if given scopes are permitted to User
     * @param grantedAuthorities List of scopes which User belongs
     * @param scopes List of given scopes
     * @return Boolean that means scopes are available for User
     */
    boolean checkScopes(Collection<? extends GrantedAuthority> grantedAuthorities,
                        List<String> scopes);

}
