package Login;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import Base.BaseClass;
import objrepo.LoginPage;
import utilities.ExcelUtils;

public class IntegrationTest extends BaseClass {

    ExcelUtils eu = new ExcelUtils();

    // ITS_01: Verify credentials work with the Login button
    @Test(groups = {"integration"}, priority = 1)
    public void verifyLoginWithButton() throws Exception {

        String username = eu.getData("Sheet1", 1, 0);
        String password = eu.getData("Sheet1", 1, 1);

        LoginPage login = new LoginPage(driver);

        login.getUsername().sendKeys(username);
        login.getPassword().sendKeys(password);
        login.getLoginButton().click();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("inventory.html"),
                "User was not logged in successfully"
        );

        Reporter.log(
                "ITS_01 PASSED: Credentials worked with the Login button",
                true
        );
    }

    // ITS_02: Verify redirect to the Products page
    @Test(groups = {"integration"}, priority = 2)
    public void verifyRedirectToProductsPage() throws Exception {

        String username = eu.getData("Sheet1", 1, 0);
        String password = eu.getData("Sheet1", 1, 1);

        LoginPage login = new LoginPage(driver);

        login.getUsername().sendKeys(username);
        login.getPassword().sendKeys(password);
        login.getLoginButton().click();

        Assert.assertEquals(
                driver.getCurrentUrl(),
                "https://www.saucedemo.com/inventory.html",
                "User was not redirected to the Products page"
        );

        Reporter.log(
                "ITS_02 PASSED: Redirected to the Products page",
                true
        );
    }
}

