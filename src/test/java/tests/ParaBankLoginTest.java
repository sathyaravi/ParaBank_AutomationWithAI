package tests;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;



public class ParaBankLoginTest {

	    WebDriver driver;

	    @BeforeMethod
	    public void setup() {

	        // Setup ChromeDriver
	       // WebDriver.chromedriver().setup();

	        // Launch browser
	        driver = new ChromeDriver();

	        // Maximize browser
	        driver.manage().window().maximize();

	        // Implicit wait
	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

	        // Open ParaBank application
	        driver.get("https://parabank.parasoft.com/parabank/index.htm");
	    }

	    @Test
	    public void loginPageTest() {

	        // Verify page title
	        String expectedTitle = "ParaBank | Welcome | Online Banking";

	        String actualTitle = driver.getTitle();

	        Assert.assertEquals(
	                actualTitle,
	                expectedTitle,
	                "Application page is not opened"
	        );

	        // Verify Customer Login heading
	        WebElement loginHeading =
	                driver.findElement(By.xpath("//h2[text()='Customer Login']"));

	        Assert.assertTrue(
	                loginHeading.isDisplayed(),
	                "Customer Login heading is not displayed"
	        );

	        // Locate Username field
	        WebElement username =
	                driver.findElement(By.name("username"));

	        // Verify Username field displayed
	        Assert.assertTrue(
	                username.isDisplayed(),
	                "Username field is not displayed"
	        );

	        // Enter Username
	        username.sendKeys("john");

	        // Assertion for username
	        Assert.assertEquals(
	                username.getDomProperty("value"),
	                "john"
	        );

	        // Locate Password field
	        WebElement password =
	                driver.findElement(By.name("password"));

	        // Verify Password field displayed
	        Assert.assertTrue(
	                password.isDisplayed(),
	                "Password field is not displayed"
	        );

	        // Enter Password
	        password.sendKeys("demo");

	        // Assertion for password
	        Assert.assertEquals(
	                password.getDomProperty("value"),
	                "demo"
	        );

	        // Locate Login button
	        WebElement loginButton =
	                driver.findElement(
	                        By.xpath("//input[@value='Log In']"));

	        // Verify Login button displayed
	        Assert.assertTrue(
	                loginButton.isDisplayed(),
	                "Login button is not displayed"
	        );

	        // Click Login button
	        loginButton.click();

	        // Verify login success
	        WebElement accountOverview =
	                driver.findElement(
	                        By.xpath("//h1[contains(text(),'Accounts Overview')]"));

	        Assert.assertTrue(
	                accountOverview.isDisplayed(),
	                "Login failed or Accounts Overview page not displayed"
	        );

	        System.out.println("Login successful");
	    }

	    @AfterMethod
	    public void tearDown() {

	        // Close browser
	        if (driver != null) {
	            driver.quit();
	        }
	    }
	}

