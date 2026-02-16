package com.wbandara.pages;

import com.wbandara.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * InventoryPage - Page Object for SauceDemo Products/Inventory Page.
 */
public class InventoryPage extends BasePage {

    // ==================== Web Elements ====================

    @FindBy(css = ".title")
    private WebElement pageTitle;

    @FindBy(css = ".product_sort_container")
    private WebElement sortDropdown;

    @FindBy(css = ".inventory_item")
    private List<WebElement> inventoryItems;

    @FindBy(css = ".inventory_item_name")
    private List<WebElement> productNames;

    @FindBy(css = ".inventory_item_price")
    private List<WebElement> productPrices;

    @FindBy(css = ".shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(css = ".shopping_cart_link")
    private WebElement cartLink;

    @FindBy(id = "react-burger-menu-btn")
    private WebElement menuButton;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;

    @FindBy(id = "inventory_sidebar_link")
    private WebElement inventoryLink;

    @FindBy(id = "reset_sidebar_link")
    private WebElement resetAppStateLink;

    @FindBy(css = ".bm-cross-button")
    private WebElement closeMenuButton;

    // ==================== Constructor ====================

    public InventoryPage(WebDriver driver) {
        super(driver);
        logger.info("InventoryPage initialized");
    }

    // ==================== Page Actions ====================

    @Step("Verify inventory page is displayed")
    public boolean isPageDisplayed() {
        try {
            waitForVisibility(pageTitle);
            return pageTitle.getText().equals("Products");
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Get page title")
    public String getPageTitleText() {
        return getText(pageTitle);
    }

    @Step("Get number of products")
    public int getProductCount() {
        return inventoryItems.size();
    }

    @Step("Get all product names")
    public List<String> getAllProductNames() {
        List<String> names = new ArrayList<>();
        for (WebElement element : productNames) {
            names.add(element.getText());
        }
        return names;
    }

    @Step("Get all product prices")
    public List<Double> getAllProductPrices() {
        List<Double> prices = new ArrayList<>();
        for (WebElement element : productPrices) {
            String priceText = element.getText().replace("$", "");
            prices.add(Double.parseDouble(priceText));
        }
        return prices;
    }

    @Step("Sort products by: {sortOption}")
    public InventoryPage sortProducts(String sortOption) {
        logger.info("Sorting products by: {}", sortOption);
        Select select = new Select(sortDropdown);
        select.selectByVisibleText(sortOption);
        return this;
    }

    @Step("Sort products by value: {value}")
    public InventoryPage sortProductsByValue(String value) {
        logger.info("Sorting products by value: {}", value);
        Select select = new Select(sortDropdown);
        select.selectByValue(value);
        return this;
    }

    @Step("Add product to cart by index: {index}")
    public InventoryPage addProductToCartByIndex(int index) {
        WebElement product = inventoryItems.get(index);
        WebElement addButton = product.findElement(By.cssSelector("button[id^='add-to-cart']"));
        click(addButton);
        logger.info("Added product at index {} to cart", index);
        return this;
    }

    @Step("Add product to cart by name: {productName}")
    public InventoryPage addProductToCartByName(String productName) {
        String buttonId = "add-to-cart-" + productName.toLowerCase().replace(" ", "-");
        WebElement addButton = driver.findElement(By.id(buttonId));
        click(addButton);
        logger.info("Added product '{}' to cart", productName);
        return this;
    }

    @Step("Remove product from cart by name: {productName}")
    public InventoryPage removeProductFromCartByName(String productName) {
        String buttonId = "remove-" + productName.toLowerCase().replace(" ", "-");
        WebElement removeButton = driver.findElement(By.id(buttonId));
        click(removeButton);
        logger.info("Removed product '{}' from cart", productName);
        return this;
    }

    @Step("Get cart badge count")
    public int getCartBadgeCount() {
        try {
            return Integer.parseInt(getText(cartBadge));
        } catch (Exception e) {
            return 0;
        }
    }

    @Step("Verify cart badge is displayed")
    public boolean isCartBadgeDisplayed() {
        try {
            return cartBadge.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Click on cart")
    public CartPage goToCart() {
        logger.info("Navigating to cart");
        click(cartLink);
        return new CartPage(driver);
    }

    @Step("Open menu")
    public InventoryPage openMenu() {
        click(menuButton);
        waitForVisibility(logoutLink);
        return this;
    }

    @Step("Logout from application")
    public LoginPage logout() {
        logger.info("Logging out");
        openMenu();
        click(logoutLink);
        return new LoginPage(driver);
    }

    @Step("Reset app state")
    public InventoryPage resetAppState() {
        openMenu();
        click(resetAppStateLink);
        click(closeMenuButton);
        return this;
    }

    @Step("Click on product by name: {productName}")
    public ProductDetailsPage clickOnProduct(String productName) {
        for (WebElement element : productNames) {
            if (element.getText().equals(productName)) {
                click(element);
                return new ProductDetailsPage(driver);
            }
        }
        throw new RuntimeException("Product not found: " + productName);
    }

    @Step("Get current URL")
    public String getInventoryUrl() {
        return getCurrentUrl();
    }

    @Step("Verify products are sorted by price low to high")
    public boolean isProductsSortedByPriceLowToHigh() {
        List<Double> prices = getAllProductPrices();
        for (int i = 0; i < prices.size() - 1; i++) {
            if (prices.get(i) > prices.get(i + 1)) {
                return false;
            }
        }
        return true;
    }

    @Step("Verify products are sorted by price high to low")
    public boolean isProductsSortedByPriceHighToLow() {
        List<Double> prices = getAllProductPrices();
        for (int i = 0; i < prices.size() - 1; i++) {
            if (prices.get(i) < prices.get(i + 1)) {
                return false;
            }
        }
        return true;
    }
}

