CREATE TABLE uphill.vehicles (
    id INT(255) NOT NULL AUTO_INCREMENT,
    manufacturer VARCHAR(50) NOT NULL,
    model  VARCHAR(25) NOT NULL,
    year YEAR NOT NULL,
    consumption  FLOAT(24),
    image VARCHAR(255),
    PRIMARY KEY (id)
);

-- CREATE TABLE uphill.images (
--     vehicle_id INT(255) NOT NULL AUTO_INCREMENT,
--     path VARCHAR(255) NOT NULL,
--     PRIMARY KEY (vehicle_id),
--     FOREIGN KEY (vehicle_id) REFERENCES uphill.vehicles (id)
-- );