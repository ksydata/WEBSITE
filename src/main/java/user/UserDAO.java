package main.java.user;

import main.java.util.DatabaseUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;


// Data Access Object: util() 활용
public class UserDAO {
	public int join(String userID, String userPassword) {
		String SQL = "INSERT INTO USER VALUES (?, ?)";
		// try-with-resources문으로 자원을 자동으로 닫아주는 기능 활용
	    try (Connection connection = DatabaseUtil.getConnection();
	         PreparedStatement statement = connection.prepareStatement(SQL)) {
	    		statement.setString(1, userID);
	    		statement.setString(2, userPassword);
			
	    		return statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}