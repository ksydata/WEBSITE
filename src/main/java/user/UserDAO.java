package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import util.DatabaseUtil;

/* 로그인 인증 결과(로직과 메시지를 분리하여 유지보수의 편의성 확보)
  1 : 로그인 성공
  0 : 비밀번호 불일치
 -1 : 아이디 없음
 -2 : 데이터베이스 오류
*/

// Data Access Object: util() 활용
public class UserDAO {
	// 회원가입
	public int join(String userID, String userPassword) {
	    // 이미 해당 아이디가 존재하는지 확인하는 SQL 쿼리
	    String checkSQL = "SELECT userID FROM USER WHERE userID = ?";
	    // 회원가입 SQL 쿼리
	    String insertSQL = "INSERT INTO USER VALUES (?, ?)";

	    try (Connection connection = DatabaseUtil.getConnection();
	         PreparedStatement checkStatement = connection.prepareStatement(checkSQL)) {

	        checkStatement.setString(1, userID);
	        ResultSet resultSet = checkStatement.executeQuery();

	        if (resultSet.next()) {
	            // 이미 존재하는 중복된 아이디
	            return 2;
	        }

	        // 아이디가 없으면 회원가입 진행
	        try (PreparedStatement insertStatement = connection.prepareStatement(insertSQL)) {
	            insertStatement.setString(1, userID);
	            insertStatement.setString(2, userPassword);
	            insertStatement.executeUpdate();
	            // 회원가입
	            return 1;
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
			// 데이터베이스 오류 발생	        
	        return -2;
	    }
	}
	
	// 로그인 인증
	public int login(String userID, String userPassword) {
	    // 로그인 SQL 쿼리
		String SQL = "SELECT userPassword FROM USER WHERE userID = ?";
		
		try (Connection connection = DatabaseUtil.getConnection();
			 PreparedStatement statement = connection.prepareStatement(SQL)) {
			statement.setString(1,  userID);
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				String storedPassword = resultSet.getString("userPassword");
				if (storedPassword.equals(userPassword) ) {
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