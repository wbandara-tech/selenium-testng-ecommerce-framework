package com.wbandara.tests;

import com.wbandara.base.BaseTest;
import com.wbandara.pages.*;
import com.wbandara.utils.RandomDataUtil;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * CheckoutTest - Test class for Checkout module functionality.
 * Contains tests for complete checkout flow with dynamic data.
 */
@Epic("E-Commerce")
@Feature("Checkout Functionality")
public class CheckoutTest extends BaseTest {

    private static final String VALID_USERNAME = "standard_user";
    private static final String VALID_PASSWORD = "secret_sauce";
    private static final String TEST_PRODUCT = "Sauce Labs Backpack";

    private InventoryPage inventoryPage;

    @BeforeMethod(alwaysRun = true)
    public void loginAndSetup() {
        LoginPage loginPage = new LoginPage(getDriver());
        inventoryPage = loginPage.login(VALID_USERNAME, VALID_PASSWORD);
        logger.info("Logged in successfully for checkout test");
    }

    // ==================== Complete Checkout Tests ====================

    @Test(groups = {"smoke", "regression"}, priority = 1)
    @Story("Complete Checkout")
    @Description("Verify complete checkout flow with dynamic user data")
    @Severity(SeverityLevel.BLOCKER)
    public void testCompleteCheckout() {
        logger.info("Starting complete checkout test");

        // Generate dynamic test data
        String firstName = RandomDataUtil.generateFirstName();
        String lastName = RandomDataUtil.generateLastName();
        String postalCode = RandomDataUtil.generateZipCode();

        // Add product to cart
        inventoryPage.addProductToCartByName(TEST_PRODUCT);
        Assert.assertEquals(inventoryPage.getCartBadgeCount(), 1,
                "Cart badge should show 1 item");

        // Go to cart
        CartPage cartPage = inventoryPage.goToCart();
        Assert.assertTrue(cartPage.isItemInCart(TEST_PRODUCT),
                "Product should be in cart");

        // Go to checkout
        CheckoutInfoPage checkoutInfoPage = cartPage.clickCheckout();

        // Fill checkout information
        checkoutInfoPage.enterFirstName(firstName);
        checkoutInfoPage.enterLastName(lastName);
        checkoutInfoPage.enterPostalCode(postalCode);

        // Continue to overview
        CheckoutOverviewPage overviewPage = checkoutInfoPage.clickContinue();
        Assert.assertTrue(overviewPage.isItemInOrder(TEST_PRODUCT),
                "Product should be in order overview");

        // Complete order
        CheckoutCompletePage completePage = overviewPage.clickFinish();
        Assert.assertTrue(completePage.isOrderSuccessful(),
                "Order should be successful");

        logger.info("Complete checkout test passed successfully");
    }
}
