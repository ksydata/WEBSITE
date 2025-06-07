package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dto.AdminPersonalInfoDTO;
import util.DatabaseUtil;

public class AdminListDAO {

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
	
	// 정렬 메서드
	public List<AdminPersonalInfoDTO> getUserListByRoleWithPagingAndSorting(
	        String userRole, int offset, int limit, String sortOrder, String orderField) {

	    List<AdminPersonalInfoDTO> userList = new ArrayList<>();
	    String sql;
	    boolean allRoles = (userRole == null || userRole.equals("") || userRole.equalsIgnoreCase("전체"));

	    // 정렬 방향 검증 (asc 또는 desc)
	    String order = "ASC";
	    if ("desc".equalsIgnoreCase(sortOrder)) {
	        order = "DESC";
	    }

	    // 허용된 정렬 필드만 사용 (SQL Injection 방지)
	    List<String> allowedFields = Arrays.asList("userID", "name", "email", "role");
	    if (!allowedFields.contains(orderField)) {
	        orderField = "name";  // 기본값
	    }

	    if (allRoles) {
	        sql = "SELECT * FROM USER ORDER BY " + orderField + " " + order + " LIMIT ? OFFSET ?";
	    } else {
	        sql = "SELECT * FROM USER WHERE role = ? ORDER BY " + orderField + " " + order + " LIMIT ? OFFSET ?";
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
	            userList.add(admin);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return userList;
	}
	
	
}
