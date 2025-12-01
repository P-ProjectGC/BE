CREATE TABLE notification (
                              id BIGINT NOT NULL AUTO_INCREMENT,
                              member_id BIGINT NOT NULL,
                              type ENUM('trip_reminder', 'friend_request') NOT NULL,
                              title VARCHAR(100) NOT NULL,
                              message VARCHAR(255) NOT NULL,
                              related_room_id BIGINT NULL,
                              related_member_id BIGINT NULL,
                              created_at DATETIME(6) NOT NULL,
                              updated_at DATETIME(6) NOT NULL,
                              PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;