package com.rocketteam.auth.services;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.rocketteam.auth.web.models.CreateJWT;

public interface JwtService {

    /**
     * Creates JWT with given criterias
     * @param createJWT Contains information about claims of token
     * @return The object that is signed JWT
     */
    SignedJWT createJwt(CreateJWT createJWT);

    /**
     * Signes JWT with private key
     * @param jwtClaimsSet contains information about token's payload
     * @return Signed JWT
     */
    SignedJWT signJWT(JWTClaimsSet jwtClaimsSet);

    /**
     * Parses JWT
     * @param token that will be parsed
     * @return The object that contains payload information
     */
    JWTClaimsSet parse(String token);

    /**
     * Validates token if it is valid or not
     * @param token that will be validated
     * @return Status of validation
     */
    boolean validate(String token);

}
