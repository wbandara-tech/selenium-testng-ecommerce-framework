package com.wbandara.tests;

import com.wbandara.base.BaseTest;
import com.wbandara.pages.InventoryPage;
import com.wbandara.pages.LoginPage;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * SessionTest - Test class for Session validation functionality.
 * Contains tests for session management and access control.
 */
@Epic("Security")
@Feature("Session Management")
public class SessionTest extends BaseTest {

    private static final String VALID_USERNAME = "standard_user";
    private static final String VALID_PASSWORD = "secret_sauce";

    @Test(groups = {"smoke", "regression"}, priority = 1)
    @Story("Session Validation")
    @Description("Verify accessing inventory page without login redirects to login page with error")
    @Severity(SeverityLevel.CRITICAL)
    public void testAccessInventoryWithoutLogin() {
        logger.info("Testing access inventory without login");

        // Navigate directly to inventory page without login
        getDriver().get(config.getBaseUrl() + "inventory.html");

        LoginPage loginPage = new LoginPage(getDriver());

        // Verify redirected to login page with error
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Error message should be displayed");
        Assert.assertTrue(loginPage.getErrorMessage().contains("You can only access"),
                "Error should indicate access restriction");

        logger.info("Access inventory without login test passed");
    }

    @Test(groups = {"smoke", "regression"}, priority = 2)
    @Story("Session Validation")
    @Description("Verify accessing cart page without login shows error")
    @Severity(SeverityLevel.CRITICAL)
    public void testAccessCartWithoutLogin() {
        logger.info("Testing access cart without login");

        // Navigate directly to cart page without login
        getDriver().get(config.getBaseUrl() + "cart.html");

        LoginPage loginPage = new LoginPage(getDriver());

        // Verify error is displayed
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Error message should be displayed");
        Assert.assertTrue(loginPage.getErrorMessage().contains("You can only access"),
                "Error should indicate access restriction");

        logger.info("Access cart without login test passed");
    }

    @Test(groups = {"regression"}, priority = 3)
    @Story("Login Persistence")
    @Description("Verify valid session persists across page refreshes")
    @Severity(SeverityLevel.NORMAL)
    public void testSessionPersistsOnRefresh() {
        logger.info("Testing session persistence on page refresh");

        // Login
        LoginPage loginPage = new LoginPage(getDriver());
        InventoryPage inventoryPage = loginPage.login(VALID_USERNAME, VALID_PASSWORD);
        Assert.assertTrue(inventoryPage.isPageDisplayed(),
                "Should be on inventory page after login");

        // Refresh the page
        getDriver().navigate().refresh();

        // Verify still on inventory page
        inventoryPage = new InventoryPage(getDriver());
        Assert.assertTrue(inventoryPage.isPageDisplayed(),
                "Should still be on inventory page after refresh");

        logger.info("Session persistence on refresh test passed");
    }
}
