package com.wbandara.pages;

import com.wbandara.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * ProductDetailsPage - Page Object for SauceDemo Product Details Page.
 */
public class ProductDetailsPage extends BasePage {

    // ==================== Web Elements ====================

    @FindBy(css = ".inventory_details_name")
    private WebElement productName;

    @FindBy(css = ".inventory_details_desc")
    private WebElement productDescription;

    @FindBy(css = ".inventory_details_price")
    private WebElement productPrice;

    @FindBy(css = "button[id^='add-to-cart']")
    private WebElement addToCartButton;

    @FindBy(css = "button[id^='remove']")
    private WebElement removeButton;

    @FindBy(id = "back-to-products")
    private WebElement backButton;

    @FindBy(css = ".inventory_details_img")
    private WebElement productImage;

    // ==================== Constructor ====================

    public ProductDetailsPage(WebDriver driver) {
        super(driver);
        logger.info("ProductDetailsPage initialized");
    }

    // ==================== Page Actions ====================

    @Step("Get product name")
    public String getProductName() {
        return getText(productName);
    }

    @Step("Get product description")
    public String getProductDescription() {
        return getText(productDescription);
    }

    @Step("Get product price")
    public String getProductPrice() {
        return getText(productPrice);
    }

    @Step("Get product price as double")
    public double getProductPriceAsDouble() {
        String priceText = getProductPrice().replace("$", "");
        return Double.parseDouble(priceText);
    }

    @Step("Add product to cart")
    public ProductDetailsPage addToCart() {
        logger.info("Adding product to cart");
        click(addToCartButton);
        return this;
    }

    @Step("Remove product from cart")
    public ProductDetailsPage removeFromCart() {
        logger.info("Removing product from cart");
        click(removeButton);
        return this;
    }

    @Step("Go back to products")
    public InventoryPage goBackToProducts() {
        logger.info("Going back to products");
        click(backButton);
        return new InventoryPage(driver);
    }

    @Step("Verify add to cart button is displayed")
    public boolean isAddToCartButtonDisplayed() {
        return isDisplayed(addToCartButton);
    }

    @Step("Verify remove button is displayed")
    public boolean isRemoveButtonDisplayed() {
        return isDisplayed(removeButton);
    }

    @Step("Verify product image is displayed")
    public boolean isProductImageDisplayed() {
        return isDisplayed(productImage);
    }
}
