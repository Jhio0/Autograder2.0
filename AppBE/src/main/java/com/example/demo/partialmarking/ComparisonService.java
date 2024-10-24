package com.example.demo.partialmarking;

import tech.tablesaw.api.Table;
import tech.tablesaw.api.ColumnType;
import tech.tablesaw.columns.Column;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ComparisonService {
    private static final Logger logger = Logger.getLogger(ComparisonService.class.getName());

    public static Result compareRowContent(Table actual, Table expected) {
        try {
            // Convert tables to sets of tuples to ignore row order
            Set<List<Object>> actualSet = new HashSet<>();
            Set<List<Object>> expectedSet = new HashSet<>();

            // Populate sets with rows as lists of objects
            for (int i = 0; i < actual.rowCount(); i++) {
                List<Object> actualRow = new ArrayList<>();
                for (int j = 0; j < actual.columnCount(); j++) {
                    actualRow.add(actual.get(i, j)); // Add cell values to the list
                }
                actualRow.sort(Comparator.comparing(Object::toString));
                actualSet.add(actualRow); // Add row as a list to the set
            }

            for (int i = 0; i < expected.rowCount(); i++) {
                List<Object> expectedRow = new ArrayList<>();
                for (int j = 0; j < expected.columnCount(); j++) {
                    expectedRow.add(expected.get(i, j)); // Add cell values to the list
                }
                expectedRow.sort(Comparator.comparing(Object::toString));
                expectedSet.add(expectedRow); // Add row as a list to the set
            }

            // Log the actual and expected sets for debugging
            logger.info("Actual Set of Rows: " + actualSet);
            logger.info("Expected Set of Rows: " + expectedSet);

            // Compare the sets
            boolean contentMatch = actualSet.equals(expectedSet);
            double contentScore = contentMatch ? 1.0 : 0.0;

            // Return the result based on the content comparison
            return new Result(contentMatch, contentScore, actual);

        } catch (Exception e) {
            logger.severe("Error comparing row content: " + e.getMessage());
            return new Result(false, 0.0, actual);
        }
    }

    public static Result compareRowOrder(Table actual, Table expected) {
        try {
            // Convert DataFrames (tables) to ArrayLists of rows
            List<List<Object>> actualRows = new ArrayList<>();
            List<List<Object>> expectedRows = new ArrayList<>();

            // Populate actualRows with rows as lists of objects
            for (int i = 0; i < actual.rowCount(); i++) {
                List<Object> actualRow = new ArrayList<>();
                for (int j = 0; j < actual.columnCount(); j++) {
                    actualRow.add(actual.get(i, j)); // Add each cell value to the list
                }
                actualRows.add(actualRow); // Add row to actualRows
            }

            // Populate expectedRows with rows as lists of objects
            for (int i = 0; i < expected.rowCount(); i++) {
                List<Object> expectedRow = new ArrayList<>();
                for (int j = 0; j < expected.columnCount(); j++) {
                    expectedRow.add(expected.get(i, j)); // Add each cell value to the list
                }
                expectedRows.add(expectedRow); // Add row to expectedRows
            }
logger.info("expected.columnCount()  " +  expected.columnCount() );
            // Log both actual and expected rows for debugging
            logger.info("Actual Rows (Ordered): " + actualRows);
            logger.info("Expected Rows (Ordered): " + expectedRows);

            // Check if the actual and expected rows are equal (content and order)
            boolean rowsMatch = actualRows.equals(expectedRows);

            // Log mismatch details if rows do not match
            if (!rowsMatch) {
                logger.info("Row order mismatch found.");
                return new Result(false, 0.0, actual); // Return false if rows do not match
            }

            // If all rows match in order and content, return success
            return new Result(true, 1.0, actual);

        } catch (Exception e) {
            logger.severe("Error comparing row order: " + e.getMessage());
            return new Result(false, 0.0, actual); // Return false in case of an error
        }
    }

    public static Result compareRowCount(Table actual, Table expected) {
        double actualRowCount = actual.rowCount();
        double expectedRowCount = expected.rowCount();
        logger.info("actualRowCount "+ actualRowCount);
        logger.info("expectedRowCount "+ expectedRowCount);
        try {
            boolean match = actualRowCount == expectedRowCount;
            double score = match ? 1.0 : 0.0;
            return new Result(match, score, actualRowCount, expectedRowCount); // Return both actual and expected row counts
        } catch (Exception e) {
            logger.severe("Error comparing row count: " + e.getMessage());
            return new Result(false, 0.0, actualRowCount, expectedRowCount);
        }
    }

    public static Result compareNumberOfColumns(Table actual, Table expected) {
        try {
            boolean match = actual.columnCount() == expected.columnCount();
            double score = match ? 1.0 : 0.0;
            return new Result(match, score, actual.columnCount(), null); // Return actual column count
        } catch (Exception e) {
            logger.severe("Error comparing number of columns: " + e.getMessage());
            return new Result(false, 0.0, actual);
        }
    }

    public static Result compareColumnNames(Table actual, Table expected) {
        try {
            // Convert column names to sets
            Set<String> actualColumns = new HashSet<>(actual.columnNames());
            Set<String> expectedColumns = new HashSet<>(expected.columnNames());

            logger.info("ActualNames: " + actualColumns);
            logger.info("ExpectedNames: " + expectedColumns);

            // Find common column names
            Set<String> commonColumns = new HashSet<>(actualColumns);
            commonColumns.retainAll(expectedColumns);

            // Check if row count matches or if there are any common column names

            // Calculate the percentage of matching columns
            int totalExpectedColumns = expectedColumns.size();
            double score = totalExpectedColumns > 0 ? (double) commonColumns.size() / totalExpectedColumns : 0.0;

            boolean fullMatch = score > 0;
            logger.info("Full Match (rowCount or common columns): " + fullMatch);

            // Log the matched columns and the partial score
            logger.info("Matched Columns: " + commonColumns);
            logger.info("Partial Score: " + score);

            // Return the result, including the actual column names, matched columns, and partial score
            return new Result(fullMatch, score, actualColumns, commonColumns);
        } catch (Exception e) {
            logger.severe("Error comparing column names: " + e.getMessage());
            return new Result(false, 0.0, actual);
        }
    }


    public static Result compareColumnOrder(Table actual, Table expected) {
        try {
            // Convert DataFrames (tables) to ArrayLists of rows
            List<List<Object>> actualColumns = new ArrayList<>();
            List<List<Object>> expectedColumns = new ArrayList<>();

            // Populate actualColumns with rows as lists of objects
            for (int i = 0; i < actual.rowCount(); i++) {
                List<Object> actualRow = new ArrayList<>();
                for (int j = 0; j < actual.columnCount(); j++) {
                    actualRow.add(actual.get(i, j)); // Add each cell value to the list
                }
                actualColumns.add(actualRow); // Add row to actualColumns
            }

            // Populate expectedColumns with rows as lists of objects
            for (int i = 0; i < expected.rowCount(); i++) {
                List<Object> expectedRow = new ArrayList<>();
                for (int j = 0; j < expected.columnCount(); j++) {
                    expectedRow.add(expected.get(i, j)); // Add each cell value to the list
                }
                expectedColumns.add(expectedRow); // Add row to expectedColumns
            }

            logger.info("Actual Columns (Ordered): " + actualColumns);
            logger.info("Expected Columns (Ordered): " + expectedColumns);

            // Ensure that both actual and expected have at least one row to compare
            if (actualColumns.isEmpty() || expectedColumns.isEmpty()) {
                logger.info("One or both tables have no rows to compare.");
                return new Result(false, 0.0, actual); // Return false if one or both are empty
            }

            // Compare only the first row from both actualColumns and expectedColumns
            List<Object> actualFirstRow = actualColumns.get(0);
            List<Object> expectedFirstRow = expectedColumns.get(0);

            // Check if the first row content matches, ignoring the order of elements within the row
            boolean firstRowMatch = new HashSet<>(actualFirstRow).equals(new HashSet<>(expectedFirstRow));

            // Log the result of the first row comparison
            if (!firstRowMatch) {
                logger.info("First row mismatch found.");
                return new Result(false, 0.0, actual); // Return false if first rows do not match
            }

            // If the first rows match, return success
            return new Result(true, 1.0, actual);

        } catch (Exception e) {
            logger.severe("Error comparing row order: " + e.getMessage());
            return new Result(false, 0.0, actual); // Return false in case of an error
        }
    }




    public static class Result {
        public final boolean match;
        public final double score;
        public final Object actualDetail;
        public final Object expectedDetail;

        public Result(boolean match, double score) {
            this(match, score, null, null);
        }

        public Result(boolean match, double score, Object actualDetail, Object expectedDetail) {
            this.match = match;
            this.score = score;
            this.actualDetail = actualDetail;
            this.expectedDetail = expectedDetail;
        }


        // New constructor for Table details
        public Result(boolean match, double score, Table actualTable) {
            this.match = match;
            this.score = score;
            this.actualDetail = actualTable; // Store the actual Table
            this.expectedDetail = null; // Keep expected detail as null
        }
    }
}

