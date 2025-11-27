-- login_id 를 일반 로그인 전용 필드로 사용하기 위해
-- 카카오 전용 회원에 대해서는 NULL 을 허용한다.

ALTER TABLE member
    MODIFY COLUMN login_id VARCHAR(50) NULL;
