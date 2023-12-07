-- Table for Persons
CREATE TABLE IF NOT EXISTS Persons
(
    university_id VARCHAR(20) PRIMARY KEY,
    person_name   VARCHAR(255)        NOT NULL,
    email         VARCHAR(255) UNIQUE NOT NULL
);

-- Table for Departments
CREATE TABLE IF NOT EXISTS Departments
(
    department_code CHAR(4) PRIMARY KEY,
    department_name VARCHAR(255) UNIQUE NOT NULL
);

-- Table for Programs
CREATE TABLE IF NOT EXISTS Programs
(
    program_id         INT PRIMARY KEY AUTO_INCREMENT,
    program_name       VARCHAR(255) UNIQUE NOT NULL,
    department_code    CHAR(4)             NOT NULL,
    head_of_program_id VARCHAR(20) UNIQUE  NOT NULL,
    FOREIGN KEY (department_code) REFERENCES Departments (department_code),
    FOREIGN KEY (head_of_program_id) REFERENCES Persons (university_id)
);

-- Table for FacultyMembers
CREATE TABLE IF NOT EXISTS FacultyMembers
(
    faculty_id      INT PRIMARY KEY AUTO_INCREMENT,
    faculty_name    VARCHAR(255)                                       NOT NULL,
    email_address   VARCHAR(255) UNIQUE                                NOT NULL,
    faculty_rank    ENUM ('full', 'associate', 'assistant', 'adjunct') NOT NULL,
    department_code CHAR(4),
    FOREIGN KEY (department_code) REFERENCES Departments (department_code)
);

-- Table for Courses
CREATE TABLE IF NOT EXISTS Courses
(
    course_id          CHAR(8) PRIMARY KEY,
    course_title       VARCHAR(255) NOT NULL,
    course_description TEXT,
    department_code    CHAR(4),
    FOREIGN KEY (department_code) REFERENCES Departments (department_code)
);

-- Table for Sections/
CREATE TABLE IF NOT EXISTS Sections
(
    section_number    INT(3) ZEROFILL AUTO_INCREMENT PRIMARY KEY,
    course_id         CHAR(8)                         NOT NULL,
    semester          ENUM ('Fall','Spring','Summer') NOT NULL,
    faculty_id        INT                             NOT NULL,
    enrolled_students INT                             NOT NULL,
    year              INT                             NOT NULL,
    FOREIGN KEY (course_id) REFERENCES Courses (course_id),
    FOREIGN KEY (faculty_id) REFERENCES FacultyMembers (faculty_id)
);

-- Table for LearningObjectives
CREATE TABLE IF NOT EXISTS LearningObjectives
(
    objective_code VARCHAR(20) PRIMARY KEY,
    program_id     INT,
    FOREIGN KEY (program_id) REFERENCES Programs (program_id)

);

-- Table for SubObjectives
CREATE TABLE IF NOT EXISTS SubObjectives
(
    sub_objective_code INT AUTO_INCREMENT PRIMARY KEY,
    objective_code     VARCHAR(20),
    description        TEXT NOT NULL,
    FOREIGN KEY (objective_code) REFERENCES LearningObjectives (objective_code)
);

-- Table for ProgramCourses
CREATE TABLE IF NOT EXISTS ProgramCourses
(
    program_id INT,
    course_id  CHAR(8),
    PRIMARY KEY (program_id, course_id),
    FOREIGN KEY (program_id) REFERENCES Programs (program_id),
    FOREIGN KEY (course_id) REFERENCES Courses (course_id)
);

-- Table for ProgramObjectives
CREATE TABLE IF NOT EXISTS ProgramObjectives
(
    program_id         INT,
    course_id          CHAR(8),
    objective_code     VARCHAR(20),
    sub_objective_code INT,
    PRIMARY KEY (program_id, course_id, objective_code, sub_objective_code),
    FOREIGN KEY (program_id) REFERENCES Programs (program_id),
    FOREIGN KEY (course_id) REFERENCES Courses (course_id),
    FOREIGN KEY (objective_code) REFERENCES LearningObjectives (objective_code),
    FOREIGN KEY (sub_objective_code) REFERENCES SubObjectives (sub_objective_code)
);

-- Table for EvaluationResults
CREATE TABLE IF NOT EXISTS EvaluationResults
(
    section_number     INT(3) ZEROFILL AUTO_INCREMENT PRIMARY KEY,
    sub_objective_code INT,
    evaluation_method  VARCHAR(50) NOT NULL,
    students_met       INT         NOT NULL,
    FOREIGN KEY (sub_objective_code) REFERENCES SubObjectives (sub_objective_code),
    FOREIGN KEY (section_number) REFERENCES Sections (section_number)
);
