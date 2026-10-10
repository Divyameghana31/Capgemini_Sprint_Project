package Login;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import Base.BaseClass;
import objrepo.LoginPage;
import utilities.ExcelUtils;

public class SmokeTest extends BaseClass {

    ExcelUtils eu = new ExcelUtils();

    // SMK_01: Verify login page availability
    @Test(groups = {"smoke"}, priority = 1)
    public void verifyLoginPageLoads() {

        LoginPage login = new LoginPage(driver);

        Assert.assertTrue(
                login.getUsername().isDisplayed()
                        && login.getUsername().isEnabled(),
                "Username field is not visible or enabled"
        );

        Assert.assertTrue(
                login.getPassword().isDisplayed()
                        && login.getPassword().isEnabled(),
                "Password field is not visible or enabled"
        );

        Assert.assertTrue(
                login.getLoginButton().isDisplayed()
                        && login.getLoginButton().isEnabled(),
                "Login button is not visible or enabled"
        );

        Reporter.log(
                "SMK_01 PASSED: Login page and essential elements verified",
                true
        );
    }

    // SMK_02: Verify successful login
    @Test(groups = {"smoke"}, priority = 2)
    public void verifySuccessfulLogin() throws Exception {

        String username = eu.getData("Sheet1", 1, 0);
        String password = eu.getData("Sheet1", 1, 1);

        LoginPage login = new LoginPage(driver);

        login.getUsername().sendKeys(username);
        login.getPassword().sendKeys(password);
        login.getLoginButton().click();

        Assert.assertEquals(
                driver.getCurrentUrl(),
                "https://www.saucedemo.com/inventory.html",
                "Login failed or Products page was not displayed"
        );

        Reporter.log(
                "SMK_02 PASSED: Login successful and Products page opened",
                true
        );
    }
}
