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
			 // SQL Insert문에 아이디, 비밀번호 추가
			 statement.setString(1, userID);
			 statement.setString(2, userPassword);
			 // SQL Insert문을 실행한 결과를 반환
			 return statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}