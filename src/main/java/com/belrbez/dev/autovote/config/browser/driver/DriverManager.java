/*
 * Decompiled with CFR 0.139.
 *
 * Could not load the following classes:
 *  com.belrbez.dev.autovote.config.browser.driver.DriverManager
 *  com.belrbez.dev.autovote.config.browser.driver.DriverPropertyAware
 *  com.belrbez.dev.autovote.config.configuration.Property
 *  com.belrbez.dev.autovote.util.PathFileResolverService
 *  com.codeborne.selenide.Configuration
 */
package com.belrbez.dev.autovote.config.browser.driver;

import com.belrbez.dev.autovote.config.configuration.Property;
import com.belrbez.dev.autovote.util.PathFileResolverService;
import com.codeborne.selenide.Configuration;

/*
 * Exception performing whole class analysis ignored.
 */
public final class DriverManager
        implements DriverPropertyAware {
    public DriverManager() {
    }

    public static void prepareDriverSettings() {
        if (DRIVER_MANAGER_ENABLED.valueAsBoolean()) {
            Configuration.driverManagerEnabled = true;
            if (DRIVER_MANAGER_PROXY_HTTP.presented()) {
                System.setProperty("HTTP_PROXY", DRIVER_MANAGER_PROXY_HTTP.value());
            }
            if (DRIVER_MANAGER_PROXY_HTTPS.presented()) {
                System.setProperty("HTTPS_PROXY", DRIVER_MANAGER_PROXY_HTTPS.value());
            }
        } else {
            Configuration.driverManagerEnabled = false;
        }
        DriverManager.setupDriverBinaryPath("webdriver.chrome.driver", DRIVER_BINARY_CHROME);
        DriverManager.setupDriverBinaryPath("webdriver.edge.driver", DRIVER_BINARY_EDGE);
        DriverManager.setupDriverBinaryPath("webdriver.gecko.driver", DRIVER_BINARY_FIREFOX);
        DriverManager.setupDriverBinaryPath("phantomjs.binary.path", DRIVER_BINARY_PHANTOMJS);
    }

    private static void setupDriverBinaryPath(String driver, Property property) {
        if (property.presented()) {
            System.setProperty(driver, PathFileResolverService.getAbsolutePath(property.value()));
        }
    }
}

