package com.rocketteam.auth.utils;

import java.time.LocalDateTime;

public final class TokenUtils {

    /**
     * Checks if a token expiry date time is before from now
     * @param expiryTime
     * @return Status if token is expired or not
     */
    public static boolean isTokenExpired(LocalDateTime expiryTime) {
        return expiryTime.isBefore(LocalDateTime.now());
    }

}
