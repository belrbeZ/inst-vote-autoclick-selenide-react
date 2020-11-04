/*
 * Decompiled with CFR 0.139.
 *
 * Could not load the following classes:
 *  com.belrbez.dev.autovote.api.RestSessionStarterController
 *  com.belrbez.dev.autovote.api.model.OperationStatusResponse
 *  com.belrbez.dev.autovote.api.model.OperationStatusResponse$OperationStatusResponseBuilder
 *  com.belrbez.dev.autovote.reliability.OperationResultStatus
 *  com.belrbez.dev.autovote.service.SessionStarter
 *  org.springframework.http.HttpStatus
 *  org.springframework.http.ResponseEntity
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.belrbez.dev.autovote.api;

import com.belrbez.dev.autovote.api.model.OperationStatusResponse;
import com.belrbez.dev.autovote.reliability.OperationResultStatus;
import com.belrbez.dev.autovote.service.SessionStarter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = {"/api"})
@RestController
public class RestSessionStarterController {
    private final SessionStarter sessionStarter;

    public RestSessionStarterController(SessionStarter sessionStarter) {
        this.sessionStarter = sessionStarter;
    }

    @RequestMapping(value = {"/vote/daily"}, method = {RequestMethod.GET}, produces = {"application/json"})
    public ResponseEntity<OperationStatusResponse> voteDaily() {
        this.sessionStarter.startVoterDailyGift();
        return new ResponseEntity<>(OperationStatusResponse.builder().code(OperationResultStatus.SUCCESS).message("Daily complete!").build(), HttpStatus.OK);
    }

    @RequestMapping(value = {"/vote/custom"}, method = {RequestMethod.GET}, produces = {"application/json"})
    public ResponseEntity<OperationStatusResponse> voteConfigured(@RequestParam(value = "loopsForSocial", defaultValue = "1") Integer loopsOfSocial, @RequestParam(value = "loopsForTaskType", defaultValue = "1") Integer loopsOfTaskType, @RequestParam(value = "loopsForTaskType", defaultValue = "1") Integer loopsOfTaskList) {
        loopsOfSocial = 1;
        this.sessionStarter.startVoterConfigured(loopsOfSocial, loopsOfTaskType, loopsOfTaskList);
        return new ResponseEntity<>(OperationStatusResponse.builder().code(OperationResultStatus.SUCCESS).message("Custom complete!").build(), HttpStatus.OK);
    }

    @RequestMapping(value = {"/interrupt"}, method = {RequestMethod.GET}, produces = {"application/json"})
    public ResponseEntity<OperationStatusResponse> voteConfigured() {
        this.sessionStarter.setInterrupt();
        return new ResponseEntity<>(OperationStatusResponse.builder().code(OperationResultStatus.SUCCESS).message("Will be interrupted at the end of inner loop!").build(), HttpStatus.OK);
    }
}

