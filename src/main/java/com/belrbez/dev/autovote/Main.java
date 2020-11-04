/*
 * Decompiled with CFR 0.139.
 *
 * Could not load the following classes:
 *  com.belrbez.dev.autovote.Main
 *  com.belrbez.dev.autovote.service.SessionStarter
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.belrbez.dev.autovote;

import com.belrbez.dev.autovote.service.SessionStarter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        new SessionStarter().prepare().startVoterDailyGift();
    }
}

