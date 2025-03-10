package com.prime.testcases;

import com.prime.base.BaseClass;
import com.prime.pageobjects.CartPage;
import com.prime.pageobjects.LoginPage;
import com.prime.pageobjects.OrderPage;
import com.prime.utilities.ListenersClass;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

//@Listeners(ListenersClass.class)
public class CartTest extends BaseClass {

    private LoginPage login;
    private CartPage home;
    private OrderPage order;
    private static final Logger logs = Logger.getLogger(CartTest.class);

    /**
     * set up browser
     */

    @BeforeClass
    public void launch(){
        setUp();
        login=new LoginPage();
        home=new CartPage();
        order=new OrderPage();
    }

    /**
     * add items
     */


    @Test(priority = 1)
    public void LogUser(){

        login.loginDetails(properties.getProperties("username"),properties.getProperties("password"));
         home.addToCart();
         assertEquals(home.UpdateBadge(),"2");
        logs.info("Item added to cart");

    }



    /**
     * view cart and verify list of items
     */

    @Test(priority = 2)
    public void ViewCart(){
        home.OpenCart();
        int number=home.getItemsCount();
        assertTrue(number>0,"Items not available");
        logs.info("Items are available in cart. ");

    }

    /**
     * Method for removing an item cart
     */

    @Test(priority = 3)

    public void RemoveItem(){
        home.removeItem();
        assertEquals(home.UpdateBadge(),"1");
        logs.info("Item removed from cart");


    }

    //order

    /**
     * Method for proceeding to checkout
     */

    @Test(priority = 4)
    public void Checkout()
    {
       home.Checkout();
       order.orderDetails(properties.getProperties("firstname"), properties.getProperties("lastname"), properties.getProperties("zip") );
       assertTrue(order.TotalPrice(),"Price not is displayed");
        logs.info("------Checkout-------- ");
    }

    /**
     * Method for ordering items
     */

    @Test(priority = 5)
    public void OrderItem(){
        assertTrue(order.order(),"The order is complete");
        login.LogOut();
    }



    /**
     * close browser
     */

    @AfterClass
    public void tearDown(){
        getDriver().quit();
    }




}
