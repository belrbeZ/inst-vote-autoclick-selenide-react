/*
 * Decompiled with CFR 0.139.
 *
 * Could not load the following classes:
 *  com.belrbez.dev.autovote.service.model.SocialTaskType
 *  com.belrbez.dev.autovote.service.model.SocialType
 *  com.belrbez.dev.autovote.service.processor.SocialTaskProcessor
 *  com.belrbez.dev.autovote.service.processor.SocialTaskProcessor$1
 *  com.belrbez.dev.autovote.service.shared.CommonFrontendJobTemplate
 *  com.belrbez.dev.autovote.service.shared.selenide.SimpleSelenideEventAware
 *  com.codeborne.selenide.Condition
 *  com.codeborne.selenide.SelenideElement
 *  com.codeborne.selenide.WebDriverRunner
 *  org.openqa.selenium.WebDriver
 *  org.openqa.selenium.WebDriver$TargetLocator
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.belrbez.dev.autovote.service.processor;

import com.belrbez.dev.autovote.service.model.SocialTaskType;
import com.belrbez.dev.autovote.service.model.SocialType;
import com.belrbez.dev.autovote.service.shared.CommonFrontendJobTemplate;
import com.belrbez.dev.autovote.service.shared.selenide.SimpleSelenideEventAware;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class SocialTaskProcessor
        extends CommonFrontendJobTemplate
        implements SimpleSelenideEventAware {
    private static final Logger log = LoggerFactory.getLogger(SocialTaskProcessor.class);

    public void processTask(SocialType socialType, SocialTaskType socialTasksType, SelenideElement linkToTask) {
        switch (socialType) {
            default:
                this.processInstagramTask(socialTasksType, linkToTask);
        }
    }

    public void processInstagramTask(SocialTaskType socialTasksType, SelenideElement linkToTask) {
        String winHandleBefore = WebDriverRunner.getWebDriver().getWindowHandle();
        linkToTask.click();
        this.waitForBrowser();
        this.waitForVisualControl();
        for (String winHandle : WebDriverRunner.getWebDriver().getWindowHandles()) {
            WebDriverRunner.getWebDriver().switchTo().window(winHandle);
        }
        switch (socialTasksType) {
            case LIKE: {
                try {
                    if (this.$$findByClass("span", "glyphsSpriteHeart__filled__24__red_5").is(Condition.exist)) {
                        this.$$findByClass("span", "glyphsSpriteHeart__filled__24__red_5").shouldBe(Condition.visible).click();
                        this.waitForVisualControl();
                    }
                    this.$$findByClass("span", "glyphsSpriteHeart__outline__24__grey_9").shouldBe(Condition.visible).click();
                    this.waitForVisualControl();
                    this.$$findByClass("span", "glyphsSpriteHeart__filled__24__red_5").shouldBe(Condition.visible);
                    log.info("SUCCESS: like task!");
                } catch (Throwable ex) {
                    log.warn("ERROR: Smth went wrong with like task! " + Arrays.toString(ex.getStackTrace()));
                }
                break;
            }
            case SUBSCRIBE: {
                try {
                    if (this.$$findByText("button", "Following").is(Condition.exist)) {
                        this.clickButton("Following");
                        this.waitForVisualControl();
                    }
                    this.clickButton("Follow");
                    this.waitForVisualControl();
                    this.$$findByText("button", "Following").shouldBe(Condition.visible);
                    log.info("SUCCESS: subscribe task!");
                    break;
                } catch (Throwable ex) {
                    log.warn("ERROR: Smth went wrong with subscribe task! " + Arrays.toString(ex.getStackTrace()));
                }
            }
        }
        if (!WebDriverRunner.getWebDriver().getWindowHandle().equals(winHandleBefore)) {
            log.info("Window for task closed!");
            WebDriverRunner.getWebDriver().close();
        }
        WebDriverRunner.getWebDriver().switchTo().window(winHandleBefore);
    }
}

