package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.StudentDTO;
import util.DatabaseUtil;

// 사용자 로그인 시 아이디로 받아온 학번/사번(userID)으로 DB에 접근하여 학생 1명의 정보를 StudentDTO에 담아 반환
public class StudentDAO {
	public StudentDTO getMyInfo(String userID) {
		// 학번/사번(userID)으로 나의 개인정보 페이지에서 조회 또는 수정할 정보 불러오는 SQL 쿼리 
		String userInfoSQL = "SELECT * FROM USER WHERE userID = ?";
		String personalInfoSQL = "SELECT * FROM PERSONAL_INFO WHERE userID = ?";

		// 로그인 성공했을 때 사용자 정보 활용
    	StudentDTO student = null;
    	
    	// USER 테이블에서 로그인 인증 관련 정보 추출
		try (Connection connection = DatabaseUtil.getConnection();
			 PreparedStatement userInfoStatement = connection.prepareStatement(userInfoSQL)) {	
			// 쿼리(where절 userID = ?)에 학번 포함
			userInfoStatement.setString(1, userID);
			
			try (ResultSet resultSet = userInfoStatement.executeQuery()) {
	            if (resultSet.next()) {
	            	// 사용자 정보가 존재할 경우 StudentDTO 객체 생성
	            	student = new StudentDTO();
	                student.setUserID(resultSet.getString("userID"));
	                student.setUserPassword(resultSet.getString("userPassword"));
	                student.setName(resultSet.getString("name"));
	                student.setPhoneNumber(resultSet.getString("phoneNumber"));
	                student.setEmail(resultSet.getString("email"));
	            } else {
	            	// userID에 해당하는 사용자가 존재하지 않을 경우
	            	return null;
	            }
	        }				

		} catch (Exception e) {
			// 데이터베이스 오류 발생			
			e.printStackTrace();
			return null;
		}
		
		// PERSONAL_INFO 테이블에서 개인정보, 학사정보 추출
		try (Connection connection = DatabaseUtil.getConnection();
			 PreparedStatement personalInfoStatement = connection.prepareStatement(personalInfoSQL)) {
			// 쿼리(where절 userID = ?)에 학번 포함
	        personalInfoStatement.setString(1, userID);
	        
	        try (ResultSet resultSet = personalInfoStatement.executeQuery()) {
	            if (resultSet.next()) {
	            	// PERSONAL_INFO가 있을 경우에만 학사 정보 세팅
	                student.setCollege(resultSet.getString("college"));
	                student.setMajor(resultSet.getString("major"));
	                student.setAdmissionYear(resultSet.getInt("admissionYear"));
	                student.setStatus(resultSet.getString("status"));
	                student.setResidentNumber(resultSet.getString("residentNumber"));	                
	                student.setAddress(resultSet.getString("address"));
	            }
	        }
			
		} catch (Exception e) {
			// 데이터베이스 오류 발생			
			e.printStackTrace();
			return null;
		}
    	// 학생 데이터 객체 반환
    	return student;
	}
	
	public List<StudentDTO> getRecordsByStudent(String userID) {
		// 학번/사번(userID)으로 나의 학사정보 페이지에서 조회할 정보 불러오는 SQL 쿼리
		// 학사정보 객체를 받기 위한 빈 배열 객체 생성 
		List<StudentDTO> recordList = new ArrayList<>();
		String sql = "SELECT * FROM ACADEMIC_RECORD WHERE userID = ?";
		
		// ACADEMIC_RECORD 테이블에서 학사정보 추출
		try (Connection connection = DatabaseUtil.getConnection();
			 PreparedStatement checkStatement = connection.prepareStatement(sql)) {
			// 쿼리(where절 userID = ?)에 학번 포함
			checkStatement.setString(1, userID);
			ResultSet resultSet = checkStatement.executeQuery();
			
			while (resultSet.next()) {
				StudentDTO record = new StudentDTO();
				record.setUserID(resultSet.getString("userID"));
				record.setCollege(resultSet.getString("college"));
				record.setMajor(resultSet.getString("major"));
				record.setAcademicYear(resultSet.getInt("academicYear"));
				record.setSemester(resultSet.getString("semester"));
				record.setCourseID(resultSet.getInt("courseID"));
				record.setCourseName(resultSet.getString("courseName"));
				record.setCourseType(resultSet.getString("courseType"));
				record.setCoursePF(resultSet.getString("coursePF"));
				record.setPassOrFail(resultSet.getBoolean("pass_or_fail"));
				record.setGrade(resultSet.getString("grade"));
				record.setGradePoint(resultSet.getFloat("gradePoint"));
				record.setRetakeYear(resultSet.getInt("retakeYear"));
				record.setRetakeSemester(resultSet.getString("retakeSemester"));
				record.setRetakeCourseID(resultSet.getInt("retakeCourseID"));
				record.setEnrollmentReason(resultSet.getString("enrollmentReason"));
				
				// 학사정보 배열에 저장
				recordList.add(record);	
			}
			
		} catch (SQLException e) {
			// 데이터베이스 오류 발생			
			e.printStackTrace();
		}
    	// 학사 데이터 배열 객체 반환
		return recordList;
	}	
	
	public StudentDTO updatePhoneNumber(String userID, String phoneNumber) {
		// 학번/사번(userID)으로 나의 개인정보 페이지에서 조회되는 정보를 수정하는 SQL 쿼리
		// ** 수정 예정 **
		String updateQuery = "UPDATE USER SET phoneNumber = ? WHERE userID = ?";
		
	    try (Connection connection = DatabaseUtil.getConnection();
	         PreparedStatement statement = connection.prepareStatement(updateQuery)) {
			statement.setString(1, phoneNumber);
			statement.setString(2, userID);
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;	    
	}
	
	public StudentDTO updateEmail(String userID, String email) {
		// 학번/사번(userID)으로 나의 개인정보 페이지에서 조회되는 정보를 수정하는 SQL 쿼리
		// ** 수정 예정 **
		String updateQuery = "UPDATE USER SET email = ? WHERE userID = ?";
		
	    try (Connection connection = DatabaseUtil.getConnection();
		     PreparedStatement statement = connection.prepareStatement(updateQuery)) {
			statement.setString(1, email);
			statement.setString(2, userID);
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;	    
	}
	
	public StudentDTO updateAddress(String userID, String address) {
		// 학번/사번(userID)으로 나의 개인정보 페이지에서 조회되는 정보를 수정하는 SQL 쿼리
		// ** 수정 예정 **
		String updateQuery = "UPDATE USER SET address = ? WHERE userID = ?";
		
	    try (Connection connection = DatabaseUtil.getConnection();
		     PreparedStatement statement = connection.prepareStatement(updateQuery)) {
			statement.setString(1, address);
			statement.setString(2, userID);
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}

/*
config.properties 파일을 성공적으로 로드했습니다.
java.sql.SQLException: Column 'residentNumber' not found.
	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:130)
	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:98)
	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:90)
	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:64)
	at com.mysql.cj.jdbc.result.ResultSetImpl.findColumn(ResultSetImpl.java:584)
	at com.mysql.cj.jdbc.result.ResultSetImpl.getString(ResultSetImpl.java:896)
	at dao.StudentDAO.getMyInfo(StudentDAO.java:33)
	at service.StudentService.getStudentInfo(StudentService.java:24)
userID from session: S2024096
studentInfo: null
*/