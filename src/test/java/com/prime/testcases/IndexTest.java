package com.prime.testcases;

import com.prime.base.BaseClass;
import com.prime.pageobjects.HomePage;
import com.prime.pageobjects.LoginPage;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class IndexTest extends BaseClass {

    private LoginPage login;
    private HomePage home;

    private  final Logger logs = Logger.getLogger(IndexTest.class);
    @BeforeClass
    public void launch(){
        setUp();
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
        logs.info("products list are displayed");

    }


    /**
     * verify list of products are displayed
     */
    @Test(priority = 2,dependsOnMethods = {"Verifylink"})
    public void verifyProductCoount(){
        int count = home.getProductCount();
        logs.info("Number of products displayed: " + count);
        Assert.assertTrue(count > 0, "No products found on the page!");
    }


    /**
     * verify the price is sorted from low to high
     */
    @Test(priority = 3,dependsOnMethods = {"Verifylink"},enabled = false)
    public void verifyLowToHighSorting() {
        home.selectDropDown(properties.getProperties("value")); // Select sorting option
        boolean isSorted = home.isSortedLowToHigh();
        Assert.assertTrue(isSorted, "Prices are NOT sorted from low to high!");
        logs.info("Items sorted by price: " );


    }


    /**
     * verify product detais are correct
     */
    @Test(priority = 4)
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
    public void tearDown(){
        getDriver().quit();
    }
}
