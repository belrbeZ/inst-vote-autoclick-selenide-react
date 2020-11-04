/*
 * Decompiled with CFR 0.139.
 *
 * Could not load the following classes:
 *  com.belrbez.dev.autovote.config.configuration.Property
 *  com.belrbez.dev.autovote.config.configuration.PropertyException
 *  com.belrbez.dev.autovote.config.configuration.PropertyFacade
 *  javax.annotation.Nullable
 */
package com.belrbez.dev.autovote.config.configuration;

import javax.annotation.Nullable;
import java.util.Objects;

/*
 * Exception performing whole class analysis ignored.
 */
public final class Property {
    private final String name;
    private final Object defaultValue;
    private final boolean optional;

    private Property(String name, @Nullable Object defaultValue, boolean optional) {
        Objects.requireNonNull(name, "property name cannot be null");
        this.name = name;
        this.defaultValue = defaultValue;
        this.optional = optional;
    }

    public static Property optional(String name) {
        return Property.optional(name, null);
    }

    public static Property optional(String name, @Nullable Object defaultValue) {
        return new Property(name, defaultValue, true);
    }

    public static Property required(String name) {
        return new Property(name, null, false);
    }

    public String name() {
        return this.name;
    }

    public boolean optional() {
        return this.optional;
    }

    public boolean presented() {
        return PropertyFacade.value(this.name) != null;
    }

    public String value() {
        String result = this.valueNullable();
        return result == null ? "" : result;
    }

    @Nullable
    public String valueNullable() {
        Object result = this.valueAsObject();
        return result == null ? null : result.toString();
    }

    public boolean valueAsBoolean() {
        Object value = this.valueAsObject();
        return value instanceof Boolean ? ((Boolean) value).booleanValue() : this.parseBoolean(value);
    }

    @Nullable
    private Object valueAsObject() {
        String value = PropertyFacade.value(this.name);
        if (value != null) {
            return value;
        }
        if (!this.optional) {
            throw PropertyException.missingRequiredProperty(this.name);
        }
        return this.defaultValue;
    }

    private boolean parseBoolean(Object value) {
        if (value == null) {
            return false;
        }
        String textValue = value.toString();
        if (textValue.equalsIgnoreCase("true") || textValue.equalsIgnoreCase("yes") || textValue.equalsIgnoreCase("1")) {
            return true;
        }
        if (textValue.equalsIgnoreCase("false") || textValue.equalsIgnoreCase("no") || textValue.equalsIgnoreCase("0")) {
            return false;
        }
        throw PropertyException.illegalPropertyValue(this.name, value);
    }
}

