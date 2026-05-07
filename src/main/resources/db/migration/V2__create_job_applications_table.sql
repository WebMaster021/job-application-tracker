CREATE TABLE job_applications (
    id UUID PRIMARY KEY NOT NULL,
    user_id UUID NOT NULL,
    company_name VARCHAR(100) NOT NULL,
    position VARCHAR(100) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'APPLIED',
    applied_date DATE NOT NULL,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    interview_date DATE,
    salary_offered DECIMAL(10, 2)
);