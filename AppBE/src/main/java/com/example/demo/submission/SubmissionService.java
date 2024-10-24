package com.example.demo.submission;

import com.example.demo.result_submissions.ResultSubmissions;
import com.example.demo.result_submissions.ResultSubmissionsDTO;
import com.example.demo.result_submissions.ResultSubmissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private ResultSubmissionsService resultSubmissionsService;

    public List<Submission> getAllSubmissions() {
        return submissionRepository.findAll();
    }

    public Optional<Submission> getSubmissionById(Long submissionId) {
        return submissionRepository.findById(submissionId);
    }

    public Submission saveSubmission(Submission submission) {
        // Save the submission first
        Submission savedSubmission = submissionRepository.save(submission);

        // Automatically create a ResultSubmission entry when a submission is saved
        ResultSubmissionsDTO resultSubmissionDTO = new ResultSubmissionsDTO();
        resultSubmissionDTO.setSubmissionId(savedSubmission.getSubmissionId());

        // Create the ResultSubmission and save it (this will also save test cases)
        ResultSubmissionsDTO createdResultSubmission = resultSubmissionsService.createResultSubmission(resultSubmissionDTO);

        return savedSubmission;
    }

    public void deleteSubmission(Long submissionId) {
        submissionRepository.deleteById(submissionId);
    }
}

