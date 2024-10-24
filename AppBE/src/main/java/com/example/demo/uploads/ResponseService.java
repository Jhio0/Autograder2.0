package com.example.demo.uploads;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ResponseService {

    private List<Map<String, Object>> testResultData;

    // Setter for test result data
    public void setTestResultData(List<Map<String, Object>> testResultData) {
        this.testResultData = testResultData;
    }

    // Getter for test result data (optional)
    public List<Map<String, Object>> getTestResultData() {
        return testResultData;
    }
}