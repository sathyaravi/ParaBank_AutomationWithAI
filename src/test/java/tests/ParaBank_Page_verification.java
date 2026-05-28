package tests;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;


public class ParaBank_Page_verification {
	
	

	    WebDriver driver;

	    @BeforeMethod
	    public void setup() {

	        // Setup ChromeDriver
	        //WebDriverManager.chromedriver().setup();

	        // Launch browser
	        driver = new ChromeDriver();

	        // Maximize window
	        driver.manage().window().maximize();

	        // Implicit wait
	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

	        // Open ParaBank Register Page
	        driver.get("https://parabank.parasoft.com/parabank/register.htm");
	    }

	    @Test
	    public void verifyRegisterPageIsOpened() {

	        // Expected page title
	        String expectedTitle = "ParaBank | Register for Free Online Account Access";

	        // Actual page title
	        String actualTitle = driver.getTitle();

	        // Assertion
	        Assert.assertEquals(actualTitle, expectedTitle,
	                "Register page is not opened successfully");

	        System.out.println("ParaBank Register page opened successfully");
	    }

	    @Test
	    public void signupFormTest() {

	        // Fill Signup Form

	        // First Name
	        driver.findElement(By.id("customer.firstName"))
	                .sendKeys("John");

	        // Last Name
	        driver.findElement(By.name("customer.lastName"))
	                .sendKeys("doe");

	        // Address
	        driver.findElement(By.id("customer.address.street"))
	                .sendKeys("123 Main Street");

	        // City
	        driver.findElement(By.name("customer.address.city"))
	                .sendKeys("Portland");

	        // State
	        driver.findElement(By.xpath("//input[@id='customer.address.state']"))
	                .sendKeys("Oregon");

	        // Zip Code
	        driver.findElement(By.id("customer.address.zipCode"))
	                .sendKeys("97035");

	        // Phone Number
	        driver.findElement(By.id("customer.phoneNumber"))
	                .sendKeys("9876543210");

	        // SSN
	        driver.findElement(By.name("customer.ssn"))
	                .sendKeys("123456789");

	        // Username
	        driver.findElement(By.id("customer.username"))
	                .sendKeys("john.doe1234@mail.com");

	        // Password
	        driver.findElement(By.id("customer.password"))
	                .sendKeys("Test@123");

	        // Confirm Password
	        driver.findElement(By.id("repeatedPassword"))
	                .sendKeys("Test@123");

	        // Assertions
	        Assert.assertEquals(
	                driver.findElement(By.id("customer.firstName"))
	                        .getDomProperty("value"),
	                "John");

	        Assert.assertEquals(
	                driver.findElement(By.id("customer.lastName"))
	                        .getDomProperty("value"),
	                "doe");

	        System.out.println("Signup form filled successfully");
	    }


	    @AfterMethod
	    public void tearDown() {

	        // Close browser
	        if (driver != null) {
	            driver.quit();
	        }
	    }
	}
