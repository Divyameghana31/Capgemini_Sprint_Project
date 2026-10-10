package Login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import Base.BaseClass;
import objrepo.LoginPage;
import utilities.ExcelUtils;

public class SystemTest extends BaseClass {

    ExcelUtils eu = new ExcelUtils();

    // SYS_01: Verify the complete login journey
    @Test(groups = {"system"}, priority = 1)
    public void verifyCompleteLoginJourney() throws Exception {

        String username = eu.getData("Sheet1", 1, 0);
        String password = eu.getData("Sheet1", 1, 1);

        LoginPage login = new LoginPage(driver);

        Assert.assertTrue(
                driver.getTitle().contains("Swag Labs"),
                "Login page did not load correctly"
        );

        login.getUsername().sendKeys(username);
        login.getPassword().sendKeys(password);
        login.getLoginButton().click();

        Assert.assertEquals(
                driver.getCurrentUrl(),
                "https://www.saucedemo.com/inventory.html",
                "Login was not successful"
        );

        Assert.assertTrue(
                driver.findElement(By.className("title")).isDisplayed(),
                "Products page heading is not displayed"
        );

        Reporter.log(
                "SYS_01 PASSED: Complete login journey verified",
                true
        );
    }

    // SYS_02: Verify Products heading and product list
    @Test(groups = {"system"}, priority = 2)
    public void verifyProductsPageContent() throws Exception {

        String username = eu.getData("Sheet1", 1, 0);
        String password = eu.getData("Sheet1", 1, 1);

        LoginPage login = new LoginPage(driver);

        login.getUsername().sendKeys(username);
        login.getPassword().sendKeys(password);
        login.getLoginButton().click();

        Assert.assertEquals(
                driver.getCurrentUrl(),
                "https://www.saucedemo.com/inventory.html",
                "Products page did not load"
        );

        WebElement heading =
                driver.findElement(By.className("title"));

        Assert.assertEquals(
                heading.getText(),
                "Products",
                "Incorrect Products page heading"
        );

        int productCount = driver.findElements(
                By.className("inventory_item")
        ).size();

        Assert.assertTrue(
                productCount > 0,
                "No products are displayed on the Products page"
        );

        Reporter.log(
                "SYS_02 PASSED: Products heading displayed; product count = "
                        + productCount,
                true
        );
    }
}

