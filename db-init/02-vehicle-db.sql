\connect vehicle_rental_db;

CREATE TYPE vehicle_status AS ENUM ('AVAILABLE', 'NOT_AVAILABLE');

CREATE TABLE vehicles (
                          id SERIAL PRIMARY KEY,
                          brand VARCHAR(50) NOT NULL,
                          model VARCHAR(50) NOT NULL,
                          year INT NOT NULL,
                          plate VARCHAR(20) UNIQUE NOT NULL,
                          status vehicle_status DEFAULT 'AVAILABLE',
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);