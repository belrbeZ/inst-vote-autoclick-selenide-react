package com.belrbez.dev.autovote.generic;

import com.belrbez.dev.autovote.service.model.parser.DataModel;
import com.belrbez.dev.autovote.service.model.parser.JsonDataHandler;
import com.belrbez.dev.autovote.service.shared.CommonFrontendJobTemplate;
import com.belrbez.dev.autovote.service.shared.login.LoginActionAware;
import org.junit.Ignore;
import org.junit.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static junit.framework.TestCase.assertNotNull;

/**
 * Collection of login-related tests.
 */
@Ignore
public class LoginTestCollection extends CommonFrontendJobTemplate implements LoginActionAware {

    private static final String INPUT_ILLEGAL = "*";
    private static final String INPUT_MISSING = "";
    private static final String APP_ROOT_INITIAL_VALUE = "Loading...";

    /**
     * Checks if a legal user can login into the application.
     */
    @Test
    public void loginWithLegalCredentials() {
        openBase();
        loginFormVoterShouldAppear();
        applicationContentShouldAppear();
    }

    /**
     * Checks if a illegal user cannot login into the application.
     */
    @Test
    public void failToLoginWithIllegalCredentials() {
        openBase();
        loginFormVoterShouldAppear();
//        login(INPUT_ILLEGAL, INPUT_ILLEGAL);
        errorAlertShouldAppear();
    }

    /**
     * Checks if user credentials are required for login.
     */
    @Test
    public void failToLoginWithoutCredentials() {
        openBase();
        loginFormVoterShouldAppear();
//        loginVoter(INPUT_MISSING, INPUT_MISSING);
        errorAlertShouldAppear();
    }

    /**
     * Checks if username is required for login.
     */
    @Test
    public void failToLoginWithoutUsername() {
        openBase();
        loginFormVoterShouldAppear();
//        login(INPUT_MISSING, INPUT_ILLEGAL);
//        errorAlertShouldAppear();
    }

    /**
     * Checks if user password is required for login.
     */
    @Test
    public void failToLoginWithoutPassword() {
        openBase();
        loginFormVoterShouldAppear();
//        login(INPUT_ILLEGAL, INPUT_MISSING);
//        errorAlertShouldAppear();
    }

    @Test
    public void testData() {
        DataModel dataModel = JsonDataHandler.getInstance().getData();
        assertNotNull(dataModel);
    }

    private void applicationContentShouldAppear() {
        $("app-root").shouldHave(text(APP_ROOT_INITIAL_VALUE));
    }

    private void errorAlertShouldAppear() {
        $(".alert-error").shouldBe(visible);
    }
}
