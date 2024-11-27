-- User 테이블 삭제 (존재하는 경우)
DROP TABLE IF EXISTS users CASCADE;

-- User 테이블 생성
CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,                                -- 사용자 ID
                       user_name VARCHAR(50) NOT NULL,                          -- 사용자 이름
                       user_email VARCHAR(100) NOT NULL UNIQUE,                 -- 사용자 이메일 (고유)
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 생성 시간
                       updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP  -- 수정 시간
);

-- Post 테이블 삭제 (존재하는 경우)
DROP TABLE IF EXISTS posts CASCADE;

-- Post 테이블 생성
CREATE TABLE posts (
                       id BIGSERIAL PRIMARY KEY,                                -- 게시물 ID (자동 증가)
                       user_id BIGINT NOT NULL,                                 -- 사용자 ID (users 테이블 참조)
                       title VARCHAR(100) NOT NULL,                             -- 게시물 제목
                       content TEXT NOT NULL,                                   -- 게시물 내용
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 생성 시간
                       updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 수정 시간
                       CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);