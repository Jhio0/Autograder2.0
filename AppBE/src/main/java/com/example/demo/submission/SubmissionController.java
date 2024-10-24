package com.example.demo.submission;

import com.example.demo.student.StudentRepository;
import com.example.demo.student.StudentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private StudentRepository studentRepository;
    private static final Logger logger = LogManager.getLogger(SubmissionController.class);

    // Convert Submission entity to SubmissionDTO
    private SubmissionDTO convertToDTO(Submission submission) {
        return new SubmissionDTO(
                submission.getSubmissionId(),
                submission.getSubmissionDate(),
                submission.getStudent() != null ? submission.getStudent().getStudentId() : null,
                submission.getAssignmentID(),
                submission.getFileSubmission(),
                submission.getTotalGrade()
        );
    }

    // Convert SubmissionDTO to Submission entity
    private Submission convertToEntity(SubmissionDTO submissionDTO) {
        Submission submission = new Submission();
        submission.setSubmissionId(submissionDTO.getSubmissionId());
        submission.setSubmissionDate(submissionDTO.getSubmissionDate());
        submission.setFileSubmission(submissionDTO.getFileSubmission());
        submission.setTotalGrade(submissionDTO.getTotalGrade());
        submission.setAssignmentID(submissionDTO.getAssignmentId());
        // Assuming you have services to find students and assignments
        if (submissionDTO.getStudentId() != null) {
            submission.setStudent(studentRepository.findByStudentId(submissionDTO.getStudentId()));
        }

        // Handle Student and Assignment relationships if needed
        return submission;
    }

    @GetMapping
    public List<SubmissionDTO> getAllSubmissions() {
        return submissionService.getAllSubmissions().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubmissionDTO> getSubmissionById(@PathVariable Long id) {
        Optional<Submission> submission = submissionService.getSubmissionById(id);
        return submission.map(s -> ResponseEntity.ok(convertToDTO(s)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Updated method to handle file uploads and form data using MultipartFile
    @PostMapping
    public SubmissionDTO createSubmission(
            @RequestParam("fileSubmission") MultipartFile file,
            @RequestParam("submissionDate") Date submissionDate,
            @RequestParam("studentId") String studentId,
            @RequestParam("assignmentId") Long assignmentId,
            @RequestParam("totalGrade") Double totalGrade) {

        Submission submission = new Submission();
        submission.setSubmissionDate(submissionDate);

        try {
            // Directly convert the file to byte array using MultipartFile's method
            byte[] fileBytes = file.getBytes();
            submission.setFileSubmission(fileBytes);   // Store file as byte array

            logger.info("File uploaded successfully, bytes: {}", fileBytes);
        } catch (IOException e) {
            logger.error("Failed to store file", e);
            throw new RuntimeException("Failed to store file", e);
        }

        submission.setTotalGrade(totalGrade);
        submission.setAssignmentID(assignmentId);
        submission.setStudent(studentRepository.findByStudentId(studentId));

        Submission savedSubmission = submissionService.saveSubmission(submission);
        return convertToDTO(savedSubmission);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubmission(@PathVariable Long id) {
        submissionService.deleteSubmission(id);
        return ResponseEntity.noContent().build();
    }
}
