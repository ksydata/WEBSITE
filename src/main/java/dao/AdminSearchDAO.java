package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.AdminPersonalInfoDTO;
import util.DatabaseUtil;

public class AdminSearchDAO {

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
	
}
