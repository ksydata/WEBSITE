package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.AdminPersonalInfoDTO;
import dto.AdminRecordDTO;
import util.DatabaseUtil;

public class AdminPageDAO {
	
	// userID를 통해 유저별 학사정보 페이지 정보 가져오기
	public List<AdminRecordDTO> getRecordsByStudent(String userID) {
		// 학번/사번(userID)으로 나의 학사정보 페이지에서 조회할 정보 불러오는 SQL 쿼리
		// 학사정보 객체를 받기 위한 빈 배열 객체 생성 
		List<AdminRecordDTO> recordList = new ArrayList<>();
		String sql = "SELECT * FROM ACADEMIC_RECORD WHERE userID = ?";
		
		// ACADEMIC_RECORD 테이블에서 학사정보 추출
		try (Connection connection = DatabaseUtil.getConnection();
			 PreparedStatement checkStatement = connection.prepareStatement(sql)) {
			// 쿼리(where절 userID = ?)에 학번 포함
			checkStatement.setString(1, userID);
			ResultSet resultSet = checkStatement.executeQuery();
			
			while (resultSet.next()) {
				AdminRecordDTO record = new AdminRecordDTO();
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
	
	// 사용자 자신의 개인정보 가져오기
	public AdminPersonalInfoDTO getMyInfo(String userID) {
		// 학번/사번(userID)으로 나의 개인정보 페이지에서 조회 또는 수정할 정보 불러오는 SQL 쿼리 
		String userInfoSQL = "SELECT * FROM USER WHERE userID = ?";
		String personalInfoSQL = "SELECT * FROM PERSONAL_INFO WHERE userID = ?";

		AdminPersonalInfoDTO admin = null;
		
		// USER 테이블에서 로그인 인증 관련 정보 추출
				try (Connection connection = DatabaseUtil.getConnection();
					 PreparedStatement userInfoStatement = connection.prepareStatement(userInfoSQL)) {	
					// 쿼리(where절 userID = ?)에 사번 포함
					userInfoStatement.setString(1, userID);
					
					try (ResultSet resultSet = userInfoStatement.executeQuery()) {
			            if (resultSet.next()) {
			            	// 사용자 정보가 존재할 경우 DTO 객체 생성
			            	admin = new AdminPersonalInfoDTO();
			            	admin.setUserID(resultSet.getString("userID"));
			            	admin.setUserPassword(resultSet.getString("userPassword"));
			            	admin.setName(resultSet.getString("name"));
			            	admin.setPhoneNumber(resultSet.getString("phoneNumber"));
			            	admin.setOfficeNumber(resultSet.getString("officeNumber"));	            	
			            	admin.setEmail(resultSet.getString("email"));
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
					// 쿼리(where절 userID = ?)에 사번 포함
			        personalInfoStatement.setString(1, userID);
			        
			        try (ResultSet resultSet = personalInfoStatement.executeQuery()) {
			            if (resultSet.next()) {
			            	// PERSONAL_INFO가 있을 경우에만 학사 정보 세팅
			            	admin.setCollege(resultSet.getString("college"));
			            	admin.setMajor(resultSet.getString("major"));
			            	admin.setStatus(resultSet.getString("status"));
			            	admin.setResidentNumber(resultSet.getString("residentNumber"));	                
			            	admin.setAddress(resultSet.getString("address"));
			            }
			        }
					
				} catch (Exception e) {
					// 데이터베이스 오류 발생			
					e.printStackTrace();
					return null;
				}
		    	// 관리자 데이터 객체 반환
		    	return admin;
		
		
	}
	
		

}
