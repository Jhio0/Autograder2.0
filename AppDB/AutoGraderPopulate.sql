-- Populate Users Table
INSERT INTO Users (email_address, status, role) VALUES
('john.doe@example.com', TRUE, 'student'),
('jane.smith@example.com', TRUE, 'instructor'),
-- Populate Admins Table


INSERT INTO Students (student_id, first_name, last_name, email_address, phone_number, date_of_birth, enrollment_date, user_id) VALUES
(1, 'John', 'Doe', 'john.doe@example.com', '1234567890', '2000-05-15', '2023-09-01', 1); -- Corresponds to user_id = 1
-- Populate Instructors Table with explicit instructor_id as strings
INSERT INTO Instructors (instructor_id, first_name, last_name, email_address, phone_number, joining_date, user_id) VALUES
(1, 'Jane', 'Smith', 'jane.smith@example.com', '2345678901', '2022-01-10', 2)
-- Populate Courses Table with explicit student_id and instructor_id as strings
INSERT INTO Courses (course_title, course_description, start_date, end_date, student_id, instructor_id) VALUES
('Mathematics 101', 'Introduction to Algebra and Geometry', '2023-01-10', '2023-05-10', 's1', 'i1'),
('Physics 201', 'Basic Concepts of Physics', '2023-02-15', '2023-06-15', 's1', 'i1'),
('Chemistry 301', 'Organic and Inorganic Chemistry', '2023-03-20', '2023-07-20', 's1', 'i1'),
('Biology 401', 'Fundamentals of Biology', '2023-04-25', '2023-08-25', 's1', 'i1'),
('History 501', 'World History Overview', '2023-05-30', '2023-09-30', 's1', 'i1');

-- Make sure to update the Assignment and Submission tables accordingly
-- Populate Assignments Table with proper course IDs
INSERT INTO Assignments (assignment_title, description, due_date, course_id, assignment_file, solution_file) VALUES
('Assignment 1', 'Solve algebra problems', '2024-08-15', '11', NULL, NULL),
('Assignment 2', 'Complete physics experiments', '2024-08-20', '12', NULL, NULL),
('Assignment 3', 'Write chemistry lab report', '2024-08-25', '13', NULL, NULL),
('Assignment 4', 'Biology research project', '2024-09-01', '14', NULL, NULL),
('Assignment 5', 'History essay', '2024-09-10', '15', NULL, NULL);

-- Populate Submissions Table with proper student IDs and assignment IDs
INSERT INTO Submissions (submission_date, student_id, assignment_id, file_submission, total_grade) VALUES
('2024-08-10', 's1', '11', NULL, 90.00),
('2024-08-18', 's1', '12', NULL, 85.50),
('2024-08-24', 's1', '13', NULL, 78.00),
('2024-09-02', 's1', '14', NULL, 92.00),
('2024-09-08', 's1', '15', NULL, 88.00);

-- Insert data into Result_Assignments table (empty for now)
-- INSERT INTO Result_Assignments (assignment_id) VALUES
-- (1);

-- Insert data into Result_Assignment_Test_Cases table
INSERT INTO Result_Assignment_Test_Cases (result_assignment_id, testcase_id, test_results) VALUES
-- (1, 1, '{"status": "pass", "details": "Test case 1 passed."}');

-- Insert data into Result_Submissions table (empty for now)
-- INSERT INTO Result_Submissions (submission_id) VALUES
-- (1);

-- Insert data into Result_Submission_Test_Cases table
INSERT INTO Result_Submission_Test_Cases (result_submission_id, testcase_id, test_results) VALUES
-- (1, 1, '{"status": "pass", "details": "Test case 1 passed."}');
;
