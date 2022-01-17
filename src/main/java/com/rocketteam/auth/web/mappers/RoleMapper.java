package com.rocketteam.auth.web.mappers;

import com.rocketteam.auth.domain.Role;
import com.rocketteam.auth.web.models.RoleDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper(uses = {AuthorityMapper.class})
public interface RoleMapper {

    /**
     * Converts Role to RoleDto without authority details
     * @param role
     * @return RoleDto object
     */
    @Named("Pure")
    @Mappings({
            @Mapping(target = "roleId", expression = "java( role.getId() )"),
            @Mapping(source = "authorities", target = "authorities", ignore = true)
    })
    RoleDto roleToRoleDto(Role role);

    /**
     * Converts Role to RoleDto with authority details
     * @param role
     * @return RoleDto object
     */
    @Named("Detailed")
    @Mappings({
            @Mapping(target = "roleId", expression = "java( role.getId() )"),
            @Mapping(source = "authorities", target = "authorities", qualifiedByName = "AuthoritiesWithoutRole")
    })
    RoleDto roleToRoleDtoWithDetails(Role role);

}
