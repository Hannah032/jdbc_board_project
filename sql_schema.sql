-- ========================================================
-- Oracle SQL 스키마 생성 스크립트
-- ========================================================
-- 실행 순서:
-- 1. 아래의 모든 DROP 문을 실행하여 기존 테이블과 시퀀스를 삭제합니다.
--    (만약 객체가 존재하지 않아 오류가 발생해도 괜찮습니다.)
-- 2. 아래의 모든 CREATE 문을 실행하여 테이블과 시퀀스를 생성합니다.
-- ========================================================

-- ========================================================
-- 1. DROP (기존 객체 삭제)
-- ========================================================
-- 제약조건 비활성화 (참조 관계 때문에 삭제가 안될 경우)
ALTER TABLE likes DISABLE CONSTRAINT fk_likes_board;
ALTER TABLE likes DISABLE CONSTRAINT fk_likes_employee;
ALTER TABLE comments DISABLE CONSTRAINT fk_comments_board;

DROP TABLE likes;
DROP TABLE comments;
DROP TABLE board;
DROP TABLE employee;

DROP SEQUENCE SEQ_BOARD;
DROP SEQUENCE SEQ_COMMENT;
DROP SEQUENCE SEQ_EMPLOYEE;

-- ========================================================
-- 2. CREATE (신규 객체 생성)
-- ========================================================

-- 직원 정보 테이블
CREATE TABLE employee (
  bno NUMBER PRIMARY KEY,              -- 직원 번호 (PK)
  name VARCHAR2(100) NOT NULL,       -- 이름
  department VARCHAR2(100),          -- 부서
  bposition VARCHAR2(100),           -- 직급
  user_id VARCHAR2(100) NOT NULL UNIQUE, -- 로그인 아이디 (UNIQUE)
  password VARCHAR2(200) NOT NULL,     -- 로그인 비밀번호
  hire_date DATE DEFAULT SYSDATE,    -- 입사일자
  upd_date DATE DEFAULT SYSDATE      -- 정보 수정일자
);

 
-- 관리자 정보 생성
 INSERT INTO employee (bno, name, department, bposition, user_id, password, hire_date)
 VALUES (SEQ_EMPLOYEE.NEXTVAL, '관리자', '관리부서', '관리자', 'admin', '1234',
 SYSDATE);
 COMMIT;

-- 게시글 테이블
CREATE TABLE board (
  bno NUMBER PRIMARY KEY,              -- 게시글 번호 (PK)
  title VARCHAR2(200) NOT NULL,      -- 제목
  writer VARCHAR2(100) NOT NULL,     -- 작성자 (employee의 name과 동기화될 수 있음)
  content CLOB,                        -- 내용
  reg_date DATE DEFAULT SYSDATE,     -- 등록일자
  upd_date DATE DEFAULT SYSDATE      -- 수정일자
);

-- 댓글 테이블
CREATE TABLE comments (
  bno NUMBER PRIMARY KEY,              -- 댓글 번호 (PK)
  board_no NUMBER NOT NULL,            -- 원본 게시글 번호 (FK)
  writer VARCHAR2(100) NOT NULL,     -- 작성자
  content VARCHAR2(2000) NOT NULL,   -- 내용
  reg_date DATE DEFAULT SYSDATE,     -- 등록일자
  upd_date DATE DEFAULT SYSDATE      -- 수정일자
);

-- 좋아요 테이블
CREATE TABLE likes (
  board_no NUMBER NOT NULL,            -- 게시글 번호 (FK)
  emp_no NUMBER NOT NULL,              -- 좋아요 누른 직원 번호 (FK)
  PRIMARY KEY (board_no, emp_no)       -- 복합 기본 키 (한 사람이 한 게시글에 한 번만 좋아요 가능)
);


CREATE TABLE academic_schedule (
schedule_id NUMBER PRIMARY KEY,
title VARCHAR2(200) NOT NULL,
start_date DATE NOT NULL,
end_date DATE NOT NULL,
description CLOB
);

 INSERT INTO academic_schedule (schedule_id, title, start_date, end_date, description)
 VALUES (SEQ_ACADEMIC_SCHEDULE.NEXTVAL, '여름방학', TO_DATE('2025-07-21', 'YYYY-MM-DD'), TO_DATE(
 '2025-08-31', 'YYYY-MM-DD'), '여름방학 기간입니다.');


-- 시퀀스 생성
CREATE SEQUENCE SEQ_BOARD START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEQ_COMMENT START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEQ_EMPLOYEE START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEQ_ACADEMIC_SCHEDULE START WITH 1 INCREMENT BY 1;


-- 외래 키(Foreign Key) 제약조건 추가
ALTER TABLE comments ADD CONSTRAINT fk_comments_board
  FOREIGN KEY (board_no) REFERENCES board(bno)
  ON DELETE CASCADE; -- 게시글 삭제 시 관련 댓글도 모두 삭제

ALTER TABLE likes ADD CONSTRAINT fk_likes_board
  FOREIGN KEY (board_no) REFERENCES board(bno)
  ON DELETE CASCADE; -- 게시글 삭제 시 관련 좋아요도 모두 삭제

ALTER TABLE likes ADD CONSTRAINT fk_likes_employee
  FOREIGN KEY (emp_no) REFERENCES employee(bno)
  ON DELETE CASCADE; -- 직원 탈퇴 시 관련 좋아요도 모두 삭제

-- 커밋
COMMIT;
