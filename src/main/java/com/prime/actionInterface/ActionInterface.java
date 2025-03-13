package com.prime.actionInterface;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.time.Duration;
import java.util.List;

public interface ActionInterface {


    public void explicitWait(WebDriver driver, WebElement element, long timeOut);
    public void explicitWaitClicable(WebDriver driver, WebElement element, long timeOut);
    public boolean isDisplayed(WebDriver driver, WebElement ele);
    public void sendkeys(WebElement elm,String text);
    public boolean findElement(WebDriver driver, WebElement ele);
    public boolean JSClick(WebDriver driver, WebElement ele);


}
