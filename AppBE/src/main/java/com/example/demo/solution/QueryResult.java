package com.example.demo.solution;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
public class QueryResult {
    private String query;
    private boolean success;
    private List<Map<String, Object>> result;
    private String errorMessage;

    public QueryResult(String query, boolean success, List<Map<String, Object>> result, String errorMessage) {
        this.query = query;
        this.success = success;
        this.result = result;
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "QueryResult{" +
                "query='" + query + '\'' +
                ", success=" + success +
                ", result=" + result +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}

