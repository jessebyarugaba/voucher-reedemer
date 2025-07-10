CREATE TABLE voucher (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(255),
    used BOOLEAN,
    package_type VARCHAR(255),
    expiration_date DATE
);