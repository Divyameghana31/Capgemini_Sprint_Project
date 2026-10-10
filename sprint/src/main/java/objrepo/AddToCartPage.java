
package objrepo;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddToCartPage {

    WebDriver driver;
    WebDriverWait wait;

    @FindBy(className = "shopping_cart_link")
    WebElement cartIcon;

    @FindBy(className = "shopping_cart_badge")
    WebElement cartBadge;

    @FindBy(className = "inventory_item")
    List<WebElement> products;

    public AddToCartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // Find a product by its name
    public WebElement getProduct(String productName) {

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.className("inventory_item")));

        for (WebElement product : products) {

            String name = product.findElement(
                    By.className("inventory_item_name")).getText();

            if (name.equals(productName)) {
                return product;
            }
        }

        throw new RuntimeException("Product not found: " + productName);
    }

    // Add a selected product to the cart
    public void addProductToCart(String productName) {

        WebElement product = getProduct(productName);

        WebElement addButton = product.findElement(By.tagName("button"));

        wait.until(ExpectedConditions.elementToBeClickable(addButton));

        addButton.click();
    }

    // Open the shopping cart
    public void openCart() {

        wait.until(ExpectedConditions.elementToBeClickable(cartIcon));

        cartIcon.click();

        wait.until(ExpectedConditions.urlContains("/cart.html"));
    }

    // Get the cart item count
    public String getCartCount() {

        WebElement badge = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.className("shopping_cart_badge")));

        return badge.getText();
    }

    // Get all product names displayed in the cart
    public List<WebElement> getCartProducts() {

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.className("cart_item")));

        return driver.findElements(
                By.className("inventory_item_name"));
    }
}

