\connect operation_db;

CREATE TYPE operation_status AS ENUM (
    'ACTIVE',
    'CANCELLED'
);

CREATE TABLE operations (
                            id SERIAL PRIMARY KEY,
                            vehicle_id INT NOT NULL,
                            customer_name VARCHAR(100) NOT NULL,
                            status operation_status DEFAULT 'ACTIVE',
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);