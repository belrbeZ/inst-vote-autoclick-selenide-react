/*
 * Decompiled with CFR 0.139.
 *
 * Could not load the following classes:
 *  com.belrbez.dev.autovote.config.browser.BrowserActionAware
 *  com.codeborne.selenide.Selenide
 */
package com.belrbez.dev.autovote.config.browser;

import com.codeborne.selenide.Selenide;

public interface BrowserActionAware {
    default void openAbsolute(String url) {
        Selenide.open(url);
    }

    default void openBase() {
        this.openRelative("");
    }

    default void openRelative(String url) {
        Selenide.open(url);
    }
}

