package com.prime.pageobjects;

import com.prime.actions.ActionsClass;
import com.prime.base.BaseClass;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BaseClass {

    ActionsClass act=new ActionsClass();

    @FindBy(id = "user-name")
    private WebElement username;

    @FindBy(id="password")
    private WebElement password;

    @FindBy(id="login-button")
    private WebElement btnLogin;

    @FindBy(xpath = "//h3[contains(text(),'Epic sadface: Sorry, this user has been locked out')]")
    private WebElement errorText;

    @FindBy(xpath = "//button[@class='error-button']")
    private WebElement closeBtn;

    @FindBy(id="react-burger-menu-btn")
    private WebElement menu;

    @FindBy(id="logout_sidebar_link")
    private WebElement logout;

    @FindBy(xpath = "//div[@class='login-box']//form")
    private WebElement form;






    public LoginPage() {
        PageFactory.initElements(getDriver(), this);
    }

    //send keys and submitting the form
    public void loginDetails(String nameText,String passwordText){
        
        act.sendkeys(username,nameText);
        password.sendKeys(Keys.CONTROL, "a", Keys.DELETE);
        act.sendkeys(password,passwordText);
        act.JSClick(getDriver(),btnLogin);

    }

    public void loginMultipleUsers(String nameText,String passwordText) throws InterruptedException {
        username.clear();
        act.sendkeys(username,nameText);
        password.clear();
        act.sendkeys(password,passwordText);
        act.JSClick(getDriver(),btnLogin);



    }

    //validate the text erre is displayed
    public boolean Erromessage() {
        act.explicitWait(getDriver(), errorText, 4000);
        return act.isDisplayed(getDriver(), errorText);

    }

    //get the error message
    public String Text()
    {
        return errorText.getText();
    }


    /**
     * Method for logging out
     */
    public void LogOut(){
        act.JSClick(getDriver(),menu);
        act.explicitWait(getDriver(),logout,3000);
        act.JSClick(getDriver(),logout);

    }

    /**
     * Method for validating I have successfully loged out
     */
    public boolean FormVisible(){
        act.explicitWait(getDriver(),form,2000);
        return act.isDisplayed(getDriver(),form);
    }
    /**
     * Method for dissmising error message
     */
    public void closeBtn(){
        act.JSClick(getDriver(),closeBtn);
    }






}
