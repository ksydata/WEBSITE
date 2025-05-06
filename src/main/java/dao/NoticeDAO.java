package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import dto.NoticeDTO;
import util.DatabaseUtil;

public class NoticeDAO {

	// 게시글 리스트 조회
	public List<NoticeDTO> getAllNotices() {
		List<NoticeDTO> noticeList = new ArrayList<>();
		String sql = "SELECT * FROM NOTICE";
		
		try (Connection connection = DatabaseUtil.getConnection();
			PreparedStatement checkStatement = connection.prepareStatement(sql)) {
			ResultSet resultSet = checkStatement.executeQuery();
			
			while (resultSet.next()) {
				NoticeDTO notice = new NoticeDTO();
				notice.setNoticeID(resultSet.getInt("noticeID"));
				notice.setUserID(resultSet.getString("userID"));
				notice.setTitle(resultSet.getString("title"));
				notice.setContents(resultSet.getString("contents"));
				notice.setCreateDate(resultSet.getTimestamp("createDate"));
				notice.setUpdateDate(resultSet.getTimestamp("updateDate"));
				notice.setEndDate(resultSet.getTimestamp("endDate"));
				notice.setPermissionRole(resultSet.getString("permissionRole"));
				
				noticeList.add(notice);
				
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return noticeList;
	}
	
	
	// 특정 게시글 조회 - 게시글 표 & 게시글 작성자 조회 (학생/교수/관리자 여부와 함께 조회)
	public NoticeDTO getNoticeByID(int noticeID) {
		NoticeDTO notice = new NoticeDTO();
		String sql = "SELECT * FROM NOTICE WHERE noticeID = ?";

		try (Connection connection = DatabaseUtil.getConnection();
			PreparedStatement checkStatement = connection.prepareStatement(sql)) {
			checkStatement.setInt(1, noticeID);
			ResultSet resultSet = checkStatement.executeQuery();
			
			if (resultSet.next()) {
				notice.setNoticeID(resultSet.getInt("noticeID"));
				notice.setUserID(resultSet.getString("userID"));
				notice.setTitle(resultSet.getString("title"));
				notice.setContents(resultSet.getString("contents"));
				notice.setCreateDate(resultSet.getTimestamp("createDate"));
				notice.setUpdateDate(resultSet.getTimestamp("updateDate"));
				notice.setEndDate(resultSet.getTimestamp("endDate"));
				notice.setPermissionRole(resultSet.getString("permissionRole"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return notice;
	}
	
	public int uploadNotice(String userID, String title, String contents, String endDate, String permissionRole) {
		String sql = "INSERT INTO NOTICE (userID, title, contents, createDate, updateDate, endDate, permissionRole) VALUES (?, ?, ?, ?, ?, ?, ?)";		
		int insertedID = -1;
		
		// 문자열 endDate를 Timestamp로 변환
        Timestamp endDateTime = null;
        if (endDate != null && !endDate.isEmpty()) {
            endDateTime = Timestamp.valueOf(endDate.replace("T", " ") + ":00");
        }
		
		try (Connection connection = DatabaseUtil.getConnection();
			PreparedStatement insertStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {
			
			insertStatement.setString(1, userID);
			insertStatement.setString(2, title);
			insertStatement.setString(3, contents);
			insertStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis())); // createDate: 현재 시간 입력
			insertStatement.setTimestamp(5, null); // updateDate: 수정이 아니므로 null값 입력
			insertStatement.setTimestamp(6, endDateTime); // endDate: 미리 지정한 endDate값 입력
			insertStatement.setString(7, permissionRole);
			
			int rowsAffected = insertStatement.executeUpdate();
			
			if (rowsAffected > 0) {
	            ResultSet rs = insertStatement.getGeneratedKeys();
	            if (rs.next()) {
	                insertedID = rs.getInt(1);  // 자동 증가한 PK 반환
	            }
	        }
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		// 게시 성공 시 해당 행의 noticeID (auto increment), 게시 실패 시 -1 반환
		return insertedID;
		
	}
	
	public boolean updateNotice(int id, String title, String contents) {
	    String sql = "UPDATE NOTICE SET title = ?, contents = ?, updateDate = NOW() WHERE noticeID = ?";
	    
	    try (Connection conn = DatabaseUtil.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, title);
	        pstmt.setString(2, contents);
	        pstmt.setInt(3, id);
	        return pstmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	
	public boolean deleteNotice(int id) {
	    String sql = "DELETE FROM NOTICE WHERE noticeID = ?";

	    try (Connection conn = DatabaseUtil.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, id);
	        return pstmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
}
