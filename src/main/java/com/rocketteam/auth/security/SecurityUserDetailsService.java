package com.rocketteam.auth.security;

import com.rocketteam.auth.domain.User;
import com.rocketteam.auth.repository.UserRepository;
import com.rocketteam.auth.web.models.ErrorDesc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class SecurityUserDetailsService implements UserDetailsService {

    /** UserRepository injection */
    private final UserRepository userRepository;

    /**
     * Looks to database in order to find user with given identifier
     * @param identifier An identifier of user.
     *                   It could be email or phone number
     * @return The SecurityUser contains information about logged User.
     * @throws UsernameNotFoundException
     */
    @Override
    public SecurityUser loadUserByUsername(String identifier) throws UsernameNotFoundException {
        User user = userRepository.findUserByIdentifier(identifier)
                .orElseThrow(() -> {
                   log.warn("User not found with given identifier [identifier: {}]", identifier);
                   throw new UsernameNotFoundException(ErrorDesc.USERNAME_NOT_FOUND.getDesc());
                });

        return new SecurityUser(user);
    }
}
