package com.example.demo.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    // Convert Course entity to CourseDTO
    private CourseDTO convertToDTO(Course course) {
        return new CourseDTO(
                course.getCourseId(),
                course.getCourseTitle(),
                course.getCourseDescription(),
                course.getStartDate(),
                course.getEndDate(),
                course.getStudent() != null ? course.getStudent().getStudentId() : null,
                course.getInstructor() != null ? course.getInstructor().getInstructorId() : null
        );
    }

    // Convert CourseDTO to Course entity
    private Course convertToEntity(CourseDTO courseDTO) {
        Course course = new Course();
        course.setCourseId(courseDTO.getCourseId());
        course.setCourseTitle(courseDTO.getCourseTitle());
        course.setCourseDescription(courseDTO.getCourseDescription());
        course.setStartDate(courseDTO.getStartDate());
        course.setEndDate(courseDTO.getEndDate());
        // Handle Student and Instructor relationships if needed
        return course;
    }

    @GetMapping
    public List<CourseDTO> getAllCourses() {
        return courseService.getAllCourses().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable Long id) {
        Optional<Course> course = courseService.getCourseById(id);
        return course.map(c -> ResponseEntity.ok(convertToDTO(c)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public CourseDTO createCourse(@RequestBody CourseDTO courseDTO) {
        Course course = convertToEntity(courseDTO);
        Course savedCourse = courseService.saveCourse(course);
        return convertToDTO(savedCourse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}

