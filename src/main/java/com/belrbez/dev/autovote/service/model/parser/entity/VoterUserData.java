/*
 * Decompiled with CFR 0.139.
 *
 * Could not load the following classes:
 *  com.belrbez.dev.autovote.service.model.SocialType
 *  com.belrbez.dev.autovote.service.model.parser.entity.Account
 *  com.belrbez.dev.autovote.service.model.parser.entity.User
 *  com.belrbez.dev.autovote.service.model.parser.entity.VoterUserData
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.belrbez.dev.autovote.service.model.parser.entity;

import com.belrbez.dev.autovote.service.model.SocialType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VoterUserData {
    @JsonProperty(value = "user")
    private User user;
    @JsonProperty(value = "accounts")
    private Map<SocialType, List<Account>> userSocialAccounts;

    public User getUser() {
        return this.user;
    }

    public Map<SocialType, List<Account>> getUserSocialAccounts() {
        return this.userSocialAccounts;
    }
}

