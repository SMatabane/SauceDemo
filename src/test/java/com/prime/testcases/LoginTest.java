package com.prime.testcases;

import com.prime.base.BaseClass;
import com.prime.pageobjects.HomePage;
import com.prime.pageobjects.LoginPage;
import org.apache.log4j.Logger;
import org.testng.annotations.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
public class LoginTest extends BaseClass {

    private LoginPage login;
    private HomePage home;


    private  final Logger logs = Logger.getLogger(LoginTest.class);
    /**
     * set up driver
     */
    @Parameters("browser")
    @BeforeClass
    public void launch(String browser){
        setUp(browser);
        login=new LoginPage();
        home=new HomePage();

    }
    /**
     * log user in
     */
    @Test(priority = 1)
    public void LogUser(){

        login.loginDetails(properties.getProperties("username"),properties.getProperties("password"));
        assertTrue(home.areProductsDisplayed(),"Items not displayed");
        login.LogOut();
        logs.info("products list are displayed");

    }

    /**
     * verify error text is shown when logged with invalid credentials
     */
    @Test(priority = 2,dependsOnMethods = {"LogUser"})
    public void veryfyErrorText() {
        login.loginDetails(properties.getProperties("invalidusername"),properties.getProperties("invalidpassword"));
        assertTrue(login.Erromessage(),"Message is not displayed");
    }

    /**
     * verify logib beg=haviour of different users
     */
    @Test(priority = 3,dataProvider = "userData")
    public void accountManagement(String username, String password, String userType) throws InterruptedException {

        login.loginMultipleUsers(username,password);

        if (userType.equals("locked_out_user")) {
            assertEquals(login.Text(),
                    "Epic sadface: Sorry, this user has been locked out.");

        } else {

            assertTrue(home.areProductsDisplayed(),"Items not displayed");
        }


    }
    /**
     * verify user is logged out
     */
    @Test(priority = 4,dependsOnMethods = {"accountManagement"})
    public void veryfyLogedOut(){
        login.LogOut();
        assertTrue(login.FormVisible(), "No form found");

    }




    @DataProvider(name = "userData")
    public Object[][] getUsers() {
        return new Object[][] {
               // { properties.getProperties("username"), properties.getProperties("password"), "standard_user" },
                { properties.getProperties("username2"), properties.getProperties("password2"), "locked_out_user" },
                {properties.getProperties("username3"), properties.getProperties("password2"), "problem_users" }
        };
    }


    /**
     * close browser
     */
    @AfterClass
    public void CloseBrowser(){
        tearDown();
    }
}
