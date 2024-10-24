package com.example.demo.uploads;

import com.example.demo.solution.QueryResult;
import com.example.demo.partialmarking.ComparisonService;
import com.example.demo.solution.Solution;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class SQLQueryTestSuite {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Solution solution;

    private Logger logger = LogManager.getLogger(SQLQueryTestSuite.class);
    @Getter
    private List<Map<String, Object>> queryDetails = new ArrayList<>();
    @Getter
    private double pointsObtained = 0;
    @Getter
    private double totalPoints = 0;
//"solutionSQL/solution.sql"
    public void testSuite(File uploadedSqlFile, String SolutionSQL) {
        try {
            Resource resource = new ClassPathResource(SolutionSQL);
            File solutionFile = resource.getFile();

            List<QueryResult> expectedResults = solution.generateResults(solutionFile.getAbsolutePath());
            List<QueryResult> actualResults = solution.generateResults(uploadedSqlFile.getAbsolutePath());

            List<List<Map<String, Object>>> expectedResultsList = convertQueryResultsToList(expectedResults);
            List<List<Map<String, Object>>> actualResultsList = convertQueryResultsToList(actualResults);

            List<Table> expectedResultsTable = convertListToTable(expectedResultsList, "expected");
            List<Table> actualResultsTable = convertListToTable(actualResultsList, "actual");

            for (int i = 0; i < expectedResultsTable.size(); i++) {
                Map<String, Object> detail = new HashMap<>();
                Map<String, Object> queryInfo = new HashMap<>();
                String queryKey = "query" + (i + 1); // e.g., query1, query2
                queryInfo.put(queryKey, detail); // Wrap detail in a query key map


                Table expectedResult = expectedResultsTable.get(i);
                detail.put("expectedResultTable", expectedResult.print());
                // Check for actual results
                if (i >= actualResultsTable.size() || actualResultsTable.get(i).rowCount() == 0) {
                    logger.warn("Missing actual result for query {}", i + 1);
                    detail.put("result", "No actual result for this query");
                    totalPoints += 1.0; // Increment total points as this query was expected
                    queryDetails.add(queryInfo);
                    continue; // No points obtained for missing result
                }

                Table actualResult = actualResultsTable.get(i);
                detail.put("actualResultTable", actualResult.print());


                // Compare results and populate the detail map
                boolean allMatches = compareQueryResults(actualResult, expectedResult, detail);
                logAlignedTables(actualResult, expectedResult, i + 1);

                // Update points if all matches; otherwise, set to zero
                if (!allMatches) {
                    pointsObtained += 0; // Points should be zero if not all match
                }

                // Add the formatted detail to queryDetails

                queryDetails.add(queryInfo);
            }

            // Calculate the grade percentage
            double gradePercentage = (pointsObtained / totalPoints) * 100;

            // Convert queryDetails to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonQueryDetails = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(queryDetails);

            logger.info("query_details: {}", jsonQueryDetails);
            logger.info("Grade Percentage: {}%", gradePercentage);
            logger.info("Points Obtained: {}", pointsObtained);
            logger.info("Total Points: {}", totalPoints);

        } catch (IOException e) {
            logger.error("Error reading SQL files: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Error executing test suite: {}", e.getMessage());
        }
    }

    private boolean compareQueryResults(Table actualResult, Table expectedResult, Map<String, Object> detail) {
        boolean allMatches = true;

        ComparisonService.Result contentResult = ComparisonService.compareRowContent(actualResult, expectedResult);
        detail.put("content_match", contentResult.match);
        detail.put("content_score", contentResult.score);
        updateScores(contentResult);
        if (!contentResult.match) allMatches = false;

        ComparisonService.Result orderResult = ComparisonService.compareRowOrder(actualResult, expectedResult);
        detail.put("order_match", orderResult.match);
        detail.put("order_score", orderResult.score);
        updateScores(orderResult);
        if (!orderResult.match) allMatches = false;

        ComparisonService.Result rowCountResult = ComparisonService.compareRowCount(actualResult, expectedResult);
        detail.put("row_count_match", rowCountResult.match);
        detail.put("row_count_score", rowCountResult.score);
//        detail.put("ActualRowCount", rowCountResult.actualDetail);
//        detail.put("ExpectedRowCount", rowCountResult.expectedDetail);
        updateScores(rowCountResult);
        if (!rowCountResult.match) allMatches = false;

        ComparisonService.Result columnResult = ComparisonService.compareNumberOfColumns(actualResult, expectedResult);
        detail.put("column_count_match", columnResult.match);
        detail.put("column_count_score", columnResult.score);
        updateScores(columnResult);
        if (!columnResult.match) allMatches = false;

        ComparisonService.Result columnNamesResult = ComparisonService.compareColumnNames(actualResult, expectedResult);
        detail.put("column_names_match", columnNamesResult.match);
        detail.put("column_names_score", columnNamesResult.score);
        updateScores(columnNamesResult);
        if (!columnNamesResult.match) allMatches = false;

        ComparisonService.Result columnOrderResult = ComparisonService.compareColumnOrder(actualResult, expectedResult);
        detail.put("column_order_match", columnOrderResult.match);
        detail.put("column_order_score", columnOrderResult.score);
        updateScores(columnOrderResult);
        if (!columnOrderResult.match) allMatches = false;

        return allMatches;
    }

    // Helper method to update scores
    private void updateScores(ComparisonService.Result result) {
        totalPoints += 1.0; // Increment total points by 1 for each comparison
        pointsObtained += result.score; // Add the result score to points obtained
    }

    private List<List<Map<String, Object>>> convertQueryResultsToList(List<QueryResult> queryResults) {
        List<List<Map<String, Object>>> resultList = new ArrayList<>();
        for (QueryResult queryResult : queryResults) {
            if (queryResult.isSuccess()) {
                resultList.add(queryResult.getResult());
            } else {
                logger.error("Error in query: {} - {}", queryResult.getQuery(), queryResult.getErrorMessage());
                resultList.add(new ArrayList<>()); // Add an empty list if the query failed
            }
        }
        return resultList;
    }

    private void logAlignedTables(Table actual, Table expected, int tableIndex) {
        String actualPrint = actual.print().replaceAll("(?m)^", "  "); // Indent actual table
        String expectedPrint = expected.print().replaceAll("(?m)^", "  "); // Indent expected table

        logger.info("Alignment Check - Actual Table {}:\n{}\nExpected Table {}:\n{}",
                tableIndex, actualPrint, tableIndex, expectedPrint);
    }

    private List<Table> convertListToTable(List<List<Map<String, Object>>> resultsAsList, String tableNamePrefix) {
        List<Table> tables = new ArrayList<>();

        for (int i = 0; i < resultsAsList.size(); i++) {
            List<Map<String, Object>> rows = resultsAsList.get(i);

            // Create a new Table for each result
            Table table = Table.create(tableNamePrefix + "_" + (i + 1));

            if (rows.isEmpty()) {
                // If there are no rows, we can return an empty table with no columns
                tables.add(table);
                continue;
            }

            // Assuming there are rows, collect column names
            Set<String> columnNames = rows.get(0).keySet();
            StringColumn[] stringColumns = new StringColumn[columnNames.size()];
            int index = 0;
            for (String columnName : columnNames) {
                stringColumns[index] = StringColumn.create(columnName);
                index++;
            }

            // Populate the table with data
            for (Map<String, Object> row : rows) {
                index = 0;
                for (String columnName : columnNames) {
                    String value = row.get(columnName) != null ? row.get(columnName).toString() : "";
                    stringColumns[index].append(value);
                    index++;
                }
            }

            // Add columns to the table
            for (StringColumn column : stringColumns) {
                table.addColumns(column);
            }

            tables.add(table);
        }

        return tables;
    }

//    private void logResults(String resultType, List<Table> resultsTable) {
//        for (int i = 0; i < resultsTable.size(); i++) {
//            logger.info("{} Result Table {}: \n{}", resultType, i + 1, resultsTable.get(i).print());
//        }
//    }

    public void reset() {
        this.queryDetails = new ArrayList<>(); // or however you want to initialize
        this.pointsObtained = 0.0;
        this.totalPoints = 0.0;
    }

}
