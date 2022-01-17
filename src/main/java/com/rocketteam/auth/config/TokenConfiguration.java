package com.rocketteam.auth.config;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;

@Getter
@Setter
public class TokenConfiguration {

    /**
     * Given lifetime of Token
     */
    Duration lifetime;

}
