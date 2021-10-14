CREATE TABLE uphill.vehicles (
    id INT(255) NOT NULL AUTO_INCREMENT,
    manufacturer VARCHAR(50) NOT NULL,
    model  VARCHAR(25) NOT NULL,
    year YEAR NOT NULL,
    consumption  FLOAT(24),
    PRIMARY KEY (id)
);