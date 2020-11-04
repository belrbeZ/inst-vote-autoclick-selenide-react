/*
 * Decompiled with CFR 0.139.
 *
 * Could not load the following classes:
 *  com.belrbez.dev.autovote.config.configuration.Property
 *  com.belrbez.dev.autovote.reliability.OperationException
 *  com.belrbez.dev.autovote.reliability.OperationResultStatus
 *  com.belrbez.dev.autovote.service.SessionStarter
 *  com.belrbez.dev.autovote.service.processor.VoterSessionProcessor
 *  com.codeborne.selenide.Selenide
 *  com.codeborne.selenide.WebDriverRunner
 *  org.openqa.selenium.WebDriver
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.stereotype.Service
 */
package com.belrbez.dev.autovote.service;

import com.belrbez.dev.autovote.config.configuration.Property;
import com.belrbez.dev.autovote.reliability.OperationException;
import com.belrbez.dev.autovote.reliability.OperationResultStatus;
import com.belrbez.dev.autovote.service.processor.VoterSessionProcessor;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Random;

@Service
public class SessionStarter {
    static final Property LOOPS_AMOUNT = Property.optional("voter.session.loops.amount");
    static final Thread thread;
    private static final Logger log = LoggerFactory.getLogger(SessionStarter.class);
    static VoterSessionProcessor voterSessionProcessor;
    static WebDriver webDriver;

    static {
        thread = new Thread();
    }

    @PostConstruct
    public SessionStarter prepare() {
        voterSessionProcessor = VoterSessionProcessor.getInstance().prepareSession();
        webDriver = WebDriverRunner.getWebDriver();
        return this;
    }

    public void setInterrupt() {
        if (voterSessionProcessor == null) {
            log.warn("INITIALIZE session first!");
            throw new OperationException(OperationResultStatus.INTERNAL_SESSION_NOT_INITIALIZED);
        }
        if (!VoterSessionProcessor.isIsInProgress()) {
            log.warn("START session first!");
            throw new OperationException(OperationResultStatus.INTERNAL_SESSION_NOT_INITIALIZED);
        }
        VoterSessionProcessor.setIsInterrupted(true);
    }

    public void startVoterInfinite() {
        this.checkIsApplicable();
        WebDriverRunner.setWebDriver(webDriver);
        int loopSession = LOOPS_AMOUNT.value() == null ? 1 : Integer.parseInt(LOOPS_AMOUNT.value());
        do {
            voterSessionProcessor.startSession(false, null, null);
            log.info("Loop global session for voter {} ended.", loopSession);
            if (loopSession <= 1) continue;
            Selenide.sleep((long) (new Random().nextInt(50000) + 50000));
        } while (--loopSession > 0);
    }

    public void startVoterConfigured(Integer loopsForSocial, Integer loopsForTaskType, Integer loopsOfTaskList) {
        this.checkIsApplicable();
        WebDriverRunner.setWebDriver(webDriver);
        do {
            voterSessionProcessor.startSession(false, loopsForTaskType, loopsOfTaskList);
            log.info("Loop global session for voter {} ended.", loopsForSocial);
            if (loopsForSocial <= 1) continue;
            Selenide.sleep((long) (new Random().nextInt(50000) + 50000));
        } while ((loopsForSocial = Integer.valueOf(loopsForSocial - 1)) > 0);
    }

    public void startVoterDailyGift() {
        this.checkIsApplicable();
        WebDriverRunner.setWebDriver(webDriver);
        voterSessionProcessor.startSession(true, 5, null);
        log.info("ENDED SESSION FOR DAILY GIFT .");
    }

    private void checkIsApplicable() {
        if (voterSessionProcessor == null) {
            log.warn("INITIALIZE session first!");
            this.prepare();
        }
        if (VoterSessionProcessor.isIsInProgress()) {
            throw new OperationException(OperationResultStatus.INTERNAL_PROGRESS);
        }
    }
}

