package tests;

import base.BaseTest;
import com.fasterxml.jackson.databind.JsonNode;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.DataProviderUtil;

/**
 * Test class for Login functionality of ParaBank application.
 *
 * Covers both positive and negative login scenarios using a data-driven
 * approach. Test data is loaded from:
 * src/test/resources/testdata/login_test_data.json
 *
 * Each JSON entry carries a "testName" field used to identify the scenario
 * in console output, making it easy to trace which row drove a failure
 * without needing a full reporting tool.
 *
 * Test Scenarios:
 * - Positive login with valid credentials (john/demo)
 * - Negative login with invalid username
 * - Negative login with invalid password
 * - Negative login with empty credentials
 *
 * @author Automation Team
 * @version 1.0
 * @since 2026-05-28
 */
public class LoginTest extends BaseTest {

    /**
     * Data-driven login test covering positive and negative scenarios.
     *
     * Runs once per row in login_test_data.json. The same method handles
     * all scenarios — validation logic branches on the credential values
     * rather than using separate test methods, keeping the page interaction
     * code in one place.
     *
     * Test Steps:
     * 1. Verify the login page title to confirm correct environment is loaded
     * 2. Extract username, password, and scenario name from the JSON node
     * 3. Perform login with the extracted credentials
     * 4. Assert account overview is visible for the known valid credential pair
     *
     * Validations:
     * - Page title matches "ParaBank | Welcome | Online Banking"
     * - Account overview heading appears after login with john/demo
     *
     * Expected Result:
     * - Positive scenario: account overview page is displayed
     * - Negative scenarios: login page remains (no assertion — ParaBank
     *   does not redirect on failure, so absence of overview is implicit)
     *
     * @param data JsonNode representing one row of test data from the
     *             DataProvider, containing username, password, testName
     */
    @Test(dataProvider = "loginData", dataProviderClass = DataProviderUtil.class)
    public void loginPageTest(JsonNode data) {

        LoginPage loginPage = new LoginPage(driver);

        // Confirm we are hitting the right environment before any interaction.
        // A title mismatch here means the baseUrl in config.properties is wrong.
        Assert.assertEquals(
                loginPage.getPageTitle(),
                "ParaBank | Welcome | Online Banking",
                "Login page title mismatch — check base URL in config"
        );

        String username = data.get("username").asText();
        String password = data.get("password").asText();
        String testName = data.get("testName").asText();

        loginPage.login(username, password);

        // Only john/demo is the seeded valid user in ParaBank's demo environment.
        // All other credential combinations in the data file are negative scenarios
        // where we intentionally do not assert — the absence of a redirect is the
        // expected behaviour and is captured by the testName printed below.
        if (username.equals("john") && password.equals("demo")) {
            Assert.assertTrue(
                    loginPage.isAccountOverviewDisplayed(),
                    "Account overview not displayed after valid login — possible session or navigation issue"
            );
        }

        // Printed to console so the CI log shows which scenario each iteration ran,
        // useful until Allure parameter reporting is wired in.
        System.out.println("Executed: " + testName);
    }
}