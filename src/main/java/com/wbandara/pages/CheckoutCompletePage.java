package com.wbandara.pages;

import com.wbandara.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * CheckoutCompletePage - Page Object for SauceDemo Checkout Complete Page.
 */
public class CheckoutCompletePage extends BasePage {

    // ==================== Web Elements ====================

    @FindBy(css = ".title")
    private WebElement pageTitle;

    @FindBy(css = ".complete-header")
    private WebElement completeHeader;

    @FindBy(css = ".complete-text")
    private WebElement completeText;

    @FindBy(id = "back-to-products")
    private WebElement backToProductsButton;

    @FindBy(css = ".pony_express")
    private WebElement successImage;

    // ==================== Constructor ====================

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
        logger.info("CheckoutCompletePage initialized");
    }

    // ==================== Page Actions ====================

    @Step("Verify checkout complete page is displayed")
    public boolean isPageDisplayed() {
        try {
            waitForVisibility(pageTitle);
            return pageTitle.getText().equals("Checkout: Complete!");
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Get page title")
    public String getPageTitleText() {
        return getText(pageTitle);
    }

    @Step("Get success header text")
    public String getSuccessHeader() {
        return getText(completeHeader);
    }

    @Step("Get success message text")
    public String getSuccessMessage() {
        return getText(completeText);
    }

    @Step("Verify order is successful")
    public boolean isOrderSuccessful() {
        String header = getSuccessHeader();
        return header.contains("Thank you for your order");
    }

    @Step("Verify success image is displayed")
    public boolean isSuccessImageDisplayed() {
        return isDisplayed(successImage);
    }

    @Step("Click Back Home button")
    public InventoryPage clickBackHome() {
        logger.info("Clicking Back Home button");
        click(backToProductsButton);
        return new InventoryPage(driver);
    }

    @Step("Get confirmation message")
    public String getConfirmationMessage() {
        return getSuccessHeader() + " " + getSuccessMessage();
    }
}

