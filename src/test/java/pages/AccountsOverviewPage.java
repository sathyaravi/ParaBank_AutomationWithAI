package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Page Object Model for the Accounts Overview page of ParaBank application.
 * 
 * This class manages interactions with the accounts overview page after successful login.
 * It provides methods to:
 * - Verify page is displayed
 * - Extract account table data
 * - Calculate total balance
 * - Print account details to console
 * - Navigate to Transfer Funds and Logout pages
 * 
 * The accounts table contains rows with Account Number, Balance, and Available Amount.
 * A special "Total" row at the end shows the aggregate balance.
 * 
 * @author Automation Team
 * @version 1.0
 * @since 2026-05-28
 */
public class AccountsOverviewPage {

    /** WebDriver instance for browser interactions */
    private WebDriver driver;

    // Constructor
    /**
     * Initializes the AccountsOverviewPage with the WebDriver instance.
     * 
     * @param driver the WebDriver instance to use for page interactions
     */
    public AccountsOverviewPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators - Element locators for the accounts overview page
    /**
     * XPath locator for the "Accounts Overview" heading
     */
    private By overviewHeading = By.xpath("//h1[contains(text(),'Accounts Overview')]");
    
    /**
     * ID-based locator for the account table container
     */
    private By accountTable = By.id("accountTable");
    
    /**
     * CSS selector for all rows in the account table body
     */
    private By accountRows = By.cssSelector("#accountTable tbody tr");

    // Visibility Checks
    /**
     * Verifies that the Accounts Overview heading is displayed on the page.
     * 
     * @return true if the heading is visible, false otherwise
     */
    public boolean isOverviewDisplayed() {
        return driver.findElement(overviewHeading).isDisplayed();
    }

    // Data Extraction Methods

    /**
     * Extracts all rows from the account table and returns them as a List of String arrays.
     * 
     * Each row is represented as a String[] with three elements:
     * - Index 0: Account cell content (account number or "Total")
     * - Index 1: Balance amount (e.g., "$5022.93")
     * - Index 2: Available Amount (e.g., "$5022.93" or empty for Total row)
     * 
     * The table includes actual account rows followed by a summary Total row.
     * 
     * Example data structure:
     * [["13344", "$5022.93", "$5022.93"],
     *  ["Total", "$5022.93", ""]]
     * 
     * @return a List of String arrays where each array represents a table row with three columns
     */
    public List<String[]> getAccountRows() {
        List<String[]> data = new ArrayList<>();

        // Find all row elements in the table body
        List<WebElement> rows = driver.findElements(accountRows);

        // Iterate through each row and extract column data
        for (WebElement row : rows) {
            List<WebElement> cols = row.findElements(By.tagName("td"));
            
            // Skip rows with no table data cells
            if (cols.size() == 0) {
                continue;
            }

            // Extract text from each column, trim whitespace
            String col0 = cols.get(0).getText().trim();
            String col1 = cols.size() > 1 ? cols.get(1).getText().trim() : "";
            String col2 = cols.size() > 2 ? cols.get(2).getText().trim() : "";

            // Add row data to results
            data.add(new String[] { col0, col1, col2 });
        }

        return data;
    }

    /**
     * Retrieves the total balance from the Total row in the accounts table.
     * 
     * Searches through all rows for a cell containing "Total" and returns
     * the balance amount from that row.
     * 
     * @return the total balance text (e.g., "$5022.93"), or null if Total row not found
     */
    public String getTotalBalance() {
        List<String[]> rows = getAccountRows();

        // Find the Total row and return its balance
        for (String[] cols : rows) {
            // Check if first column contains "Total" (case-insensitive)
            if (cols[0].toLowerCase().contains("total")) {
                return cols.length > 1 ? cols[1] : null;
            }
        }

        return null;
    }

    /**
     * Prints account details to the console in a formatted manner.
     * 
     * Output format:
     * Accounts Overview:
     * Account: 13344 | Balance: $5022.93 | Available: $5022.93
     * Account: 13455 | Balance: $199.00 | Available: $199.00
     * Total Balance: $5022.93
     * 
     * Individual account rows are printed (excluding Total row to avoid duplication),
     * followed by the aggregate Total Balance on a separate line.
     */
    public void printAccountsToConsole() {
        List<String[]> rows = getAccountRows();

        System.out.println("Accounts Overview:");
        
        // Print individual account rows (skip Total row)
        for (String[] cols : rows) {
            if (cols[0].toLowerCase().contains("total")) {
                // Skip Total in this section - will be printed separately
                continue;
            }

            System.out.printf("Account: %s | Balance: %s | Available: %s%n",
                    cols[0], cols[1], cols[2]);
        }

        // Print the total balance separately
        String total = getTotalBalance();
        if (total != null) {
            System.out.println("Total Balance: " + total);
        }
    }

    // Navigation Methods
    /**
     * Clicks the 'Transfer Funds' link under Account Services.
     * 
     * This action navigates the user from the Accounts Overview page
     * to the Transfer Funds page where they can perform fund transfers
     * between their accounts.
     */
    public void clickTransferFunds() {
        driver.findElement(By.xpath("//a[@href='transfer.htm']")).click();
    }

    /**
     * Clicks the 'Log Out' link under Account Services.
     * 
     * This action logs out the user from the application and returns
     * to the login page.
     */
    public void clickLogout() {
        driver.findElement(By.xpath("//a[@href='logout.htm']")).click();
    }
}
