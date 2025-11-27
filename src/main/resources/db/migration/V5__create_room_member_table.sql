CREATE TABLE room_member (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    room_id BIGINT NOT NULL,
    member_id BIGINT NOT NULL,

    role VARCHAR(20) NOT NULL,

    created_at DATETIME NOT NULL,
    updated_at DATETIME NULL,

    CONSTRAINT fk_room_member_room
        FOREIGN KEY (room_id) REFERENCES room(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_room_member_member
        FOREIGN KEY (member_id) REFERENCES member(id)
        ON DELETE CASCADE
);
