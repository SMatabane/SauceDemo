Automated SauceDemo website

## Project Overview

This project automates the end-to-end testing of an e-commerce web application using Selenium, TestNG, and the Page Object Model (POM). The test suite covers various user scenarios, including login, product search, cart operations, and checkout.

🛠️ Technologies Used

Java (Test Automation)

Selenium WebDriver (Browser Automation)

TestNG (Test Framework)

Maven (Build & Dependency Management)

Page Object Model (POM) (Test Design Pattern)

Extent Reports (Test Reporting)

##  Features Tested

1️ User Login

- Automates login with standard user credentials.

- Verifies successful login by checking for the presence of a specific element on the landing page.

2️ Product Search and Filter

- Sorts products by price (low to high).

- Validates that the products are sorted correctly.

- Checks for broken links on the page.

3️ Add to Cart and Purchase

- Selects a product and adds it to the cart.

- Proceeds to checkout, enters shipping details, and places the order.

- Verifies the order confirmation message.

4️ Remove Item from Cart

- Automates the removal of a product from the cart.

- Validates that the product is successfully removed.

5️ Product Details

- Navigates to a product's detail page.

- Validates that the displayed product information is correct.

6️ Account Management

- Automates login for multiple types of users:

- Standard User

- Locked Out User

- Problem User

- Validates login behavior for each user type.

7️ Log Out

- Automates the logout process.

- Validates that the user is successfully logged out and redirected to the login page.

 ## Setup & Execution

 PRun Tests

1️ Using TestNG XML

$ mvn clean test

️⃣ Run Specific Test Class
$ mvn test -Dtest=TestClassName

Generate Test Reports

- Extent Reports are generated automatically after execution.
- screenshots are attached on the extent report as Base64

-  test-output/ExtentReport/MyReport.html in a browser to view detailed reports.