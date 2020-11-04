/*
 * Decompiled with CFR 0.139.
 *
 * Could not load the following classes:
 *  com.belrbez.dev.autovote.config.configuration.Property
 *  com.belrbez.dev.autovote.service.model.SocialType
 *  com.belrbez.dev.autovote.service.shared.BrowserJobTemplate
 *  com.belrbez.dev.autovote.service.shared.CommonFrontendJobTemplate
 *  com.belrbez.dev.autovote.service.shared.CommonFrontendJobTemplate$1
 */
package com.belrbez.dev.autovote.service.shared;

import com.belrbez.dev.autovote.config.configuration.Property;
import com.belrbez.dev.autovote.service.model.SocialType;

public abstract class CommonFrontendJobTemplate
        extends BrowserJobTemplate {
    public static final Property TARGET_URL_VOTER = Property.required("dev.autovote.voter.target.url");
    public static final Property TARGET_URL_INSTAGRAM = Property.required("dev.autovote.instagram.target.url");

    protected String baseUrl() {
        return TARGET_URL_VOTER.value();
    }

    protected String socialUrl(SocialType socialType) {
        switch (socialType) {
            default:
                return TARGET_URL_INSTAGRAM.value();
        }
    }
}

