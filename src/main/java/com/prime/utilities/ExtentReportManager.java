package com.prime.utilities;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import lombok.Getter;

public class ExtentReportManager {

    /**
     * -- GETTER --
     *  Get ExtentReports instance
     */
    @Getter
    private static ExtentReports extent;
    private static ExtentSparkReporter sparkReporter;

    // ThreadLocal for thread-safe ExtentTest
    private static ThreadLocal<ExtentTest> extentTestThreadLocal = new ThreadLocal<>();

    /**
     * Setup Extent Report
     */
    public static void setUpReports() {
        String reportPath = System.getProperty("user.dir") + "/test-output/ExtentReport/reports.html";
        sparkReporter = new ExtentSparkReporter(reportPath);

        // Report configurations
        sparkReporter.config().setDocumentTitle("Automation Test Report");
        sparkReporter.config().setReportName("SauceDemo Project Test Report");
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // System info
        extent.setSystemInfo("Project Name", "SauceDemo");
        extent.setSystemInfo("Tester", "Mankgethwa");
        extent.setSystemInfo("Browser", "Chrome");
    }

    /**
     * Get ExtentTest instance for current thread
     */
    public static ExtentTest getTest() {
        return extentTestThreadLocal.get();
    }

    /**
     * Set ExtentTest instance for current thread
     */
    public static void setTest(ExtentTest test) {
        extentTestThreadLocal.set(test);
    }

    /**
     * Flush the report
     */
    public static void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}
