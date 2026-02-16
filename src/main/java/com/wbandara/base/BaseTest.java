package com.wbandara.base;

import com.wbandara.utils.ConfigManager;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * BaseTest - Base class for all test classes.
 * Provides setup, teardown, and common utilities for test execution.
 */
public class BaseTest {
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);
    protected static final ConfigManager config = ConfigManager.getInstance();
    protected SoftAssert softAssert;

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        logger.info("========================================");
        logger.info("Test Suite Execution Started");
        logger.info("Environment: {}", config.getProperty("environment", "QA"));
        logger.info("Browser: {}", config.getBrowser());
        logger.info("Headless Mode: {}", config.isHeadless());
        logger.info("========================================");

        // Create screenshots directory
        createScreenshotDirectory();
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser"})
    public void setUp(@Optional String browser) {
        softAssert = new SoftAssert();

        if (browser != null && !browser.isEmpty()) {
            DriverFactory.initDriver(browser);
        } else {
            DriverFactory.initDriver();
        }

        logger.info("Navigating to: {}", config.getBaseUrl());
        getDriver().get(config.getBaseUrl());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            logger.error("Test FAILED: {}", result.getName());
            captureScreenshot(result.getName());
        } else if (result.getStatus() == ITestResult.SKIP) {
            logger.warn("Test SKIPPED: {}", result.getName());
        } else {
            logger.info("Test PASSED: {}", result.getName());
        }

        DriverFactory.quitDriver();
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        logger.info("========================================");
        logger.info("Test Suite Execution Completed");
        logger.info("========================================");
    }

    /**
     * Get WebDriver instance
     */
    protected WebDriver getDriver() {
        return DriverFactory.getDriver();
    }

    /**
     * Navigate to a specific URL
     */
    protected void navigateTo(String url) {
        logger.info("Navigating to: {}", url);
        getDriver().get(url);
    }

    /**
     * Navigate to application base URL
     */
    protected void navigateToBaseUrl() {
        navigateTo(config.getBaseUrl());
    }

    /**
     * Capture screenshot and attach to Allure report
     */
    protected void captureScreenshot(String testName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) getDriver();
            byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);

            // Attach to Allure report
            Allure.addAttachment(testName + "_screenshot",
                    new ByteArrayInputStream(screenshot));

            // Save to file
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = testName + "_" + timestamp + ".png";
            Path screenshotPath = Paths.get(config.getProperty("screenshot.path", "target/screenshots/"), fileName);

            Files.write(screenshotPath, screenshot);
            logger.info("Screenshot captured: {}", screenshotPath);

        } catch (IOException e) {
            logger.error("Failed to capture screenshot: {}", e.getMessage());
        }
    }

    /**
     * Create screenshot directory if not exists
     */
    private void createScreenshotDirectory() {
        String screenshotPath = config.getProperty("screenshot.path", "target/screenshots/");
        File directory = new File(screenshotPath);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                logger.info("Screenshot directory created: {}", screenshotPath);
            }
        }
    }

    /**
     * Get current page title
     */
    protected String getPageTitle() {
        return getDriver().getTitle();
    }

    /**
     * Get current URL
     */
    protected String getCurrentUrl() {
        return getDriver().getCurrentUrl();
    }
}
