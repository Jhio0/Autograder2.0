package com.example.demo.result_submissions_test_cases;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResultSubmissionTestCasesService {

    @Autowired
    private ResultSubmissionTestCasesRepository resultSubmissionTestCasesRepository;

    public List<ResultSubmissionTestCases> getAllTestCases() {
        return resultSubmissionTestCasesRepository.findAll();
    }

    public Optional<ResultSubmissionTestCases> getTestCaseById(Long id) {
        return resultSubmissionTestCasesRepository.findById(id);
    }

    @Transactional
    public ResultSubmissionTestCases saveTestCase(ResultSubmissionTestCases testCase) {
        return resultSubmissionTestCasesRepository.save(testCase);
    }

    public void deleteTestCase(Long id) {
        resultSubmissionTestCasesRepository.deleteById(id);
    }
}
