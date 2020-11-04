/*
 * Decompiled with CFR 0.139.
 *
 * Could not load the following classes:
 *  com.belrbez.dev.autovote.config.configuration.Property
 *  com.belrbez.dev.autovote.service.model.SocialTaskType
 *  com.belrbez.dev.autovote.service.model.SocialType
 *  com.belrbez.dev.autovote.service.model.VoterType
 *  com.belrbez.dev.autovote.service.model.parser.DataModel
 *  com.belrbez.dev.autovote.service.model.parser.JsonDataHandler
 *  com.belrbez.dev.autovote.service.model.parser.entity.Account
 *  com.belrbez.dev.autovote.service.model.parser.entity.User
 *  com.belrbez.dev.autovote.service.model.parser.entity.VoterUserData
 *  com.belrbez.dev.autovote.service.processor.SocialTaskProcessor
 *  com.belrbez.dev.autovote.service.processor.VoterSessionProcessor
 *  com.belrbez.dev.autovote.service.processor.VoterSessionProcessor$1
 *  com.belrbez.dev.autovote.service.processor.VoterSessionProcessor$VoterSessionHolder
 *  com.belrbez.dev.autovote.service.shared.login.SingleLoginBrowserJobTemplate
 *  com.belrbez.dev.autovote.util.xpath.SimpleAscXPathBuilder
 *  com.codeborne.selenide.Condition
 *  com.codeborne.selenide.ElementsCollection
 *  com.codeborne.selenide.Selenide
 *  com.codeborne.selenide.SelenideElement
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.belrbez.dev.autovote.service.processor;

import com.belrbez.dev.autovote.config.configuration.Property;
import com.belrbez.dev.autovote.service.model.SocialTaskType;
import com.belrbez.dev.autovote.service.model.SocialType;
import com.belrbez.dev.autovote.service.model.parser.DataModel;
import com.belrbez.dev.autovote.service.model.parser.JsonDataHandler;
import com.belrbez.dev.autovote.service.model.parser.entity.User;
import com.belrbez.dev.autovote.service.shared.login.SingleLoginBrowserJobTemplate;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

/*
 * Exception performing whole class analysis ignored.
 */
public class VoterSessionProcessor extends SingleLoginBrowserJobTemplate {
    static final Property LOOPS_AMOUNT = Property.optional("voter.session.loops.amount");
    private static final Logger log = LoggerFactory.getLogger(VoterSessionProcessor.class);
    static boolean isInterrupted;
    static boolean isInProgress;

    static {
    }

    private SocialTaskProcessor socialTaskProcessor = new SocialTaskProcessor();
    private DataModel dataModel;

    private VoterSessionProcessor() {
    }

    public static VoterSessionProcessor getInstance() {
        return VoterSessionHolder.getVoterSessionProcessor();
    }

    public static boolean isIsInterrupted() {
        return isInterrupted;
    }

    public static void setIsInterrupted(boolean isInterrupted) {
        VoterSessionProcessor.isInterrupted = isInterrupted;
    }

    public static boolean isIsInProgress() {
        return isInProgress;
    }

