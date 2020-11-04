/*
 * Decompiled with CFR 0.139.
 *
 * Could not load the following classes:
 *  com.belrbez.dev.autovote.config.configuration.provider.AbstractConfigurationProvider
 *  com.belrbez.dev.autovote.config.configuration.provider.SystemConfigurationProvider
 */
package com.belrbez.dev.autovote.config.configuration.provider;

import java.util.Map;
import java.util.Properties;

/*
 * Exception performing whole class analysis ignored.
 */
public class SystemConfigurationProvider
        extends AbstractConfigurationProvider {
    public SystemConfigurationProvider() {
    }

    public Map<String, String> get() {
        return SystemConfigurationProvider.map(System.getProperties());
    }
}

