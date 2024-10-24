package com.example.demo.Instructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/instructors")
public class InstructorController {
    @Autowired
    private InstructorService instructorService;

    // Convert Instructor entity to InstructorDTO
    private InstructorDTO convertToDTO(Instructor instructor) {
        return new InstructorDTO(
                instructor.getInstructorId(),
                instructor.getFirstName(),
                instructor.getLastName(),
                instructor.getEmailAddress(),
                instructor.getPhoneNumber(),
                instructor.getJoiningDate(),
                instructor.getUser() != null ? instructor.getUser().getUserId() : null
        );
    }

    // Convert InstructorDTO to Instructor entity
    private Instructor convertToEntity(InstructorDTO instructorDTO) {
        Instructor instructor = new Instructor();
        instructor.setInstructorId(instructorDTO.getInstructorId());
        instructor.setFirstName(instructorDTO.getFirstName());
        instructor.setLastName(instructorDTO.getLastName());
        instructor.setEmailAddress(instructorDTO.getEmailAddress());
        instructor.setPhoneNumber(instructorDTO.getPhoneNumber());
        instructor.setJoiningDate(instructorDTO.getJoiningDate());
        // Handle user relationship
        return instructor;
    }

    @GetMapping
    public List<InstructorDTO> getAllInstructors() {
        return instructorService.getAllInstructors().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstructorDTO> getInstructorById(@PathVariable Long id) {
        Optional<Instructor> instructor = instructorService.getInstructorById(id);
        return instructor.map(i -> ResponseEntity.ok(convertToDTO(i)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public InstructorDTO createInstructor(@RequestBody InstructorDTO instructorDTO) {
        Instructor instructor = convertToEntity(instructorDTO);
        Instructor savedInstructor = instructorService.saveInstructor(instructor);
        return convertToDTO(savedInstructor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstructor(@PathVariable Long id) {
        instructorService.deleteInstructor(id);
        return ResponseEntity.noContent().build();
    }
}

