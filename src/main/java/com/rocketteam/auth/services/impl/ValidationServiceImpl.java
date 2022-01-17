package com.rocketteam.auth.services.impl;

import com.rocketteam.auth.repository.UserRepository;
import com.rocketteam.auth.services.ValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ValidationServiceImpl implements ValidationService {

    /** UserRepository injection */
    private final UserRepository userRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmailTaken(String email) {
        return !userRepository.existsByEmail(email);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPhoneNumberTaken(String phoneNumber) {
        return !userRepository.existsByPhoneNumber(phoneNumber);
    }
}
