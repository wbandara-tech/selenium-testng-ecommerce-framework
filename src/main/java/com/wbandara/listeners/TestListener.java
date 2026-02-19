package com.wbandara.listeners;

import com.wbandara.base.DriverFactory;
import com.wbandara.utils.ScreenshotUtil;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;

/**
 * TestListener - Custom TestNG listener implementing ITestListener.
 * Handles test lifecycle events and captures screenshots on failure.
 */
public class TestListener implements ITestListener {
    private static final Logger logger = LogManager.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("========================================");
        logger.info("TEST STARTED: {}", result.getName());
        logger.info("========================================");
        Allure.step("Test Started: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("========================================");
        logger.info("TEST PASSED: {}", result.getName());
        logger.info("Duration: {} ms", result.getEndMillis() - result.getStartMillis());
        logger.info("========================================");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("========================================");
        logger.error("TEST FAILED: {}", result.getName());
        logger.error("Failure Reason: {}", result.getThrowable().getMessage());
        logger.error("========================================");

        // Capture screenshot on failure
        captureScreenshotOnFailure(result);

        // Log stack trace
        if (result.getThrowable() != null) {
            logger.error("Stack Trace:", result.getThrowable());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("========================================");
        logger.warn("TEST SKIPPED: {}", result.getName());
        if (result.getThrowable() != null) {
            logger.warn("Skip Reason: {}", result.getThrowable().getMessage());
        }
        logger.warn("========================================");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        logger.warn("TEST FAILED BUT WITHIN SUCCESS PERCENTAGE: {}", result.getName());
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        logger.error("TEST FAILED WITH TIMEOUT: {}", result.getName());
        captureScreenshotOnFailure(result);
    }

    @Override
    public void onStart(ITestContext context) {
        logger.info("========================================");
        logger.info("TEST SUITE STARTED: {}", context.getName());
        logger.info("Total Tests: {}", context.getAllTestMethods().length);
        logger.info("========================================");
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("========================================");
        logger.info("TEST SUITE FINISHED: {}", context.getName());
        logger.info("Passed Tests: {}", context.getPassedTests().size());
        logger.info("Failed Tests: {}", context.getFailedTests().size());
        logger.info("Skipped Tests: {}", context.getSkippedTests().size());
        logger.info("========================================");
    }

    /**
     * Capture screenshot and attach to Allure report on test failure
     */
    private void captureScreenshotOnFailure(ITestResult result) {
        try {
            if (DriverFactory.isDriverActive()) {
                WebDriver driver = DriverFactory.getDriver();
                byte[] screenshot = ScreenshotUtil.getScreenshotAsBytes(driver);

                // Attach to Allure report
                Allure.addAttachment(
                        result.getName() + "_failure_screenshot",
                        "image/png",
                        new ByteArrayInputStream(screenshot),
                        ".png"
                );

                // Also save to file
                ScreenshotUtil.captureScreenshot(driver, result.getName() + "_failure");

                logger.info("Screenshot captured for failed test: {}", result.getName());
            }
        } catch (Exception e) {
            logger.error("Failed to capture screenshot: {}", e.getMessage());
        }
    }
}
