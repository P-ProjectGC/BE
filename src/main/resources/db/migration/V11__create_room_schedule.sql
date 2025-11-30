CREATE TABLE room_schedule (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    room_id      BIGINT      NOT NULL,
    room_place_id BIGINT     NULL,
    day_index    INT         NOT NULL,
    start_time   TIME        NOT NULL,
    end_time     TIME        NOT NULL,
    memo         VARCHAR(255) NULL,
    sort_order   INT         NOT NULL,
    created_at   DATETIME    NOT NULL,
    updated_at   DATETIME    NOT NULL,
    CONSTRAINT fk_room_schedule_room
        FOREIGN KEY (room_id) REFERENCES room (id),
    CONSTRAINT fk_room_schedule_room_place
        FOREIGN KEY (room_place_id) REFERENCES room_place (id)
);