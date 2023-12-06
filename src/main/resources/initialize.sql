-- Table for Persons
CREATE TABLE IF NOT EXISTS Persons
(
    university_id VARCHAR(20) PRIMARY KEY,
    person_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL
);

-- Table for Departments
CREATE TABLE IF NOT EXISTS Departments (
    department_code VARCHAR(4) PRIMARY KEY,
    department_name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS Programs
(
    program_name   VARCHAR(255) PRIMARY KEY,
    university_id VARCHAR(20),
    department_code VARCHAR(4),
    FOREIGN KEY (university_id) UNIQUE REFERENCES Persons(university_id)
    FOREIGN KEY (department_code) REFERENCES Departments(department_code)
);


CREATE TABLE FacultyMembers (
    faculty_id INT PRIMARY KEY,
    faculty_name VARCHAR(255) NOT NULL,
    email_address VARCHAR(255) NOT NULL,
    rank enum ('full', 'associate', 'assistant', 'adjunct') NOT NULL,
    department_code CHAR(4) NOT NULL,
    FOREIGN KEY (department_code) REFERENCES Departments(department_code)
);

CREATE TABLE Courses (
     course_id CHAR(8) PRIMARY KEY,
     course_title VARCHAR(255) NOT NULL,
     course_description TEXT,
     department_code CHAR(4) NOT NULL,
     FOREIGN KEY (department_code) REFERENCES Departments(department_code)
);

CREATE TABLE CourseOfferings (
     offering_id INT PRIMARY KEY,
     course_id CHAR(8) NOT NULL,
     semester enum('Fall','Spring','Summer') NOT NULL,
     section_number UNIQUE INT NOT NULL, /* Auto increment 001 - 999 */
     faculty_id INT NOT NULL,
     enrolled_students INT NOT NULL,
     PRIMARY KEY (course_id, semester, section_number),
     FOREIGN KEY (course_id) REFERENCES Courses(course_id),
     FOREIGN KEY (faculty_id) REFERENCES FacultyMembers(faculty_id)
);
-- Table for learning objectives
CREATE TABLE LearningObjective (
                                   ObjectiveCode VARCHAR(20) PRIMARY KEY,
                                   Description TEXT NOT NULL
);

-- Table for subobjectives
CREATE TABLE SubObjective (
                              ObjectiveCode VARCHAR(20),
                              SubObjectiveCode INT,
                              Description TEXT NOT NULL,
                              PRIMARY KEY (ObjectiveCode, SubObjectiveCode),
                              FOREIGN KEY (ObjectiveCode) REFERENCES LearningObjective(ObjectiveCode)
);


-- Table for program-learning objective relationships
CREATE TABLE ProgramObjective (
                                  program_name VARCHAR(255),
                                  course_id CHAR(8) NOT NULL,
                                  objective_code VARCHAR(20),
                                  sub_objective_code VARCHAR(20),
                                   num_met INT,
                                  PRIMARY KEY (ProgramName, ObjectiveCode),
                                  FOREIGN KEY (ProgramName) REFERENCES Program(ProgramName),
                                  FOREIGN KEY (course_id) REFERENCES Courses(course_id),
                                  FOREIGN KEY (objective_code) REFERENCES LearningObjective(objective_code),
                                  FOREIGN KEY (sub_objective_code) REFERENCES SubObjective(sub_objective_code)
);
