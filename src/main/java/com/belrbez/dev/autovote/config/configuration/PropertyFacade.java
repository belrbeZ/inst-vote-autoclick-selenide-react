/*
 * Decompiled with CFR 0.139.
 *
 * Could not load the following classes:
 *  com.belrbez.dev.autovote.config.configuration.PropertyFacade
 *  com.belrbez.dev.autovote.config.configuration.provider.ConfigurationProvider
 *  com.belrbez.dev.autovote.config.configuration.provider.OverlappedConfigurationProvider
 */
package com.belrbez.dev.autovote.config.configuration;

import com.belrbez.dev.autovote.config.configuration.provider.ConfigurationProvider;
import com.belrbez.dev.autovote.config.configuration.provider.OverlappedConfigurationProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/*
 * Exception performing whole class analysis ignored.
 */
public final class PropertyFacade {
    private static final Map<String, String> PROPERTIES = new HashMap();
    private static final ConfigurationProvider PROVIDER = new OverlappedConfigurationProvider();

    static {
        PropertyFacade.reset();
    }

    private PropertyFacade() {
    }

    public static void reset() {
        PROPERTIES.clear();
        PROPERTIES.putAll(PROVIDER.get());
    }

    public static String value(String name) {
        Objects.requireNonNull(name, "property name cannot be null");
        return PROPERTIES.get(name);
    }
}

