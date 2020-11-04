/*
 * Decompiled with CFR 0.139.
 *
 * Could not load the following classes:
 *  com.belrbez.dev.autovote.config.browser.proxy.ProxyManager
 *  com.belrbez.dev.autovote.config.browser.proxy.ProxyPropertyAware
 *  com.belrbez.dev.autovote.config.configuration.Property
 *  com.belrbez.dev.autovote.config.configuration.PropertyException
 *  com.codeborne.selenide.WebDriverRunner
 *  org.openqa.selenium.Proxy
 *  org.openqa.selenium.Proxy$ProxyType
 *  org.openqa.selenium.firefox.FirefoxProfile
 */
package com.belrbez.dev.autovote.config.browser.proxy;

import com.belrbez.dev.autovote.config.configuration.PropertyException;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.util.Objects;

/*
 * Exception performing whole class analysis ignored.
 */
public final class ProxyManager
        implements ProxyPropertyAware {
    private ProxyManager() {
    }

    public static void prepareProxySettings(String browserType) {
        Objects.requireNonNull(browserType, "browser type cannot be null");
        switch (browserType) {
            case "chrome": {
                ProxyManager.prepareProxySettingsForChrome();
                return;
            }
            case "marionette":
            case "firefox": {
                ProxyManager.prepareProxySettingsForFirefox();
                return;
            }
        }
        ProxyManager.prepareProxySettingsForChrome();
    }

    private static void prepareProxySettingsForChrome() {
        Proxy proxy = new Proxy();
        Proxy.ProxyType type = ProxyManager.detectProxyType();
        proxy.setProxyType(type);
        if (type == Proxy.ProxyType.MANUAL) {
            proxy.setNoProxy(PROXY_BYPASS.valueNullable());
            proxy.setHttpProxy(PROXY_HTTP.valueNullable());
            proxy.setSslProxy(PROXY_HTTPS.valueNullable());
            proxy.setSocksProxy(PROXY_SOCKS.valueNullable());
        }
        WebDriverRunner.setProxy(proxy);
    }

    private static void prepareProxySettingsForFirefox() {
        String noProxyValue;
        Proxy proxy = new Proxy();
        Proxy.ProxyType type = ProxyManager.detectProxyType();
        proxy.setProxyType(type);
        if (type == Proxy.ProxyType.MANUAL) {
            proxy.setHttpProxy(PROXY_HTTP.valueNullable());
            proxy.setSslProxy(PROXY_HTTPS.valueNullable());
            proxy.setSocksProxy(PROXY_SOCKS.valueNullable());
        }
        if ((noProxyValue = PROXY_BYPASS.valueNullable()) != null) {
            noProxyValue = noProxyValue.replace(";", ", ");
        }
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        firefoxProfile.setPreference("network.proxy.no_proxies_on", noProxyValue);
    }

    private static Proxy.ProxyType detectProxyType() {
        String proxyTypeValue;
        switch (proxyTypeValue = PROXY_TYPE.value()) {
            case "":
            case "no":
            case "none":
            case "direct": {
                return Proxy.ProxyType.DIRECT;
            }
            case "manual": {
                return Proxy.ProxyType.MANUAL;
            }
        }
        throw PropertyException.illegalPropertyValue(PROXY_TYPE.name(), proxyTypeValue);
    }
}

