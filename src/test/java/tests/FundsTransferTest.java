package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.AccountsOverviewPage;
import pages.FundsTransferPage;
import pages.LoginPage;

/**
 * Test class for Transfer Funds functionality of ParaBank application.
 * 
 * This test class verifies the complete fund transfer workflow:
 * 1. Login with valid credentials
 * 2. View and print accounts overview
 * 3. Navigate to Transfer Funds page
 * 4. Dynamically read available accounts from dropdowns
 * 5. Enter transfer amount and select accounts
 * 6. Submit the transfer
 * 7. Verify transfer completion
 * 8. Print transfer results
 * 9. Logout
 * 
 * Key Design:
 * - Uses dynamic account selection instead of hardcoded account IDs
 * - This ensures the test works even if account IDs change in the application
 * - Demonstrates best practices for data-driven test scenarios
 * 
 * @author Automation Team
 * @version 2.0
 * @since 2026-05-28
 */
public class FundsTransferTest extends BaseTest {

    /**
     * Test: Positive Funds Transfer with Dynamic Account Selection
     * 
     * This test verifies the complete happy-path scenario of transferring funds
     * between two accounts using dynamically read account IDs.
     * 
     * Test Steps:
     * 1. Login with valid credentials (john/demo)
     * 2. Verify accounts overview page loads and print account details
     * 3. Click on "Transfer Funds" navigation link
     * 4. Verify transfer page is displayed
     * 5. Read first available from-account from dropdown
     * 6. Read a different to-account from dropdown
     * 7. Enter transfer amount ($100.00)
     * 8. Select source account
     * 9. Select destination account
     * 10. Click Transfer button
     * 11. Verify transfer completion heading is displayed
     * 12. Print transfer result details to console
     * 13. Logout
     * 
     * Expected Result: Transfer should complete successfully with transfer confirmation.
     * 
     * Design Pattern:
     * - Dynamic account selection ensures test works regardless of actual account IDs
     * - Never hardcoding account IDs makes the test more resilient to data changes
     */
    @Test(priority = 1)
    public void positiveFundsTransfer() {

        // ── Initialize and Login ────────────────────────────────────────────────
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("john", "demo");

        // ── View and Print Accounts Overview ────────────────────────────────────
        AccountsOverviewPage accountsPage = new AccountsOverviewPage(driver);
        accountsPage.printAccountsToConsole();
        
        // ── Navigate to Transfer Funds ──────────────────────────────────────────
        accountsPage.clickTransferFunds();

        // ── Initialize Transfer Page ───────────────────────────────────────────
        FundsTransferPage transferPage = new FundsTransferPage(driver);
        
        // ── Verify Page Load ────────────────────────────────────────────────────
        Assert.assertTrue(transferPage.isTransferPageDisplayed(), 
                "Transfer Funds page not displayed");

        // ── Read Actual Account IDs from Dropdowns ──────────────────────────────
        // IMPORTANT: Never hardcode account IDs! Read from the live dropdowns instead.
        // This ensures the test works even if account IDs change or new accounts are added.
        String fromAccount = transferPage.getFirstAvailableFromAccount();
        String toAccount   = transferPage.getFirstAvailableToAccount();

        System.out.println("\nTransfer Details:");
        System.out.println("From Account: " + fromAccount);
        System.out.println("To Account: " + toAccount);
        System.out.println("Amount: $100.00");

        // ── Fill In Transfer Form ───────────────────────────────────────────────
        transferPage.enterAmount("100.00");
        transferPage.selectFromAccount(fromAccount);
        transferPage.selectToAccount(toAccount);
        
        // ── Submit Transfer ─────────────────────────────────────────────────────
        transferPage.clickTransfer();

        // ── Verify Transfer Success ─────────────────────────────────────────────
        Assert.assertTrue(
            transferPage.isTransferCompleteDisplayed(),
            "Transfer did not complete. Check that both accounts are valid and different."
        );

        // ── Print Transfer Result Details ───────────────────────────────────────
        System.out.println("\n✓ Transfer Successful!");
        System.out.println("Result Amount: $" + transferPage.getAmountResult());
        System.out.println("Result From: " + transferPage.getFromResult());
        System.out.println("Result To: " + transferPage.getToResult());

        // ── Logout ──────────────────────────────────────────────────────────────
        accountsPage.clickLogout();
    }

}
