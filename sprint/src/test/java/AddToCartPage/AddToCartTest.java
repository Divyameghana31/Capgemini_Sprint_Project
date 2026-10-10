package AddToCartPage;

import java.util.List;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import Base.BaseClass;
import objrepo.LoginPage;
import utilities.Screenshot;
import objrepo.AddToCartPage;

public class AddToCartTest extends BaseClass {

    LoginPage login;
    AddToCartPage cart;


    public void loginToApplication() throws InterruptedException {

        // Confirm that the browser driver exists
        if (driver == null) {
            throw new RuntimeException(
                    "Driver is null. BaseClass setup did not run.");
        }

        // Open SauceDemo
        driver.get("https://www.saucedemo.com/");

        // Initialize LoginPage using the current driver
        login = new LoginPage(driver);

        // Enter username slowly
        for (char ch : "standard_user".toCharArray()) {
            login.getUsername().sendKeys(String.valueOf(ch));
            Thread.sleep(200);
        }

        // Enter password slowly
        for (char ch : "secret_sauce".toCharArray()) {
            login.getPassword().sendKeys(String.valueOf(ch));
            Thread.sleep(200);
        }

        login.getLoginButton().click();
        Thread.sleep(1000);

        // Initialize AddToCartPage after login
        cart = new AddToCartPage(driver);

        System.out.println("Login successful. Products page opened.");
    }
  


    // AC-FUN-001: Add one product to the cart
    @Test(groups = {"functional"})
    public void verifyAddProductToCart() throws InterruptedException {
        loginToApplication();

        cart.addProductToCart("Sauce Labs Backpack");

        Assert.assertEquals(
                cart.getCartCount(),
                "1",
                "Cart count should be 1 after adding the product"
        );

        Reporter.log(
                "AC-FUN-001 PASSED: Sauce Labs Backpack added to cart"
        );
    }

    // AC-INT-002: Verify the selected product appears in the cart
    @Test(groups = {"integration"})
    public void verifyProductDisplayedInCart()
            throws InterruptedException {
        loginToApplication();

        cart.addProductToCart("Sauce Labs Backpack");
        cart.openCart();

        List<WebElement> cartProducts = cart.getCartProducts();

        Assert.assertTrue(
                cartProducts.stream().anyMatch(
                        product -> product.getText()
                                .equals("Sauce Labs Backpack")),
                "Sauce Labs Backpack was not displayed in the cart"
        );

        Reporter.log(
                "AC-INT-002 PASSED: Product displayed correctly in cart"
        );
    }

    // AC-FUN-003: Add multiple products and capture the cart
    @Test(groups = {"functional"})
    public void verifyMultipleProductsInCart()
            throws Exception {
        loginToApplication();

        cart.addProductToCart("Sauce Labs Backpack");
        cart.addProductToCart("Sauce Labs Bike Light");
        cart.addProductToCart("Sauce Labs Bolt T-Shirt");

        Assert.assertEquals(
                cart.getCartCount(),
                "3",
                "Cart should contain 3 products"
        );

        cart.openCart();

        List<WebElement> cartProducts = cart.getCartProducts();

        Assert.assertEquals(
                cartProducts.size(),
                3,
                "Cart should display 3 products"
        );


        Reporter.log(
                "AC-FUN-003 PASSED: Three products displayed in cart"
        );
        Screenshot.takeScreenshot(driver, "AddToCart");
    }
    
 // AC-SYS-001: Verify the complete Add to Cart flow
    @Test(groups = {"system"})
    public void verifyCompleteAddToCartFlow() throws InterruptedException {

        loginToApplication();

        // Add a product
        cart.addProductToCart("Sauce Labs Backpack");

        // Verify cart badge
        Assert.assertEquals(
                cart.getCartCount(),
                "1",
                "Cart badge should display 1"
        );

        // Open the cart page
        cart.openCart();

        // Verify the product is displayed
        List<WebElement> cartProducts = cart.getCartProducts();

        Assert.assertTrue(
                cartProducts.stream().anyMatch(
                        product -> product.getText()
                                .equals("Sauce Labs Backpack")),
                "Sauce Labs Backpack should be displayed in the cart"
        );

        Reporter.log(
                "AC-SYS-001 PASSED: Complete Add to Cart flow works",
                true
        );

        System.out.println(
                "System test passed: Product added and verified in cart"
        );
    }
    
 // AC-SM-001: Verify basic Add to Cart functionality
    @Test(groups = {"smoke"})
    public void verifyAddToCartSmokeTest() throws InterruptedException {

        loginToApplication();

        cart.addProductToCart("Sauce Labs Backpack");

        Assert.assertEquals(
                cart.getCartCount(),
                "1",
                "Cart badge should display 1"
        );

        Reporter.log(
                "AC-SM-001 PASSED: Basic Add to Cart functionality works",
                true
        );

        System.out.println("Smoke test passed: Product added successfully");
    }

   
    }

