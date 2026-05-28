package base;


import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Base Test class for all test classes in the ParaBank automation framework.
 * 
 * This class provides common setup and teardown functionality for all tests:
 * - Browser initialization (Chrome)
 * - Window maximization
 * - Implicit wait configuration (10 seconds)
 * - Application URL navigation
 * - Browser cleanup and quit
 * 
 * All test classes should extend this class to inherit this functionality.
 * 
 * Usage:
 * {@code
 * public class MyTest extends BaseTest {
 *     @Test
 *     public void myTestMethod() {
 *         // driver is already initialized by BeforeMethod
 *         LoginPage page = new LoginPage(driver);
 *         page.login("user", "pass");
 *     }
 * }
 * }
 * 
 * 
 * @version 1.0
 * @since 2026-05-28
 */
public class BaseTest {

    /** 
     * WebDriver instance shared across all test methods.
     * Initialized in setup() before each test and closed in tearDown() after each test.
     * Protected visibility allows access from subclasses (test classes).
     */
    protected WebDriver driver;

    /**
     * Getter method to expose WebDriver to external classes (utilities, listeners).
     * Allows listeners and utilities to access the test's WebDriver instance
     * without directly extending this class.
     * 
     * Example usage:
     * {@code
     * BaseTest test = result.getInstance();
     * WebDriver driver = test.getDriver();  // Get the driver for screenshot capture
     * }
     * 
     * @return the WebDriver instance for the current test
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Setup method executed before each test method.
     * 
     * Responsibilities:
     * 1. Initialize Chrome WebDriver
     * 2. Maximize browser window for better visibility
     * 3. Set implicit wait to 10 seconds for element location
     * 4. Navigate to ParaBank application home page
     * 
     * This method is executed automatically by TestNG before each @Test method.
     * 
     * @see TestNG @BeforeMethod annotation
     */
    @BeforeMethod
    public void setup() {

        // Initialize ChromeDriver for browser automation
        // ChromeDriver must be on system PATH or WebDriverManager should be configured
        driver = new ChromeDriver();

        // Maximize the browser window to use full screen for better element visibility
        driver.manage().window().maximize();

        // Set implicit wait to 10 seconds
        // WebDriver will wait up to 10 seconds when trying to find an element before throwing exception
        // Note: Use explicit waits (WebDriverWait) for better control and reliability
        driver.manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(10));

        // Navigate to ParaBank application home page
        // All tests start from this URL and navigate within the application
        driver.get("https://parabank.parasoft.com/parabank/index.htm");
    }

    /**
     * Teardown method executed after each test method.
     * 
     * Responsibilities:
     * 1. Close all browser windows and sessions
     * 2. Clean up WebDriver resources
     * 3. Free memory and connections
     * 
     * This method executes automatically after each @Test method completes
     * (whether test passed, failed, or skipped).
     * 
     * Using null check prevents exceptions if driver initialization fails
     * during setup() and tearDown() is still called.
     * 
     * @see TestNG @AfterMethod annotation
     */
    @AfterMethod
    public void tearDown() {

        // Safely close browser and free resources
        if (driver != null) {
            // driver.quit() closes all browser windows and ends the WebDriver session
            // This is more reliable than driver.close() as it handles multiple windows
            driver.quit();
        }
    }
}