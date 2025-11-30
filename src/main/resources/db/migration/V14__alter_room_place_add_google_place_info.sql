ALTER TABLE room_place
    ADD COLUMN google_place_id VARCHAR(255) NULL AFTER address,
    ADD COLUMN latitude DECIMAL(10,7) NULL AFTER google_place_id,
    ADD COLUMN longitude DECIMAL(10,7) NULL AFTER latitude,
    ADD COLUMN formatted_address VARCHAR(255) NULL AFTER longitude;

CREATE INDEX idx_room_place_google_place_id
    ON room_place (google_place_id);
