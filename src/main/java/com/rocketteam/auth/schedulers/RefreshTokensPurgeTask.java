package com.rocketteam.auth.schedulers;

import com.rocketteam.auth.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class RefreshTokensPurgeTask {

    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * Runs scheduler by given cron expression
     * In order to delete expired tokens
     */
    @Scheduled(cron = "${authserver.auth-config.purging-refresh-tokens-cron}", zone="Europe/Istanbul")
    public void purgeExpiredRefreshTokens() {
        if(refreshTokenRepository.findAll().size() > 0) {
            refreshTokenRepository.deleteAllExpiredSince();
            log.info("Expired tokens have been purged");
        }
    }

}
