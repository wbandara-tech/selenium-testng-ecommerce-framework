package com.wbandara.pages;

import com.wbandara.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

/**
 * CheckoutOverviewPage - Page Object for SauceDemo Checkout Overview Page.
 */
public class CheckoutOverviewPage extends BasePage {

    // ==================== Web Elements ====================

    @FindBy(css = ".title")
    private WebElement pageTitle;

    @FindBy(css = ".cart_item")
    private List<WebElement> cartItems;

    @FindBy(css = ".cart_item .inventory_item_name")
    private List<WebElement> itemNames;

    @FindBy(css = ".cart_item .inventory_item_price")
    private List<WebElement> itemPrices;

    @FindBy(css = ".summary_subtotal_label")
    private WebElement subtotalLabel;

    @FindBy(css = ".summary_tax_label")
    private WebElement taxLabel;

    @FindBy(css = ".summary_total_label")
    private WebElement totalLabel;

    @FindBy(id = "finish")
    private WebElement finishButton;

    @FindBy(id = "cancel")
    private WebElement cancelButton;

    @FindBy(css = ".summary_value_label")
    private List<WebElement> summaryValues;

    // ==================== Constructor ====================

    public CheckoutOverviewPage(WebDriver driver) {
        super(driver);
        logger.info("CheckoutOverviewPage initialized");
    }

    // ==================== Page Actions ====================

    @Step("Verify checkout overview page is displayed")
    public boolean isPageDisplayed() {
        try {
            waitForVisibility(pageTitle);
            return pageTitle.getText().equals("Checkout: Overview");
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Get page title")
    public String getPageTitleText() {
        return getText(pageTitle);
    }

    @Step("Get number of items in order")
    public int getItemsCount() {
        return cartItems.size();
    }

    @Step("Get all item names in order")
    public List<String> getItemNames() {
        List<String> names = new ArrayList<>();
        for (WebElement element : itemNames) {
            names.add(element.getText());
        }
        return names;
    }

    @Step("Get subtotal amount")
    public double getSubtotal() {
        String subtotalText = getText(subtotalLabel);
        // Extract number from "Item total: $X.XX"
        return extractPrice(subtotalText);
    }

    @Step("Get tax amount")
    public double getTax() {
        String taxText = getText(taxLabel);
        return extractPrice(taxText);
    }

    @Step("Get total amount")
    public double getTotal() {
        String totalText = getText(totalLabel);
        return extractPrice(totalText);
    }

    private double extractPrice(String text) {
        String priceStr = text.replaceAll("[^0-9.]", "");
        return Double.parseDouble(priceStr);
    }

    @Step("Click Finish button")
    public CheckoutCompletePage clickFinish() {
        logger.info("Clicking Finish button");
        click(finishButton);
        return new CheckoutCompletePage(driver);
    }

    @Step("Click Cancel button")
    public InventoryPage clickCancel() {
        logger.info("Clicking Cancel button");
        click(cancelButton);
        return new InventoryPage(driver);
    }

    @Step("Complete order")
    public CheckoutCompletePage completeOrder() {
        return clickFinish();
    }

    @Step("Verify item is in order: {itemName}")
    public boolean isItemInOrder(String itemName) {
        List<String> names = getItemNames();
        return names.contains(itemName);
    }

    @Step("Get payment information")
    public String getPaymentInfo() {
        if (!summaryValues.isEmpty()) {
            return getText(summaryValues.get(0));
        }
        return "";
    }

    @Step("Get shipping information")
    public String getShippingInfo() {
        if (summaryValues.size() > 1) {
            return getText(summaryValues.get(1));
        }
        return "";
    }
}

