-- 1) login_id 컬럼 추가 (기존 데이터 때문에 일단 NULL 허용으로 추가)
ALTER TABLE member
    ADD COLUMN login_id VARCHAR(50) NULL AFTER id;

-- 2) 기존 회원들의 login_id를 email 기준으로 초기화
UPDATE member
SET login_id = email
WHERE login_id IS NULL;

-- 3) login_id NOT NULL + 고유 제약조건 부여
ALTER TABLE member
    MODIFY COLUMN login_id VARCHAR(50) NOT NULL;

CREATE UNIQUE INDEX ux_member_login_id
    ON member (login_id);
