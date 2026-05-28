# ParaBank Automation Testing Framework

A robust, well-documented Selenium WebDriver automation testing framework built with Java,
TestNG, and the Page Object Model (POM) design pattern. This framework is designed to test
the [ParaBank](https://parabank.parasoft.com/parabank/) online banking application with
comprehensive test coverage for login, account overview, and funds transfer functionalities.

---

## 📋 Table of Contents

## 📋 Table of Contents

## Features
## Project Structure
## Prerequisites
## Installation
## Configuration
## Running Tests
## Test Coverage
## Architecture and Design Patterns
## Code Documentation Standards
## Reporting
## Troubleshooting
## Future Enhancements

---

## ✨ Features

- **Page Object Model (POM)** — Clean separation between test logic and page interactions
- **Data-Driven Testing** — TestNG data providers reading from external JSON files
- **Retry Mechanism** — Automatic retry of failed tests via configurable `RetryAnalyzer`
- **Extent Reports** — HTML test reports with screenshots on failure
- **AJAX-Aware Interactions** — Explicit waits guard against dynamically loaded elements
- **Native Event Firing** — JavaScript `change` events dispatched after programmatic selections
- **Listener Architecture** — Custom TestNG listeners for reporting and retry logic
- **JSON-Based Test Data** — External test data management decoupled from test code
- **Cross-Browser Ready** — Easily extensible to support Chrome, Firefox, and Edge

---

## 📁 Project Structure

```
SeleniumWebDriverProjectWithAI/
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── utils/
│   │           ├── DataProviderUtil.java        # TestNG @DataProvider reading JSON files
│   │           ├── JsonReaderUtil.java          # JSON file reader and parser
│   │           ├── ExtentManager.java           # Extent Reports lifecycle manager
│   │           └── ScreenshotUtil.java          # Screenshot capture on failure
│   │
│   └── test/
│       ├── java/
│       │   ├── base/
│       │   │   └── BaseTest.java                # WebDriver setup, navigation, teardown
│       │   │
│       │   ├── pages/
│       │   │   ├── LoginPage.java               # Login page interactions
│       │   │   ├── AccountsOverviewPage.java    # Accounts overview page interactions
│       │   │   └── FundsTransferPage.java       # Funds transfer page interactions
│       │   │
│       │   ├── tests/
│       │   │   ├── LoginTest.java               # Login test scenarios (data-driven)
│       │   │   ├── AccountsOverviewTest.java    # Accounts overview test scenarios
│       │   │   └── FundsTransferTest.java       # Funds transfer test scenarios
│       │   │
│       │   └── listeners/
│       │       ├── TestListener.java            # Captures results, logs, screenshots
│       │       ├── RetryListener.java           # Annotation transformer for retry
│       │       └── RetryAnalyzer.java           # Retry decision logic
│       │
│       └── resources/
│           ├── loginData.json                   # Test data for login scenarios
│           └── extent.properties                # Extent Reports configuration
│
├── pom.xml                                      # Maven dependencies and build config
├── testng.xml                                   # TestNG suite configuration
└── README.md                                    # Project documentation (this file)
```

---

## 🔧 Prerequisites

| Tool | Version |
|---|---|
| Java JDK | 11 or higher |
| Maven | 3.6 or higher |
| Chrome Browser | Latest stable |
| Git | Any recent version |

> ChromeDriver is auto-managed — no manual driver download required.

---

## 📥 Installation

### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/SeleniumWebDriverProjectWithAI.git
cd SeleniumWebDriverProjectWithAI
```

### 2. Install Dependencies
```bash
mvn clean install -DskipTests
```

### 3. Verify Setup
```bash
mvn --version
java -version
```

---

## ⚙️ Configuration

### Test Data
Test scenarios are driven by `src/test/resources/loginData.json`.
Add or remove scenarios here without touching any Java code:

```json
[
  {
    "testName": "Valid Login - john/demo",
    "username": "john",
    "password": "demo"
  },
  {
    "testName": "Invalid Login - wrong password",
    "username": "john",
    "password": "wrongpassword"
  },
  {
    "testName": "Invalid Login - unknown user",
    "username": "unknownuser",
    "password": "demo"
  }
]
```

### Extent Reports
Configure output path in `src/test/resources/extent.properties`:

```properties
extent.reporter.html.out=reports/extent-report.html
extent.reporter.html.append=true
```

### Browser
Default browser is Chrome, configured in `BaseTest.java`.
Extend `BaseTest` to add Firefox or Edge support when needed.

### Implicit Wait
Default implicit wait is 10 seconds in `BaseTest.java`. Increase for slower environments:

```java
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
```

---

## 🚀 Running Tests

### Run the Full Suite
```bash
mvn -Dsurefire.suiteXmlFiles=testng.xml test
```

### Run a Specific Test Class
```bash
mvn -Dtest=LoginTest test
mvn -Dtest=FundsTransferTest test
```

### Run a Specific Test Method
```bash
mvn -Dtest=LoginTest#loginPageTest test
```

### Run from IDE
1. Right-click `testng.xml`
2. Select **Run As → TestNG Suite**

### View Reports
After the run completes, open:
```
reports/extent-report.html
```

---

## 📊 Test Coverage

### 1. Login Tests — `LoginTest.java`
Data-driven via `loginData.json`. Runs one iteration per JSON entry.

| Scenario | Credentials | Expected Outcome |
|---|---|---|
| Valid login | john / demo | Account overview page displayed |
| Invalid password | john / wrongpassword | Login page remains |
| Unknown user | unknownuser / demo | Login page remains |

### 2. Accounts Overview Tests — `AccountsOverviewTest.java`

| Validation | Detail |
|---|---|
| Page title | "ParaBank \| Welcome \| Online Banking" |
| Heading visibility | Accounts Overview heading displayed after login |
| Table content | At least one row present (accounts + Total row) |
| Account details | Account number, balance, and available amount extracted and logged |

### 3. Funds Transfer Tests — `FundsTransferTest.java`

| Validation | Detail |
|---|---|
| Transfer form display | Transfer Funds heading visible after navigation |
| Account dropdown loading | Dropdowns populated via AJAX before selection |
| Transfer submission | Transfer Complete heading displayed after submit |
| Transfer details | Amount, from-account, to-account logged to console |

---

## 🏗️ Architecture & Design Patterns

### 1. Page Object Model (POM)
Each page of the application is represented by a dedicated class in the `pages` package.
Page classes contain only locators and user-action methods — no assertions, no test logic.

```
Page class     → models one screen of the application
Page methods   → model one user action (click, type, select, read)
Test class     → orchestrates page actions and owns all assertions
```

**Benefits:**
- A UI change only requires updating one page class, not every test that touches that screen
- Page actions are reusable across multiple test classes
- Tests read as business scenarios, not as Selenium code

### 2. Base Test Class
`BaseTest.java` provides shared setup and teardown inherited by all test classes:

- Launches Chrome and navigates to the ParaBank base URL
- Maximises the browser window
- Sets implicit wait
- Quits the browser after each test class

### 3. AJAX-Aware Dropdown Handling
ParaBank's Transfer Funds page loads account numbers from a server call *after* the page
renders. `FundsTransferPage` handles this correctly in two steps:

**Step 1 — Wait for options to load before selecting:**
```java
// Account options arrive via AJAX after page load.
// Selecting before they exist silently does nothing.
private void waitForOptions(By selectLocator) {
    wait.until(driver -> {
        List<WebElement> options =
            new Select(driver.findElement(selectLocator)).getOptions();
        return !options.isEmpty()
            && !options.get(0).getAttribute("value").isEmpty();
    });
}
```

**Step 2 — Fire the browser `change` event after selection:**
```java
// Selenium's selectByValue changes the DOM value but skips the browser
// event system. ParaBank's JS listens for 'change' to register the
// selection — without this dispatch, the form treats the field as unset.
private void fireChangeEvent(WebElement element) {
    ((JavascriptExecutor) driver).executeScript(
        "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
        element
    );
}
```

### 4. Dynamic Account ID Resolution
Account numbers in ParaBank are assigned per session and differ between environments.
The test reads actual account IDs from the dropdowns at runtime rather than hardcoding:

```java
String fromAccount = transferPage.getFirstAvailableFromAccount();
String toAccount   = transferPage.getFirstAvailableToAccount();
```

### 5. TestNG Listeners
Three listeners registered in `testng.xml` monitor test execution:

| Listener | Responsibility |
|---|---|
| `TestListener` | Logs results to Extent Reports; captures screenshot on failure |
| `RetryListener` | Annotation transformer that wires `RetryAnalyzer` onto every test |
| `RetryAnalyzer` | Decides whether a failed test should be retried and how many times |

### 6. Utility Classes

| Class | Responsibility |
|---|---|
| `DataProviderUtil` | Reads JSON file and supplies `Object[][]` to `@DataProvider` |
| `JsonReaderUtil` | Parses JSON arrays/objects using Jackson |
| `ExtentManager` | Singleton that manages the `ExtentReports` instance lifecycle |
| `ScreenshotUtil` | Captures `TakesScreenshot` output and saves to `reports/screenshots/` |

---

## 📝 Code Documentation Standards

### Class-Level JavaDoc
Every public class carries a JavaDoc header describing its purpose, what page or
concern it models, and standard tags:

```java
/**
 * Page Object representing the Login page of ParaBank.
 *
 * Manages user interactions with the login form. Uses john/demo —
 * ParaBank's built-in demo account — as the known-valid credential
 * pair for positive scenarios, keeping tests environment-independent.
 *
 * @author Automation Team
 * @version 1.0
 * @since 2026-05-28
 */
public class LoginPage { ... }
```

### Method-Level JavaDoc
Public methods document their intent, parameters, and return value:

```java
/**
 * Selects a from-account by value and fires the browser change event.
 *
 * The change event dispatch is required because ParaBank's JavaScript
 * listens for it to register the selection internally. Without it,
 * the form treats the dropdown as unset even after a valid selection.
 *
 * @param value the option value attribute matching the account ID
 */
public void selectFromAccount(String value) { ... }
```

### Inline Comments
Inline comments explain *why*, never *what*. The code itself describes what happens;
comments provide context that cannot be read from the code:

```java
// >= 1 because the table always contains a Total row even when
// no real accounts exist, so an empty table still returns one row.
Assert.assertTrue(rows.size() >= 1, "Account table empty or failed to load");
```

---

## 📊 Reporting

### Extent Reports
Generated at `reports/extent-report.html` after every test run.

Includes:
- Overall pass/fail/skip summary with timestamps
- Per-test execution detail with steps
- Screenshots embedded inline on failure
- Stack traces for errors

### TestNG Default Report
Generated at `test-output/index.html`.

Includes:
- Suite and test group summary
- Per-method results and durations
- Retry attempt information

---

## 🐛 Troubleshooting

### ChromeDriver version mismatch
**Symptom:** `SessionNotCreatedException` on launch  
**Fix:** Update Selenium WebDriver to the latest version — WebDriverManager resolves
the driver version automatically.

### Dropdown selection has no effect on transfer form
**Symptom:** Transfer button click does nothing; Transfer Complete never appears  
**Root causes and fixes:**
1. Selecting before AJAX completes → `waitForOptions()` guards this
2. Missing `change` event → `fireChangeEvent()` dispatches it
3. Hardcoded account IDs that don't exist → use `getFirstAvailableFromAccount()`

### Test timeout
**Symptom:** `TimeoutException` in `WebDriverWait`  
**Fix:** Increase the wait duration in the relevant page object or `BaseTest`.
Check network connectivity and confirm the ParaBank demo site is reachable.

### Screenshot not saved
**Symptom:** `reports/screenshots/` missing or empty  
**Fix:** Ensure the `reports/screenshots/` directory exists before the run,
or add directory-creation logic to `ScreenshotUtil`.

### `NoSuchElementException` on accounts page
**Symptom:** Account table rows not found  
**Fix:** The accounts table loads after a brief redirect post-login. Add an
explicit wait for the table element before calling `getAccountRows()`.

---

## 📚 Key Dependencies

See `pom.xml` for the complete and pinned list.

| Dependency | Version | Purpose |
|---|---|---|
| Selenium WebDriver | 4.27.0 | Browser automation |
| TestNG | 7.10.0 | Test framework and assertions |
| Extent Reports | 5.1.1 | HTML test reporting |
| Jackson Databind | 2.15.x | JSON parsing for test data |
| WebDriverManager | 5.x | Automatic ChromeDriver management |

---

## 🔍 How to Extend the Framework

### Add a New Page Object

1. Create a class in `src/test/java/pages/`
2. Accept `WebDriver` in the constructor
3. Declare `By` locators as private fields
4. Expose user actions as public methods
5. Add class and method JavaDoc

```java
/**
 * Page Object for [page name].
 */
public class NewPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locator — prefer ID > name > CSS > XPath
    private final By someElement = By.id("elementId");

    public NewPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void clickSomething() {
        wait.until(ExpectedConditions.elementToBeClickable(someElement)).click();
    }
}
```

### Add a New Test Class

1. Create a class in `src/test/java/tests/`
2. Extend `BaseTest`
3. Annotate test methods with `@Test`
4. Use page objects for all interactions — no raw Selenium in test classes

```java
public class NewFeatureTest extends BaseTest {

