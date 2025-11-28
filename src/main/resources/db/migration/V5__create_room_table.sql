CREATE TABLE room (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    name VARCHAR(50) NOT NULL,
    memo TEXT,

    start_date DATE NOT NULL,
    end_date DATE NOT NULL,

    owner_id BIGINT NOT NULL,

    created_at DATETIME NOT NULL,
    updated_at DATETIME NULL,

     CONSTRAINT fk_room_owner
        FOREIGN KEY (owner_id) REFERENCES member(id)
        ON DELETE CASCADE
);
