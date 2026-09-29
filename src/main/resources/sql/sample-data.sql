INSERT INTO students (full_name, email, phone, gender, date_of_birth, address, city, registration_date, status) VALUES
('Aarav Sharma', 'aarav.sharma@example.com', '+91 9876543210', 'Male', '2002-05-15', '12 Green Park', 'Delhi', '2024-01-05', 'Active'),
('Meera Iyer', 'meera.iyer@example.com', '+91 9876543211', 'Female', '2001-11-28', '45 Lotus Street', 'Bengaluru', '2024-01-08', 'Active'),
('Rohan Patel', 'rohan.patel@example.com', '+91 9876543212', 'Male', '2003-02-14', '78 River Road', 'Ahmedabad', '2024-01-10', 'Inactive'),
('Sonia Verma', 'sonia.verma@example.com', '+91 9876543213', 'Female', '2002-07-23', '18 Hill View', 'Jaipur', '2024-02-01', 'Active'),
('Vikram Singh', 'vikram.singh@example.com', '+91 9876543214', 'Male', '2001-09-09', '22 Rose Lane', 'Lucknow', '2024-02-05', 'Active'),
('Priya Nair', 'priya.nair@example.com', '+91 9876543215', 'Female', '2003-01-18', '9 Bay Avenue', 'Kochi', '2024-02-12', 'Inactive'),
('Kabir Das', 'kabir.das@example.com', '+91 9876543216', 'Male', '2002-03-22', '12 Orchard Rd', 'Kolkata', '2024-03-02', 'Active'),
('Ananya Rao', 'ananya.rao@example.com', '+91 9876543217', 'Female', '2001-08-30', '34 Cedar Court', 'Hyderabad', '2024-03-06', 'Active'),
('Nikhil Roy', 'nikhil.roy@example.com', '+91 9876543218', 'Male', '2002-12-07', '5 Sunset Avenue', 'Pune', '2024-03-15', 'Active'),
('Disha Shah', 'disha.shah@example.com', '+91 9876543219', 'Female', '2003-04-17', '88 Sunrise Colony', 'Mumbai', '2024-03-20', 'Inactive');

INSERT INTO courses (course_name, course_code, description, duration, instructor_name, course_fee, start_date, end_date, status) VALUES
('Java Programming', 'JAVA101', 'Learn core and advanced Java concepts with object-oriented principles and practical exercises.', '12 Weeks', 'Dr. Arjun Mehta', 2500.00, '2024-01-10', '2024-03-30', 'Active'),
('Database Management', 'DBMS201', 'Understand relational databases, SQL queries, normalization, and schema planning.', '10 Weeks', 'Prof. Ritu Sharma', 2200.00, '2024-01-15', '2024-03-25', 'Active'),
('Web Development', 'WEB301', 'Build responsive websites using HTML, CSS, JavaScript, and Bootstrap.', '14 Weeks', 'Ms. Nisha Kumar', 2800.00, '2024-02-01', '2024-05-12', 'Active'),
('Operating Systems', 'OS401', 'Study system architecture, process scheduling, memory management, and file systems.', '8 Weeks', 'Dr. Mohan Bhatia', 2000.00, '2024-02-10', '2024-04-10', 'Completed'),
('Software Engineering', 'SE501', 'Develop software using requirement analysis, UML, testing, and project lifecycle concepts.', '12 Weeks', 'Prof. Sandeep Rao', 2600.00, '2024-03-01', '2024-05-20', 'Active'),
('Data Structures', 'DS601', 'Explore arrays, linked lists, stacks, queues, trees, and algorithmic problem solving.', '10 Weeks', 'Dr. Kavita Sen', 2300.00, '2024-01-20', '2024-03-30', 'Completed');

INSERT INTO enrollments (student_id, course_id, enrollment_date, status) VALUES
(1, 1, '2024-01-12', 'Enrolled'),
(2, 2, '2024-01-18', 'Completed'),
(3, 3, '2024-02-05', 'Cancelled'),
(4, 1, '2024-02-10', 'Enrolled'),
(5, 4, '2024-02-15', 'Completed'),
(6, 5, '2024-03-01', 'Enrolled'),
(7, 3, '2024-03-08', 'Enrolled'),
(8, 6, '2024-03-12', 'Completed'),
(9, 2, '2024-03-16', 'Enrolled'),
(10, 5, '2024-03-22', 'Cancelled');
