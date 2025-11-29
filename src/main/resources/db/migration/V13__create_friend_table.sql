CREATE TABLE friend (
    id BIGINT NOT NULL AUTO_INCREMENT,
    requester_id BIGINT NOT NULL,
    receiver_id BIGINT NOT NULL,
    status ENUM('requested', 'accepted') NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    PRIMARY KEY (id),
    CONSTRAINT fk_friend_requester
        FOREIGN KEY (requester_id) REFERENCES member (id),
    CONSTRAINT fk_friend_receiver
        FOREIGN KEY (receiver_id) REFERENCES member (id),
    CONSTRAINT uk_friend_requester_receiver
        UNIQUE (requester_id, receiver_id)
);
