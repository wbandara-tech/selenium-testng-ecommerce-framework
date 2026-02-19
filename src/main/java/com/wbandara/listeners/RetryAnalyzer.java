package com.wbandara.listeners;

import com.wbandara.utils.ConfigManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * RetryAnalyzer - Custom retry analyzer for retrying failed tests.
 * Implements IRetryAnalyzer for automatic test retry on failure.
 */
public class RetryAnalyzer implements IRetryAnalyzer {
    private static final Logger logger = LogManager.getLogger(RetryAnalyzer.class);
    private int retryCount = 0;
    private static final int MAX_RETRY_COUNT = ConfigManager.getInstance().getRetryCount();

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < MAX_RETRY_COUNT) {
            retryCount++;
            logger.warn("Retrying test '{}' for the {} time", result.getName(), retryCount);
            return true;
        }
        return false;
    }

    /**
     * Get current retry count
     */
    public int getRetryCount() {
        return retryCount;
    }

    /**
     * Get max retry count
     */
    public static int getMaxRetryCount() {
        return MAX_RETRY_COUNT;
    }
}
