CREATE TABLE vehicles (
    id INT(255) NOT NULL,
    manufacturer VARCHAR(50) NOT NULL,
    model  VARCHAR(25) NOT NULL,
    year YEAR NOT NULL,
    consumption  FLOAT(24),
    PRIMARY KEY (id)
);