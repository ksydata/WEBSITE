package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
				notice.setIdx(resultSet.getInt("idx"));
				notice.setUserID(resultSet.getString("userID"));
				notice.setTitle(resultSet.getString("title"));
				notice.setCreateDate(resultSet.getTimestamp("createDate"));
				notice.setUpdateDate(resultSet.getTimestamp("updateDate"));
				notice.setContents(resultSet.getString("contents"));
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
	public NoticeDTO getNoticeByIdx(int idx) {
		NoticeDTO notice = new NoticeDTO();
		String sql = "SELECT * FROM notice WHERE idx = ?";

		try (Connection connection = DatabaseUtil.getConnection();
			PreparedStatement checkStatement = connection.prepareStatement(sql)) {
			checkStatement.setInt(1, idx);
			ResultSet resultSet = checkStatement.executeQuery();
			
			if (resultSet.next()) {
				notice.setIdx(resultSet.getInt("idx"));
				notice.setUserID(resultSet.getString("userID"));
				notice.setTitle(resultSet.getString("title"));
				notice.setCreateDate(resultSet.getTimestamp("createDate"));
				notice.setUpdateDate(resultSet.getTimestamp("updateDate"));
				notice.setContents(resultSet.getString("contents"));
				notice.setPermissionRole(resultSet.getString("permissionRole"));
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return notice;
		
		
	}
}
