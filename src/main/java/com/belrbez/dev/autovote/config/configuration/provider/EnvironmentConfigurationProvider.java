/*
 * Decompiled with CFR 0.139.
 *
 * Could not load the following classes:
 *  com.belrbez.dev.autovote.config.configuration.provider.AbstractConfigurationProvider
 *  com.belrbez.dev.autovote.config.configuration.provider.EnvironmentConfigurationProvider
 */
package com.belrbez.dev.autovote.config.configuration.provider;

import java.util.Map;

public class EnvironmentConfigurationProvider
        extends AbstractConfigurationProvider {
    public Map<String, String> get() {
        return System.getenv();
    }
}

