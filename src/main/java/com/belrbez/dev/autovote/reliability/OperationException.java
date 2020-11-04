/*
 * Decompiled with CFR 0.139.
 *
 * Could not load the following classes:
 *  com.belrbez.dev.autovote.reliability.OperationException
 *  com.belrbez.dev.autovote.reliability.OperationResultStatus
 */
package com.belrbez.dev.autovote.reliability;

public class OperationException
        extends RuntimeException {
    private final String description;
    private final OperationResultStatus status;

    public OperationException(OperationResultStatus status) {
        this(status, null);
    }

    public OperationException(OperationResultStatus status, String description) {
        this(status, description, null);
    }

    public OperationException(OperationResultStatus status, String description, Throwable cause) {
        super(cause);
        this.status = status;
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public OperationResultStatus getStatus() {
        return this.status;
    }

    @Override
    public String getMessage() {
        return String.format("%s: %s", this.status.getCode(), this.description != null ? this.description : this.status.getDefaultDescription());
    }
}

