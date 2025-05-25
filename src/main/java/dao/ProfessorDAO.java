package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.ProfessorDTO;
import dto.StudentDTO;
import dto.AcademicRecordDTO;
import util.DatabaseUtil;

// 사용자 로그인 시 아이디로 받아온 학번/사번(userID)으로 DB에 접근하여 교수 1명의 정보를 ProfessorDTO에 담아 반환
public class ProfessorDAO {
	public ProfessorDTO getMyInfo(String userID) {
		// 학번/사번(userID)으로 나의 개인정보 페이지에서 조회 또는 수정할 정보 불러오는 SQL 쿼리 
		String userInfoSQL = "SELECT * FROM USER WHERE userID = ?";
		String personalInfoSQL = "SELECT * FROM PERSONAL_INFO WHERE userID = ?";

		// 로그인 성공했을 때 사용자 정보 활용
		ProfessorDTO student = null;
    	
    	// USER 테이블에서 로그인 인증 관련 정보 추출
		try (Connection connection = DatabaseUtil.getConnection();
			 PreparedStatement userInfoStatement = connection.prepareStatement(userInfoSQL)) {	
			// 쿼리(where절 userID = ?)에 학번 포함
			userInfoStatement.setString(1, userID);
			
			try (ResultSet resultSet = userInfoStatement.executeQuery()) {
	            if (resultSet.next()) {
	            	// 사용자 정보가 존재할 경우 StudentDTO 객체 생성
	            	student = new ProfessorDTO();
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

	public ProfessorDTO updatePhoneNumber(String userID, String phoneNumber) {
		// 학번/사번(userID)으로 나의 개인정보 페이지에서 조회되는 정보를 수정하는 SQL 쿼리
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

	
	public ProfessorDTO updateOfficeNumber(String userID, String officeNumber) {
		// 학번/사번(userID)으로 나의 개인정보 페이지에서 조회되는 정보를 수정하는 SQL 쿼리
		String updateQuery = "UPDATE USER SET officeNumber = ? WHERE userID = ?";
		
	    try (Connection connection = DatabaseUtil.getConnection();
	         PreparedStatement statement = connection.prepareStatement(updateQuery)) {
			statement.setString(1, officeNumber);
			statement.setString(2, userID);
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;	    
	}
	
	
	public ProfessorDTO updateEmail(String userID, String email) {
		// 학번/사번(userID)으로 나의 개인정보 페이지에서 조회되는 정보를 수정하는 SQL 쿼리
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
	
	public ProfessorDTO updateAddress(String userID, String address) {
		// 학번/사번(userID)으로 나의 개인정보 페이지에서 조회되는 정보를 수정하는 SQL 쿼리
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
	
	public ProfessorDTO updatePassword(String userID, String newPassword) {
		// 학번/사번(userID)으로 나의 개인정보 페이지에서 조회되는 비밀번호를 변경하는 SQL 쿼리
		String updateQuery = "UPDATE USER SET newPassword = ? WHERE userID = ?";
		
	    try (Connection connection = DatabaseUtil.getConnection();
	         PreparedStatement statement = connection.prepareStatement(updateQuery)) {
			statement.setString(1, newPassword);
			statement.setString(2, userID);
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;	    
	}
	
    public List<AcademicRecordDTO> getAcademicRecordsByCollege(String professorId) {
    	// 교수 1명의 동일 단과대학에 해당하는 수강생 1명의 정보에 대한 접근하는 조인 쿼리
        String recordQuery = """
            SELECT
                U.user_id,
                U.name,
                P.college,                
                P.major,
                R.courseName,
                R.grade,
                R.gradePoint,                
                R.semester,
            FROM USER U
            JOIN PERSONAL_INFO P ON U.user_id = P.user_id
            JOIN ACADEMIC_RECORD R ON U.user_id = R.student_id
            WHERE P.college = (
                SELECT college FROM PERSONAL_INFO WHERE user_id = ?
            )
            AND U.role = 'student'
            ORDER BY R.semester DESC, R.name ASC;
        """;
        List<AcademicRecordDTO> classRecordsList = new ArrayList<>();        

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(recordQuery)) {
        	preparedStatement.setString(1, professorId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                AcademicRecordDTO dto = new AcademicRecordDTO();
                dto.setUserID(resultSet.getString("user_id"));
                dto.setName(resultSet.getString("name"));
                dto.setMajor(resultSet.getString("major"));
                dto.setCourseName(resultSet.getString("courseName"));
                dto.setGrade(resultSet.getString("grade"));
                dto.setGradePoint(resultSet.getInt("gradePoint"));
                dto.setSemester(resultSet.getString("semester"));
                classRecordsList.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classRecordsList;
    }
}