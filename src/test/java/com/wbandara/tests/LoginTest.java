package com.wbandara.tests;

import com.wbandara.base.BaseTest;
import com.wbandara.pages.InventoryPage;
import com.wbandara.pages.LoginPage;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * LoginTest - Test class for Login module functionality.
 * Contains tests for valid login scenarios.
 */
@Epic("Authentication")
@Feature("Login Functionality")
public class LoginTest extends BaseTest {

    private static final String VALID_PASSWORD = "secret_sauce";

    // ==================== Data Providers ====================

    @DataProvider(name = "validLoginData")
    public Object[][] getValidLoginData() {
        return new Object[][] {
            {"standard_user", "secret_sauce"},
            {"problem_user", "secret_sauce"}
        };
    }

    // ==================== Valid Login Tests ====================

    @Test(groups = {"smoke", "regression"}, priority = 1)
    @Story("Valid Login")
    @Description("Verify that a valid user can successfully login to the application")
    @Severity(SeverityLevel.BLOCKER)
    public void testValidLogin() {
        logger.info("Starting valid login test");

        LoginPage loginPage = new LoginPage(getDriver());
        InventoryPage inventoryPage = loginPage.login("standard_user", VALID_PASSWORD);

        Assert.assertTrue(inventoryPage.isPageDisplayed(),
                "Inventory page should be displayed after successful login");
        Assert.assertEquals(inventoryPage.getPageTitleText(), "Products",
                "Page title should be 'Products'");

        logger.info("Valid login test completed successfully");
    }

    @Test(groups = {"regression"}, dataProvider = "validLoginData", priority = 2)
    @Story("Valid Login")
    @Description("Verify valid login with multiple user types")
    @Severity(SeverityLevel.CRITICAL)
    public void testValidLoginWithMultipleUsers(String username, String password) {
        logger.info("Testing login with username: {}", username);

        LoginPage loginPage = new LoginPage(getDriver());
        InventoryPage inventoryPage = loginPage.login(username, password);

        Assert.assertTrue(inventoryPage.isPageDisplayed(),
                "Inventory page should be displayed for user: " + username);

        logger.info("Login successful for user: {}", username);
    }

    @Test(groups = {"smoke", "regression"}, priority = 3)
    @Story("Login Page")
    @Description("Verify login page loads correctly")
    @Severity(SeverityLevel.NORMAL)
    public void testLoginPageLoads() {
        logger.info("Testing login page loads");

        LoginPage loginPage = new LoginPage(getDriver());

        Assert.assertTrue(loginPage.isLoginPageDisplayed(),
                "Login page should be displayed");

        logger.info("Login page loads test passed");
    }
}
