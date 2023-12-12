-- Departments
INSERT IGNORE INTO university.departments (department_code, department_name)
VALUES ('COMP', 'Computer Science Department'),
       ('BSKW', 'Basket Weaving Department');


-- Insert data for Computer Science Department (Code: COMP)
-- Faculty Members
INSERT IGNORE INTO university.facultymembers (faculty_id, faculty_name, email_address, faculty_rank, department_code)
VALUES (1, 'John Smith', 'john.smith@comp.edu', 'full', 'COMP'),
       (2, 'Jane Doe', 'jane.doe@comp.edu', 'associate', 'COMP'),
       (3, 'Bob Johnson', 'bob.johnson@comp.edu', 'assistant', 'COMP'),
       (4, 'Alice Williams', 'alice.williams@comp.edu', 'full', 'COMP'),
       (5, 'Charlie Brown', 'charlie.brown@comp.edu', 'associate', 'COMP'),
       (6, 'Eva Davis', 'eva.davis@comp.edu', 'assistant', 'COMP'),
       (7, 'Frank Miller', 'frank.miller@comp.edu', 'full', 'COMP'),
       (8, 'Grace Taylor', 'grace.taylor@comp.edu', 'associate', 'COMP'),
       (9, 'Henry Wilson', 'henry.wilson@comp.edu', 'assistant', 'COMP'),
       (10, 'Ivy White', 'ivy.white@comp.edu', 'full', 'COMP'),
       (11, 'Jack Thomas', 'jack.thomas@comp.edu', 'associate', 'COMP'),
       (12, 'Kelly Adams', 'kelly.adams@comp.edu', 'assistant', 'COMP');

-- Courses
INSERT IGNORE INTO university.courses (course_id, course_title, course_description, department_code)
VALUES ('COMP1000', 'CS 1', 'Introduction to Computer Science', 'COMP'),
       ('COMP1100', 'CS 2', 'Programming Fundamentals', 'COMP'),
       ('COMP1200', 'CS 3', 'Advanced Programming', 'COMP'),
       ('COMP2000', 'Data Structures', 'Study of data structures', 'COMP'),
       ('COMP3000', 'Algorithm', 'Advanced algorithms and problem-solving', 'COMP');

-- Sections
INSERT IGNORE INTO university.sections (section_number, course_id, semester, faculty_id, enrolled_students, year)
VALUES (001, 'COMP1000', 'Spring', 1, 30, 2023),
       (002, 'COMP1000', 'Fall', 2, 55, 2023),
       (101, 'COMP1100', 'Spring', 3, 52, 2023),
       (102, 'COMP1100', 'Fall', 4, 44, 2023),
       (201, 'COMP1200', 'Spring', 5, 50, 2023),
       (202, 'COMP1200', 'Fall', 6, 35, 2023),
       (301, 'COMP2000', 'Spring', 7, 40, 2023),
       (302, 'COMP2000', 'Fall', 8, 32, 2023),
       (401, 'COMP3000', 'Spring', 9, 62, 2023),
       (402, 'COMP3000', 'Fall', 10, 42, 2023),
       (403, 'COMP3000', 'Spring', 1, 30, 2024);

-- Programs
INSERT IGNORE INTO university.programs (program_name, department_code, head_of_program_id)
VALUES ('BS in Computer Science', 'COMP', 1);

-- Insert data for Basket Weaving Department (Code: BSKW)
-- Faculty Members
INSERT IGNORE INTO university.facultymembers (faculty_id, faculty_name, email_address, faculty_rank, department_code)
VALUES (13, 'Olivia Green', 'olivia.green@bskw.edu', 'full', 'BSKW'),
       (14, 'Paul Brown', 'paul.brown@bskw.edu', 'associate', 'BSKW'),
       (15, 'Quincy Smith', 'quincy.smith@bskw.edu', 'assistant', 'BSKW'),
       (16, 'Rachel Davis', 'rachel.davis@bskw.edu', 'full', 'BSKW'),
       (17, 'Sam Wilson', 'sam.wilson@bskw.edu', 'associate', 'BSKW'),
       (18, 'Tina Miller', 'tina.miller@bskw.edu', 'assistant', 'BSKW');

-- Courses
INSERT IGNORE INTO university.courses (course_id, course_title, course_description, department_code)
VALUES ('BSKW1000', 'Basics of Basket Weaving', 'Introduction to basket weaving techniques', 'BSKW'),
       ('BSKW1010', 'Baskets of the World', 'Exploration of basket weaving styles worldwide', 'BSKW'),
       ('BSKW2020', 'History of Basket Weaving', 'Historical perspectives on basket weaving', 'BSKW'),
       ('BSKW3000', 'Newtonian Physics for Basket Weaving', 'Physics principles applied to basket weaving', 'BSKW');

-- Sections
INSERT IGNORE INTO university.sections (section_number, course_id, semester, faculty_id, enrolled_students, year)
VALUES (001, 'BSKW1000', 'Spring', 13, 50, 2023),
       (002, 'BSKW1000', 'Summer', 14, 40, 2023),
       (003, 'BSKW1000', 'Fall', 15, 30, 2023),
       (101, 'BSKW1010', 'Spring', 16, 65, 2023),
       (102, 'BSKW1010', 'Summer', 17, 55, 2023),
       (103, 'BSKW1010', 'Fall', 18, 43, 2023),
       (201, 'BSKW2020', 'Spring', 13, 39, 2023),
       (301, 'BSKW3000', 'Fall', 14, 51, 2023);

-- Programs
INSERT IGNORE INTO university.programs (program_name, department_code, head_of_program_id)
VALUES ('BA in Basket Weaving', 'BSKW', 13);


