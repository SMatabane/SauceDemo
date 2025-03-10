package com.prime.pageobjects;

import com.prime.actions.ActionsClass;
import com.prime.base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrderPage extends BaseClass {
    ActionsClass act=new ActionsClass();

    @FindBy(id = "first-name")
    private WebElement fname;

    @FindBy(id = "last-name")
    private WebElement lname;

    @FindBy(id = "postal-code")
    private WebElement zipCode;

    @FindBy(id = "continue")
    private WebElement continueBTn;

    @FindBy(xpath = "//div[@class='summary_total_label']")
    private WebElement totalPrice;

    @FindBy(id = "finish")
    private WebElement finishBtn;

    @FindBy(xpath = "//div[@class='complete-text']")
    private WebElement completeorder;

    @FindBy(xpath = "//button[@class='error-button']")
    private WebElement errorText;



    public OrderPage() {
        PageFactory.initElements(getDriver(), this);
    }

    //send keys and submitting the details
    public void orderDetails(String nameText,String l_nameTest,String zipcode){
        act.sendkeys(fname,nameText);
        act.sendkeys(lname,l_nameTest);
        act.sendkeys(zipCode,zipcode);
        act.JSClick(getDriver(),continueBTn);

    }


    //Is total Price displayed
    public Boolean TotalPrice(){
         //
        return act.isDisplayed(getDriver(),totalPrice);
    }

    public boolean order(){
        act.JSClick(getDriver(),finishBtn);
        return act.isDisplayed(getDriver(),completeorder);
    }



    public String getMessage(){
        act.explicitWait(getDriver(),errorText,2000);
        return errorText.getText();

    }

    // Check if last name field is accepting input
    public boolean isLastNameFieldEmpty() {
        By lastNameField = By.id("last-name");
        return getDriver().findElement(lastNameField).getAttribute("value").isEmpty();
    }





}