    @Test
    public void verifyNewFeature() {
        NewPage page = new NewPage(driver);
        Assert.assertTrue(page.isDisplayed(), "New page not displayed");
    }
}
```

### Add New Test Data

Add a new JSON file under `src/test/resources/` and create a matching
`@DataProvider` method in `DataProviderUtil.java`:

```json
[
  {
    "testName": "Scenario description",
    "field1": "value1",
    "field2": "value2"
  }
]
```

---

## 🎯 Future Enhancements

- [ ] Cross-browser testing — Firefox, Edge, Safari
- [ ] Parallel test execution via TestNG `parallel="methods"`
- [ ] Allure Reports integration alongside Extent Reports
- [ ] CI/CD pipeline — GitHub Actions workflow for push and PR triggers
- [ ] API test layer — REST Assured for backend validation
- [ ] Database assertion steps for balance verification
- [ ] Page Factory (`@FindBy`) adoption for cleaner locator management
- [ ] Performance baseline metrics per test

---

## 🤝 Best Practices Implemented

| Practice | Implementation |
|---|---|
| Page Object Model | All page interactions isolated in `pages/` package |
| DRY Principle | Shared setup in `BaseTest`; reusable utilities in `utils/` |
| Explicit Waits | `WebDriverWait` throughout; no `Thread.sleep()` |
| AJAX Handling | `waitForOptions()` guards all dynamic dropdown interactions |
| Event Firing | `fireChangeEvent()` ensures JS listeners receive selections |
| Data-Driven Tests | All scenarios in JSON; zero test data in Java source |
| Retry Logic | `RetryAnalyzer` handles transient infrastructure failures |
| Why-Comments | Inline comments explain reasoning, not code narration |
| Screenshot on Failure | `TestListener` captures and embeds evidence automatically |

---

## 📄 License

This project is licensed under the MIT License. See the `LICENSE` file for details.

---

*Last Updated: May 28, 2026*
