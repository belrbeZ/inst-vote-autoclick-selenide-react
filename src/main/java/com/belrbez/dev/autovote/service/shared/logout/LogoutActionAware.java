/*
 * Decompiled with CFR 0.139.
 *
 * Could not load the following classes:
 *  com.belrbez.dev.autovote.service.shared.logout.LogoutActionAware
 *  com.codeborne.selenide.Condition
 *  com.codeborne.selenide.Selenide
 *  com.codeborne.selenide.SelenideElement
 */
package com.belrbez.dev.autovote.service.shared.logout;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;

public interface LogoutActionAware {
    String CSS_LOGOUT_BUTTON = "[type=\"quit\"]";

    default void logout() {
        Selenide.$(CSS_LOGOUT_BUTTON).shouldBe(Condition.visible).click();
    }
}

