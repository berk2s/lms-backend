package com.rocketteam.auth.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ConfigurationProperties("authserver.auth-config")
@Component
public class ServerConfiguration {

    /**
     * Server URL
     */
    private String serverUrl;

    /**
     * Given access token configuration
     */
    private TokenConfiguration accessToken;

    /**
     * Given refresh token configuration
     */
    private TokenConfiguration refreshToken;

}
