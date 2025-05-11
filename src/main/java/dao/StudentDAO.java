package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dto.StudentDTO;
import util.DatabaseUtil;

// 사용자 로그인 시 아이디로 받아온 학번/사번(userID)으로 DB에 접근하여 학생 1명의 정보를 StudentDTO에 담아 반환
public class StudentDAO {
	public StudentDTO getMyInfo(String userID) {
		// 학번/사번(userID)으로 나의 개인정보 페이지에서 조회 또는 수정할 정보 불러오는 SQL 쿼리 
		String userInfoSQL = "SELECT * FROM USER WHERE userID = ?";
		String personalInfoSQL = "SELECT * FROM PERSONAL_INFO WHERE userID = ?";

		// 로그인 성공했을 때 사용자 정보 활용
    	StudentDTO student = new StudentDTO();
    	
    	// USER 테이블에서 로그인 인증 관련 정보 추출
		try (Connection connection = DatabaseUtil.getConnection();
			 PreparedStatement userInfoStatement = connection.prepareStatement(userInfoSQL)) {	
			// 쿼리(where절 userID = ?)에 학번 포함
			userInfoStatement.setString(1, userID);
			
			try (ResultSet resultSet = userInfoStatement.executeQuery()) {
	            if (resultSet.next()) {
	                student.setUserID(resultSet.getString("userID"));
	                student.setUserPassword(resultSet.getString("userPassword"));
	                student.setName(resultSet.getString("name"));
	                student.setResidentNumber(resultSet.getString("residentNumber"));
	                student.setPhoneNumber(resultSet.getString("phoneNumber"));
	                student.setEmail(resultSet.getString("email"));
	            }
	        }				

		} catch (Exception e) {
			e.printStackTrace();
			// 데이터베이스 오류 발생
			return null;
		}
		
		// PERSONAL_INFO 테이블에서 개인정보, 학사정보 추출
		try (Connection connection = DatabaseUtil.getConnection();
			 PreparedStatement personalInfoStatement = connection.prepareStatement(personalInfoSQL)) {
			// 쿼리(where절 userID = ?)에 학번 포함
	        personalInfoStatement.setString(1, userID);
	        
	        try (ResultSet resultSet = personalInfoStatement.executeQuery()) {
	            if (resultSet.next()) {
	                student.setCollege(resultSet.getString("college"));
	                student.setMajor(resultSet.getString("major"));
	                student.setAdmissionYear(resultSet.getInt("admissionYear"));
	                student.setStatus(resultSet.getString("status"));
	                student.setAddress(resultSet.getString("address"));
	            }
	        }
			
		} catch (Exception e) {
			e.printStackTrace();
			// 데이터베이스 오류 발생
			return null;
		}
		
    	// 학생 데이터 객체 반환
    	return student;
	}
}

/*
    if (resultSet.next()) {
        String storedPassword = resultSet.getString("userPassword");
       // 아이디, 비밀번호 모두 일치하여 로그인 성공
        if (storedPassword.equals(userPassword)) {
        	// 로그인 성공했을 때 사용자 정보 활용
        	UserDTO user = new UserDTO();
        	user.setUserID(userID);
        	// 쿼리 실행결과 객체에서 name, role값 추출
            user.setUserName(resultSet.getString("name"));
            user.setUserRole(resultSet.getString("role"));
            // UserDTO 객체를 생성하여 반환하는 형태
            return user;
            
        } else {
            // 비밀번호 불일치하여 인증 오류발생
            return null; // return 0;
        }
*/