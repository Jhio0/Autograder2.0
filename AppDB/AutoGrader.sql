-- Drop existing tables if they exist
DROP TABLE IF EXISTS Result_Submission_Test_Cases;
DROP TABLE IF EXISTS Result_Submissions;
DROP TABLE IF EXISTS Result_Assignment_Test_Cases;
DROP TABLE IF EXISTS Result_Assignments;
DROP TABLE IF EXISTS Submission_Grades;
DROP TABLE IF EXISTS Submissions;
DROP TABLE IF EXISTS Assignments;
DROP TABLE IF EXISTS Courses;
DROP TABLE IF EXISTS Instructors;
DROP TABLE IF EXISTS Students;
DROP TABLE IF EXISTS Admins;
DROP TABLE IF EXISTS Users;

-- Table: Users
CREATE TABLE Users (
    user_id SERIAL PRIMARY KEY,
    email_address VARCHAR(255) UNIQUE,
    status BOOLEAN NOT NULL CHECK (status IN (TRUE, FALSE)),
    role VARCHAR(50) NOT NULL CHECK (role IN ('student', 'instructor')) -- Indicates user type
);

-- Table: Students
CREATE TABLE Students (
    student_id VARCHAR(255) PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email_address VARCHAR(255) UNIQUE,
    phone_number CHAR(10), -- Fixed length for phone numbers
    date_of_birth DATE,
    enrollment_date DATE,
    user_id INT UNIQUE REFERENCES Users(user_id) ON DELETE SET NULL
);

-- Table: Instructors
CREATE TABLE Instructors (
    instructor_id VARCHAR(255) PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email_address VARCHAR(255) UNIQUE,
    phone_number CHAR(10),
    joining_date DATE,
    user_id INT UNIQUE REFERENCES Users(user_id) ON DELETE SET NULL
);

-- Table: Courses
CREATE TABLE Courses (
    course_id SERIAL PRIMARY KEY,
    course_title VARCHAR(255) NOT NULL,
    course_description TEXT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    student_id VARCHAR(255) REFERENCES Students(student_id) ON DELETE SET NULL,  -- Changed to VARCHAR
    instructor_id VARCHAR(255) REFERENCES Instructors(instructor_id) ON DELETE SET NULL  -- Changed to VARCHAR
);

-- Table: Assignments
CREATE TABLE Assignments (
    assignment_id SERIAL PRIMARY KEY,
    assignment_title VARCHAR(255) NOT NULL,
    description TEXT,
    due_date DATE,
    course_id INTEGER REFERENCES Courses(course_id) ON DELETE SET NULL,
    assignment_file BYTEA,
    solution_file BYTEA
);

-- Table: Submissions
CREATE TABLE Submissions (
    submission_id SERIAL PRIMARY KEY,
    submission_date DATE NOT NULL,
    student_id VARCHAR(255) NOT NULL REFERENCES Students(student_id) ON DELETE CASCADE,  -- Changed to VARCHAR
    assignment_id INTEGER NOT NULL REFERENCES Assignments(assignment_id) ON DELETE CASCADE,
    file_submission BYTEA,
    total_grade NUMERIC(5, 2)
);

-- Table: Result_Assignments
CREATE TABLE Result_Assignments (
  Result_Assignment_ID SERIAL PRIMARY KEY,
  AssignmentID INTEGER NOT NULL,
  FOREIGN KEY (AssignmentID) REFERENCES Assignments(assignment_id)  -- Corrected column name
);

-- Create Result_Assignment_Test_Cases Table with JSONB for test_results
CREATE TABLE Result_Assignment_Test_Cases (
  Result_Assignment_ID INTEGER NOT NULL,
  testcaseid SERIAL,
  test_results JSONB, -- Storing test results as JSONB
  PRIMARY KEY (Result_Assignment_ID, testcaseid),
  FOREIGN KEY (Result_Assignment_ID) REFERENCES Result_Assignments(Result_Assignment_ID)
);

-- Create Result_Submissions Table
CREATE TABLE Result_Submissions (
  Result_submission_ID SERIAL PRIMARY KEY,
  SubmissionID INTEGER NOT NULL,
  FOREIGN KEY (SubmissionID) REFERENCES Submissions(submission_id)  -- Corrected column name
);

-- Create Result_Submission_Test_Cases Table with JSONB for test_results
CREATE TABLE Result_Submission_Test_Cases (
  Result_submission_ID INTEGER NOT NULL,
  testcaseid SERIAL,
  test_results JSONB, -- Storing test results as JSONB
  PRIMARY KEY (Result_submission_ID, testcaseid),
  FOREIGN KEY (Result_submission_ID) REFERENCES Result_Submissions(Result_submission_ID)
);

CREATE TABLE Partial_Marks_Instructor (
    partial_mark_id SERIAL PRIMARY KEY,
    testcaseid INTEGER NOT NULL,
    mark BOOLEAN NOT NULL,  -- TRUE or FALSE for partial marking
    mark_description VARCHAR(255),  -- Description of the partial mark
    FOREIGN KEY (testcaseid) REFERENCES Result_Assignment_Test_Cases(testcaseid) ON DELETE CASCADE
);

CREATE TABLE Partial_Marks_Student (
    partial_mark_id SERIAL PRIMARY KEY,
    testcaseid INTEGER NOT NULL,
    mark BOOLEAN NOT NULL,  -- TRUE or FALSE for partial marking
    mark_description VARCHAR(255),  -- Description of the partial mark
    FOREIGN KEY (testcaseid) REFERENCES Result_Assignment_Test_Cases(testcaseid) ON DELETE CASCADE
);

-- Indexes for performance
CREATE INDEX idx_submissions_student_id ON Submissions(student_id);
CREATE INDEX idx_submissions_assignment_id ON Submissions(assignment_id);
CREATE INDEX idx_assignments_course_id ON Assignments(course_id);
CREATE INDEX idx_result_assignments_assignment_id ON Result_Assignments(AssignmentID);  -- Corrected column name
CREATE INDEX idx_result_submissions_submission_id ON Result_Submissions(SubmissionID);  -- Corrected column name
