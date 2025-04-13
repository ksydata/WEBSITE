package user;

import java.sql.Connection;
import java.sql.PreparedStatement;

import util.DatabaseUtil;

// Data Access Object: util() 활용
public class UserDAO {
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
}

/*
[UserDAO.java를 컴파일해서 UserDAO.class를 WEB-INF/classes/user/에 직접 복사]
1. 
cd C:\WEBSITE\WEBSITE\src\main\java
2. 컴파일 명령어
javac -cp ".;C:\Users\sooyeon Kang\.m2\repository\org\jasypt\jasypt\1.9.3\jasypt-1.9.3.jar" -d C:\WEBSITE\WEBSITE\webapp\WEB-INF\classes user\UserDAO.java util\DatabaseUtil.java
*/