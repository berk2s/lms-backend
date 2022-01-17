package com.rocketteam.auth.security;

import com.rocketteam.auth.web.models.ErrorDesc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserAuthenticationProvider implements AuthenticationProvider {

    /** SecurityDetailsService injection **/
    private final SecurityUserDetailsService securityUserDetailsService;

    /** PasswordEncoder injection that returns BCryptPasswordEncoder instance */
    private final PasswordEncoder passwordEncoder;

    /**
     * Authenticates user with given identifier and Password
     * @param authentication contains identifier and password
     * @return The <code>UsernamePasswordAuthenticationToken</code> that contains authorities
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        SecurityUser securityUser =
                securityUserDetailsService.loadUserByUsername(username);

        if(passwordEncoder.matches(password, securityUser.getPassword())) {
            return new UsernamePasswordAuthenticationToken(username,
                    password, securityUser.getAuthorities());
        } else  {
            log.warn("Passwords are not matching [username: {}]", username);
            throw new BadCredentialsException(ErrorDesc.BAD_CREDENTIALS.getDesc());
        }
    }

    /**
     * Checks given authentication type is acceptable
     * @param authentication Object of authentication type
     * @return The status of acceptance
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
