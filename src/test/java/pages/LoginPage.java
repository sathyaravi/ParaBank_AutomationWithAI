package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Page Object Model for the Login page of ParaBank application.
 * 
 * This class encapsulates all interactions with the login page including:
 * - Entering username and password
 * - Clicking the login button
 * - Verifying login elements and page title
 * - Checking for successful login by verifying Accounts Overview page
 * 
 * @author Automation Team
 * @version 1.0
 * @since 2026-05-28
 */
public class LoginPage {

    /** WebDriver instance used for interactions with the browser */
    private WebDriver driver;

    // Constructor
    /**
     * Constructor to initialize the LoginPage with WebDriver instance.
     * 
     * @param driver the WebDriver instance to use for page interactions
     */
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators - Web elements defined by their locating strategies
    /**
     * XPath locator for the "Customer Login" heading on the login page
     */
    private By loginHeading =
            By.xpath("//h2[text()='Customer Login']");

    /**
     * Name-based locator for the username input field
     */
    private By usernameField =
            By.name("username");

    /**
     * Name-based locator for the password input field
     */
    private By passwordField =
            By.name("password");

    /**
     * XPath locator for the "Log In" submit button
     */
    private By loginButton =
            By.xpath("//input[@value='Log In']");

    /**
     * XPath locator for the "Accounts Overview" heading that appears after successful login
     */
    private By accountOverviewHeading =
            By.xpath("//h1[contains(text(),'Accounts Overview')]");

    // Actions - Methods representing user interactions with the page

    /**
     * Retrieves the page title of the login page.
     * Used to verify that the correct page has loaded.
     * 
     * @return the title of the current page
     */

    public String getPageTitle() {
        return driver.getTitle();
    }

    public boolean isLoginHeadingDisplayed() {

        return driver.findElement(loginHeading)
                .isDisplayed();
    }

    /**
     * Checks if the username input field is displayed on the page.
     * 
     * @return true if the username field is visible, false otherwise
     */
    public boolean isUsernameDisplayed() {

        return driver.findElement(usernameField)
                .isDisplayed();
    }

    /**
     * Checks if the password input field is displayed on the page.
     * 
     * @return true if the password field is visible, false otherwise
     */
    public boolean isPasswordDisplayed() {

        return driver.findElement(passwordField)
                .isDisplayed();
    }

    /**
     * Checks if the login button is displayed on the page.
     * 
     * @return true if the login button is visible, false otherwise
     */
    public boolean isLoginButtonDisplayed() {

        return driver.findElement(loginButton)
                .isDisplayed();
    }

    /**
     * Enters the username into the username field.
     * Clears any existing value before entering the new username.
     * 
     * @param username the username to enter in the username field
     */
    public void enterUsername(String username) {

        WebElement user =
                driver.findElement(usernameField);

        user.clear();
        user.sendKeys(username);
    }

    /**
     * Enters the password into the password field.
     * Clears any existing value before entering the new password.
     * 
     * @param password the password to enter in the password field
     */
    public void enterPassword(String password) {

        WebElement pass =
                driver.findElement(passwordField);

        pass.clear();
        pass.sendKeys(password);
    }

    /**
     * Retrieves the value currently in the username field.
     * Used for verification after entering username.
     * 
     * @return the value of the username field
     */
    public String getEnteredUsername() {

        return driver.findElement(usernameField)
                .getDomProperty("value");
    }

    /**
     * Retrieves the value currently in the password field.
     * Used for verification after entering password.
     * 
     * @return the value of the password field
     */
    public String getEnteredPassword() {

        return driver.findElement(passwordField)
                .getDomProperty("value");
    }

    /**
     * Clicks the Login button to submit the login form.
     * After this action, the page should navigate to the Accounts Overview page
     * if credentials are valid.
     */
    public void clickLogin() {

        driver.findElement(loginButton)
                .click();
    }

    /**
     * Checks if the Accounts Overview page is displayed.
     * This indicates that the login was successful and user is now logged in.
     * 
     * @return true if the Accounts Overview heading is displayed, false otherwise
     */
    public boolean isAccountOverviewDisplayed() {

        return driver.findElement(accountOverviewHeading)
                .isDisplayed();
    }

    // Business Methods - Higher-level actions combining multiple steps

    /**
     * Performs a complete login operation with the provided credentials.
     * This is a business method that combines entering username, password, and clicking login.
     * 
     * Steps:
     * 1. Enter username
     * 2. Enter password
     * 3. Click login button
     * 
     * @param username the username to use for login
     * @param password the password to use for login
     */
    public void login(String username, String password) {

        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }
}
