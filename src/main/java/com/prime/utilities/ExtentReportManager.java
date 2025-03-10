package com.prime.utilities;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {
    
    public static ExtentReports extent;
    public static ExtentTest test;
    public static ExtentSparkReporter sparkReporter;
    
    public static void setUpReports() {
        String reportPath = System.getProperty("user.dir") + "/test-output/ExtentReport/reports.html";
        sparkReporter = new ExtentSparkReporter(reportPath);
        
        // Configuration settings
        sparkReporter.config().setDocumentTitle("Automation Test Report");
        sparkReporter.config().setReportName("SauceDemo Project Test Report");
        sparkReporter.config().setTheme(Theme.DARK);
        
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        
        
        // System Info
        extent.setSystemInfo("Project Name", "SauceDemo");
        extent.setSystemInfo("Tester", "Mankgethwa");
        extent.setSystemInfo("Browser", "Chrome");
    }
    
    
    public static ExtentReports getExtent() {
        return extent;
    }
    
    public static void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    
}

}
