/*
 * Decompiled with CFR 0.139.
 *
 * Could not load the following classes:
 *  com.belrbez.dev.autovote.config.configuration.provider.AbstractConfigurationProvider
 *  com.belrbez.dev.autovote.config.configuration.provider.ConfigurationProvider
 *  com.belrbez.dev.autovote.config.configuration.provider.EnvironmentConfigurationProvider
 *  com.belrbez.dev.autovote.config.configuration.provider.FilesystemConfigurationProvider
 *  com.belrbez.dev.autovote.config.configuration.provider.OverlappedConfigurationProvider
 *  com.belrbez.dev.autovote.config.configuration.provider.SystemConfigurationProvider
 */
package com.belrbez.dev.autovote.config.configuration.provider;

import java.util.HashMap;
import java.util.Map;

public class OverlappedConfigurationProvider
        extends AbstractConfigurationProvider {
    private final ConfigurationProvider environmentProvider = new EnvironmentConfigurationProvider();
    private final ConfigurationProvider filesystemProvider = new FilesystemConfigurationProvider();
    private final ConfigurationProvider systemProvider = new SystemConfigurationProvider();

    public Map<String, String> get() {
        HashMap<String, String> result = new HashMap<String, String>();
        result.putAll(this.environmentProvider.get());
        result.putAll(this.filesystemProvider.get());
        result.putAll(this.systemProvider.get());
        return result;
    }
}

