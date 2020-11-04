/*
 * Decompiled with CFR 0.139.
 *
 * Could not load the following classes:
 *  com.belrbez.dev.autovote.config.browser.proxy.ProxyPropertyAware
 *  com.belrbez.dev.autovote.config.configuration.Property
 */
package com.belrbez.dev.autovote.config.browser.proxy;

import com.belrbez.dev.autovote.config.configuration.Property;

public interface ProxyPropertyAware {
    Property PROXY_TYPE = Property.optional("dev.autovote.browser.proxy.type");
    Property PROXY_HTTP = Property.optional("dev.autovote.browser.proxy.http");
    Property PROXY_HTTPS = Property.optional("dev.autovote.browser.proxy.https");
    Property PROXY_SOCKS = Property.optional("dev.autovote.browser.proxy.socks");
    Property PROXY_BYPASS = Property.optional("dev.autovote.browser.proxy.bypass");
}

