package com.rocketteam.auth.web.mappers;

import com.rocketteam.auth.domain.Authority;
import com.rocketteam.auth.web.models.AuthorityDto;
import org.mapstruct.*;

import java.util.List;

@Mapper()
public interface AuthorityMapper {

    /**
     * Converts Authority to AuthorityDto
     * @param authority
     * @return AuthorityDto object
     */
    @Named("AuthorityWithoutRole")
    @Mappings({
            @Mapping(target = "authorityId", expression = "java( authority.getId() )"),
            @Mapping(source = "roles", target = "roles", ignore = true)
    })
    AuthorityDto authorityToAuthorityDto(Authority authority);

    /**
     * Converts Authorities to AuthorityDto
     * @param authorities
     * @return List of AuthorityDto
     */
    @Named("AuthoritiesWithoutRole")
    @IterableMapping(qualifiedByName = "AuthorityWithoutRole")
    List<AuthorityDto> authoritiesToAuthorityDto(List<Authority> authorities);

}
