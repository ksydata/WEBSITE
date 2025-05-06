USE WEB_SECURITY_LAB;

/*
1. USER 테이블 (10,000행)
	학생: 학번 S20240001~S20249500 (약 9,500명, 대부분 재학(70% 이상), 나머지는 휴학/졸업 등)
	교수: 사번 P10001~P10400 (약 400명, 대부분 재직(80% 이상))
	교직원: 사번 E20001~E20090 (약 90명, 대부분 재직(약 80% 이상))
	관리자: 사번 A30001 ~ A30005 (관리자는 5명, 전원 활성화)
	
	비밀번호는 올바른 패스워드 작성규칙
	영문을 시작으로 영문, 숫자, 특수문자 중 2종류 이상을 조합하여 최소 10자리 이상 또는 3종류 이상을 조합하여 최소 8자리 이상의 길이로 구성

2. PERSONAL_INFO 테이블
	학생: "재학", "휴학", "수료", "졸업", "자퇴", "제적", "기타"
	교수: "재직", "휴직", "안식년", "퇴직", "기타"
	교직원: "재직", "휴직", "퇴직", "기타"
	관리자: "활성화", "비활성화"
	
3. NOTICE_INFO 테이블
	FK인 USER 테이블의 사번/학번 컬럼인 userID의 범위 내에서 구성
	
4. ACADEMIC_RECORD 테이블
	FK인 USER 테이블의 사번/학번 컬럼인 userID의 범위 내에서 구성
	또한, userID가 동일한 경우 USER 테이블의 데이터와 PERSONAL_INFO 테이블의 데이터와 
	내용이 연계되어야 함(정합성 문제)
	재수강 데이터도 생성해야 함
	
각 테이블 
USER는 학생 950명, 교수 40명, 교직원 9명, 관리자 5명으로 구성 
NOTICE는 글 100개만 구성해주고 글쓴 사람은 교수, 교직원, 관리자만으로 구성
ACADEMIC_RECORD는 학생 950명만으로 구성
PERSONAL_INFO는 학생 950명, 교수 40명, 교직원 9명만 구성
*/

TRUNCATE TABLE USER;
-- ALTER TABLE USER DROP userID;
-- ALTER TABLE USER CHANGE COLUMN userPassword asis_userPassword VARCHAR(100);
-- ALTER TABLE USER DROP asis_userPassword;

ALTER TABLE USER ADD (
	userID VARCHAR(100) PRIMARY KEY,
    userPassword VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    name VARCHAR(100) NOT NULL,
    phoneNumber VARCHAR(100) NOT NULL,
    officeNumber VARCHAR(100),
    role VARCHAR(20) NOT NULL
);


CREATE TABLE NOTICE (
    noticeID INT PRIMARY KEY AUTO_INCREMENT,
    userID VARCHAR(100),
    title VARCHAR(100) NOT NULL,
    contents VARCHAR(10000),
    createDate DATETIME NOT NULL,
    updateDate DATETIME,
    endDate DATETIME NOT NULL,
    permissionRole VARCHAR(20) NOT NULL,
    FOREIGN KEY (userID) REFERENCES USER(userID)
);

CREATE TABLE PERSONAL_INFO (
    personalInfoID INT PRIMARY KEY AUTO_INCREMENT,
    userID VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    birthDate DATE,
    gender VARCHAR(20),
    residentNumber VARCHAR(20),
    college VARCHAR(100),
    major VARCHAR(100),
    admissionYear YEAR,
    status VARCHAR(20) NOT NULL,
    FOREIGN KEY (userID) REFERENCES USER(userID)
);

CREATE TABLE ACADEMIC_RECORD (
    recordID INT PRIMARY KEY AUTO_INCREMENT,
    userID VARCHAR(100),
    college VARCHAR(100),
    major VARCHAR(100),
    academicYear YEAR,
    semester ENUM('1', '2', '여름', '겨울'),
    courseID INT,
    courseName VARCHAR(255),
    courseType ENUM('전공필수', '전공선택', '교양필수', '교양선택'),
    coursePF BOOLEAN, -- PF 과목 여부
    credit TINYINT, -- 과목 학점
    pass_or_fail BOOLEAN,
    grade VARCHAR(2),
    gradePoint DECIMAL(3, 2),
    retakeYear YEAR,
    retakeSemester ENUM('1', '2', '여름', '겨울'),
    retakeCourseID INT,
    enrollmentReason VARCHAR(255),
    FOREIGN KEY (userID) REFERENCES USER(userID)
);
ALTER TABLE ACADEMIC_RECORD MODIFY COLUMN retakeSemester VARCHAR(10);

-- MySQL의 옵션으로 인해 LOAD DATA INFILE 명령어가 실행되는 특정 디렉토리 조회
SHOW VARIABLES LIKE "secure_file_priv";

-- CSV 파일을 MySQL 서버로 업로드
-- 사용자 계정 테이블
LOAD DATA INFILE "C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/user.csv"
INTO TABLE USER 
FIELDS TERMINATED BY "," 
ENCLOSED BY '"' 
LINES TERMINATED BY "\n" 
IGNORE 1 ROWS 
(userID, userPassword, email, name, phoneNumber, officeNumber, role);

