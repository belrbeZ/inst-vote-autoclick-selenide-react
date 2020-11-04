/*
 * Decompiled with CFR 0.139.
 *
 * Could not load the following classes:
 *  com.belrbez.dev.autovote.service.model.SocialType
 *  com.belrbez.dev.autovote.service.model.parser.entity.Account
 *  com.belrbez.dev.autovote.service.model.parser.entity.User
 *  com.belrbez.dev.autovote.service.shared.login.LoginActionAware
 *  com.belrbez.dev.autovote.service.shared.login.LoginActionAware$1
 *  com.belrbez.dev.autovote.service.shared.selenide.SimpleSelenideEventAware
 *  com.belrbez.dev.autovote.util.xpath.SimpleAscXPathBuilder
 *  com.codeborne.selenide.Condition
 *  com.codeborne.selenide.ElementsCollection
 *  com.codeborne.selenide.Selenide
 *  com.codeborne.selenide.SelenideElement
 *  com.codeborne.selenide.WebDriverRunner
 *  org.openqa.selenium.By
 *  org.openqa.selenium.JavascriptExecutor
 */
package com.belrbez.dev.autovote.service.shared.login;

import com.belrbez.dev.autovote.service.model.SocialType;
import com.belrbez.dev.autovote.service.model.parser.entity.Account;
import com.belrbez.dev.autovote.service.model.parser.entity.User;
import com.belrbez.dev.autovote.service.shared.selenide.SimpleSelenideEventAware;
import com.codeborne.selenide.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import java.io.IOException;

public interface LoginActionAware
        extends SimpleSelenideEventAware {
    String CSS_LOGIN_BUTTON_INSTAGRAM_CLASS = "._0mzm-.sqdOP.L3NKy";
    String CSS_LOGIN_BUTTON_INSTAGRAM_TEXT = "Log in";
    String CSS_LOGIN_USERNAME_INSTAGRAM_NAME = "username";
    String CSS_LOGIN_PASSWORD_INSTAGRAM_NAME = "password";
    String CSS_VOTER_BUTTON_VOTER_TEXT = "\u0412\u043e\u0439\u0442\u0438";
    String CSS_LOGIN_USERNAME_VOTER_NAME = "login";
    String CSS_LOGIN_PASSWORD_VOTER_NAME = "password";

    default void loginSocial(SocialType socialType, Account account) {
        this.loginFormShouldAppear(socialType);
        switch (socialType) {
            case INSTAGRAM:
                if (this.$find("a").withAttr("href", "/accounts/activity/").get() != null && this.$find("a").withAttr("href", "/accounts/activity/").get().is(Condition.visible)) {
                    System.out.println("Already logged in " + socialType);
                } else {
                    this.$findByName("input", CSS_LOGIN_USERNAME_INSTAGRAM_NAME).setValue(account.getUsername());
                    this.$findByName("input", "password").setValue(account.getPassword());
                    Selenide.$(CSS_LOGIN_BUTTON_INSTAGRAM_CLASS).click();
                    Selenide.sleep(5000L);
                    if (this.$$findByText("button", "Send Security Code").is(Condition.visible) ||
                            this.$$findByText("a", "Not Now") == null ||
                            this.$find("a").withAttr("href", "/accounts/activity/").get() == null ||
                            !this.$find("a").withAttr("href", "/accounts/activity/").get().is(Condition.visible)) {
                        System.out.println("CAN NOT LOG in " + socialType);
                        Selenide.sleep(10000L);
                        try {
                            System.in.read();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    this.$find("a").withAttr("href", "/accounts/activity/").get().shouldBe(Condition.visible);
                }
        }
    }

    default void loginVoter(User user) {
        if (Selenide.$$(By.tagName("div")).find(Condition.text("\u041c\u0435\u043d\u044e")).is(Condition.visible)) {
            System.out.println("Already logged in");
            return;
        }
        this.clickButton(CSS_VOTER_BUTTON_VOTER_TEXT);
        this.loginFormVoterShouldAppear();
        this.$findByName("input", CSS_LOGIN_USERNAME_VOTER_NAME).setValue(user.getLogin());
        this.$findByName("input", "password").setValue(user.getPassword());
        this.$$find("button").withAttr("type", "submit").withText(CSS_VOTER_BUTTON_VOTER_TEXT).get().findBy(Condition.visible).click();
        while (!((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("return document.readyState").equals("complete")) {
            Selenide.sleep(1000L);
        }
        if (this.$find("iframe").andCheck().is(Condition.visible)) {
            System.out.println("ROBO CHECK");
            Selenide.sleep(10000L);
            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Selenide.$$(By.tagName("div")).find(Condition.text("\u041c\u0435\u043d\u044e")).shouldBe(Condition.visible);
    }

    default void loginFormVoterShouldAppear() {
        this.$$findByText("button", CSS_VOTER_BUTTON_VOTER_TEXT).shouldBe(Condition.visible);
    }

    default void loginFormShouldAppear(SocialType socialType) {
        switch (socialType) {
            default:
                if (this.$$findByText("a", "Not Now") != null) {
                    System.out.println("Already logged in INSTA");
                } else if (this.$find("a").withAttr("href", "/accounts/activity/").get() != null && this.$find("a").withAttr("href", "/accounts/activity/").get().is(Condition.visible)) {
                    System.out.println("Already logged in INSTA");
                } else {
                    this.$$findByText("button", CSS_LOGIN_BUTTON_INSTAGRAM_TEXT).shouldBe(Condition.visible);
                    Selenide.$(CSS_LOGIN_BUTTON_INSTAGRAM_CLASS).shouldBe(Condition.visible);
                }
        }
    }
}

