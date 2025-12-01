CREATE TABLE notification_setting (
                                      member_id BIGINT NOT NULL,
                                      all_chat_room_enabled TINYINT(1) NOT NULL DEFAULT 1,
                                      trip_reminder_enabled TINYINT(1) NOT NULL DEFAULT 1,
                                      friend_request_enabled TINYINT(1) NOT NULL DEFAULT 1,

                                      created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
                                      updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),

                                      CONSTRAINT pk_notification_setting PRIMARY KEY (member_id),
                                      CONSTRAINT fk_notification_setting_member
                                          FOREIGN KEY (member_id) REFERENCES member(id)
                                              ON DELETE CASCADE
);