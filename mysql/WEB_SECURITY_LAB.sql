USE WEB_SECURITY_LAB;

/*
1. USER 테이블 (10,000행)
	학생: 학번 S20240001~S20249500 (약 9,500명, 대부분 재학(70% 이상), 나머지는 휴학/졸업 등)
	교수: 사번 P10001~P10400 (약 400명, 대부분 재직(80% 이상))
	교직원: 사번 E20001~E20090 (약 90명, 대부분 재직(약 80% 이상))
	관리자: 사번 A30001 ~ A30005 (관리자는 5명, 전원 활성화)

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

-- MySQL의 옵션으로 인해 LOAD DATA INFILE 명령어가 실행되는 특정 디렉토리 조회
SHOW VARIABLES LIKE "secure_file_priv";

-- CSV 파일을 MySQL 서버로 업로드
-- 사용자 계정 테이블
LOAD DATA INFILE "C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/user_data.csv"
INTO TABLE USER 
FIELDS TERMINATED BY "," 
ENCLOSED BY '"' 
LINES TERMINATED BY "\n" 
IGNORE 1 ROWS 
(
  userID, 
  userPassword, 
  email, 
  name, 
  phoneNumber, 
  officeNumber, 
  role);

-- 개인정보 테이블
LOAD DATA INFILE "C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/personal_info_data.csv"
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
(
    userID,
    address,
    -- 임시 변수로 저장
    @birthDate,
    gender,
    @residentNumber,
    @college,
    @major,
    @admissionYear,
    @status
)
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
LOAD DATA INFILE "C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/notice_data.csv"
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
LOAD DATA INFILE "C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/academic_record_data.csv"
INTO TABLE ACADEMIC_RECORD 
FIELDS TERMINATED BY "," 
ENCLOSED BY '"' 
LINES TERMINATED BY "\n" 
IGNORE 1 ROWS 
(recordID, userID, college, major, academicYear, semester, courseID, courseName, courseType, 
 coursePF, @credit, @pass_or_fail, grade, gradePoint, retakeYear, retakeSemester, 
 retakeCourseID, enrollmentReason)
SET
    academicYear = CASE
        WHEN academicYear IS NULL OR academicYear = '' THEN NULL
        ELSE CAST(academicYear AS UNSIGNED)
    END,
    semester = CASE
        WHEN semester NOT IN ('1', '2', '여름', '겨울') THEN NULL
        ELSE semester
    END,
    coursePF = CASE
        WHEN @coursePF = 'TRUE' THEN 1
        WHEN @coursePF = 'FALSE' THEN 0
        ELSE NULL
    END,
    pass_or_fail = CASE
        WHEN @pass_or_fail = 'TRUE' THEN 1
        WHEN @pass_or_fail = 'FALSE' THEN 0
        ELSE NULL
    END,
    gradePoint = CASE
        WHEN gradePoint IS NULL OR gradePoint = '' THEN NULL
        ELSE CAST(gradePoint AS DECIMAL(3, 2))
    END,
    retakeYear = CASE
        WHEN retakeYear IS NULL OR retakeYear = '' THEN NULL
        ELSE CAST(retakeYear AS UNSIGNED)
    END,
    retakeSemester = CASE
        WHEN retakeSemester NOT IN ('1', '2', '여름', '겨울') THEN NULL
        ELSE retakeSemester
    END,
    retakeCourseID = CASE
        WHEN retakeCourseID IS NULL OR retakeCourseID = '' THEN NULL
        ELSE CAST(retakeCourseID AS UNSIGNED)
    END;

-- 테이블 조회
SELECT * FROM USER;
SELECT * FROM NOTICE;
SELECT * FROM PERSONAL_INFO;
SELECT * FROM ACADEMIC_RECORD;
