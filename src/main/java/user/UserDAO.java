package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import util.DatabaseUtil;

/* 로그인 인증 결과(로직과 메시지를 분리하여 유지보수의 편의성 확보)
  1 : 로그인 / 회원가입 등 작업 성공
  0 : 비밀번호 불일치
 -1 : 아이디 없음
 -2 : 데이터베이스 오류
  2 : 이미 존재하는 아이디와 중복값
*/

// Data Access Object: util() 활용
public class UserDAO {
    // 회원가입
    public int join(String userID, String userPassword, String email, String name, String phoneNumber, String officeNumber,
            String role, String address, String residentNumberFront, String residentNumberBack, String gender,
            String college, String major, int admissionYear, String status) {
        
        // 이미 해당 아이디가 존재하는지 확인하는 SQL 쿼리
        String checkSQL = "SELECT userID FROM USER WHERE userID = ?";
        
        // 회원가입 SQL 쿼리
        // USER 테이블에 사용자 정보를 삽입하는 쿼리
        String insertUserSQL = "INSERT INTO USER (userID, userPassword, email, name, phoneNumber, officeNumber, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        // PERSONAL_INFO 테이블에 사용자 개인 정보를 삽입하는 쿼리
        String insertPersonalInfoSQL = "INSERT INTO PERSONAL_INFO (userID, address, birthDate, gender, residentNumber, college, major, admissionYear, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        // ACADEMIC_RECORD 테이블에 학사 정보를 삽입하는 쿼리
        String insertAcademicRecordSQL = "INSERT INTO ACADEMIC_RECORD (userID, college, major) VALUES (?, ?, ?)";
        
        // DB 테이블 연결 객체 초기화
        PreparedStatement userStatement = null;
        PreparedStatement personalInfoStatement = null;
        PreparedStatement academicRecordStatement = null;
        Connection connection = null; 
        	// connection 변수 선언 사유: try-with-resources 구문에서 접근할 수 없는 범위에서 객체가 선언되면, 에러 메시지 반환하기 어려움 

        // DB 연결 및 쿼리 실행
        try {
        	// connection 초기화
            connection = DatabaseUtil.getConnection(); 
            // 아이디 중복 체크
            connection.setAutoCommit(false);
            // 자동 커밋 모드 비활성화: 해당 모드가 활성화된 상태에서 commit()이나 rollback()을 호출하려고 하면 문제 발생
            try (PreparedStatement checkStatement = connection.prepareStatement(checkSQL)) {

                checkStatement.setString(1, userID);
                ResultSet resultSet = checkStatement.executeQuery();

                if (resultSet.next()) {
                    // 이미 존재하는 중복된 아이디
                    return 2;
                }

                // 아이디가 없으면 회원가입 진행
                // USER 테이블에 데이터 삽입
                userStatement = connection.prepareStatement(insertUserSQL);
                userStatement.setString(1, userID);
                // 비밀번호는 원칙적으로 평문이 아닌 해시(일방향) 암호화하여 저장하여야 함
                // e.g. MessageDigest.getInstance("SHA-256") password.getBytes() messageDigest.digest()
                userStatement.setString(2, userPassword);
                userStatement.setString(3, email);
                userStatement.setString(4, name);
                userStatement.setString(5, phoneNumber);
                userStatement.setString(6, officeNumber);
                userStatement.setString(7, role);
                userStatement.executeUpdate();

                // PERSONAL_INFO 테이블에 데이터 삽입
                personalInfoStatement = connection.prepareStatement(insertPersonalInfoSQL);
                personalInfoStatement.setString(1, userID);
                personalInfoStatement.setString(2, address);
                // 생년월일은 주민등록번호 앞 6자리 별도로 저장
                personalInfoStatement.setString(3, residentNumberFront);  
                personalInfoStatement.setString(4, gender);
                // 나누어 입력받은 두 값을 하나로 합쳐서 주민등록번호 형식으로 저장 (e.g. "123456-1234567")
                // 주민등록번호 뒷자리는 원칙적으로 평문이 아닌 해시(일방향) 암호화하여 저장하여야 함
                personalInfoStatement.setString(5, residentNumberFront + "-" + residentNumberBack);
                personalInfoStatement.setString(6, college);
                personalInfoStatement.setString(7, major);
                // role별(교수/교직원/관리자)로 분기 처리(admissionYear 개념이 없으므로, 값을 NULL 또는 0으로 저장)
                personalInfoStatement.setInt(8, admissionYear);
                personalInfoStatement.setString(9, status);
                personalInfoStatement.executeUpdate();

                // ACADEMIC_RECORD 테이블에 학사정보 삽입
                academicRecordStatement = connection.prepareStatement(insertAcademicRecordSQL);
                academicRecordStatement.setString(1, userID);
                academicRecordStatement.setString(2, college);
                academicRecordStatement.setString(3, major);
                academicRecordStatement.executeUpdate();

                // 모든 작업이 성공적으로 끝나면 커밋
                connection.commit();
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (connection != null) {
                    // 예외 발생 시 롤백
                    connection.rollback();
                }
            } catch (Exception rollbackException) {
                rollbackException.printStackTrace();
            }
            // 데이터베이스 오류 발생 시
            return -2;
        } finally {
            // 자원 해제
            try {
                if (userStatement != null) userStatement.close();
                if (personalInfoStatement != null) personalInfoStatement.close();
                if (academicRecordStatement != null) academicRecordStatement.close();
                // 자동 커밋 모드 복구
                if (connection != null) connection.setAutoCommit(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 로그인 인증
    public int login(String userID, String userPassword) {
        // 로그인 SQL 쿼리
        String SQL = "SELECT userPassword FROM USER WHERE userID = ?";
        
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            
            statement.setString(1, userID);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                String storedPassword = resultSet.getString("userPassword");
                if (storedPassword.equals(userPassword)) {
                    // 아이디, 비밀번호 모두 일치하여 로그인 성공
                    return 1;
                } else {
                    // 비밀번호 불일치하여 인증 오류발생
                    return 0;
                }
            } else {
                // DB에서 아이디가 조회되지 않아 인증 오류발생
                return -1;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            // 데이터베이스 오류 발생
            return -2;
        }
    }
}

			
/*
[UserDAO.java를 컴파일해서 UserDAO.class를 WEB-INF/classes/user/에 직접 복사]
1. 
cd C:\WEBSITE\WEBSITE\src\main\java
2. 컴파일 명령어
javac -cp ".;C:\Users\sooyeon Kang\.m2\repository\org\jasypt\jasypt\1.9.3\jasypt-1.9.3.jar" -d C:\WEBSITE\WEBSITE\webapp\WEB-INF\classes user\UserDAO.java util\DatabaseUtil.java
*/

/*
[초기 소스코드]
	// 회원가입
	public int join(String userID, String userPassword) {
		String SQL = "INSERT INTO USER VALUES (?, ?)";
		
		// try-with-resources문으로 자원을 자동으로 닫아주는 기능 활용
		try (Connection connection = DatabaseUtil.getConnection();
			 PreparedStatement statement = connection.prepareStatement(SQL)) {
			 
			 // SQL insert 문에 아이디, 비밀번호 추가
			 statement.setString(1, userID);
			 statement.setString(2, userPassword);
			 
			 // SQL insert 문을 실행한 결과를 반환
			 return statement.executeUpdate();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}
*/