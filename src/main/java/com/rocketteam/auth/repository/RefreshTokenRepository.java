package com.rocketteam.auth.repository;

import com.rocketteam.auth.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    /**
     * Find not expired token by given token
     * @param token
     * @return The Token
     */
    @Query("SELECT t FROM RefreshToken t WHERE t.token = :value AND t.expiryDateTime > CURRENT_TIMESTAMP")
    Optional<RefreshToken> findByToken(@Param("value") String token);

    /**
     * Check if token exists by given token
     * @param token
     * @return The status of token
     */
    boolean existsByToken(String token);

    /**
     * Deletes token by given token
     * @param token
     */
    void deleteByToken(String token);

    /**
     * Deletes expired tokens
     */
    @Modifying
    @Query("DELETE FROM RefreshToken t WHERE t.expiryDateTime < CURRENT_TIMESTAMP")
    void deleteAllExpiredSince();

}
