package com.example.demo.controller;

import com.example.demo.uploads.ResponseService;
import com.example.demo.uploads.SQLQueryTestSuite;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@RestController
public class FileUploadController {

    @Autowired
    private SQLQueryTestSuite sqlQueryTestSuite;

    @Autowired
    private ResponseService responseService;

    private Logger logger = LogManager.getLogger(FileUploadController.class);

    @PostMapping("/api/submission/assignment1")
    public ResponseEntity<Map<String, Object>> handleFileUploadAssignment1(@RequestParam("file") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();

        if (file.isEmpty()) {
            response.put("error", "File is empty");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            sqlQueryTestSuite.reset();
            // Save the uploaded file to a temporary location
            Path tempFilePath = Paths.get("uploads/" + file.getOriginalFilename());
            Files.createDirectories(tempFilePath.getParent());
            Files.write(tempFilePath, file.getBytes());

            // Convert the Path to a File object
            File uploadedSqlFile = tempFilePath.toFile();

            // Process the file using SQLQueryTestSuite
            sqlQueryTestSuite.testSuite(uploadedSqlFile, "solutionSQL/solution.sql");

            // Extract test results data
            List<Map<String, Object>> testResultData = sqlQueryTestSuite.getQueryDetails();
            double pointsObtained = sqlQueryTestSuite.getPointsObtained();
            double totalPoints = sqlQueryTestSuite.getTotalPoints();
            double gradePercentage = (pointsObtained / totalPoints) * 100;

            // Prepare the response
            response.put("gradePercentage", gradePercentage);
            response.put("testResultData", testResultData);
            response.put("points_obtained", pointsObtained);
            response.put("total_points", totalPoints);

            // Save the response map to the ResponseService
            if (response.containsKey("testResultData")) {
                List<Map<String, Object>> testResults = (List<Map<String, Object>>) response.get("testResultData");

                // Set only test results data in the ResponseService
                responseService.setTestResultData(testResults);

                logger.info("Test results size: " + testResults);
            } else {
                logger.info("Response does not contain test results.");
            }
            // Return a success response
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (IOException e) {
            logger.error("Error processing file: {}", e.getMessage());
            response.put("error", "Failed to process file");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/api/submission/assignment2")
    public ResponseEntity<Map<String, Object>> handleFileUploadAssignment2(@RequestParam("file") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();

        if (file.isEmpty()) {
            response.put("error", "File is empty");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            sqlQueryTestSuite.reset();
            // Save the uploaded file to a temporary location
            Path tempFilePath = Paths.get("uploads/" + file.getOriginalFilename());
            Files.createDirectories(tempFilePath.getParent());
            Files.write(tempFilePath, file.getBytes());

            // Convert the Path to a File object
            File uploadedSqlFile = tempFilePath.toFile();

            // Process the file using SQLQueryTestSuite
            sqlQueryTestSuite.testSuite(uploadedSqlFile, "solutionSQL/JoinLabSolution.sql");

            // Extract test results data
            List<Map<String, Object>> testResultData = sqlQueryTestSuite.getQueryDetails();
            double pointsObtained = sqlQueryTestSuite.getPointsObtained();
            double totalPoints = sqlQueryTestSuite.getTotalPoints();
            double gradePercentage = (pointsObtained / totalPoints) * 100;

            // Prepare the response
            response.put("gradePercentage", gradePercentage);
            response.put("testResultData", testResultData);
            response.put("points_obtained", pointsObtained);
            response.put("total_points", totalPoints);

            if (response.containsKey("testResultData")) {
                List<Map<String, Object>> testResults = (List<Map<String, Object>>) response.get("testResultData");

                // Set only test results data in the ResponseService
                responseService.setTestResultData(testResults);

                logger.info("Test results size: " + testResults.size());
            } else {
                logger.info("Response does not contain test results.");
            }
            // Return a success response
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (IOException e) {
            logger.error("Error processing file: {}", e.getMessage());
            response.put("error", "Failed to process file");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

