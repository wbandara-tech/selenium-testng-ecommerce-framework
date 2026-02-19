package com.wbandara.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * RandomDataUtil - Utility class for generating random/dynamic test data.
 * Provides unique values using timestamps and random numbers.
 */
public class RandomDataUtil {
    private static final Logger logger = LogManager.getLogger(RandomDataUtil.class);
    private static final Random random = new SecureRandom();
    private static final String ALPHA_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String NUMERIC_CHARS = "0123456789";
    private static final String ALPHANUMERIC_CHARS = ALPHA_CHARS + NUMERIC_CHARS;

    /**
     * Generate unique first name with timestamp
     */
    public static String generateFirstName() {
        String firstName = "User_" + System.currentTimeMillis();
        logger.debug("Generated first name: {}", firstName);
        return firstName;
    }

    /**
     * Generate unique first name with prefix
     */
    public static String generateFirstName(String prefix) {
        String firstName = prefix + "_" + System.currentTimeMillis();
        logger.debug("Generated first name: {}", firstName);
        return firstName;
    }

    /**
     * Generate unique last name with timestamp
     */
    public static String generateLastName() {
        String lastName = "Test_" + getRandomNumber(1000, 9999);
        logger.debug("Generated last name: {}", lastName);
        return lastName;
    }

    /**
     * Generate random email
     */
    public static String generateEmail() {
        String email = "test_" + System.currentTimeMillis() + "@test.com";
        logger.debug("Generated email: {}", email);
        return email;
    }

    /**
     * Generate random email with domain
     */
    public static String generateEmail(String domain) {
        String email = "test_" + System.currentTimeMillis() + "@" + domain;
        logger.debug("Generated email: {}", email);
        return email;
    }

    /**
     * Generate random phone number
     */
    public static String generatePhoneNumber() {
        String phone = String.format("%03d-%03d-%04d",
                getRandomNumber(200, 999),
                getRandomNumber(200, 999),
                getRandomNumber(1000, 9999));
        logger.debug("Generated phone: {}", phone);
        return phone;
    }

    /**
     * Generate random ZIP code
     */
    public static String generateZipCode() {
        String zipCode = String.valueOf(getRandomNumber(10000, 99999));
        logger.debug("Generated ZIP code: {}", zipCode);
        return zipCode;
    }

    /**
     * Generate random address
     */
    public static String generateAddress() {
        String address = getRandomNumber(100, 9999) + " Test Street";
        logger.debug("Generated address: {}", address);
        return address;
    }

    /**
     * Generate random city
     */
    public static String generateCity() {
        String[] cities = {"New York", "Los Angeles", "Chicago", "Houston", "Phoenix",
                "Philadelphia", "San Antonio", "San Diego", "Dallas", "San Jose"};
        String city = cities[random.nextInt(cities.length)];
        logger.debug("Generated city: {}", city);
        return city;
    }

    /**
     * Generate random state
     */
    public static String generateState() {
        String[] states = {"CA", "TX", "FL", "NY", "PA", "IL", "OH", "GA", "NC", "MI"};
        String state = states[random.nextInt(states.length)];
        logger.debug("Generated state: {}", state);
        return state;
    }

    /**
     * Generate unique username
     */
    public static String generateUsername() {
        String username = "user_" + getTimestamp();
        logger.debug("Generated username: {}", username);
        return username;
    }

    /**
     * Generate random alphanumeric string of specified length
     */
    public static String generateAlphanumericString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(ALPHANUMERIC_CHARS.charAt(random.nextInt(ALPHANUMERIC_CHARS.length())));
        }
        return sb.toString();
    }

    /**
     * Generate random alphabetic string
     */
    public static String generateAlphaString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(ALPHA_CHARS.charAt(random.nextInt(ALPHA_CHARS.length())));
        }
        return sb.toString();
    }

    /**
     * Generate random numeric string
     */
    public static String generateNumericString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(NUMERIC_CHARS.charAt(random.nextInt(NUMERIC_CHARS.length())));
        }
        return sb.toString();
    }

    /**
     * Generate random number in range
     */
    public static int getRandomNumber(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * Generate UUID
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * Generate short UUID (first 8 characters)
     */
    public static String generateShortUUID() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    /**
     * Get current timestamp as string
     */
    public static String getTimestamp() {
        return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
    }

    /**
     * Get current date in specified format
     */
    public static String getCurrentDate(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    /**
     * Generate random boolean
     */
    public static boolean getRandomBoolean() {
        return random.nextBoolean();
    }

    /**
     * Generate test order ID
     */
    public static String generateOrderId() {
        return "ORD-" + System.currentTimeMillis();
    }

    /**
     * Generate credit card number (test only - not a real card)
     */
    public static String generateTestCreditCard() {
        return "4111111111111111"; // Standard test card number
    }

    /**
     * Generate random CVV
     */
    public static String generateCVV() {
        return generateNumericString(3);
    }

    /**
     * Generate expiry date (MM/YY format, future date)
     */
    public static String generateExpiryDate() {
        int month = getRandomNumber(1, 12);
        int year = Integer.parseInt(new SimpleDateFormat("yy").format(new Date())) + getRandomNumber(1, 5);
        return String.format("%02d/%02d", month, year);
    }
}