-- 개인정보 테이블
LOAD DATA INFILE "C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/personal_info.csv"
-- 데이터 삽입할 테이블 지정
INTO TABLE PERSONAL_INFO
-- CSV 파일의 각 열을 구분하는 구분자(쉼표)
FIELDS TERMINATED BY ","
-- 각 필드를 따옴표로 감쌈
ENCLOSED BY '"'
-- 각 행을 구분하는 줄바꿈 문자
LINES TERMINATED BY "\r\n"
-- 첫 번째 행(헤더)은 무시하고 데이터를 삽입
IGNORE 1 ROWS
-- 임시 변수로 저장
(userID, address, @birthDate, gender, @residentNumber, @college, @major, @admissionYear, @status)
SET
    birthDate = CASE
    			-- 생년월일이 NULL이거나 빈 문자열이면 NULL(빈값)로 처리
                   WHEN @birthDate IS NULL OR @birthDate = '' THEN NULL
                -- 생년월일이 있으면 '%Y-%m-%d' 형식으로 변환
                   ELSE STR_TO_DATE(@birthDate, '%Y-%m-%d') -- '%Y-%m-%d %H:%i:%s'
                END,
    -- 주민등록번호가 빈 문자열이면 NULL로 처리
    residentNumber = NULLIF(@residentNumber, ''),
    college = NULLIF(@college, ''),
    major = NULLIF(@major, ''),
    admissionYear = CASE
    				-- 입학연도가 NULL이거나 빈 문자열이면 NULL로 처리
                       WHEN @admissionYear IS NULL OR @admissionYear = '' THEN NULL
                    -- 입학연도 값이 있으면 숫자로 변환
                       ELSE CAST(@admissionYear AS UNSIGNED)
                    END,
    status = NULLIF(@status, '');

-- 공지사항(게시판) 테이블
LOAD DATA INFILE "C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/notice.csv"
INTO TABLE NOTICE 
FIELDS TERMINATED BY "," 
ENCLOSED BY '"' 
LINES TERMINATED BY "\n" 
IGNORE 1 ROWS 
(userID, title, contents, @createDate, @updateDate, @endDate, permissionRole)
SET
    createDate = CASE
                   WHEN @createDate IS NULL OR @createDate = '' THEN NULL
                   ELSE STR_TO_DATE(@createDate, '%Y-%m-%d %H:%i:%s')
                 END,
    updateDate = CASE
                   WHEN @updateDate IS NULL OR @updateDate = '' THEN NULL
                   ELSE STR_TO_DATE(@updateDate, '%Y-%m-%d %H:%i:%s')
                 END,
    endDate = CASE
                WHEN @endDate IS NULL OR @endDate = '' THEN NULL
                ELSE STR_TO_DATE(@endDate, '%Y-%m-%d %H:%i:%s')
              END;

-- 학사정보 테이블
LOAD DATA INFILE "C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/academic_record.csv"
INTO TABLE ACADEMIC_RECORD
FIELDS TERMINATED BY ","  
ENCLOSED BY '"'
LINES TERMINATED BY "\n"
IGNORE 1 ROWS
(
  userID, college, major, @academicYear, @semester, courseID, courseName, courseType, @coursePF,
  credit, @pass_or_fail, grade, @gradePoint, @retakeYear, @retakeSemester, @retakeCourseID, enrollmentReason
)
SET 
  academicYear = IF(@academicYear = '' OR @academicYear = 'NaN', NULL, CAST(@academicYear AS UNSIGNED)),
  semester = IF(@semester = '' OR @semester = 'NaN', NULL, @semester),
  coursePF = IF(@coursePF = '' OR @coursePF IS NULL, NULL, CAST(@coursePF AS UNSIGNED)),
  pass_or_fail = IF(@pass_or_fail = '' OR @pass_or_fail IS NULL, NULL, CAST(@pass_or_fail AS UNSIGNED)),
  gradePoint = IF(@gradePoint = '' OR @gradePoint = 'NaN', NULL, CAST(@gradePoint AS DECIMAL(3, 2))),
  retakeYear = IF(@retakeYear = '' OR @retakeYear = 'NaN', NULL, CAST(@retakeYear AS UNSIGNED)),
  retakeSemester = IF(@retakeSemester = '' OR @retakeSemester = 'NaN', NULL, @retakeSemester),
  retakeCourseID = IF(@retakeCourseID = '' OR @retakeCourseID = 'NaN', NULL, CAST(@retakeCourseID AS UNSIGNED));

-- 테이블 조회
SELECT * FROM USER;
SELECT * FROM NOTICE;
SELECT * FROM PERSONAL_INFO;
SELECT * FROM ACADEMIC_RECORD;

-- DCL 
SELECT college, major, COUNT(*) FROM ACADEMIC_RECORD GROUP BY college, major;
SELECT college, COUNT(*) FROM ACADEMIC_RECORD GROUP BY college;
SELECT major, COUNT(*) FROM ACADEMIC_RECORD GROUP BY major;

DESC PERSONAL_INFO;
SELECT PERSONAL_INFO.status, USER.role, COUNT(*) 
FROM PERSONAL_INFO
JOIN USER ON PERSONAL_INFO.userID = USER.userID
GROUP BY PERSONAL_INFO.status, USER.role;
