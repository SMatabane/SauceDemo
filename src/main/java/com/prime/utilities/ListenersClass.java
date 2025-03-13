package com.prime.utilities;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.prime.actions.ActionsClass;
import com.prime.base.BaseClass;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ListenersClass implements ITestListener {

    private static final Logger logs = Logger.getLogger(ListenersClass.class);

    @Override
    public void onTestStart(ITestResult result) {
        ExtentReportManager.setTest(ExtentReportManager.getExtent().createTest(result.getName()));
        logs.info("Test started: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentReportManager.getTest().log(Status.PASS, "Test Passed: " + result.getName());
        logs.info("Test passed: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentReportManager.getTest().log(Status.FAIL,
                MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
        ExtentReportManager.getTest().log(Status.FAIL, result.getThrowable());
        logs.error("Test failed: " + result.getName());

        WebDriver driver = BaseClass.getDriver(); // Get thread-safe driver

        if (driver != null) {
            String base64Screenshot = ActionsClass.captureScreenshot(driver, result.getName());
            ExtentReportManager.getTest().fail("Screenshot of failure:",
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentReportManager.getTest().log(Status.SKIP, "Test Skipped: " + result.getName());
        logs.warn("Test skipped: " + result.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        logs.warn("Test failed but within success percentage: " + result.getName());
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        logs.error("Test failed with timeout: " + result.getName());
    }

    @Override
    public void onStart(ITestContext context) {
        logs.info("Test suite started: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        logs.info("Test suite finished: " + context.getName());
        ExtentReportManager.flushReport();
    }
}
