CREATE TABLE refresh_token (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    token VARCHAR(512) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NULL,
    CONSTRAINT fk_refresh_token_member
        FOREIGN KEY (member_id) REFERENCES member(id)
            ON DELETE CASCADE,
    CONSTRAINT uk_refresh_token_member
        UNIQUE (member_id)
);
