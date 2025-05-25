package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.AdminDTO;
import util.DatabaseUtil;

public class AdminDAO {
	
	// userID를 통해 유저별 개인정보 페이지 정보 가져오기
	public AdminDTO getUserInfo(String userID) {
		// 결과값 담기
		AdminDTO admin = null;
		
		// USER 테이블에서 정보 가져오기
		String userInfoSQL = "SELECT * FROM USER WHERE userID = ?";
		
		try (Connection connection = DatabaseUtil.getConnection();
			PreparedStatement userInfoStatement = connection.prepareStatement(userInfoSQL)) {
			
			userInfoStatement.setString(1, userID);

			try (ResultSet resultSet = userInfoStatement.executeQuery()) {
	            if (resultSet.next()) {
	            	// 사용자 정보가 존재할 경우 StudentDTO 객체 생성
	            	admin = new AdminDTO();
	                admin.setUserID(resultSet.getString("userID"));
	                admin.setUserPassword(resultSet.getString("userPassword"));
	                admin.setName(resultSet.getString("name"));
	                admin.setPhoneNumber(resultSet.getString("phoneNumber"));
	                admin.setOfficeNumber(resultSet.getString("officeNumber"));
	                admin.setEmail(resultSet.getString("email"));
	                admin.setUserRole(resultSet.getString("role"));
	            } else {
	            	// userID에 해당하는 사용자가 존재하지 않을 경우
	            	return null;
	            }
	        }			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
		// 권한이 '학생'인 경우 PERSONAL_INFO 테이블에서 정보 가져오기
		if (admin.getUserRole().equals("학생")) {
			String personalInfoSQL = "SELECT * FROM PERSONAL_INFO WHERE userID = ?";
			
			try (Connection connection = DatabaseUtil.getConnection();
				 PreparedStatement personalInfoStatement = connection.prepareStatement(personalInfoSQL)) {
				 // 쿼리(where절 userID = ?)에 학번 포함
		         personalInfoStatement.setString(1, userID);
		        
		         try (ResultSet resultSet = personalInfoStatement.executeQuery()) {
		            if (resultSet.next()) {
		            	// PERSONAL_INFO가 있을 경우에만 학사 정보 세팅
		                admin.setCollege(resultSet.getString("college"));
		                admin.setMajor(resultSet.getString("major"));
		                admin.setAdmissionYear(resultSet.getInt("admissionYear"));
		                admin.setStatus(resultSet.getString("status"));
		                admin.setResidentNumber(resultSet.getString("residentNumber"));	                
		                admin.setAddress(resultSet.getString("address"));
		            }
		        }
					
				} catch (Exception e) {
					// 데이터베이스 오류 발생			
					e.printStackTrace();
				}
			
		}

		// 관리자용 유저 페이지 데이터 객체 반환
		return admin;
		
	}
	
	// 유저 리스트 정보 가져오기 : 모든 권한, 학생 / 교수 / 교직원 / 관리자
	public List<AdminDTO> getUserListByRole(String userRole) {
	    List<AdminDTO> userList = new ArrayList<>();

	    String sql;
	    boolean allRoles = (userRole == null || userRole.equals("") || userRole.equalsIgnoreCase("전체"));

	    if (allRoles) {
	        sql = "SELECT * FROM USER";
	    } else {
	        sql = "SELECT * FROM USER WHERE role = ?";
	    }

	    try (Connection connection = DatabaseUtil.getConnection();
			PreparedStatement userInfoStatement = connection.prepareStatement(sql)) {

	        if (!allRoles) {
	        	userInfoStatement.setString(1, userRole);
	        }

	        ResultSet rs = userInfoStatement.executeQuery();

	        while (rs.next()) {
	            AdminDTO dto = new AdminDTO();
	            dto.setUserID(rs.getString("user_id"));
	            dto.setUserPassword(rs.getString("user_password"));
	            dto.setName(rs.getString("name"));
	            dto.setPhoneNumber(rs.getString("phone_number"));
	            dto.setEmail(rs.getString("email"));
	            dto.setUserRole(rs.getString("role")); // role도 담아야 함

	            if ("학생".equals(dto.getUserRole())) {
	                // 학생이면 PERSONAL_INFO 테이블도 조회
	                String infoSql = "SELECT * FROM PERSONAL_INFO WHERE user_id = ?";
	                try (PreparedStatement infoStmt = connection.prepareStatement(infoSql)) {
	                    infoStmt.setString(1, dto.getUserID());
	                    ResultSet infoRs = infoStmt.executeQuery();
	                    if (infoRs.next()) {
	                        dto.setCollege(infoRs.getString("college"));
	                        dto.setMajor(infoRs.getString("major"));
	                        dto.setAdmissionYear(infoRs.getInt("admission_year"));
	                        dto.setStatus(infoRs.getString("status"));
	                        dto.setResidentNumber(infoRs.getString("resident_number"));
	                        dto.setAddress(infoRs.getString("address"));
	                    }
	                }
	            }

	            userList.add(dto);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        // 예외 처리 로깅 또는 throw
	    }

	    return userList;
	}
	
	// userID를 통해 유저별 학사정보 페이지 정보 가져오기
	public List<AdminDTO> getRecordsByStudent(String userID) {
		// 학번/사번(userID)으로 나의 학사정보 페이지에서 조회할 정보 불러오는 SQL 쿼리
		// 학사정보 객체를 받기 위한 빈 배열 객체 생성 
		List<AdminDTO> recordList = new ArrayList<>();
		String sql = "SELECT * FROM ACADEMIC_RECORD WHERE userID = ?";
		
		// ACADEMIC_RECORD 테이블에서 학사정보 추출
		try (Connection connection = DatabaseUtil.getConnection();
			 PreparedStatement checkStatement = connection.prepareStatement(sql)) {
			// 쿼리(where절 userID = ?)에 학번 포함
			checkStatement.setString(1, userID);
			ResultSet resultSet = checkStatement.executeQuery();
			
			while (resultSet.next()) {
				AdminDTO record = new AdminDTO();
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
	
	
}