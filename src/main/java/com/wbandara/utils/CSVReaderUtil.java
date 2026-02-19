package com.wbandara.utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CSVReaderUtil - Utility class for reading test data from CSV files.
 * Supports TestNG DataProvider integration.
 */
public class CSVReaderUtil {
    private static final Logger logger = LogManager.getLogger(CSVReaderUtil.class);
    private static final String TEST_DATA_PATH = "src/test/resources/testdata/";

    /**
     * Read CSV file and return as 2D Object array (for TestNG DataProvider)
     * @param fileName CSV file name (without path)
     * @return Object[][] for DataProvider
     */
    public static Object[][] getCSVData(String fileName) {
        String filePath = TEST_DATA_PATH + fileName;
        List<String[]> allData = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            // Skip header row
            reader.skip(1);
            allData = reader.readAll();
            logger.info("CSV data loaded from: {}. Records: {}", filePath, allData.size());
        } catch (IOException | CsvException e) {
            logger.error("Failed to read CSV file: {}", e.getMessage());
            throw new RuntimeException("Failed to read CSV file: " + filePath, e);
        }

        // Convert to Object[][]
        Object[][] data = new Object[allData.size()][];
        for (int i = 0; i < allData.size(); i++) {
            data[i] = allData.get(i);
        }

        return data;
    }

    /**
     * Read CSV file and return as List of Maps (column name -> value)
     * @param fileName CSV file name (without path)
     * @return List of Maps containing row data
     */
    public static List<Map<String, String>> getCSVDataAsMap(String fileName) {
        String filePath = TEST_DATA_PATH + fileName;
        List<Map<String, String>> dataList = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> allData = reader.readAll();

            if (allData.isEmpty()) {
                logger.warn("CSV file is empty: {}", filePath);
                return dataList;
            }

            // First row is header
            String[] headers = allData.get(0);

            // Process data rows
            for (int i = 1; i < allData.size(); i++) {
                String[] row = allData.get(i);
                Map<String, String> rowMap = new HashMap<>();

                for (int j = 0; j < headers.length && j < row.length; j++) {
                    rowMap.put(headers[j].trim(), row[j].trim());
                }

                dataList.add(rowMap);
            }

            logger.info("CSV data loaded as Map from: {}. Records: {}", filePath, dataList.size());

        } catch (IOException | CsvException e) {
            logger.error("Failed to read CSV file: {}", e.getMessage());
            throw new RuntimeException("Failed to read CSV file: " + filePath, e);
        }

        return dataList;
    }

    /**
     * Read CSV with specific columns only
     * @param fileName CSV file name
     * @param columns Column names to extract
     * @return Object[][] with specified columns only
     */
    public static Object[][] getCSVDataByColumns(String fileName, String... columns) {
        List<Map<String, String>> dataList = getCSVDataAsMap(fileName);
        Object[][] result = new Object[dataList.size()][columns.length];

        for (int i = 0; i < dataList.size(); i++) {
            Map<String, String> row = dataList.get(i);
            for (int j = 0; j < columns.length; j++) {
                result[i][j] = row.getOrDefault(columns[j], "");
            }
        }

        return result;
    }

    /**
     * Get a single row by index
     * @param fileName CSV file name
     * @param rowIndex Row index (0-based, excluding header)
     * @return Map of column name to value
     */
    public static Map<String, String> getRowByIndex(String fileName, int rowIndex) {
        List<Map<String, String>> dataList = getCSVDataAsMap(fileName);

        if (rowIndex < 0 || rowIndex >= dataList.size()) {
            throw new IndexOutOfBoundsException("Row index " + rowIndex + " is out of bounds");
        }

        return dataList.get(rowIndex);
    }

    /**
     * Get rows filtered by column value
     * @param fileName CSV file name
     * @param columnName Column to filter by
     * @param value Value to match
     * @return List of matching rows
     */
    public static List<Map<String, String>> getRowsByColumnValue(String fileName, String columnName, String value) {
        List<Map<String, String>> dataList = getCSVDataAsMap(fileName);
        List<Map<String, String>> filteredList = new ArrayList<>();

        for (Map<String, String> row : dataList) {
            if (value.equals(row.get(columnName))) {
                filteredList.add(row);
            }
        }

        logger.info("Filtered rows by {}='{}'. Found: {}", columnName, value, filteredList.size());
        return filteredList;
    }
}