-- LearningObjectives
INSERT IGNORE INTO university.learningobjectives (objective_code, program_id)
VALUES ('OBJ_CS1', 1),
       ('OBJ_CS2', 1),
       ('OBJ_CS3', 1),
       ('OBJ_DS', 1),
       ('OBJ_ALGO', 1),
       ('OBJ_BSKW1', 2),
       ('OBJ_BSKW2', 2),
       ('OBJ_BSKW3', 2),
       ('OBJ_BSKW4', 2);

-- SubObjectives
INSERT IGNORE INTO university.subobjectives (sub_objective_code, objective_code, description)
VALUES ('SUBOBJ1', 'OBJ_CS1', 'Understand basic programming concepts'),
       ('SUBOBJ2', 'OBJ_CS2', 'Develop proficiency in programming fundamentals'),
       ('SUBOBJ3', 'OBJ_CS3', 'Master advanced programming techniques'),
       ('SUBOBJ4', 'OBJ_DS', 'Implement and analyze data structures'),
       ('SUBOBJ5', 'OBJ_ALGO', 'Design and analyze algorithms'),
       ('SUBOBJ6', 'OBJ_BSKW1', 'Master basic basket weaving techniques'),
       ('SUBOBJ7', 'OBJ_BSKW2', 'Explore diverse basket weaving styles worldwide'),
       ('SUBOBJ8', 'OBJ_BSKW3', 'Understand the historical context of basket weaving'),
       ('SUBOBJ9', 'OBJ_BSKW4', 'Apply Newtonian physics principles to basket weaving');

-- ProgramCourses
INSERT IGNORE INTO university.programcourses (program_id, course_id)
VALUES (1, 'COMP1000'),
       (1, 'COMP1100'),
       (1, 'COMP1200'),
       (1, 'COMP2000'),
       (1, 'COMP3000'),
       (2, 'BSKW1000'),
       (2, 'BSKW1010'),
       (2, 'BSKW2020'),
       (2, 'BSKW3000');

-- ProgramObjectives
INSERT IGNORE INTO university.programobjectives (program_id, course_id, sub_objective_code, objective_code)
VALUES (1, 'COMP1000', 'SUBOBJ1', 'OBJ_CS1'),
       (1, 'COMP1100', 'SUBOBJ2', 'OBJ_CS2'),
       (1, 'COMP1200', 'SUBOBJ3', 'OBJ_CS3'),
       (1, 'COMP2000', 'SUBOBJ4', 'OBJ_DS'),
       (1, 'COMP3000', 'SUBOBJ5', 'OBJ_ALGO'),
       (2, 'BSKW1000', 'SUBOBJ6', 'OBJ_BSKW1'),
       (2, 'BSKW1010', 'SUBOBJ7', 'OBJ_BSKW2'),
       (2, 'BSKW2020', 'SUBOBJ8', 'OBJ_BSKW3'),
       (2, 'BSKW3000', 'SUBOBJ9', 'OBJ_BSKW4');


-- EvaluationResults
INSERT IGNORE INTO university.evaluationresults (section_course_id, program_course_id, section_number, program_id, semester,
                                          sub_objective_code, evaluation_method, students_met, year)
VALUES ('COMP1000', 'COMP1000', 001, 1, 'Spring', 'SUBOBJ1', 'Exam', 25, 2023),
       ('COMP1000', 'COMP1000', 002, 1, 'Fall', 'SUBOBJ1', 'Project', 20, 2023),
       ('COMP1100', 'COMP1100', 101, 1, 'Spring', 'SUBOBJ2', 'Quiz', 18, 2023),
       ('COMP1100', 'COMP1100', 102, 1, 'Fall', 'SUBOBJ2', 'Exam', 22, 2023),
       ('COMP1200', 'COMP1200', 201, 1, 'Spring', 'SUBOBJ3', 'Project', 25, 2023),
       ('COMP1200', 'COMP1200', 202, 1, 'Fall', 'SUBOBJ3', 'Quiz', 28, 2023),
       ('COMP2000', 'COMP2000', 301, 1, 'Spring', 'SUBOBJ4', 'Exam', 30, 2023),
       ('COMP2000', 'COMP2000', 302, 1, 'Fall', 'SUBOBJ4', 'Project', 26, 2023),
       ('COMP3000', 'COMP3000', 401, 1, 'Spring', 'SUBOBJ5', 'Quiz', 24, 2023),
       ('COMP3000', 'COMP3000', 402, 1, 'Fall', 'SUBOBJ5', 'Exam', 20, 2023),
       ('COMP3000', 'COMP3000', 403, 1, 'Spring', 'SUBOBJ5', 'Exam', 30, 2024),
       ('BSKW1000', 'BSKW1000', 001, 2, 'Spring', 'SUBOBJ6', 'Project', 22, 2023),
       ('BSKW1000', 'BSKW1000', 002, 2, 'Summer', 'SUBOBJ6', 'Quiz', 18, 2023),
       ('BSKW1000', 'BSKW1000', 003, 2, 'Fall', 'SUBOBJ6', 'Exam', 25, 2023),
       ('BSKW1010', 'BSKW1010', 101, 2, 'Spring', 'SUBOBJ7', 'Project', 28, 2023),
       ('BSKW1010', 'BSKW1010', 102, 2, 'Summer', 'SUBOBJ7', 'Quiz', 30, 2023),
       ('BSKW1010', 'BSKW1010', 103, 2, 'Fall', 'SUBOBJ7', 'Exam', 26, 2023),
       ('BSKW2020', 'BSKW2020', 201, 2, 'Spring', 'SUBOBJ8', 'Quiz', 24, 2023),
       ('BSKW3000', 'BSKW3000', 301, 2, 'Fall', 'SUBOBJ9', 'Exam', 20, 2023);

