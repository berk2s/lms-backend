package com.rocketteam.auth.services.impl;

import com.rocketteam.auth.security.SecurityUser;
import com.rocketteam.auth.services.UserInfoService;
import com.rocketteam.auth.web.mappers.UserMapper;
import com.rocketteam.auth.web.models.UserInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserInfoServiceImpl implements UserInfoService {

    /** UserMapper injection */
    private final UserMapper userMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public UserInfoDto getUserInfo() {
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userMapper.userToUserInfoDto(securityUser.getUser());
    }
}
