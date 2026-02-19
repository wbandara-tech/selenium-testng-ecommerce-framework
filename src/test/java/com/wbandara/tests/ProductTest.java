package com.wbandara.tests;

import com.wbandara.base.BaseTest;
import com.wbandara.pages.CartPage;
import com.wbandara.pages.InventoryPage;
import com.wbandara.pages.LoginPage;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * ProductTest - Test class for Product/Inventory module functionality.
 * Contains tests for add to cart, remove from cart, cart badge, and product sorting.
 */
@Epic("E-Commerce")
@Feature("Product Management")
public class ProductTest extends BaseTest {

    private static final String VALID_USERNAME = "standard_user";
    private static final String VALID_PASSWORD = "secret_sauce";

    private InventoryPage inventoryPage;

    @BeforeMethod(alwaysRun = true)
    public void loginAndSetup() {
        LoginPage loginPage = new LoginPage(getDriver());
        inventoryPage = loginPage.login(VALID_USERNAME, VALID_PASSWORD);
        logger.info("Logged in for product test");
    }

    // ==================== Add to Cart Tests ====================

    @Test(groups = {"smoke", "regression"}, priority = 1)
    @Story("Add to Cart")
    @Description("Verify product can be added to cart")
    @Severity(SeverityLevel.BLOCKER)
    public void testAddProductToCart() {
        logger.info("Testing add product to cart");

        String productName = "Sauce Labs Backpack";

        // Add product to cart
        inventoryPage.addProductToCartByName(productName);

        // Verify cart badge shows 1
        Assert.assertTrue(inventoryPage.isCartBadgeDisplayed(),
                "Cart badge should be displayed");
        Assert.assertEquals(inventoryPage.getCartBadgeCount(), 1,
                "Cart badge should show 1 item");

        // Verify product is in cart
        CartPage cartPage = inventoryPage.goToCart();
        Assert.assertTrue(cartPage.isItemInCart(productName),
                "Product should be in cart");

        logger.info("Add product to cart test passed");
    }

    // ==================== Remove from Cart Tests ====================

    @Test(groups = {"smoke", "regression"}, priority = 2)
    @Story("Remove from Cart")
    @Description("Verify product can be removed from cart")
    @Severity(SeverityLevel.CRITICAL)
    public void testRemoveProductFromCart() {
        logger.info("Testing remove product from cart");

        String productName = "Sauce Labs Backpack";

        // Add product to cart
        inventoryPage.addProductToCartByName(productName);
        Assert.assertEquals(inventoryPage.getCartBadgeCount(), 1,
                "Cart should have 1 item");

        // Remove product from inventory page
        inventoryPage.removeProductFromCartByName(productName);

        // Verify cart badge is not displayed
        Assert.assertFalse(inventoryPage.isCartBadgeDisplayed(),
                "Cart badge should not be displayed after removing item");

        logger.info("Remove product from cart test passed");
    }

    // ==================== Product Sorting Tests ====================

    @Test(groups = {"smoke", "regression"}, priority = 3)
    @Story("Product Sorting")
    @Description("Verify products can be sorted by price low to high")
    @Severity(SeverityLevel.NORMAL)
    public void testSortByPriceLowToHigh() {
        logger.info("Testing sort by price low to high");

        // Sort by price low to high
        inventoryPage.sortProductsByValue("lohi");

        // Verify sorting
        Assert.assertTrue(inventoryPage.isProductsSortedByPriceLowToHigh(),
                "Products should be sorted by price low to high");

        logger.info("Sort by price low to high test passed");
    }

    @Test(groups = {"regression"}, priority = 4)
    @Story("Product Sorting")
    @Description("Verify products can be sorted by price high to low")
    @Severity(SeverityLevel.NORMAL)
    public void testSortByPriceHighToLow() {
        logger.info("Testing sort by price high to low");

        // Sort by price high to low
        inventoryPage.sortProductsByValue("hilo");

        // Verify sorting
        Assert.assertTrue(inventoryPage.isProductsSortedByPriceHighToLow(),
                "Products should be sorted by price high to low");

        logger.info("Sort by price high to low test passed");
    }

    // ==================== Product Count Tests ====================

    @Test(groups = {"regression"}, priority = 5)
    @Story("Product Display")
    @Description("Verify all products are displayed on inventory page")
    @Severity(SeverityLevel.NORMAL)
    public void testAllProductsDisplayed() {
        logger.info("Testing all products are displayed");

        int productCount = inventoryPage.getProductCount();
        logger.info("Product count: {}", productCount);

        Assert.assertTrue(productCount > 0,
                "At least one product should be displayed");
        Assert.assertEquals(productCount, 6,
                "SauceDemo should have 6 products");

        logger.info("All products displayed test passed");
    }
}
