package Login;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import Base.BaseClass;
import objrepo.LoginPage;
import utilities.ExcelUtils;
import utilities.Screenshot;

public class FunctionalTest extends BaseClass {

    ExcelUtils eu = new ExcelUtils();

    @Test(groups = {"functional"})
    public void validLogin() throws Exception {

        String username = eu.getData("Sheet1", 1, 0);
        String password = eu.getData("Sheet1", 1, 1);

        LoginPage login = new LoginPage(driver);

        login.getUsername().sendKeys(username);
        login.getPassword().sendKeys(password);
        login.getLoginButton().click();

        boolean result = driver.getCurrentUrl().contains("inventory.html");

        Assert.assertTrue(result, "Valid login failed");

        Reporter.log(username + " --> VALID LOGIN PASSED", true);
    }


    @Test(groups = {"functional"})
    public void lockedOutUserLogin() throws Exception {

        String username = eu.getData("Sheet1", 6, 0);
        String password = eu.getData("Sheet1", 6, 1);

        LoginPage login = new LoginPage(driver);

        login.getUsername().sendKeys(username);
        login.getPassword().sendKeys(password);
        login.getLoginButton().click();

        boolean result = login.getError().isDisplayed();

        Assert.assertTrue(result, "Locked out user error not displayed");

        Assert.assertTrue(
                login.getError().getText().contains(
                        "Sorry, this user has been locked out"
                ),
                "Incorrect error message"
        );

        Screenshot.takeScreenshot(driver, "locked_out_user");

        Reporter.log(username + " --> LOCKED USER TEST PASSED", true);
    }


    @Test(groups = {"functional"})
    public void blankPasswordLogin() throws Exception {

        String username = eu.getData("Sheet1", 7, 0);
        String password = eu.getData("Sheet1", 7, 1);

        LoginPage login = new LoginPage(driver);

        login.getUsername().sendKeys(username);
        login.getPassword().sendKeys(password);
        login.getLoginButton().click();

        boolean result = login.getError().isDisplayed();

        Assert.assertTrue(result, "Error message not displayed");

        Assert.assertTrue(
                login.getError().getText().contains(
                        "Password is required"
                ),
                "Incorrect error message"
        );

        Reporter.log(username + " --> BLANK PASSWORD TEST PASSED", true);
        Screenshot.takeScreenshot(driver, "blank_pasword");
    }
}