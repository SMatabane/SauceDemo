package com.prime.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
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



public class ListenersClass extends ExtentReportManager  implements ITestListener {

    private static final Logger logs = Logger.getLogger(ListenersClass.class);
    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getName());
        logs.info("Test started : " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "Pass Test case is: " + result.getName());
            logs.info("Test case passed : " + result.getName());
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
            test.log(Status.FAIL, result.getThrowable());


            WebDriver driver = BaseClass.getDriver(); // Your way to get the WebDriver instance

            if (driver != null) {
                // Capture screenshot (saved as file and returned as Base64)
                String base64Screenshot = ActionsClass.captureScreenshot(driver, result.getName());

                // Attach Base64 image to Extent report
                test.fail("Screenshot of failure:",
                        MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());



            }
        }
    }





    @Override
    public void onTestSkipped(ITestResult result) {
        logs.warn("Test case skipped : " +result.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        logs.warn("TestFailed But Within SuccessPercentage : " +result);
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        //ITestListener.super.onTestFailedWithTimeout(result);
        logs.error("Test Failed With Timeout : "+ result);
    }

    @Override
    public void onStart(ITestContext context) {
        ITestListener.super.onStart(context);
    }

    @Override
    public void onFinish(ITestContext context) {
        ITestListener.super.onFinish(context);
    }
}
