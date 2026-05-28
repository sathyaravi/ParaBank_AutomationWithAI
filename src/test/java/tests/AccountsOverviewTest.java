package tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.AccountsOverviewPage;
import pages.LoginPage;

/**
 * Test class for Accounts Overview functionality of ParaBank application.
 * 
 * This test class verifies that after successful login, the accounts overview page
 * displays correctly and contains account information that can be read and validated.
 * 
 * Test Scenarios:
 * - Login with valid credentials
 * - Verify accounts overview page loads
 * - Extract and validate account table data
 * - Print account details to console
 * 
 * @author Automation Team
 * @version 1.0
 * @since 2026-05-28
 */
public class AccountsOverviewTest extends BaseTest {

    /**
     * Test: Accounts Overview Page Displays Correctly
     * 
     * This test verifies the accounts overview page is displayed after login
     * and contains valid account information.
     * 
     * Test Steps:
     * 1. Verify the login page has the expected title
     * 2. Perform login with valid credentials (john/demo)
     * 3. Initialize the Accounts Overview page object
     * 4. Verify the Accounts Overview heading is displayed
     * 5. Retrieve all account rows from the accounts table
     * 6. Assert at least one account row exists (could be actual account or Total row)
     * 7. Print all account details to console for visibility
     * 
     * Validations:
     * - Login page has correct title: "ParaBank | Welcome | Online Banking"
     * - Accounts Overview page heading is displayed after login
     * - Accounts table contains at least one row
     * - Account details can be extracted and printed
     * 
     * Expected Result: All assertions pass and account details are displayed.
     */
    @Test
    public void accountsOverviewDisplaysCorrectly() {

        // ── Initialize Login Page and Verify Page Title ──────────────────────────
        LoginPage loginPage = new LoginPage(driver);
        
        // Verify we are on the correct login page (important for test reliability)
        Assert.assertEquals(
                loginPage.getPageTitle(), 
                "ParaBank | Welcome | Online Banking",
                "Login page title does not match expected value"
        );

        // ── Perform Login ──────────────────────────────────────────────────────
        // Login with valid test credentials
        loginPage.login("john", "demo");

        // ── Initialize Accounts Overview Page Object ────────────────────────────
        // This page object will be used to interact with the accounts overview
        AccountsOverviewPage accountsPage = new AccountsOverviewPage(driver);

        // ── Verify Accounts Overview Page is Displayed ──────────────────────────
        Assert.assertTrue(
                accountsPage.isOverviewDisplayed(), 
                "Accounts Overview heading not displayed after login"
        );

        // ── Extract Account Table Data ──────────────────────────────────────────
        // Retrieve all rows from the accounts table
        // Each row is represented as a String array: [Account, Balance, AvailableAmount]
        List<String[]> rows = accountsPage.getAccountRows();

        // ── Validate Expected Data ─────────────────────────────────────────────
        // Assert that the table contains at least one row (accounts + Total row)
        Assert.assertTrue(
                rows.size() >= 1, 
                "No rows found in account table - table may be empty or not loaded"
        );

        // ── Print Account Details to Console ────────────────────────────────────
        // This provides visibility into what accounts and balances are displayed
        // Useful for debugging and test result review
        accountsPage.printAccountsToConsole();
    }
}
