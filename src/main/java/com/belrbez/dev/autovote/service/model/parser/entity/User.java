/*
 * Decompiled with CFR 0.139.
 *
 * Could not load the following classes:
 *  com.belrbez.dev.autovote.service.model.parser.entity.User
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.belrbez.dev.autovote.service.model.parser.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    @JsonProperty(value = "login")
    private String login;
    @JsonProperty(value = "password")
    private String password;

    public String getLogin() {
        return this.login;
    }

    public String getPassword() {
        return this.password;
    }
}

