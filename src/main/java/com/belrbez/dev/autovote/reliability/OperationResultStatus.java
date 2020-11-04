/*
 * Decompiled with CFR 0.139.
 *
 * Could not load the following classes:
 *  com.belrbez.dev.autovote.reliability.OperationResultStatus
 */
package com.belrbez.dev.autovote.reliability;

public enum OperationResultStatus {
    SUCCESS(Integer.valueOf(200), "Success"),
    FAILURE_VALIDATION(Integer.valueOf(202), "Operation cannot be completed due to input validation failure"),
    INTERNAL_PROGRESS(Integer.valueOf(405), "Already in progress!"),
    INTERNAL_UNKNOWN(Integer.valueOf(500), "Internal unknown!"),
    INTERNAL_SESSION_NOT_INITIALIZED(Integer.valueOf(501), "Internal web session not initialized!"),
    INTERNAL_SESSION_NOT_STARTED(Integer.valueOf(502), "Internal web session not started!");

    private final Integer code;
    private final String defaultDescription;

    OperationResultStatus(Integer code, String defaultDescription) {
        this.code = code;
        this.defaultDescription = defaultDescription;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getDefaultDescription() {
        return this.defaultDescription;
    }
}

