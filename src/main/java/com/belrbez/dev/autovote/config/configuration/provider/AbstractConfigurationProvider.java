/*
 * Decompiled with CFR 0.139.
 *
 * Could not load the following classes:
 *  com.belrbez.dev.autovote.config.configuration.provider.AbstractConfigurationProvider
 *  com.belrbez.dev.autovote.config.configuration.provider.ConfigurationProvider
 */
package com.belrbez.dev.autovote.config.configuration.provider;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public abstract class AbstractConfigurationProvider
        implements ConfigurationProvider {
    protected static Map<String, String> map(Properties properties) {
        HashMap<String, String> result = new HashMap<String, String>();
        for (String name : properties.stringPropertyNames()) {
            result.put(name, properties.getProperty(name));
        }
        return result;
    }

    protected static String systemProperty(String name) {
        return System.getProperty(name);
    }
}

