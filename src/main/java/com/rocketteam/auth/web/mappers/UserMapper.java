package com.rocketteam.auth.web.mappers;

import com.rocketteam.auth.domain.User;
import com.rocketteam.auth.web.models.RegisterUserDto;
import com.rocketteam.auth.web.models.UserDto;
import com.rocketteam.auth.web.models.UserInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(uses = {RoleMapper.class})
public interface UserMapper {

    /**
     * Converts RegisterUserDto to User Object
     * @param registerUserDto
     * @return User object
     */
    @Mappings({
            @Mapping(source = "firstName", target = "firstName"),
            @Mapping(source = "lastName", target = "lastName"),
            @Mapping(source = "phoneNumber", target = "phoneNumber"),
            @Mapping(source = "email", target = "email"),
    })
    User registerUserDtoToUser(RegisterUserDto registerUserDto);


    /**
     * Converts User object to UserDto
     * @param user
     * @return UserDto object
     */
    @Mappings({
            @Mapping(target = "userId", expression = "java( user.getId().toString() )"),
            @Mapping(source = "role", target = "role", qualifiedByName = "Detailed")
    })
    UserDto userToUserDto(User user);

    /**
     * Converts User to UserInfoDto
     * @param user
     * @return UserInfoDto object
     */
    @Mappings({
            @Mapping(target = "userId", expression = "java( user.getId().toString() )")
    })
    UserInfoDto userToUserInfoDto(User user);

}
