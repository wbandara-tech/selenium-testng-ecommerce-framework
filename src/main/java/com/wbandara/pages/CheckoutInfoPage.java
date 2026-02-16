package com.wbandara.pages;

import com.wbandara.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * CheckoutInfoPage - Page Object for SauceDemo Checkout Info Page.
 */
public class CheckoutInfoPage extends BasePage {

    // ==================== Web Elements ====================

    @FindBy(id = "first-name")
    private WebElement firstNameInput;

    @FindBy(id = "last-name")
    private WebElement lastNameInput;

    @FindBy(id = "postal-code")
    private WebElement postalCodeInput;

    @FindBy(id = "continue")
    private WebElement continueButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    @FindBy(css = ".checkout_info_container > .title")
    private WebElement pageTitle;

    // ==================== Constructor ====================

    public CheckoutInfoPage(WebDriver driver) {
        super(driver);
        logger.info("CheckoutInfoPage initialized");
    }

    // ==================== Page Actions ====================

    @Step("Verify checkout info page is displayed")
    public boolean isPageDisplayed() {
        try {
            return isDisplayed(pageTitle);
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Enter first name: {firstName}")
    public CheckoutInfoPage enterFirstName(String firstName) {
        sendKeys(firstNameInput, firstName);
        return this;
    }

    @Step("Enter last name: {lastName}")
    public CheckoutInfoPage enterLastName(String lastName) {
        sendKeys(lastNameInput, lastName);
        return this;
    }

    @Step("Enter postal code: {postalCode}")
    public CheckoutInfoPage enterPostalCode(String postalCode) {
        sendKeys(postalCodeInput, postalCode);
        return this;
    }

    @Step("Fill checkout form with: {firstName}, {lastName}, {postalCode}")
    public CheckoutInfoPage fillCheckoutForm(String firstName, String lastName, String postalCode) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterPostalCode(postalCode);
        return this;
    }

    @Step("Click continue button")
    public CheckoutOverviewPage clickContinue() {
        click(continueButton);
        return new CheckoutOverviewPage(driver);
    }

    @Step("Complete checkout info")
    public CheckoutOverviewPage completeCheckoutInfo(String firstName, String lastName, String postalCode) {
        fillCheckoutForm(firstName, lastName, postalCode);
        return clickContinue();
    }

    @Step("Get entered first name")
    public String getEnteredFirstName() {
        return firstNameInput.getAttribute("value");
    }

    @Step("Get entered last name")
    public String getEnteredLastName() {
        return lastNameInput.getAttribute("value");
    }

    @Step("Get entered postal code")
    public String getEnteredPostalCode() {
        return postalCodeInput.getAttribute("value");
    }

    @Step("Check if error is displayed")
    public boolean isErrorDisplayed() {
        try {
            return isDisplayed(errorMessage);
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Get error message")
    public String getErrorMessage() {
        try {
            return getText(errorMessage);
        } catch (Exception e) {
            return "";
        }
    }
}
