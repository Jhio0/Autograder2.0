package com.example.demo.solution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class Solution {

    private static final Logger logger = LoggerFactory.getLogger(Solution.class);
    private final JdbcTemplate jdbcTemplate;

    public Solution(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Execute the SQL query and fetch results
    public List<Map<String, Object>> executeQuery(String sqlQuery) {
        try {
            logger.info("Executing query: {}", sqlQuery.trim());

            List<Map<String, Object>> results = jdbcTemplate.query(sqlQuery, new RowMapper<Map<String, Object>>() {
                @Override
                public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Map<String, Object> row = new HashMap<>();
                    int columnCount = rs.getMetaData().getColumnCount();

                    // Loop through columns and add to the row map
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = rs.getMetaData().getColumnName(i);
                        Object columnValue = rs.getObject(i);
                        row.put(columnName, columnValue);
                    }

                    return row;
                }
            });

            logger.info("Query executed successfully.");
            return results;

        } catch (Exception e) {
            logger.error("Error executing query: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    // Extract SQL queries from content
    public List<String> extractSqlQueries(String content) {
        List<String> queries = new ArrayList<>();
        String[] lines = content.split("\\r?\\n");

        // Regular expression to match SQL commands that start with the following keywords
        Pattern sqlPattern = Pattern.compile("^(SELECT|INSERT|UPDATE|DELETE|CREATE|ALTER|DROP|TRUNCATE|REPLACE|WITH|MERGE|CALL|EXPLAIN|SHOW|DESCRIBE)\\b", Pattern.CASE_INSENSITIVE);
        StringBuilder currentQuery = new StringBuilder();

        logger.info("Processing SQL file content...");

        for (String line : lines) {
            // Remove comments and unwanted lines
            String lineWithoutComments = line.replaceAll("--.*|/\\*.*?\\*/", "").trim();

            if (lineWithoutComments.isEmpty()) {
                continue; // Skip empty lines
            }

            logger.info("lineWithoutComments {}.", lineWithoutComments);

            // Check if the line starts with a valid SQL command
            Matcher matcher = sqlPattern.matcher(lineWithoutComments);
            boolean isSqlCommand = matcher.find();

            // If this line starts a new SQL query
            if (isSqlCommand) {
                if (!currentQuery.isEmpty()) {
                    // If we are in the middle of another query, it's an error or incomplete
                    logger.warn("Unterminated SQL query before line: {}", lineWithoutComments);
                    queries.add(currentQuery.toString().trim().replaceAll("\"", ""));
                    currentQuery.setLength(0); // Reset for the next query
                }
                // Start a new query with this line
                currentQuery.append(lineWithoutComments);
            } else if (!currentQuery.isEmpty()) {
                // Append to the current query if it hasn't ended
                currentQuery.append(" ").append(lineWithoutComments);
            }

            // Check if the query ends with a semicolon
            if (!currentQuery.isEmpty() && currentQuery.toString().trim().endsWith(";")) {
                // Clean and add the complete query without the semicolon
                String fullQuery = currentQuery.toString().trim();
                if (fullQuery.endsWith(";")) {
                    fullQuery = fullQuery.substring(0, fullQuery.length() - 1); // Remove the trailing semicolon
                }
                queries.add(fullQuery.replaceAll("\"", "")); // Add the query to the list
                currentQuery.setLength(0); // Reset for the next query
            }
        }

        // If there's any unfinished query left without a semicolon, we can log a warning or add it as-is
        if (!currentQuery.isEmpty()) {
            logger.warn("Final unterminated SQL query found.");
            queries.add(currentQuery.toString().trim().replaceAll("\"", ""));
        }

        logger.info("currentQuery {}.", currentQuery);
        logger.info("Extracted {} queries.", queries.size());
        return queries;
    }

    // Read SQL file and generate results
    public List<QueryResult> generateResults(String sqlFilePath) throws IOException {
        String content = Files.readString(Paths.get(sqlFilePath));
        List<String> queries = extractSqlQueries(content);

        List<QueryResult> queryResults = new ArrayList<>();

        for (String query : queries) {
            QueryResult queryResult;
            try {
                List<Map<String, Object>> result = executeQuery(query); // Assume executeQuery returns List<Map<String, Object>>
                queryResult = new QueryResult(query, true, result, null);
            } catch (Exception e) {
                // Capture the error and set success to false
                queryResult = new QueryResult(query, false, null, e.getMessage());
            }

            queryResults.add(queryResult);
        }

        logger.info("Queries: {}", queries);
        logger.info("Query Results: {}", queryResults);

        return queryResults;
    }
}
