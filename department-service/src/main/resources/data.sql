-- Sample data for Department Service

-- Insert sample departments
INSERT INTO departments (name, description, code, manager_name, manager_email, location, budget, is_active, created_at) VALUES
('Engineering', 'Software development and technology team', 'ENG', 'John Smith', 'john.smith@company.com', 'Building A, Floor 3', 500000.00, true, '2023-01-01'),
('Product', 'Product management and strategy team', 'PROD', 'Jane Johnson', 'jane.johnson@company.com', 'Building A, Floor 2', 300000.00, true, '2023-01-01'),
('Operations', 'IT operations and infrastructure team', 'OPS', 'Michael Brown', 'michael.brown@company.com', 'Building B, Floor 1', 400000.00, true, '2023-01-01'),
('Human Resources', 'HR and people operations team', 'HR', 'Emily Davis', 'emily.davis@company.com', 'Building A, Floor 4', 200000.00, true, '2023-01-01'),
('Marketing', 'Marketing and communications team', 'MKTG', 'David Wilson', 'david.wilson@company.com', 'Building B, Floor 2', 250000.00, true, '2023-01-01');
