package CheckoutPage;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import Base.BaseClass;
import objrepo.LoginPage;
import objrepo.AddToCartPage;
import objrepo.CheckoutPage;
import utilities.ExcelUtils;
import utilities.Screenshot;

public class CheckoutPageTest extends BaseClass {

    LoginPage login;
    AddToCartPage cart;
    CheckoutPage checkout;

    // Common method to login and add a product
    public void loginAndOpenCheckout() throws Exception {

        ExcelUtils excel = new ExcelUtils();

        String username = excel.getData("Sheet1", 1, 0);
        String password = excel.getData("Sheet1", 1, 1);

        login = new LoginPage(driver);
        login.getUsername().sendKeys(username);
        login.getPassword().sendKeys(password);
        login.getLoginButton().click();

        cart = new AddToCartPage(driver);
        cart.addProductToCart("Sauce Labs Backpack");
        cart.openCart();

        checkout = new CheckoutPage(driver);
    }

    // CHK-SM-001: Verify Checkout button opens checkout page
    @Test(groups = {"functional"})
    public void verifyCheckoutButtonOpensCheckoutPage() throws Exception {

        loginAndOpenCheckout();

        checkout.getCheckoutButton().click();

        Assert.assertEquals(
            checkout.getPageTitle().getText(),
            "Checkout: Your Information",
            "Checkout information page did not open"
        );

        Reporter.log(
            "CHK-SM-001 PASSED: Checkout information page opened",
            true
        );
    }

 // CHK-FUN-003: Verify missing postal code validation
    @Test(groups = {"functional"})
    public void verifyMissingPostalCodeValidation() throws Exception {

        loginAndOpenCheckout();

        checkout.getCheckoutButton().click();

        checkout.getFirstName().sendKeys("Tom");
        checkout.getLastName().sendKeys("Test");
        // Postal code intentionally left blank

        checkout.getContinueButton().click();

        Assert.assertTrue(
            checkout.getErrorMessage().isDisplayed(),
            "Validation error was not displayed"
        );

        Assert.assertEquals(
            checkout.getErrorMessage().getText(),
            "Error: Postal Code is required",
            "Incorrect validation message"
        );

        // Capture screenshot of the negative test result
        Screenshot.takeScreenshot(driver, "MissingPostalCode");

        Reporter.log(
            "CHK-FUN-003 PASSED: Missing postal code validation displayed",
            true
        );
    }
    

    // CHK-INT-001: Verify cart product appears on checkout overview
    @Test(groups = {"integration"})
    public void verifyCartProductOnCheckoutOverview() throws Exception {

        // Step 1: Login and add a product to the cart
        loginAndOpenCheckout();

        // Step 2: Click Checkout
        checkout.getCheckoutButton().click();

        // Step 3: Enter checkout information
        checkout.getFirstName().sendKeys("Tom");
        checkout.getLastName().sendKeys("Test");
        checkout.getPostalCode().sendKeys("560001");

        // Step 4: Continue to Checkout Overview
        checkout.getContinueButton().click();

        // Step 5: Verify the Checkout Overview page
        Assert.assertEquals(
            checkout.getPageTitle().getText(),
            "Checkout: Overview",
            "Checkout overview page did not open"
        );

        // Step 6: Verify product details carried from the cart
        Assert.assertTrue(
            driver.getPageSource().contains("Sauce Labs Backpack"),
            "Cart product was not displayed on checkout overview"
        );

        Reporter.log(
           "Cart product displayed on checkout overview",
            true
        );

    }
    
    // CHK-SM-002: Verify valid details allow Continue
    @Test(groups = {"smoke"})
    public void verifyValidDetailsAllowContinue() throws Exception {

        loginAndOpenCheckout();

        checkout.getCheckoutButton().click();

        checkout.getFirstName().sendKeys("Tom");
        checkout.getLastName().sendKeys("Test");
        checkout.getPostalCode().sendKeys("560001");

        checkout.getContinueButton().click();

        Assert.assertEquals(
            checkout.getPageTitle().getText(),
            "Checkout: Overview",
            "User did not proceed to checkout overview"
        );

        Reporter.log(
            "CHK-SM-002 PASSED: Valid details opened checkout overview",
            true
        );
        
    }
    

    // CHK-SYS-001: Verify complete checkout and order confirmation
    @Test(groups = {"system"})
    public void verifyCompleteOrderAndThankYouPage() throws Exception {

        // Login and add a product to the cart
        loginAndOpenCheckout();

        // Open checkout information page
        checkout.getCheckoutButton().click();

        // Enter checkout details
        checkout.getFirstName().sendKeys("Tom");
        checkout.getLastName().sendKeys("Test");
        checkout.getPostalCode().sendKeys("560001");

        // Continue to checkout overview
        checkout.getContinueButton().click();

        Assert.assertEquals(
            checkout.getPageTitle().getText(),
            "Checkout: Overview",
            "Checkout overview page did not open"
        );

        // Click Finish to place the order
        checkout.getFinishButton().click();

        // Verify thank-you confirmation page
        Assert.assertEquals(
            checkout.getConfirmationMessage().getText(),
            "Thank you for your order!",
            "Thank-you confirmation page was not displayed"
        );

        Reporter.log(
            "CHK-SYS-001 PASSED: Order completed and thank-you page displayed",
            true
        );

        System.out.println(
            "CHK-SYS-001 PASSED: Checkout completed successfully"
        );
    }
}
