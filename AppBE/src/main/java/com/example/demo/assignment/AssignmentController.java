package com.example.demo.assignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    // Convert Assignment entity to AssignmentDTO
    private AssignmentDTO convertToDTO(Assignment assignment) {
        return new AssignmentDTO(
                assignment.getAssignmentId(),
                assignment.getAssignmentTitle(),
                assignment.getDescription(),
                assignment.getDueDate(),
                assignment.getCourse() != null ? assignment.getCourse().getCourseId() : null,
                assignment.getAssignmentFile(),
                assignment.getSolutionFile()
        );
    }

    // Convert AssignmentDTO to Assignment entity
    private Assignment convertToEntity(AssignmentDTO assignmentDTO) {
        Assignment assignment = new Assignment();
        assignment.setAssignmentId(assignmentDTO.getAssignmentId());
        assignment.setAssignmentTitle(assignmentDTO.getAssignmentTitle());
        assignment.setDescription(assignmentDTO.getDescription());
        assignment.setDueDate(assignmentDTO.getDueDate());
        // Handle Course relationship if needed
        return assignment;
    }

    @GetMapping
    public List<AssignmentDTO> getAllAssignments() {
        return assignmentService.getAllAssignments().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssignmentDTO> getAssignmentById(@PathVariable Long id) {
        Optional<Assignment> assignment = assignmentService.getAssignmentById(id);
        return assignment.map(a -> ResponseEntity.ok(convertToDTO(a)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public AssignmentDTO createAssignment(@RequestBody AssignmentDTO assignmentDTO) {
        Assignment assignment = convertToEntity(assignmentDTO);
        Assignment savedAssignment = assignmentService.saveAssignment(assignment);
        return convertToDTO(savedAssignment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        assignmentService.deleteAssignment(id);
        return ResponseEntity.noContent().build();
    }
}

