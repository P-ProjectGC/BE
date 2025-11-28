CREATE TABLE wishlist_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    room_id BIGINT NOT NULL,

    place_name VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    category VARCHAR(255),
    memo TEXT,

    day_index INT,
    order_index INT,

    created_at DATETIME NOT NULL,
    updated_at DATETIME NULL,

    CONSTRAINT fk_wishlist_room
        FOREIGN KEY (room_id) REFERENCES room(id)
            ON DELETE CASCADE
);