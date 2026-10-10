package ProductCatalogPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import Base.BaseClass;
import objrepo.AddToCartPage;
import objrepo.LoginPage;
import objrepo.ProductCatalogPage;
import utilities.ExcelUtils;
import utilities.Screenshot;

public class ProductCatalogTest extends BaseClass {

    LoginPage login;
    ProductCatalogPage catalog;
    ExcelUtils excel = new ExcelUtils();

    // Common method to login and open the inventory page
    public void loginToInventory() throws Exception {

        String username = excel.getData("Sheet1", 1, 0);
        String password = excel.getData("Sheet1", 1, 1);

        login = new LoginPage(driver);

        login.getUsername().sendKeys(username);
        login.getPassword().sendKeys(password);
        login.getLoginButton().click();

        catalog = new ProductCatalogPage(driver);

        System.out.println("Login successful");
        System.out.println("Inventory page opened");
    }

    // CAT-FUN-002: Verify sorting by Name A-Z
    @Test(groups = {"functional"})
    public void verifySortingByNameAZ() throws Exception {

        loginToInventory();

        Select sort = new Select(catalog.getSortDropdown());
        sort.selectByValue("az");

        List<String> actualNames = new ArrayList<>();

        for (WebElement product : catalog.getProductNames()) {
            actualNames.add(product.getText());
        }

        List<String> expectedNames = new ArrayList<>(actualNames);
        Collections.sort(expectedNames);

        Assert.assertEquals(
            actualNames,
            expectedNames,
            "Products are not sorted alphabetically A-Z"
        );

        Reporter.log(
            "CAT-FUN-002 PASSED: Products sorted by Name A-Z",
            true
        );

        System.out.println("CAT-FUN-002 passed");
    }

    // CAT-FUN-003: Verify sorting by Price low to high
    @Test(groups = {"functional"})
    public void verifySortingByPriceLowToHigh() throws Exception {

        loginToInventory();

        Select sort = new Select(catalog.getSortDropdown());
        sort.selectByValue("lohi");

        List<Double> actualPrices = new ArrayList<>();

        for (WebElement price : catalog.getProductPrices()) {
            String value = price.getText().replace("$", "").trim();
            actualPrices.add(Double.parseDouble(value));
        }

        List<Double> expectedPrices = new ArrayList<>(actualPrices);
        Collections.sort(expectedPrices);

        Assert.assertEquals(
            actualPrices,
            expectedPrices,
            "Products are not sorted from lowest to highest price"
        );

        // Take screenshot after successful price sorting
        Screenshot.takeScreenshot(
            driver,
            "PriceLowToHigh"
        );

        Reporter.log(
            "CAT-FUN-003 PASSED: Products sorted by price low to high",
            true
        );

        
    }
    

@Test(groups = {"integration"})
public void verifyCatalogProductCanBeAddedToCart() throws Exception {

    // Login and open the product catalog
    loginToInventory();

    // Add a product from the catalog
    AddToCartPage cart = new AddToCartPage(driver);
    cart.addProductToCart("Sauce Labs Backpack");

    // Open the cart
    cart.openCart();

    // Verify the selected product is present
    boolean productFound = cart.getCartProducts()
            .stream()
            .anyMatch(product ->
                    product.getText().equals("Sauce Labs Backpack"));

    Assert.assertTrue(productFound,
            "Sauce Labs Backpack was not found in the cart");

    Reporter.log("CAT-INT-001 PASSED: Catalog product was added to the cart", true);
   
}


@Test(groups = {"system"})
public void verifySortingAndProductSelectionFlow() throws Exception {

    // Login and open the product catalog
    loginToInventory();

    // Sort products by price: Low to High
    Select sort = new Select(catalog.getSortDropdown());
    sort.selectByValue("lohi");

    System.out.println("Products sorted by price: Low to High");

    // Add a product to the cart
    AddToCartPage cart = new AddToCartPage(driver);
    cart.addProductToCart("Sauce Labs Backpack");

    // Open the cart
    cart.openCart();

    // Verify the product is displayed in the cart
    boolean productFound = cart.getCartProducts()
            .stream()
            .anyMatch(product ->
                    product.getText().equals("Sauce Labs Backpack"));

    Assert.assertTrue(productFound,
            "Complete product selection flow failed");

    Reporter.log("CAT-SYS-001 PASSED: Sorting and product selection flow completed", true);
    System.out.println("System test passed: Catalog sorting and cart flow completed successfully");
}

    // CAT-SM-001: Verify Inventory catalog loads after login
    @Test(groups = {"smoke"})
    public void verifyInventoryCatalogLoads() throws Exception {

        loginToInventory();

        Assert.assertEquals(
            catalog.getPageTitle().getText(),
            "Products",
            "Inventory page title is incorrect"
        );

        Assert.assertTrue(
            catalog.getProductCards().size() > 0,
            "No product cards are displayed"
        );

        Reporter.log(
            "CAT-SM-001 PASSED: Inventory and product catalog loaded",
            true
        );

        System.out.println("CAT-SM-001 passed");
    }
}