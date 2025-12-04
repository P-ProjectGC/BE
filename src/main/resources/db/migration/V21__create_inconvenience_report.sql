CREATE TABLE inconvenience_report (
                                      id          BIGINT      NOT NULL AUTO_INCREMENT,
                                      member_id   BIGINT      NOT NULL,
                                      content     TEXT        NOT NULL,
                                      created_at  DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
                                      updated_at  DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
                                      PRIMARY KEY (id),
                                      CONSTRAINT fk_inconvenience_report_member
                                          FOREIGN KEY (member_id) REFERENCES member (id)
);
