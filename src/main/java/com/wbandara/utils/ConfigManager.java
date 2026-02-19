package com.wbandara.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration Manager - Singleton class for managing application properties.
 * Supports environment-specific configurations (QA, STAGE).
 */
public class ConfigManager {
    private static final Logger logger = LogManager.getLogger(ConfigManager.class);
    private static ConfigManager instance;
    private Properties properties;
    private static final String CONFIG_PATH = "src/main/resources/config/config.properties";

    private ConfigManager() {
        properties = new Properties();
        loadProperties();
    }

    public static synchronized ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    private void loadProperties() {
        try (InputStream input = new FileInputStream(CONFIG_PATH)) {
            properties.load(input);
            logger.info("Configuration loaded successfully from: {}", CONFIG_PATH);

            // Load environment-specific properties if specified
            String environment = System.getProperty("env");
            if (environment != null && !environment.isEmpty()) {
                loadEnvironmentProperties(environment);
            }
        } catch (IOException e) {
            logger.error("Failed to load configuration file: {}", e.getMessage());
            throw new RuntimeException("Failed to load configuration file", e);
        }
    }

    private void loadEnvironmentProperties(String environment) {
        String envConfigPath = "src/main/resources/config/" + environment.toLowerCase() + ".properties";
        try (InputStream input = new FileInputStream(envConfigPath)) {
            properties.load(input);
            logger.info("Environment configuration loaded: {}", envConfigPath);
        } catch (IOException e) {
            logger.warn("Environment configuration file not found: {}", envConfigPath);
        }
    }

    public String getProperty(String key) {
        // First check system properties, then config file
        String systemProperty = System.getProperty(key);
        if (systemProperty != null) {
            return systemProperty;
        }
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        String value = getProperty(key);
        return value != null ? value : defaultValue;
    }

    public int getIntProperty(String key, int defaultValue) {
        String value = getProperty(key);
        try {
            return value != null ? Integer.parseInt(value) : defaultValue;
        } catch (NumberFormatException e) {
            logger.warn("Invalid integer value for key: {}. Using default: {}", key, defaultValue);
            return defaultValue;
        }
    }

    public boolean getBooleanProperty(String key, boolean defaultValue) {
        String value = getProperty(key);
        return value != null ? Boolean.parseBoolean(value) : defaultValue;
    }

    public String getBaseUrl() {
        return getProperty("base.url", "https://www.saucedemo.com/");
    }

    public String getBrowser() {
        return getProperty("browser", "chrome");
    }

    public boolean isHeadless() {
        return getBooleanProperty("headless", false);
    }

    public int getImplicitWait() {
        return getIntProperty("implicit.wait", 10);
    }

    public int getExplicitWait() {
        return getIntProperty("explicit.wait", 15);
    }

    public int getPageLoadTimeout() {
        return getIntProperty("page.load.timeout", 30);
    }

    public int getRetryCount() {
        return getIntProperty("retry.count", 2);
    }
}
