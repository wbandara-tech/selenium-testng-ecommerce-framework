package com.wbandara.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import com.wbandara.utils.ConfigManager;

import java.time.Duration;

/**
 * DriverFactory - Thread-safe WebDriver factory with ThreadLocal implementation.
 * Supports Chrome and Edge browsers with headless mode.
 */
public class DriverFactory {
    private static final Logger logger = LogManager.getLogger(DriverFactory.class);
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static final ConfigManager config = ConfigManager.getInstance();

    private DriverFactory() {
        // Private constructor to prevent instantiation
    }

    /**
     * Initialize WebDriver based on browser configuration
     */
    public static void initDriver() {
        String browser = config.getBrowser().toLowerCase();
        boolean headless = config.isHeadless();

        logger.info("Initializing {} browser. Headless mode: {}", browser, headless);

        WebDriver driver = createDriver(browser, headless);
        configureDriver(driver);
        driverThreadLocal.set(driver);

        logger.info("WebDriver initialized successfully. Thread ID: {}", Thread.currentThread().getId());
    }

    /**
     * Initialize WebDriver with specific browser
     */
    public static void initDriver(String browser) {
        boolean headless = config.isHeadless();
        logger.info("Initializing {} browser. Headless mode: {}", browser, headless);

        WebDriver driver = createDriver(browser.toLowerCase(), headless);
        configureDriver(driver);
        driverThreadLocal.set(driver);

        logger.info("WebDriver initialized successfully. Thread ID: {}", Thread.currentThread().getId());
    }

    private static WebDriver createDriver(String browser, boolean headless) {
        switch (browser) {
            case "chrome":
                return createChromeDriver(headless);
            case "edge":
                return createEdgeDriver(headless);
            default:
                logger.warn("Unknown browser: {}. Defaulting to Chrome.", browser);
                return createChromeDriver(headless);
        }
    }

    private static WebDriver createChromeDriver(boolean headless) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();

        if (headless) {
            options.addArguments("--headless=new");
        }

        options.addArguments("--start-maximized");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-notifications");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        
        // Disable password manager and credential popups
        options.addArguments("--disable-save-password-bubble");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        
        java.util.Map<String, Object> prefs = new java.util.HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        prefs.put("autofill.profile_enabled", false);
        options.setExperimentalOption("prefs", prefs);

        logger.debug("Chrome options configured");
        return new ChromeDriver(options);
    }

    private static WebDriver createEdgeDriver(boolean headless) {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();

        if (headless) {
            options.addArguments("--headless=new");
        }

        options.addArguments("--start-maximized");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-notifications");
        options.addArguments("--remote-allow-origins=*");

        logger.debug("Edge options configured");
        return new EdgeDriver(options);
    }

    private static void configureDriver(WebDriver driver) {
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(config.getImplicitWait())
        );
        driver.manage().timeouts().pageLoadTimeout(
                Duration.ofSeconds(config.getPageLoadTimeout())
        );
        driver.manage().deleteAllCookies();
        logger.debug("WebDriver timeouts and window configured");
    }

    /**
     * Get the current thread's WebDriver instance
     */
    public static WebDriver getDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver == null) {
            throw new IllegalStateException("WebDriver has not been initialized. Call initDriver() first.");
        }
        return driver;
    }

    /**
     * Quit the WebDriver and remove from ThreadLocal
     */
    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            logger.info("Quitting WebDriver. Thread ID: {}", Thread.currentThread().getId());
            try {
                driver.quit();
            } catch (Exception e) {
                logger.error("Error while quitting WebDriver: {}", e.getMessage());
            } finally {
                driverThreadLocal.remove();
            }
        }
    }

    /**
     * Check if driver is active
     */
    public static boolean isDriverActive() {
        return driverThreadLocal.get() != null;
    }
}
