/*
 * Decompiled with CFR 0.139.
 *
 * Could not load the following classes:
 *  com.belrbez.dev.autovote.config.SchedulerConfig
 *  com.belrbez.dev.autovote.config.configuration.Property
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.scheduling.annotation.EnableScheduling
 */
package com.belrbez.dev.autovote.config;

import com.belrbez.dev.autovote.config.configuration.Property;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class SchedulerConfig {
    public static final String VOTER_CROON_DAILY = Property.required("voter.cron.daily").value();
}

