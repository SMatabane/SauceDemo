package com.prime.pageobjects;

import com.prime.actions.ActionsClass;
import com.prime.base.BaseClass;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartPage extends BaseClass {
    ActionsClass act=new ActionsClass();



    @FindBy(id = "add-to-cart-sauce-labs-backpack")
    private WebElement cartBtn;

    @FindBy(id = "add-to-cart-sauce-labs-bike-light")
    private WebElement cartbtn2;

    @FindBy(xpath = "//span[@class='shopping_cart_badge']")
    private WebElement badge;

    @FindBy(xpath = "//a[@class='shopping_cart_link']")
    private WebElement view_cart;

    @FindBy(id = "remove-sauce-labs-backpack")
    private WebElement removeCart;

    @FindBy(xpath = "//div[@class='cart_item']")
    private List<WebElement> cartItems;

    @FindBy(xpath = "//button[@id='checkout']")
    private WebElement checkoutBTN;

    public CartPage() {
        PageFactory.initElements(getDriver(), this);
    }




    /**
     * Method to add item to cart 1
     */
    public void addToCart(){
        act.JSClick(getDriver(),cartBtn);
        act.JSClick(getDriver(),cartbtn2);
    }


    /**
     * Method to check if badge is updated after adding to cart
     */
    public String UpdateBadge() {


       act.explicitWait(getDriver(),badge,4000);
        return badge.getText();
    }

    /**
     * Method to navigate to cart
     */
    public void OpenCart(){
        act.explicitWaitClicable(getDriver(),view_cart,3000);
        act.JSClick(getDriver(),view_cart);

    }

    /**
     * Method that verifies the items are in the cart
     */
    public boolean IsAvailable(){
       // act.explicitWait(getDriver(),cartItems,3000);
        return !cartItems.isEmpty() && cartItems.get(0).isDisplayed();

    }

    /**
     * Method to get product count
     */
    public int getItemsCount() {
        return cartItems.size();
    }

    /**
     * Method removed the item form the cart
     */
    public void removeItem(){
        //remove item
        act.JSClick(getDriver(),removeCart);
    }

    /**
     * Checkout
     */
    public void Checkout(){
        act.JSClick(getDriver(),checkoutBTN);
    }






}