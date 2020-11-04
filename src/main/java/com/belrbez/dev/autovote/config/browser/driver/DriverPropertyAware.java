/*
 * Decompiled with CFR 0.139.
 *
 * Could not load the following classes:
 *  com.belrbez.dev.autovote.config.browser.driver.DriverPropertyAware
 *  com.belrbez.dev.autovote.config.configuration.Property
 */
package com.belrbez.dev.autovote.config.browser.driver;

import com.belrbez.dev.autovote.config.configuration.Property;

public interface DriverPropertyAware {
    Property DRIVER_BINARY_CHROME = Property.optional("dev.autovote.webdriver.binary.chrome");
    Property DRIVER_BINARY_EDGE = Property.optional("dev.autovote.webdriver.binary.edge");
    Property DRIVER_BINARY_FIREFOX = Property.optional("dev.autovote.webdriver.binary.firefox");
    Property DRIVER_BINARY_PHANTOMJS = Property.optional("dev.autovote.webdriver.binary.phantomjs");
    Property DRIVER_MANAGER_ENABLED = Property.optional("dev.autovote.webdriver.manager.enabled");
    Property DRIVER_MANAGER_PROXY_HTTP = Property.optional("dev.autovote.webdriver.manager.proxy.http");
    Property DRIVER_MANAGER_PROXY_HTTPS = Property.optional("dev.autovote.webdriver.manager.proxy.https");
}

