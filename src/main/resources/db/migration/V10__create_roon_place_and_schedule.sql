CREATE TABLE room_place (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    room_id      BIGINT      NOT NULL,
    name         VARCHAR(100) NOT NULL,
    address      VARCHAR(255) NULL,
    created_by_member_id BIGINT NOT NULL,
    like_count   INT DEFAULT 0,
    deleted      TINYINT(1) DEFAULT 0,
    created_at   DATETIME NOT NULL,
    updated_at   DATETIME NOT NULL,
    CONSTRAINT fk_room_place_room
        FOREIGN KEY (room_id) REFERENCES room (id)
);
