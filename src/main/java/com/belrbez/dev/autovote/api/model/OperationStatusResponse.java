/*
 * Decompiled with CFR 0.139.
 *
 * Could not load the following classes:
 *  com.belrbez.dev.autovote.api.model.OperationStatusResponse
 *  com.belrbez.dev.autovote.api.model.OperationStatusResponse$OperationStatusResponseBuilder
 *  com.belrbez.dev.autovote.reliability.OperationResultStatus
 */
package com.belrbez.dev.autovote.api.model;

import com.belrbez.dev.autovote.reliability.OperationResultStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OperationStatusResponse {
    private String message;
    private OperationResultStatus code;

    OperationStatusResponse(String message, OperationResultStatus code) {
        this.message = message;
        this.code = code;
    }
}

