/*
 * Decompiled with CFR 0.139.
 *
 * Could not load the following classes:
 *  com.belrbez.dev.autovote.api.model.OperationStatusResponse
 *  com.belrbez.dev.autovote.api.model.OperationStatusResponse$OperationStatusResponseBuilder
 *  com.belrbez.dev.autovote.reliability.GlobalExceptionHandler
 *  com.belrbez.dev.autovote.reliability.OperationException
 *  com.belrbez.dev.autovote.reliability.OperationResultStatus
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.http.HttpStatus
 *  org.springframework.http.ResponseEntity
 *  org.springframework.web.bind.annotation.ControllerAdvice
 *  org.springframework.web.bind.annotation.ExceptionHandler
 */
package com.belrbez.dev.autovote.reliability;

import com.belrbez.dev.autovote.api.model.OperationStatusResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    GlobalExceptionHandler() {
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<?> handleException(Exception exception) {
        log.warn("{}:\n{}", exception.getMessage(), exception);
        return new ResponseEntity(OperationStatusResponse.builder().code(OperationResultStatus.INTERNAL_UNKNOWN).build(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(value = {OperationException.class})
    public ResponseEntity<?> handleRuntimeException(OperationException exception) {
        log.warn("{}:\n{}", exception.getMessage(), exception);
        return new ResponseEntity(OperationStatusResponse.builder().code(exception.getStatus()).message(exception.getMessage()).build(), HttpStatus.CONFLICT);
    }
}

