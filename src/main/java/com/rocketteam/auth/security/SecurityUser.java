package com.rocketteam.auth.security;

import com.rocketteam.auth.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Transactional
public class SecurityUser implements UserDetails {

    /** User field */
    private final User user;

    /**
     * Creates an instance of SecurityUser
     * @param user The user instance that comes from database.
     */
    public SecurityUser(User user) {
        this.user = user;
    }

    /**
     * Gather together roles and authorities in to a list.
     * @return The collection of granted authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();

        if (user.getRole() != null) {
            grantedAuthorities
                    .add(new SimpleGrantedAuthority("ROLE_" + user.getRole().getRoleName().toUpperCase()));

            user.getRole().getAuthorities().forEach(authority -> {
                grantedAuthorities.add(new SimpleGrantedAuthority(authority.getAuthorityName().toUpperCase()));
            });
        }

        return grantedAuthorities;
    }

    /**
     * Returns password from User field.
     * @return The password of User
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Returns username from User field.
     * @return The username of User
     */
    @Override
    public String getUsername() {
        return user.getPhoneNumber();
    }

    /**
     * Returns account expired status.
     * @return Account expired status
     */
    @Override
    public boolean isAccountNonExpired() {
        return user.getIsAccountNonExpired();
    }

    /**
     * Returns account locked status.
     * @return Account locked status
     */
    @Override
    public boolean isAccountNonLocked() {
        return user.getIsAccountNonLocked();
    }

    /**
     * Returns credentials expired status.
     * @return Credentials expired status
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return user.getIsCredentialsNonExpired();
    }

    /**
     * Returns account enabled status.
     * @return Account enabled status
     */
    @Override
    public boolean isEnabled() {
        return user.getIsEnabled();
    }

    /**
     * Returns ID type of UUID of User
     * @return ID of User
     */
    public UUID getId() {
        return user.getId();
    }

    /**
     * Returns the User of defined in constructor
     * @return The object that is the User
     */
    public User getUser() {
        return this.user;
    }
}
