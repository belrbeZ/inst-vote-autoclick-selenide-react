package com.belrbez.dev.autovote.generic;

import com.belrbez.dev.autovote.service.shared.login.SingleLoginBrowserJobTemplate;
import com.belrbez.dev.autovote.service.shared.logout.LogoutActionAware;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Collection of logout-related tests.
 */
@Ignore
public class LogoutTestCollection extends SingleLoginBrowserJobTemplate implements LogoutActionAware {

    /**
     * Logs into the application and then logs out.
     */
    @Test
    public void logoutSuccessfully() {
        waitForVisualControl();
        logout();
    }
}
