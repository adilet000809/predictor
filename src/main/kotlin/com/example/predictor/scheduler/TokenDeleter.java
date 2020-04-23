package com.example.predictor.scheduler;

import com.example.predictor.repositories.PasswordTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;

@Configuration
@EnableScheduling
@Service
@Transactional
public class TokenDeleter {

    @Autowired
    private PasswordTokenRepository passwordTokenRepository;

    //@Scheduled(fixedDelay = 6000)
    public void purgeExpired() {
        Date now = Date.from(Instant.now());
        passwordTokenRepository.deleteByExpirationLessThan(now);
    }

}
