CREATE TABLE admin (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       login_id VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(200) NOT NULL,
                       name VARCHAR(50) NOT NULL,
                       created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
                       updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6)
);
