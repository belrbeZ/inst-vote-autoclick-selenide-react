/*
 * Decompiled with CFR 0.139.
 *
 * Could not load the following classes:
 *  com.belrbez.dev.autovote.config.configuration.PropertyException
 */
package com.belrbez.dev.autovote.config.configuration;

public class PropertyException
        extends RuntimeException {
    public PropertyException(String message) {
        super(message);
    }

    public PropertyException(Throwable cause) {
        super(cause);
    }

    public PropertyException(String message, Throwable cause) {
        super(message, cause);
    }

    public static PropertyException illegalPropertyValue(String name, Object value) {
        return new PropertyException(String.format("illegal property value: %s = %s", name, value));
    }

    public static PropertyException missingRequiredProperty(String name) {
        return new PropertyException(String.format("missing required property: %s", name));
    }
}

