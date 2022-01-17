package com.rocketteam.auth.services.impl;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.rocketteam.auth.config.JwtPkiConfiguration;
import com.rocketteam.auth.config.ServerConfiguration;
import com.rocketteam.auth.services.JwtService;
import com.rocketteam.auth.web.exceptions.JWTException;
import com.rocketteam.auth.web.models.CreateJWT;
import com.rocketteam.auth.web.models.ErrorDesc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.IdGenerator;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class JwtServiceImpl implements JwtService {

    /** IDGenerator injection which is JdkIdGenerator instance */
    private final IdGenerator idGenerator;

    /** ServerConfiguration injection */
    private final ServerConfiguration serverConfiguration;

    /** JwtPkiConfiguration injection */
    private final JwtPkiConfiguration jwtPkiConfiguration;

    /**
     * {@inheritDoc}
     */
    @Override
    public SignedJWT createJwt(CreateJWT createJWT) {
        LocalDateTime expiryDateTime = LocalDateTime.now().plusMinutes(serverConfiguration.getAccessToken().getLifetime().toMinutes());

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(createJWT.getUserId().toString())
                .jwtID(idGenerator.generateId().toString())
                .issuer(serverConfiguration.getServerUrl())
                .issueTime(new Date())
                .notBeforeTime(new Date())
                .expirationTime(Date.from(expiryDateTime.atZone(ZoneId.systemDefault()).toInstant()))
                .claim("username", createJWT.getUsername())
                .claim("scopes", createJWT.getScopes())
                .build();

        return signJWT(jwtClaimsSet);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SignedJWT signJWT(JWTClaimsSet jwtClaimsSet) {
        try {
            JWSHeader.Builder jwsHeader = new JWSHeader.Builder(JWSAlgorithm.RS256);
            jwsHeader.keyID(jwtPkiConfiguration.getPublicKey().getKeyID());

            SignedJWT signedJWT =  new SignedJWT(jwsHeader.build(), jwtClaimsSet);
            signedJWT.sign(jwtPkiConfiguration.getJwsSigner());

            return signedJWT;
        } catch (JOSEException ex) {
            log.warn("Error while signing jwt [message: {}]", ex.getMessage());
            throw new JWTException(ErrorDesc.SERVER_ERROR.getDesc());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JWTClaimsSet parse(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            signedJWT.verify(jwtPkiConfiguration.getJwsVerifier());

            return signedJWT.getJWTClaimsSet();
        } catch (JOSEException | ParseException ex) {
            log.warn("Error while parsing jwt [message: {}]", ex.getMessage());
            throw new JWTException(ErrorDesc.INVALID_TOKEN.getDesc());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.verify(jwtPkiConfiguration.getJwsVerifier());
        } catch (JOSEException | ParseException ex) {
            log.warn("Error while parsing jwt [message: {}]", ex.getMessage());
            throw new JWTException(ErrorDesc.INVALID_TOKEN.getDesc());
        }
    }
}
