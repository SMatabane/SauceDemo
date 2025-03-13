package com.prime.testcases;

import com.prime.base.BaseClass;
import com.prime.pageobjects.HomePage;
import com.prime.pageobjects.LoginPage;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class IndexTest extends BaseClass {

    private LoginPage login;
    private HomePage home;

    private  final Logger logs = Logger.getLogger(IndexTest.class);
    @Parameters("browser")
    @BeforeClass
    public void launch(String browser){
        setUp(browser);
        login=new LoginPage();
        home=new HomePage();
    }



    /**
     *
     * verify links are not broken
     */
    @Test(priority = 1)
    public void Verifylink(){

        login.loginDetails(properties.getProperties("username"),properties.getProperties("password"));
        home.validateLinks();
        login.LogOut();
        logs.info("products list are displayed");

    }


    /**
     * verify list of products are displayed
     */
    @Test(priority = 2)
    public void verifyProductCount(){
        login.loginDetails(properties.getProperties("username"),properties.getProperties("password"));
        int count = home.getProductCount();
        logs.info("Number of products displayed: " + count);
        Assert.assertTrue(count > 0, "No products found on the page!");
    }


    /**
     * verify the price is sorted from low to high
     */
    @Test(priority = 3,dependsOnMethods = {"verifyProductCount"})
    public void verifyLowToHighSorting() {
        home.selectDropDown(properties.getProperties("value")); // Select sorting option
        boolean isSorted = home.isSortedLowToHigh();
        Assert.assertTrue(isSorted, "Prices are NOT sorted from low to high!");
        logs.info("Items sorted by price: " );


    }


    /**
     * verify product detais are correct
     */
    @Test(priority = 4,dependsOnMethods = {"verifyProductCount"})
    public void verifyProductDetails() {
        String actualMessage= home.getTitle();
        String expected= home.isvalidInfor();
        assertEquals(actualMessage,expected);
        logs.info("actual :" +actualMessage +"\n expected: " +expected);
    }
    /**
     * close browser
     */
    @AfterClass
    public void CloseBrowser(){
        tearDown();
    }
}
