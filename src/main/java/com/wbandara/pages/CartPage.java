package com.wbandara.pages;

import com.wbandara.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

/**
 * CartPage - Page Object for SauceDemo Cart Page.
 */
public class CartPage extends BasePage {

    // ==================== Web Elements ====================

    @FindBy(css = ".title")
    private WebElement pageTitle;

    @FindBy(css = ".cart_item")
    private List<WebElement> cartItems;

    @FindBy(css = ".cart_item .inventory_item_name")
    private List<WebElement> cartItemNames;

    @FindBy(css = ".cart_item .inventory_item_price")
    private List<WebElement> cartItemPrices;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingButton;

    @FindBy(css = ".shopping_cart_badge")
    private WebElement cartBadge;

    // ==================== Constructor ====================

    public CartPage(WebDriver driver) {
        super(driver);
        logger.info("CartPage initialized");
    }

    // ==================== Page Actions ====================

    @Step("Verify cart page is displayed")
    public boolean isPageDisplayed() {
        try {
            waitForVisibility(pageTitle);
            return pageTitle.getText().equals("Your Cart");
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Get page title")
    public String getPageTitleText() {
        return getText(pageTitle);
    }

    @Step("Get cart items count")
    public int getCartItemsCount() {
        return cartItems.size();
    }

    @Step("Verify cart is empty")
    public boolean isCartEmpty() {
        return cartItems.isEmpty();
    }

    @Step("Get all cart item names")
    public List<String> getCartItemNames() {
        List<String> names = new ArrayList<>();
        for (WebElement element : cartItemNames) {
            names.add(element.getText());
        }
        return names;
    }

    @Step("Verify item is in cart: {itemName}")
    public boolean isItemInCart(String itemName) {
        for (WebElement element : cartItemNames) {
            if (element.getText().equals(itemName)) {
                return true;
            }
        }
        return false;
    }

    @Step("Remove item from cart: {itemName}")
    public CartPage removeItem(String itemName) {
        String buttonId = "remove-" + itemName.toLowerCase().replace(" ", "-");
        WebElement removeButton = driver.findElement(By.id(buttonId));
        click(removeButton);
        logger.info("Removed item '{}' from cart", itemName);
        return this;
    }

    @Step("Remove all items from cart")
    public CartPage removeAllItems() {
        List<WebElement> removeButtons = driver.findElements(By.cssSelector("button[id^='remove-']"));
        for (WebElement button : removeButtons) {
            click(button);
        }
        logger.info("Removed all items from cart");
        return this;
    }

    @Step("Click Checkout button")
    public CheckoutInfoPage clickCheckout() {
        logger.info("Clicking Checkout button");
        click(checkoutButton);
        return new CheckoutInfoPage(driver);
    }

    @Step("Click Continue Shopping button")
    public InventoryPage clickContinueShopping() {
        logger.info("Clicking Continue Shopping button");
        click(continueShoppingButton);
        return new InventoryPage(driver);
    }

    @Step("Get cart badge count")
    public int getCartBadgeCount() {
        try {
            return Integer.parseInt(getText(cartBadge));
        } catch (Exception e) {
            return 0;
        }
    }

    @Step("Verify cart badge is not displayed")
    public boolean isCartBadgeNotDisplayed() {
        try {
            return !cartBadge.isDisplayed();
        } catch (Exception e) {
            return true;
        }
    }

    @Step("Get total items price")
    public double getTotalPrice() {
        double total = 0;
        for (WebElement element : cartItemPrices) {
            String priceText = element.getText().replace("$", "");
            total += Double.parseDouble(priceText);
        }
        return total;
    }

    @Step("Verify remove button exists for item: {itemName}")
    public boolean doesRemoveButtonExist(String itemName) {
        String buttonId = "remove-" + itemName.toLowerCase().replace(" ", "-");
        try {
            return driver.findElement(By.id(buttonId)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
