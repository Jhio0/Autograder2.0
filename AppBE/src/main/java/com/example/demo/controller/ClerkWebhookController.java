package com.example.demo.controller;

import com.example.demo.student.Student;
import com.example.demo.student.StudentRepository;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin(origins = {
        "https://nopain.sunsab.com",
        "http://localhost:3000" })
@RequestMapping(value = "/api/clerk", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClerkWebhookController {

    private static final Logger logger = LoggerFactory.getLogger(ClerkWebhookController.class);

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/clerk-webhook")
    public void handleClerkWebhook(@RequestBody String payload) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode = mapper.readTree(payload);
            String eventType = jsonNode.path("type").asText();
            logger.info("Received Payload: {}", payload);

            JsonNode user = jsonNode.path("data");
            String userId = user.path("id").asText();
            logger.info("User ID: {}", userId);

            switch (eventType) {
                case "user.created":
                    handleUserCreated(user, userId);
                    break;

                case "user.updated":
                    handleUserUpdated(user, userId);
                    break;

                case "user.deleted":
                    handleUserDeleted(userId);
                    break;

                default:
                    logger.warn("Unhandled event type: {}", eventType);
                    break;
            }
        } catch (Exception e) {
            logger.error("Error processing webhook payload", e);
        }
    }

    private void handleUserCreated(JsonNode user, String userId) {
        logger.info("User Created Data: {}", user.toString());
        logger.info("User ID: {}", userId);
        if (userId == null || userId.isEmpty()) {
            logger.error("User ID is null or empty, cannot insert student");
            return;
        }

        if (user.has("email_addresses") && user.path("email_addresses").isArray()) {
            JsonNode emailAddresses = user.path("email_addresses");
            if (emailAddresses.size() > 0) {
                String email = emailAddresses.get(0).path("email_address").asText();
                String firstName = user.path("first_name").asText();
                String lastName = user.path("last_name").asText();
                String name = firstName + " " + lastName;

                logger.info("Email: {}", email);
                logger.info("Name: {}", name);

                // Check if the student already exists
                Student existingStudent = studentRepository.findByStudentId(userId);
                if (existingStudent == null) {
                    // Create and save the new User entity first
                    User newUser = new User();
                    newUser.setEmailAddress(email);
                    newUser.setStatus(true); // Set appropriate status
                    newUser.setRole("student"); // Set the role as student
                    userRepository.save(newUser); // Save the User entity to get its ID

                    // Create and save the new Student entity, linking it with the User
                    Student newStudent = new Student();
                    newStudent.setStudentId(userId); // Set studentId to the Clerk userId
                    newStudent.setFirstName(firstName);
                    newStudent.setLastName(lastName);
                    newStudent.setEmailAddress(email);
                    newStudent.setUser(newUser); // Associate the Student with the User entity
                    studentRepository.save(newStudent); // Save the Student entity

                    // Optionally, log the creation of both entities
                    logger.info("New student and user created: {} ({})", email, userId);
                } else {
                    logger.error("Student with ID {} already exists", userId);
                }
            } else {
                logger.error("No email addresses found in the 'email_addresses' array");
            }
        } else {
            logger.error("'email_addresses' field is missing or not an array");
        }
    }

    private void handleUserUpdated(JsonNode user, String userId) {
        logger.info("User Updated Data: {}", user.toString());

        if (user.has("email_addresses") && user.path("email_addresses").isArray()) {
            JsonNode emailAddresses = user.path("email_addresses");
            if (emailAddresses.size() > 0) {
                String email = emailAddresses.get(0).path("email_address").asText();
                String firstName = user.path("first_name").asText();
                String lastName = user.path("last_name").asText();
                String name = firstName + " " + lastName;

                logger.info("Email: {}", email);
                logger.info("Name: {}", name);

                // Update the Student entity
                Student existingStudent = studentRepository.findByStudentId(userId);
                if (existingStudent != null) {
                    existingStudent.setLastName(lastName);
                    existingStudent.setFirstName(firstName);
                    existingStudent.setEmailAddress(email); // Optionally update email
                    studentRepository.save(existingStudent);
                } else {
                    logger.error("Student with ID {} not found for update", userId);
                }

                // Update the User entity
                User existingUser = userRepository.findByEmailAddress(email); // Or fetch by userId if needed
                if (existingUser != null) {
                    existingUser.setEmailAddress(email); // Update email if necessary
                    existingUser.setStatus(true); // Assuming status should remain true
                    userRepository.save(existingUser);
                } else {
                    logger.error("User with email {} not found for update", email);
                }
            } else {
                logger.error("No email addresses found in the 'email_addresses' array");
            }
        } else {
            logger.error("'email_addresses' field is missing or not an array");
        }
    }


    private void handleUserDeleted(String userId) {
        logger.info("User Deleted Data: {}", userId);

        Student existingStudent = studentRepository.findByStudentId(userId);
        if (existingStudent != null) {
            studentRepository.delete(existingStudent);
        } else {
            logger.error("Customer with ID {} not found for deletion", userId);
        }

        User existingUser = userRepository.findByEmailAddress(existingStudent.getEmailAddress()); // Assuming you keep email consistent
        if (existingUser != null) {
            userRepository.delete(existingUser);
        } else {
            logger.error("User with email {} not found for deletion", existingStudent.getEmailAddress());
        }
    }
}
