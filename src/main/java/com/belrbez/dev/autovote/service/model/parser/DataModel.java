/*
 * Decompiled with CFR 0.139.
 *
 * Could not load the following classes:
 *  com.belrbez.dev.autovote.service.model.VoterType
 *  com.belrbez.dev.autovote.service.model.parser.DataModel
 *  com.belrbez.dev.autovote.service.model.parser.entity.VoterUserData
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.belrbez.dev.autovote.service.model.parser;

import com.belrbez.dev.autovote.service.model.VoterType;
import com.belrbez.dev.autovote.service.model.parser.entity.VoterUserData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataModel {
    @JsonProperty(value = "votersData")
    private Map<VoterType, List<VoterUserData>> votersData;

    public Map<VoterType, List<VoterUserData>> getVotersData() {
        return this.votersData;
    }
}

