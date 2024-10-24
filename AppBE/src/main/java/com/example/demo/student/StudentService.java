package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.example.demo.util.NotFoundException;
@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(String studentId) {
        return studentRepository.findById(studentId);
    }

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(String studentId) {
        studentRepository.deleteById(studentId);
    }

    public StudentDTO findByEmail(String email) {
        Student student = studentRepository.findByEmailAddress(email);
        if (student != null) {
            return mapToDTO(student, new StudentDTO());
        } else {
            throw new NotFoundException("Customer not found with email: " + email);
        }
    }

    public StudentDTO mapToDTO(final Student student, final StudentDTO studentDTO) {
        studentDTO.setStudentId(student.getStudentId());
        studentDTO.setFirstName(student.getFirstName());
        studentDTO.setLastName(student.getLastName());
        studentDTO.setEmailAddress(student.getEmailAddress());
        return studentDTO;
    }

}
