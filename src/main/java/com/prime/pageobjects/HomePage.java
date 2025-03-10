package com.prime.pageobjects;

import com.prime.actions.ActionsClass;
import com.prime.base.BaseClass;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.List;
import java.util.stream.Collectors;

public class HomePage extends BaseClass {
    ActionsClass act=new ActionsClass();

    private static final Logger logs = Logger.getLogger(HomePage.class);


    @FindBy(xpath = "//a[contains(@id,'_title_link')]")
    private List<WebElement> links;

    @FindBy(xpath = "//div[@class='inventory_item']")
    private List<WebElement> productList;

    @FindBy(xpath = "//div[normalize-space()='Sauce Labs Backpack']")
    private WebElement product;

    @FindBy(xpath = "//div[@class='inventory_details_name large_size']")
    private WebElement title;

    @FindBy(xpath = "//select[@class='product_sort_container']")
    private WebElement dropdown;

    @FindBy(xpath = "//div[@class='inventory_item_price']")
    private List<WebElement> prices;

    /**
     * Method to check if products are displayed
     */
    public boolean areProductsDisplayed() {
        return !productList.isEmpty() && productList.get(0).isDisplayed();
    }

    /**
     * Method to get product count
     */
    public int getProductCount() {
        return productList.size();
    }


    public HomePage() {
        PageFactory.initElements(getDriver(), this);
    }


    /**
     * Method for validating links
     */

    public void validateLinks() {
        List<WebElement> allLinks = links;

        for (WebElement link : allLinks) {
            String url = link.getAttribute("href");

            if (url != null && !url.isEmpty()) {
                checkLink(url);
            } else {
                logs.warn("Skipping empty or null link: " + link.getText());
            }
        }
    }

    /**
     * Method for checking the link
     */

    private void checkLink(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("HEAD");
            connection.connect();
            int responseCode = connection.getResponseCode();

            if (responseCode >= 400) {
                logs.error(" Broken Link: " + url + " | Response Code: " + responseCode);

            } else {

                logs.info("Valid Link: " + url + " | Response Code: " + responseCode);

            }

        } catch (IOException  e) {
            logs.error("Error Checking Link: " + url + " | Exception: " + e.getMessage());
        }
    }


    /**
     * Method for	Navigate to a product's detail page and validate that the product information is correct
     */

    public String isvalidInfor( ){
        return title.getText();

    }


    public String getTitle(){
         act.JSClick(getDriver(),product);

         return  product.getText();

    }

    /**
     * Method for sorting price
     */
    public void selectDropDown(String value){
        Select selector=new Select(dropdown);
        selector.selectByValue(value);
    }

    public boolean isSortedLowToHigh() {
        List<Double> actualPrices = prices.stream()
                .map(e -> e.getText().replace("$", "").trim()) // Extract text
                .map(Double::parseDouble) // Convert to Double
                .collect(Collectors.toList()); // Collect to List

        List<Double> sortedPrices = actualPrices.stream()
                .sorted() // Sort in ascending order
                .collect(Collectors.toList());

        return actualPrices.equals(sortedPrices); // Compare original vs sorted list
    }





}
