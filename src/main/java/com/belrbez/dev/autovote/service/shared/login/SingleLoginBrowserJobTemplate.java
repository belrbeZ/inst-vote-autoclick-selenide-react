/*
 * Decompiled with CFR 0.139.
 *
 * Could not load the following classes:
 *  com.belrbez.dev.autovote.service.shared.CommonFrontendJobTemplate
 *  com.belrbez.dev.autovote.service.shared.login.LoginActionAware
 *  com.belrbez.dev.autovote.service.shared.login.SingleLoginBrowserJobTemplate
 */
package com.belrbez.dev.autovote.service.shared.login;

import com.belrbez.dev.autovote.service.shared.CommonFrontendJobTemplate;

public abstract class SingleLoginBrowserJobTemplate
        extends CommonFrontendJobTemplate
        implements LoginActionAware {
    protected void beforeAll() {
        super.beforeAll();
        this.openBase();
    }
}

