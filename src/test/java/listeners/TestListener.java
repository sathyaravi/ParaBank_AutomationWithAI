package listeners;


import base.BaseTest;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import com.aventstack.extentreports.Status;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import utils.ExtentManager;
import utils.ScreenshotUtil;

public class TestListener implements ITestListener {

    private static ExtentReports extent =
            ExtentManager.getInstance();

    private static ThreadLocal<ExtentTest> test =
            new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {

        ExtentTest extentTest =
                extent.createTest(
                        result.getMethod().getMethodName()
                );

        test.set(extentTest);

        test.get().log(
                Status.INFO,
                "Test Started"
        );
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        test.get().log(
                Status.PASS,
                "Test Passed"
        );
    }

    @Override
    public void onTestFailure(ITestResult result) {

        test.get().log(
                Status.FAIL,
                result.getThrowable()
        );

        // Screenshot Capture - obtain driver from the test instance
        WebDriver driver = null;
        try {
            Object testInstance = result.getInstance();
            if (testInstance instanceof BaseTest) {
                driver = ((BaseTest) testInstance).getDriver();
            }
        } catch (Exception e) {
            // ignore - driver may remain null
        }

        String screenshotPath =
                ScreenshotUtil.captureScreenshot(
                        driver,
                        result.getMethod().getMethodName()
                );

        try {

            test.get().addScreenCaptureFromPath(
                    screenshotPath
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        test.get().log(
                Status.SKIP,
                "Test Skipped"
        );
    }

    @Override
    public void onFinish(ITestContext context) {

        extent.flush();
    }
}
