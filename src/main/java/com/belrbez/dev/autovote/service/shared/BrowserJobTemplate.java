/*
 * Decompiled with CFR 0.139.
 *
 * Could not load the following classes:
 *  com.belrbez.dev.autovote.config.browser.BrowserActionAware
 *  com.belrbez.dev.autovote.config.browser.BrowserPropertyAware
 *  com.belrbez.dev.autovote.config.browser.driver.DriverManager
 *  com.belrbez.dev.autovote.config.browser.proxy.ProxyManager
 *  com.belrbez.dev.autovote.config.configuration.Property
 *  com.belrbez.dev.autovote.service.model.SocialType
 *  com.belrbez.dev.autovote.service.shared.BrowserJobTemplate
 *  com.codeborne.selenide.Configuration
 *  com.codeborne.selenide.Selenide
 *  com.codeborne.selenide.WebDriverRunner
 *  org.junit.Assert
 *  org.openqa.selenium.WebDriver
 *  org.openqa.selenium.WebDriver$Navigation
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.belrbez.dev.autovote.service.shared;

import com.belrbez.dev.autovote.config.browser.BrowserActionAware;
import com.belrbez.dev.autovote.config.browser.BrowserPropertyAware;
import com.belrbez.dev.autovote.config.browser.driver.DriverManager;
import com.belrbez.dev.autovote.config.browser.proxy.ProxyManager;
import com.belrbez.dev.autovote.config.configuration.Property;
import com.belrbez.dev.autovote.service.model.SocialType;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class BrowserJobTemplate
        implements BrowserActionAware,
        BrowserPropertyAware {
    private static final Logger log = LoggerFactory.getLogger(BrowserJobTemplate.class);
    private static final int DEFAULT_VISUAL_CONTROL_DELAY_IN_SECONDS = 5;
    private Property waitModeFlag = Property.required("dev.autovote.browser.wait-mode");
    private Property waitingTime = Property.optional("dev.autovote.browser.wait-mode.time", 5);
    private boolean initialized;

    protected void waitForBrowser() {
        this.waitForBrowser(2);
    }

    protected abstract String baseUrl();

    protected void startBrowser() {
        this.restartBrowser();
        this.initialized = true;
    }

    protected void beforeAll() {
        if (this.initialized) {
            this.restartBrowserIfRequired(this.mustRestartBrowserBeforeAll());
        } else {
            log.info("STARTING BROWSER");
            this.startBrowser();
        }
    }

    protected boolean mustRestartBrowserBeforeAll() {
        return false;
    }

    protected void pressBack() {
        WebDriverRunner.getWebDriver().navigate().back();
    }

    protected void pressForward() {
        WebDriverRunner.getWebDriver().navigate().forward();
    }

    protected void pressRefresh() {
        WebDriverRunner.getWebDriver().navigate().refresh();
    }

    protected void pressRefreshAndWaitForVisualControl(int seconds) {
        this.waitForVisualControl(seconds);
        WebDriverRunner.getWebDriver().navigate().refresh();
        this.waitForVisualControl(seconds);
    }

    protected void waitForBrowser(int maxSecondsToWait) {
        try {
            Thread.sleep(maxSecondsToWait * 1000);
        } catch (InterruptedException var3) {
            var3.printStackTrace();
        }
    }

    protected void assertNoBrowserConsoleErrors() {
        List<String> records = Selenide.getWebDriverLogs("browser");
        for (String record : records) {
            if (!record.toLowerCase().contains("error") || record.toLowerCase().contains("expressionchangedafter"))
                continue;
            Assert.fail("Errors in console!");
            System.err.println(record);
        }
    }

    public void waitForVisualControl() {
        Integer waitTimeInSec = null;
        try {
            waitTimeInSec = Integer.parseInt(this.waitingTime.value());
        } catch (NumberFormatException ex) {
            System.err.println("Incorrect waiting time parameter");
            ex.printStackTrace();
        }
        if (waitTimeInSec == null) {
            waitTimeInSec = 5;
        }
        this.waitForVisualControl(waitTimeInSec.intValue());
    }

    protected void waitForVisualControl(int seconds) {
        if (this.waitModeFlag.value().equals("true")) {
            try {
                Thread.sleep(seconds * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void restartBrowserIfRequired(boolean required) {
        if (required) {
            log.info("RESTARTING BROWSER");
            this.restartBrowser();
        }
    }

    private void restartBrowser() {
        if (WebDriverRunner.hasWebDriverStarted()) {
            WebDriverRunner.closeWebDriver();
        }
        this.prepareBrowserSettings();
    }

    private void prepareBrowserSettings() {
        String type;
        Configuration.browser = type = this.browserTypeNormalized();
        Configuration.baseUrl = this.baseUrl();
        Configuration.holdBrowserOpen = HOLD_OPEN.valueAsBoolean();
        Configuration.reportsFolder = "target/reports";
        DriverManager.prepareDriverSettings();
        ProxyManager.prepareProxySettings(type);
    }

    private String browserTypeNormalized() {
        String type = TYPE.value();
        if (type.equals("firefox")) {
            return "marionette";
        }
        return type;
    }

    protected abstract String socialUrl(SocialType var1);
}

