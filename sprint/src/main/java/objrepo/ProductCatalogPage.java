package objrepo;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductCatalogPage {

    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(className = "product_sort_container")
    private WebElement sortDropdown;

    @FindBy(className = "inventory_item")
    private List<WebElement> productCards;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> productNames;

    @FindBy(className = "inventory_item_price")
    private List<WebElement> productPrices;

    public ProductCatalogPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public WebElement getPageTitle() {
        return pageTitle;
    }

    public WebElement getSortDropdown() {
        return sortDropdown;
    }

    public List<WebElement> getProductCards() {
        return productCards;
    }

    public List<WebElement> getProductNames() {
        return productNames;
    }

    public List<WebElement> getProductPrices() {
        return productPrices;
    }
}
