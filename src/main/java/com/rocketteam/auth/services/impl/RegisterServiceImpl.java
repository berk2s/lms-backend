package com.rocketteam.auth.services.impl;

import com.rocketteam.auth.domain.DefinedRoles;
import com.rocketteam.auth.domain.User;
import com.rocketteam.auth.repository.RoleRepository;
import com.rocketteam.auth.repository.UserRepository;
import com.rocketteam.auth.services.RegisterService;
import com.rocketteam.auth.services.RoleService;
import com.rocketteam.auth.services.ValidationService;
import com.rocketteam.auth.web.exceptions.UserAlreadyTakenException;
import com.rocketteam.auth.web.mappers.UserMapper;
import com.rocketteam.auth.web.models.ErrorDesc;
import com.rocketteam.auth.web.models.RegisterUserDto;
import com.rocketteam.auth.web.models.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RegisterServiceImpl implements RegisterService {

    /**
     * UserRepository injection
     */
    private final UserRepository userRepository;

    /**
     * RoleService injection
     */
    private final RoleService roleService;

    /**
     * ValidationService injection
     */
    private final ValidationService validationService;

    /** UserMapper injection */
    private final UserMapper userMapper;

    /** PasswordEncoder injection */
    private final PasswordEncoder passwordEncoder;

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDto register(RegisterUserDto registerUserDto) {
        if (!validationService.isEmailTaken(registerUserDto.getEmail()) || !validationService.isPhoneNumberTaken(registerUserDto.getPhoneNumber())) {
            log.warn("The email or phone number is already taken! [email: {}, phoneNumber: {}]", registerUserDto.getEmail(),
                    registerUserDto.getPassword());
            throw new UserAlreadyTakenException(ErrorDesc.USER_TAKEN.getDesc());
        }

        User user = roleService.assignRole(userMapper.registerUserDtoToUser(registerUserDto),
                DefinedRoles.CUSTOMER);
        user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));


        return userMapper.userToUserDto(userRepository.save(user));
    }
}
