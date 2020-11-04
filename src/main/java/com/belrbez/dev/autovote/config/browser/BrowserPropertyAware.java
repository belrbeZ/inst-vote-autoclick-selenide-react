/*
 * Decompiled with CFR 0.139.
 *
 * Could not load the following classes:
 *  com.belrbez.dev.autovote.config.browser.BrowserPropertyAware
 *  com.belrbez.dev.autovote.config.browser.proxy.ProxyPropertyAware
 *  com.belrbez.dev.autovote.config.configuration.Property
 */
package com.belrbez.dev.autovote.config.browser;

import com.belrbez.dev.autovote.config.browser.proxy.ProxyPropertyAware;
import com.belrbez.dev.autovote.config.configuration.Property;

public interface BrowserPropertyAware
        extends ProxyPropertyAware {
    Property TYPE = Property.optional("dev.autovote.browser.type", "phantomjs");
    Property HOLD_OPEN = Property.optional("dev.autovote.browser.hold-open");
}

