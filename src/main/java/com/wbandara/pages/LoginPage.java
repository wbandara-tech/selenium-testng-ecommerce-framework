package com.wbandara.pages;

import com.wbandara.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * LoginPage - Page Object for SauceDemo Login Page.
 */
public class LoginPage extends BasePage {

    // ==================== Web Elements ====================

    @FindBy(id = "user-name")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "h1.login_logo")
    private WebElement logo;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    @FindBy(css = ".error-button")
    private WebElement errorCloseButton;

    // ==================== Constructor ====================

    public LoginPage(WebDriver driver) {
        super(driver);
        logger.info("LoginPage initialized");
    }

    // ==================== Page Actions ====================

    @Step("Verify login page is displayed")
    public boolean isLoginPageDisplayed() {
        try {
            waitForVisibility(loginButton);
            return loginButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Enter username: {username}")
    public LoginPage enterUsername(String username) {
        sendKeys(usernameInput, username);
        return this;
    }

    @Step("Enter password: {password}")
    public LoginPage enterPassword(String password) {
        sendKeys(passwordInput, password);
        return this;
    }

    @Step("Click login button")
    public InventoryPage clickLogin() {
        click(loginButton);
        return new InventoryPage(driver);
    }

    @Step("Login with username: {username} and password: {password}")
    public InventoryPage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        return clickLogin();
    }

    @Step("Login expecting error")
    public LoginPage loginExpectingError(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        click(loginButton);
        return this;
    }

    @Step("Check if error message is displayed")
    public boolean isErrorMessageDisplayed() {
        try {
            return waitForVisibility(errorMessage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Get error message text")
    public String getErrorMessage() {
        try {
            return getText(errorMessage);
        } catch (Exception e) {
            return "";
        }
    }

    @Step("Check if locked out error is displayed")
    public boolean isLockedOutError() {
        String message = getErrorMessage();
        return message.contains("locked out");
    }

    @Step("Close error message")
    public LoginPage closeErrorMessage() {
        try {
            click(errorCloseButton);
        } catch (Exception e) {
            logger.warn("Error close button not found");
        }
        return this;
    }

    @Step("Clear form")
    public LoginPage clearForm() {
        usernameInput.clear();
        passwordInput.clear();
        return this;
    }
}
