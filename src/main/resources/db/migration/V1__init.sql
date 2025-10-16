CREATE TABLE IF NOT EXISTS sample_migration_check (
                                                      id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                                      note VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );
