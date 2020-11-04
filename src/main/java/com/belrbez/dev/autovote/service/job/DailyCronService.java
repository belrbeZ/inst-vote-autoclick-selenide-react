/*
 * Decompiled with CFR 0.139.
 *
 * Could not load the following classes:
 *  com.belrbez.dev.autovote.service.SessionStarter
 *  com.belrbez.dev.autovote.service.job.DailyCronService
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.scheduling.annotation.Scheduled
 *  org.springframework.stereotype.Service
 */
package com.belrbez.dev.autovote.service.job;

import com.belrbez.dev.autovote.service.SessionStarter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class DailyCronService {
    private static final Logger log = LoggerFactory.getLogger(DailyCronService.class);
    private final SessionStarter sessionStarter;

    public DailyCronService(SessionStarter sessionStarter) {
        this.sessionStarter = sessionStarter;
    }

    @Scheduled(cron = "${voter.cron.daily}")
    public void startDailyProcessor() {
        this.sessionStarter.startVoterDailyGift();
    }
}

