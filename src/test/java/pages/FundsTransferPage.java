package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.List;

/**
 * Page Object Model for the Transfer Funds page of ParaBank application.
 * 
 * This class manages all interactions with the Transfer Funds page, including:
 * - Entering transfer amount
 * - Selecting source and destination accounts with AJAX-aware waits
 * - Submitting the transfer form
 * - Verifying transfer completion
 * - Retrieving transfer result details
 * 
 * Key Features:
 * - AJAX-aware waits for dropdown population before selection
 * - Browser change event firing to ensure JavaScript is aware of programmatic changes
 * - Robust element interaction with scroll-into-view before clicking
 * - Safe text extraction with null handling
 * 
 * The transfer form requires:
 * - Amount: A numeric value for the transfer amount
 * - From Account: Source account (select dropdown populated via AJAX)
 * - To Account: Destination account (select dropdown populated via AJAX)
 * 
 * @author Automation Team
 * @version 2.0
 * @since 2026-05-28
 */
public class FundsTransferPage {

    /** WebDriver instance for browser interactions */
    private final WebDriver driver;
    
    /** WebDriverWait instance with 15-second timeout for explicit waits */
    private final WebDriverWait wait;

    // ── Page Element Locators ───────────────────────────────────────────────────

    /**
     * XPath locator for the "Transfer Funds" page heading
     */
    private final By transferHeading     = By.xpath("//h1[contains(text(),'Transfer Funds')]");
    
    /**
     * ID-based locator for the amount input field
     */
    private final By amountInput         = By.id("amount");
    
    /**
     * ID-based locator for the source account dropdown selector
     */
    private final By fromAccountSelect   = By.id("fromAccountId");
    
    /**
     * ID-based locator for the destination account dropdown selector
     */
    private final By toAccountSelect     = By.id("toAccountId");
    
    /**
     * XPath locator for the Transfer submit button
     */
    private final By transferButton      = By.xpath("//input[@value='Transfer']");
    
    /**
     * XPath locator for the "Transfer Complete!" heading that appears after successful transfer
     */
    private final By transferCompleteHeading = By.xpath("//h1[contains(text(),'Transfer Complete')]");
    
    /**
     * ID-based locator for the transferred amount display on result page
     */
    private final By amountResult        = By.id("amountResult");
    
    /**
     * ID-based locator for the source account display on result page
     */
    private final By fromResult          = By.id("fromAccountIdResult");
    
    /**
     * ID-based locator for the destination account display on result page
     */
    private final By toResult            = By.id("toAccountIdResult");

    /**
     * Initializes the FundsTransferPage with the WebDriver instance.
     * Creates a WebDriverWait with 15-second timeout for explicit waits.
     * 
     * @param driver the WebDriver instance to use for page interactions
     */
    public FundsTransferPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // ── Page Verification Methods ───────────────────────────────────────────────

