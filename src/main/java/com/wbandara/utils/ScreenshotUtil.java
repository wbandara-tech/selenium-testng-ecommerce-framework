package com.wbandara.utils;

import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ScreenshotUtil - Utility class for capturing and managing screenshots.
 */
public class ScreenshotUtil {
    private static final Logger logger = LogManager.getLogger(ScreenshotUtil.class);
    private static final String SCREENSHOT_PATH = "target/screenshots/";

    /**
     * Capture screenshot and save to file
     */
    public static String captureScreenshot(WebDriver driver, String testName) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = testName + "_" + timestamp + ".png";
        Path screenshotPath = Paths.get(SCREENSHOT_PATH, fileName);

        try {
            createDirectory();
            TakesScreenshot ts = (TakesScreenshot) driver;
            byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
            Files.write(screenshotPath, screenshot);

            logger.info("Screenshot saved: {}", screenshotPath.toAbsolutePath());
            return screenshotPath.toAbsolutePath().toString();

        } catch (IOException e) {
            logger.error("Failed to capture screenshot: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Capture screenshot and attach to Allure report
     */
    public static void captureAndAttachScreenshot(WebDriver driver, String testName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);

            // Attach to Allure
            Allure.addAttachment(testName + "_screenshot",
                    "image/png",
                    new ByteArrayInputStream(screenshot),
                    ".png");

            // Also save to file
            captureScreenshot(driver, testName);

            logger.info("Screenshot captured and attached to Allure report: {}", testName);

        } catch (Exception e) {
            logger.error("Failed to capture screenshot: {}", e.getMessage());
        }
    }

    /**
     * Get screenshot as bytes
     */
    public static byte[] getScreenshotAsBytes(WebDriver driver) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        return ts.getScreenshotAs(OutputType.BYTES);
    }

    /**
     * Get screenshot as Base64 string
     */
    public static String getScreenshotAsBase64(WebDriver driver) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        return ts.getScreenshotAs(OutputType.BASE64);
    }

    /**
     * Create screenshot directory if not exists
     */
    private static void createDirectory() {
        File directory = new File(SCREENSHOT_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
}