    public VoterSessionProcessor prepareSession() {
        this.beforeAll();
        this.dataModel = JsonDataHandler.getInstance().getData();
        return this;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void startSession(boolean isDailyGiftCheck, Integer loopsTaskType, Integer loopsOfTaskList) {
        if (this.dataModel == null) {
            throw new IllegalArgumentException("The session wasn't started!");
        }
        if (isInterrupted) {
            return;
        }
        isInProgress = true;
        try {
            this.dataModel.getVotersData().forEach((voterType, voterUserData) -> {
                log.info("{}: Session started.", voterType);
                voterUserData.forEach(voterUserDataTmp -> {
                    User voterUser = voterUserDataTmp.getUser();
                    log.info("{}: Login attempt for user '{}'.", voterType, voterUser.getLogin());
                    this.doAbsoluteNavigation(this.baseUrl());
                    this.loginVoter(voterUser);
                    voterUserDataTmp.getUserSocialAccounts().forEach((socialType, accounts) -> {
                        log.info("{}: Session started for user '{}', and social '{}'.", voterType, voterUser.getLogin(), socialType);
                        accounts.forEach(socialAccount -> {
                            int loop = 1;
                            log.info("{}: Login attempt for user '{}', and social '{}' with account '{}'.", voterType, voterUser.getLogin(), socialType, socialAccount.getUsername());
                            this.doAbsoluteNavigation(this.socialUrl(socialType) + "accounts/login");
                            this.loginSocial(socialType, socialAccount);
                            this.waitForBrowser();
                            this.doAbsoluteNavigation(this.baseUrl());
                            int n = loopsTaskType != null ? loopsTaskType : (loop = LOOPS_AMOUNT.value() != null ? Integer.parseInt(LOOPS_AMOUNT.value()) : 1);
                            while (!(isInterrupted || Stream.of(SocialTaskType.values()).anyMatch(socialTaskType -> this.processTaskType(socialType, socialTaskType, isDailyGiftCheck, loopsOfTaskList)) && isDailyGiftCheck)) {
                                log.info("Loop {} of {} ended.", loop, socialType);
                                if (loop > 1 && !isInterrupted) {
                                    Selenide.sleep((long) (new Random().nextInt(50000) + 50000));
                                }
                                if (--loop > 1 && !isInterrupted) {
                                    continue;
                                } else {
                                    break;
                                }
                            }
                            log.info("Ended Social type {} {}.", loop, socialType);
                        });
                        log.info("Fully Ended Social type {}.", socialType);
                    });
                });
            });
        } finally {
            if (isInterrupted) {
                isInterrupted = false;
            }
            isInProgress = false;
        }
    }

    public boolean processTaskType(SocialType socialType, SocialTaskType socialTaskType, boolean isDailyGiftCheck, Integer loopsOfTaskList) {
        ElementsCollection tasksPage;
        log.info("Task scope started for {} and {}", socialType, socialTaskType);
        this.waitForBrowser();
        switch (socialTaskType) {
            case SUBSCRIBE: {
                this.doNavigation("tasks/instagram/subscribe");
                break;
            }
            case LIKE: {
                this.doNavigation("tasks/instagram/like");
            }
        }
        boolean isDailyGiftFound = false;
        Integer loop = loopsOfTaskList != null ? socialTaskType == SocialTaskType.LIKE ? loopsOfTaskList : loopsOfTaskList/2 : 1;
        while ((tasksPage = this.$$find("a").andThen().filterBy(Condition.text("\u0412\u044b\u043f\u043e\u043b\u043d\u0438\u0442\u044c"))) != null && tasksPage.size() != 0) {
            log.info("Tasks found for {} and {} with amount {}.", socialType, socialTaskType, tasksPage.size());
            if (!tasksPage.isEmpty()) {
                try {
                    this.socialTaskProcessor.processTask(socialType, socialTaskType, tasksPage.first());
                } catch (Throwable ex) {
                    log.warn("ERROR: Smth went wrong! " + Arrays.toString(ex.getStackTrace()));
                }
            }
            this.waitForVisualControl();
            switch (socialTaskType) {
                case SUBSCRIBE: {
                    this.doNavigation("tasks/instagram/like");
                    this.doNavigation("tasks/instagram/subscribe");
                    break;
                }
                case LIKE: {
                    this.doNavigation("tasks/instagram/subscribe");
                    this.doNavigation("tasks/instagram/like");
                }
            }
            if (isInterrupted) {
                log.info("Stop Loop: INTERRUPTION for {} and {}", socialType, socialTaskType);
                break;
            }
            if (isDailyGiftCheck && (isDailyGiftFound = this.isDailyGiftFound())) {
                log.info("Stop Loop:  DailyGift Found for {} and {}", socialType, socialTaskType);
                break;
            }
            if (!isDailyGiftCheck && (loopsOfTaskList == null || --loop <= 0)) {
                log.info("Stop Loop: Loops ended for {} and {}", socialType, socialTaskType);
                break;
            }
            if (socialTaskType == SocialTaskType.SUBSCRIBE && (loopsOfTaskList == null || --loop <= 0)) {
                log.info("Stop Loop: Loops ended for {} and {}", socialType, socialTaskType);
                break;
            }
            if (tasksPage.isEmpty()) {
                log.info("Stop Loop: There is no more tasks for {} and {}", socialType, socialTaskType);
                break;
            }
        }
        return isDailyGiftFound;
    }

    private boolean isDailyGiftFound() {
        boolean found = this.$find("button").withText("\u0417\u0430\u043a\u0440\u044b\u0442\u044c \u043e\u043a\u043d\u043e").andCheck().is(Condition.visible);
        if (found) {
            log.info("DAILY GIFT FOUND!");
            this.$find("button").withText("\u0417\u0430\u043a\u0440\u044b\u0442\u044c \u043e\u043a\u043d\u043e").andCheck().click();
        }
        return found;
    }

    public void doAbsoluteNavigation(String url) {
        this.openAbsolute(url);
        this.waitForBrowser();
        this.waitForVisualControl();
    }

    public void doNavigation(String url) {
        this.openRelative(url);
        this.waitForBrowser();
        this.waitForVisualControl();
    }

    public static class VoterSessionHolder {
        private static VoterSessionProcessor voterSessionProcessor = new VoterSessionProcessor();

        public static VoterSessionProcessor getVoterSessionProcessor() {
            return voterSessionProcessor;
        }
    }
}