    /**
     * Verifies that the Transfer Funds page is displayed.
     * Waits for the "Transfer Funds" heading to become visible before checking.
     * 
     * @return true if the page heading is visible, false otherwise
     */
    public boolean isTransferPageDisplayed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(transferHeading));
        return driver.findElement(transferHeading).isDisplayed();
    }

    // ── Form Interaction Methods ────────────────────────────────────────────────

    /**
     * Enters the transfer amount into the amount field.
     * 
     * Process:
     * 1. Wait until the amount field is clickable
     * 2. Clear any existing value
     * 3. Enter the new amount
     * 4. Fire a 'change' event to notify the page JavaScript of the change
     * 
     * The change event is critical because Selenium's programmatic value setting
     * bypasses the normal browser event system that the ParaBank application's
     * JavaScript depends on to register the value change.
     * 
     * @param amount the amount to transfer (e.g., "100.00")
     */
    public void enterAmount(String amount) {
        WebElement amt = wait.until(ExpectedConditions.elementToBeClickable(amountInput));
        amt.clear();
        amt.sendKeys(amount);
        fireChangeEvent(amt); // tells the form JS the value was set
    }

    /**
     * Selects the source account (from account) from the dropdown.
     * 
     * Process:
     * 1. Wait until dropdown options are loaded via AJAX
     * 2. Get the select element
     * 3. Select the value using Selenium's Select class
     * 4. Fire a 'change' event to notify the page JavaScript of the selection
     * 
     * Waiting for options ensures the AJAX call has completed before attempting selection.
     * The change event is essential because programmatic dropdown changes don't trigger
     * the browser's change event that the ParaBank application depends on.
     * 
     * @param value the account ID value to select (e.g., "13344")
     */
    public void selectFromAccount(String value) {
        waitForOptions(fromAccountSelect);
        WebElement el = driver.findElement(fromAccountSelect);
        new Select(el).selectByValue(value);
        fireChangeEvent(el); // critical — without this the form ignores the selection
    }

    /**
     * Selects the destination account (to account) from the dropdown.
     * 
     * Process:
     * 1. Wait until dropdown options are loaded via AJAX
     * 2. Get the select element
     * 3. Select the value using Selenium's Select class
     * 4. Fire a 'change' event to notify the page JavaScript of the selection
     * 
     * @param value the account ID value to select (e.g., "13455")
     */
    public void selectToAccount(String value) {
        waitForOptions(toAccountSelect);
        WebElement el = driver.findElement(toAccountSelect);
        new Select(el).selectByValue(value);
        fireChangeEvent(el);
    }

    /**
     * Reads and returns the first available option value from the source account dropdown.
     * 
     * Useful for dynamic test scenarios where account IDs may vary.
     * First waits for dropdown options to be populated via AJAX.
     * 
     * @return the value of the first available account option
     */
    public String getFirstAvailableFromAccount() {
        waitForOptions(fromAccountSelect);
        List<WebElement> options = new Select(driver.findElement(fromAccountSelect)).getOptions();
        return options.get(0).getAttribute("value");
    }

    /**
     * Reads and returns an available destination account ID from the destination dropdown.
     * 
     * Strategy: Returns the second account option if available (to ensure from != to),
     * otherwise returns the first option.
     * 
     * Useful for dynamic test scenarios where account IDs may vary.
     * First waits for dropdown options to be populated via AJAX.
     * 
     * @return the value of an available account option (preferably different from source)
     */
    public String getFirstAvailableToAccount() {
        waitForOptions(toAccountSelect);
        List<WebElement> options = new Select(driver.findElement(toAccountSelect)).getOptions();
        // return second option if available so from != to
        return options.size() > 1 ? options.get(1).getAttribute("value")
                                  : options.get(0).getAttribute("value");
    }

    /**
     * Submits the transfer form by clicking the Transfer button.
     * 
     * Process:
     * 1. Wait until the Transfer button is clickable
     * 2. Scroll the button into view (ensures visibility even if page is scrolled)
     * 3. Click the button
     * 
     * Scrolling into view is necessary to handle cases where the form may extend
     * beyond the visible viewport area.
     */
    public void clickTransfer() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(transferButton));
        // Scroll button into view to ensure it's visible before clicking
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", btn);
        btn.click();
    }

    // ── Result Verification Methods ─────────────────────────────────────────────

    /**
     * Waits for and verifies that the transfer completion page is displayed.
     * 
     * Waits up to 15 seconds for the "Transfer Complete!" heading to become visible.
     * Returns false on timeout without throwing an exception.
     * 
     * @return true if transfer completion is verified, false if timeout occurs
     */
    public boolean isTransferCompleteDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(transferCompleteHeading));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    // ── Result Data Retrieval Methods ───────────────────────────────────────────

    /**
     * Retrieves the transferred amount from the transfer result page.
     * 
     * @return the transfer amount display text (e.g., "$100.00"), or null if not found
     */
    public String getAmountResult() { return safeGetText(amountResult); }
    
    /**
     * Retrieves the source account ID from the transfer result page.
     * 
     * @return the source account ID (e.g., "13344"), or null if not found
     */
    public String getFromResult()   { return safeGetText(fromResult); }
    
    /**
     * Retrieves the destination account ID from the transfer result page.
     * 
     * @return the destination account ID (e.g., "13455"), or null if not found
     */
    public String getToResult()     { return safeGetText(toResult); }

    // ── Private Helper Methods ──────────────────────────────────────────────────

    /**
     * Waits until the specified select dropdown element has at least one option loaded.
     * 
     * This method guards against reading an empty AJAX-populated dropdown by waiting
     * for:
     * 1. At least one option to exist in the dropdown
     * 2. The first option to have a non-empty value attribute
     * 
     * This is critical because ParaBank populates dropdowns via AJAX, and attempting
     * to select before that completes will fail.
     * 
     * @param selectLocator the By locator for the select element
     */
    private void waitForOptions(By selectLocator) {
        wait.until(driver -> {
            List<WebElement> options =
                new Select(driver.findElement(selectLocator)).getOptions();
            return !options.isEmpty() && !options.get(0).getAttribute("value").isEmpty();
        });
    }

    /**
     * Fires a native browser 'change' event on the specified element.
     * 
     * Why this is necessary:
     * Selenium's programmatic interaction (sendKeys for inputs, selectByValue for dropdowns)
     * sets the DOM value, but bypasses the browser's normal event system. The ParaBank
     * application's JavaScript listens for 'change' events to know when values have been
     * modified. Without firing this event, the application's JS won't process the changes.
     * 
     * This method dispatches the event with bubbles enabled so parent listeners also
     * receive the event.
     * 
     * @param element the WebElement on which to fire the change event
     */
    private void fireChangeEvent(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
            element
        );
    }

    /**
     * Safely retrieves text from an element without throwing exceptions.
     * 
     * This helper method is used internally to extract result values from the
     * transfer completion page. It handles missing elements gracefully by returning null
     * instead of throwing NoSuchElementException.
     * 
     * @param locator the By locator for the element to extract text from
     * @return the element's text with whitespace trimmed, or null if element not found
     */
    private String safeGetText(By locator) {
        try {
            return driver.findElement(locator).getText().trim();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}