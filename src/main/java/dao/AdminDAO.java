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

public class AdminDAO {
	
	// userID를 통해 유저별 개인정보 페이지 정보 가져오기
	public AdminPersonalInfoDTO getUserInfo(String userID) {
		// 결과값 담기
		AdminPersonalInfoDTO admin = null;
		
		// USER 테이블에서 정보 가져오기
		String userInfoSQL = "SELECT * FROM USER WHERE userID = ?";
		
		try (Connection connection = DatabaseUtil.getConnection();
			PreparedStatement userInfoStatement = connection.prepareStatement(userInfoSQL)) {
			
			userInfoStatement.setString(1, userID);

			try (ResultSet resultSet = userInfoStatement.executeQuery()) {
	            if (resultSet.next()) {
	            	// 사용자 정보가 존재할 경우 StudentDTO 객체 생성
	            	admin = new AdminPersonalInfoDTO();
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
		
		
		// PERSONAL_INFO 테이블에서 정보 가져오기
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
			

		// 관리자용 유저 페이지 데이터 객체 반환
		return admin;
		
	}
	
	// 검색 메서드
	public List<AdminPersonalInfoDTO> searchUsersWithPaging(String keyword, String filterType, int offset, int limit) {
	    List<AdminPersonalInfoDTO> userList = new ArrayList<>();

	    String baseSQL = 
	        "SELECT U.*, P.college, P.major, P.admissionYear, P.status, P.residentNumber, P.address " +
	        "FROM USER U LEFT JOIN PERSONAL_INFO P ON U.userID = P.userID ";

	    // WHERE 절 동적 구성
	    String whereClause = "";
	    boolean hasKeyword = (keyword != null && !keyword.trim().isEmpty());
	    if (hasKeyword) {
	        switch (filterType) {
	            case "userID":
	            case "name":
	            case "email":
	            case "phoneNumber":
	            case "officeNumber":
	            case "role":
	                whereClause = "WHERE U." + filterType + " LIKE ?";
	                break;
	            case "college":
	            case "major":
	            case "status":
	            case "residentNumber":
	            case "address":
	                whereClause = "WHERE P." + filterType + " LIKE ?";
	                break;
	            default:
	                // 필터가 유효하지 않은 경우 필터 적용하지 않음
	                break;
	        }
	    }

	    String pagingClause = " LIMIT ? OFFSET ?";
	    String sql = baseSQL + whereClause + pagingClause;

	    try (Connection conn = DatabaseUtil.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        int paramIndex = 1;
	        if (hasKeyword && !whereClause.isEmpty()) {
	            pstmt.setString(paramIndex++, "%" + keyword + "%");
	        }
	        pstmt.setInt(paramIndex++, limit);
	        pstmt.setInt(paramIndex++, offset);

	        ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) {
	            AdminPersonalInfoDTO admin = new AdminPersonalInfoDTO();
	            admin.setUserID(rs.getString("userID"));
	            admin.setUserPassword(rs.getString("userPassword"));
	            admin.setName(rs.getString("name"));
	            admin.setPhoneNumber(rs.getString("phoneNumber"));
	            admin.setOfficeNumber(rs.getString("officeNumber"));
	            admin.setEmail(rs.getString("email"));
	            admin.setUserRole(rs.getString("role"));
	            admin.setCollege(rs.getString("college"));
	            admin.setMajor(rs.getString("major"));
	            admin.setAdmissionYear(rs.getInt("admissionYear"));
	            admin.setStatus(rs.getString("status"));
	            admin.setResidentNumber(rs.getString("residentNumber"));
	            admin.setAddress(rs.getString("address"));

	            userList.add(admin);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return userList;
	}
	
	// 전체 결과 수 카운팅 메서드
	public int countUsersBySearch(String keyword, String filterType) {
	    String baseSQL = 
	        "SELECT COUNT(*) FROM USER U LEFT JOIN PERSONAL_INFO P ON U.userID = P.userID ";

	    String whereClause = "";
	    boolean hasKeyword = (keyword != null && !keyword.trim().isEmpty());
	    if (hasKeyword) {
	        switch (filterType) {
	            case "userID":
	            case "name":
	            case "email":
	            case "phoneNumber":
	            case "officeNumber":
	            case "role":
	                whereClause = "WHERE U." + filterType + " LIKE ?";
	                break;
	            case "college":
	            case "major":
	            case "status":
	            case "residentNumber":
	            case "address":
	                whereClause = "WHERE P." + filterType + " LIKE ?";
	                break;
	            default:
	                break;
	        }
	    }

	    String sql = baseSQL + whereClause;

	    try (Connection conn = DatabaseUtil.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        if (hasKeyword && !whereClause.isEmpty()) {
	            pstmt.setString(1, "%" + keyword + "%");
	        }

	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            return rs.getInt(1);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return 0;
	}
	
	// 유저 리스트 정보 가져오기 : 모든 권한, 학생 / 교수 / 교직원 / 관리자
	public List<AdminPersonalInfoDTO> getUserListByRole(String userRole) {
	    List<AdminPersonalInfoDTO> userList = new ArrayList<>();

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

	        ResultSet resultSet = userInfoStatement.executeQuery();

	        while (resultSet.next()) {
	        	AdminPersonalInfoDTO admin = new AdminPersonalInfoDTO();
	        	admin.setUserID(resultSet.getString("userID"));
                admin.setUserPassword(resultSet.getString("userPassword"));
                admin.setName(resultSet.getString("name"));
                admin.setPhoneNumber(resultSet.getString("phoneNumber"));
                admin.setOfficeNumber(resultSet.getString("officeNumber"));
                admin.setEmail(resultSet.getString("email"));
                admin.setUserRole(resultSet.getString("role"));

                // PERSONAL_INFO 테이블 조회
                String infoSql = "SELECT * FROM PERSONAL_INFO WHERE userID = ?";
                try (PreparedStatement infoStmt = connection.prepareStatement(infoSql)) {
                    infoStmt.setString(1, admin.getUserID());
                    ResultSet infoRs = infoStmt.executeQuery();
                    if (infoRs.next()) {
                    	admin.setCollege(infoRs.getString("college"));
		                admin.setMajor(infoRs.getString("major"));
		                admin.setAdmissionYear(infoRs.getInt("admissionYear"));
		                admin.setStatus(infoRs.getString("status"));
		                admin.setResidentNumber(infoRs.getString("residentNumber"));	                
		                admin.setAddress(infoRs.getString("address"));
                    }
                }

	            userList.add(admin);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        // 예외 처리 로깅 또는 throw
	    }

	    return userList;
	}
	
	// 페이징을 위한 메서드
	public List<AdminPersonalInfoDTO> getUserListByRoleWithPaging(String userRole, int offset, int limit) {
	    List<AdminPersonalInfoDTO> userList = new ArrayList<>();
	    String sql;
	    boolean allRoles = (userRole == null || userRole.equals("") || userRole.equalsIgnoreCase("전체"));

	    if (allRoles) {
	        sql = "SELECT * FROM USER LIMIT ? OFFSET ?";
	    } else {
	        sql = "SELECT * FROM USER WHERE role = ? LIMIT ? OFFSET ?";
	    }

	    try (Connection conn = DatabaseUtil.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        if (allRoles) {
	            pstmt.setInt(1, limit);
	            pstmt.setInt(2, offset);
	        } else {
	            pstmt.setString(1, userRole);
	            pstmt.setInt(2, limit);
	            pstmt.setInt(3, offset);
	        }

	        ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) {
	            AdminPersonalInfoDTO admin = new AdminPersonalInfoDTO();
	            admin.setUserID(rs.getString("userID"));
	            admin.setUserPassword(rs.getString("userPassword"));
	            admin.setName(rs.getString("name"));
	            admin.setPhoneNumber(rs.getString("phoneNumber"));
	            admin.setOfficeNumber(rs.getString("officeNumber"));
	            admin.setEmail(rs.getString("email"));
	            admin.setUserRole(rs.getString("role"));
	            // 필요 시 PERSONAL_INFO 조회 추가
	            userList.add(admin);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return userList;
	}

	// 전체 유저 수 반환 (페이징 계산용)
	public int countUsersByRole(String role) {
	    String sql;
	    boolean allRoles = (role == null || role.equals("") || role.equalsIgnoreCase("전체"));

	    if (allRoles) {
	        sql = "SELECT COUNT(*) FROM USER";
	    } else {
	        sql = "SELECT COUNT(*) FROM USER WHERE role = ?";
	    }

	    try (Connection conn = DatabaseUtil.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        if (!allRoles) {
	            pstmt.setString(1, role);
	        }

	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            return rs.getInt(1);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return 0;
	}
	
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
	
	
}