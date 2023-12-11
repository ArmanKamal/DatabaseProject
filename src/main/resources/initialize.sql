-- Table for Departments
CREATE TABLE IF NOT EXISTS Departments
(
    department_code CHAR(4) PRIMARY KEY,
    department_name VARCHAR(255) UNIQUE NOT NULL
);

-- Table for FacultyMembers
CREATE TABLE IF NOT EXISTS FacultyMembers
(
    faculty_id      INT PRIMARY KEY,
    faculty_name    VARCHAR(255)                                       NOT NULL,
    email_address   VARCHAR(255) UNIQUE                                NOT NULL,
    faculty_rank    ENUM ('full', 'associate', 'assistant', 'adjunct') NOT NULL,
    department_code CHAR(4),
    FOREIGN KEY (department_code) REFERENCES Departments (department_code)
);

-- Table for Programs
-- Added constraint ensures that only faculty members from the same department can be the head of a program for that department.
CREATE TABLE IF NOT EXISTS Programs
(
    program_id         INT PRIMARY KEY AUTO_INCREMENT,
    program_name       VARCHAR(255) UNIQUE NOT NULL,
    department_code    CHAR(4)             NOT NULL,
    head_of_program_id INT UNIQUE          NOT NULL,
    FOREIGN KEY (department_code) REFERENCES Departments (department_code),
    FOREIGN KEY (head_of_program_id) REFERENCES FacultyMembers (faculty_id),
    CONSTRAINT fk_head_of_program_department
        FOREIGN KEY (department_code, head_of_program_id)
            REFERENCES FacultyMembers (department_code, faculty_id)
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
    section_number    INT(3),
    course_id         CHAR(8)                         NOT NULL,
    semester          ENUM ('Fall','Spring','Summer') NOT NULL,
    faculty_id        INT                             NOT NULL,
    enrolled_students INT                             NOT NULL,
    year              INT                             NOT NULL,
    PRIMARY KEY (section_number, semester, course_id, year),
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
    sub_objective_code VARCHAR(20),
    objective_code     VARCHAR(20),
    PRIMARY KEY (sub_objective_code,objective_code),
    description        TEXT NOT NULL,
    FOREIGN KEY (objective_code) REFERENCES LearningObjectives (objective_code)
);

-- Table for ProgramCourses
CREATE TABLE IF NOT EXISTS ProgramCourses
(
    program_id      INT,
    course_id       CHAR(8),
    PRIMARY KEY (program_id, course_id),
    FOREIGN KEY (program_id) REFERENCES Programs (program_id),
    FOREIGN KEY (course_id) REFERENCES Courses (course_id)
);

-- Table for ProgramObjectives

CREATE TABLE IF NOT EXISTS ProgramObjectives
(
    program_id         INT,
    course_id          CHAR(8),
    sub_objective_code     VARCHAR(20),
    objective_code         VARCHAR(20),
    PRIMARY KEY (program_id, course_id, sub_objective_code),
    FOREIGN KEY (program_id,course_id) REFERENCES ProgramCourses (program_id,course_id),
    FOREIGN KEY (sub_objective_code, objective_code) REFERENCES SubObjectives (sub_objective_code, objective_code)
);
-- Table for EvaluationResults // Change the section Number
CREATE TABLE IF NOT EXISTS EvaluationResults
(
    program_id         INT,
    course_id          CHAR(8),
    section_number     INT(3),
    semester          ENUM ('Fall','Spring','Summer') NOT NULL,
    objective_code     VARCHAR(20),
    sub_objective_code VARCHAR(20),
    evaluation_method  VARCHAR(50) NOT NULL,
    students_met       INT         NOT NULL,
    year              INT                             NOT NULL,

    PRIMARY KEY (program_id, course_id, section_number, objective_code),
    FOREIGN KEY (program_id,course_id,objective_code,sub_objective_code) REFERENCES ProgramObjectives (program_id,course_id,objective_code,sub_objective_code),
    FOREIGN KEY (section_number,semester,course_id, year) REFERENCES Sections (section_number,semester,course_id,year)

);

