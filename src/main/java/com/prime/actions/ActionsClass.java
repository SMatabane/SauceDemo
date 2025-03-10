package com.prime.actions;


import com.prime.actionInterface.ActionInterface;
import com.prime.base.BaseClass;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

/**
 * @author Mankgethwa Matabane 
 *Actions class 
 */
public class ActionsClass  extends  BaseClass implements ActionInterface {

    private Actions action;
    private static final Logger logs = Logger.getLogger(ActionsClass.class);

    public ActionsClass() {

        action = new Actions(getDriver());
    }


    @Override
    public void click(WebElement element) {
        action.click(element).perform();

    }

    @Override
    public void scrollToElement(WebElement element) {

        action.scrollToElement(element);

    }

    /**
     * This method for explicit waits
     *
     * @param element,driver,time
     * @return track
     */
    @Override
    public void explicitWait(WebDriver driver, WebElement element, long timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.visibilityOf(element));

    }

    /**
     * This method track whether the element is displayed was successful.
     *
     * @param ele,driver
     * @return track
     */

    @Override
    public boolean isDisplayed(WebDriver driver, WebElement ele) {
        boolean track = false;
        track = findElement(driver, ele);
        if (track) {
            track = ele.isDisplayed();
            if (track) {
                logs.info("The element is Displayed");
            } else {
                logs.error("The element is not Displayed");
            }
        }
        return track;
    }

    /**
     * This method locates element
     *
     * @param ele,driver
     */

    @Override
    public boolean findElement(WebDriver driver, WebElement ele) {
        boolean track = false;
        try {
            ele.isDisplayed();
            track = true;
        } catch (Exception e) {

            track = false;
        } finally {
            if (track) {
                logs.info("Successfully Found element at");

            } else {
                logs.error("Unable to locate element at");
            }
        }
        return track;
    }

    /**
     * This method returns a boolean value to confirm if the action for clicking on button is performed
     *
     * @param driver,ele
     * @return track
     */
    @Override
    public boolean JSClick(WebDriver driver, WebElement ele) {
        boolean track = false;
        try {
            // WebElement element = driver.findElement(locator);
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", ele);
            track = true;

        } catch (Exception e) {
            throw e;

        } finally {
            if (track) {
                logs.info("Click Action is performed");
            } else if (!track) {
                logs.error("Click Action is not performed");
            }
        }
        return track;
    }


    /**
     * @param elem,text This method sends keys on the targeted element
     */
    @Override
    public void sendkeys(WebElement elem, String text) {
        action.sendKeys(elem, text).perform();

    }

    /**
     * @param filename This method takes screenshots
     * @param driver
     */

    public static String captureScreenshot(WebDriver driver, String filename) {
        String dateName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        // Capture screenshot as File
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

        // Ensure directory exists
        String screenshotDir = System.getProperty("user.dir") + "/ScreenShots/";
        new File(screenshotDir).mkdirs();

        // Full path where image will be saved
        String filePath = screenshotDir + filename + "_" + dateName + ".png";

        try {
            // Save screenshot file
            FileUtils.copyFile(sourceFile, new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Also capture Base64 for inline attachment
        String base64Screenshot = takesScreenshot.getScreenshotAs(OutputType.BASE64);

        // Return Base64 encoded string to be attached to report
        return base64Screenshot;
    }





}
