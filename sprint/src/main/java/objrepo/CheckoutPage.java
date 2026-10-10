package objrepo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPage {

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    @FindBy(id = "first-name")
    private WebElement firstName;

    @FindBy(id = "last-name")
    private WebElement lastName;

    @FindBy(id = "postal-code")
    private WebElement postalCode;

    @FindBy(id = "continue")
    private WebElement continueButton;

    @FindBy(id = "finish")
    private WebElement finishButton;

    @FindBy(css = "[data-test='complete-header']")
    private WebElement confirmationMessage;

    @FindBy(className = "title")
    private WebElement pageTitle;
    
    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;


    public CheckoutPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public WebElement getCheckoutButton() {
        return checkoutButton;
    }

    public WebElement getFirstName() {
        return firstName;
    }

    public WebElement getLastName() {
        return lastName;
    }

    public WebElement getPostalCode() {
        return postalCode;
    }

    public WebElement getContinueButton() {
        return continueButton;
    }

    public WebElement getFinishButton() {
        return finishButton;
    }

    public WebElement getConfirmationMessage() {
        return confirmationMessage;
    }

    public WebElement getPageTitle() {
        return pageTitle;
    }
    public WebElement getErrorMessage() {
        return errorMessage;
    }
}