CREATE TABLE chat_message (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    room_id BIGINT NOT NULL,
    sender_id BIGINT NOT NULL,

    content VARCHAR(1000) NOT NULL,

    created_at DATETIME NOT NULL,
    updated_at DATETIME NULL,

    CONSTRAINT fk_chat_message_room
        FOREIGN KEY (room_id) REFERENCES room(id)
            ON DELETE CASCADE,

    CONSTRAINT fk_chat_message_member
        FOREIGN KEY (sender_id) REFERENCES member(id)
            ON DELETE CASCADE
);

CREATE INDEX idx_chat_message_room_created_at
    ON chat_message (room_id, created_at);
