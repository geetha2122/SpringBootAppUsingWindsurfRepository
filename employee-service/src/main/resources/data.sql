-- Sample data for Employee Service

-- Insert sample employees
INSERT INTO employees (first_name, last_name, email, phone_number, department_id, position, hire_date, salary, is_active, created_at) VALUES
('John', 'Doe', 'john.doe@company.com', '123-456-7890', 1, 'Software Engineer', '2023-01-15', 75000.00, true, '2023-01-15'),
('Jane', 'Smith', 'jane.smith@company.com', '234-567-8901', 1, 'Senior Software Engineer', '2022-03-20', 95000.00, true, '2022-03-20'),
('Michael', 'Johnson', 'michael.johnson@company.com', '345-678-9012', 2, 'Product Manager', '2022-06-10', 85000.00, true, '2022-06-10'),
('Emily', 'Brown', 'emily.brown@company.com', '456-789-0123', 2, 'UX Designer', '2023-02-28', 70000.00, true, '2023-02-28'),
('David', 'Wilson', 'david.wilson@company.com', '567-890-1234', 3, 'DevOps Engineer', '2022-11-15', 80000.00, true, '2022-11-15'),
('Sarah', 'Davis', 'sarah.davis@company.com', '678-901-2345', 3, 'QA Engineer', '2023-04-10', 65000.00, true, '2023-04-10');
