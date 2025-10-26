package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

// import dto.NoticeDTO;
// SELECT문이 없어 DTO가 필요하지 않음
import util.DatabaseUtil;

// NoticeDAO의 “SELECT” 기능과 “UPDATE, INSERT, DELETE” 기능을 분리
// 공지 등록/수정/삭제 기능
public class NoticeControlDAO {

	// 게시판 공지 등록 및 등록된 PK 반환
	public int uploadNotice(String userID, String title, String contents, String endDate, String permissionRole) {
		String sql = "INSERT INTO NOTICE (userID, title, contents, createDate, updateDate, endDate, permissionRole) VALUES (?, ?, ?, ?, ?, ?, ?)";
		// NOTICE 테이블에 (작성자이자 사용자 아이디, 글 제목, 내용, 작성일자, 수정일자, 종료일자, 권한)을 삽입하는 쿼리문
		int insertedID = -1;
		// 공지글 등록 실패 시 기본값 -1 반환
		
		// 공지글 종료일자인 문자열 endDate를 Timestamp 자료형으로 변환
        Timestamp endDateTime = null;
        if (endDate != null && !endDate.isEmpty()) {
        	// 논리곱 연산자(조건 모두 참이어야 true)를 통해 종료일자가 공백값이 아닌 경우
            endDateTime = Timestamp.valueOf(endDate.replace("T", " ") + ":00");
            // ISO 8601 형식은 "2025-12-31T12:30"이므로 중간 T를 공백값으로 대체하고 초단위 붙여서 "2025-12-31 12:30:00"으로 변환
        }
		
		try (Connection connection = DatabaseUtil.getConnection();
			PreparedStatement insertStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {
			
			insertStatement.setString(1, userID);
			insertStatement.setString(2, title);
			insertStatement.setString(3, contents);
			insertStatement.setTimestamp(
					4, new Timestamp(System.currentTimeMillis())); 
			// createDate: 공지글 작성일자는 현재 시간 입력
			insertStatement.setTimestamp(5, null); 
			// updateDate: 공지글 수정일자는 최초 작성으로 수정 전이므로 null값 입력
			insertStatement.setTimestamp(6, endDateTime); 
			// endDate: 공지글 종료일자는 미리 지정한 endDate값 입력
			insertStatement.setString(7, permissionRole);
			
			int rowsAffected = insertStatement.executeUpdate();
			// INSERT 쿼리 실행 후 실제로 DB에 반영된 행 개수 반환
			if (rowsAffected > 0) {
	            ResultSet resultSet = insertStatement.getGeneratedKeys();
	            if (resultSet.next()) {
	                insertedID = resultSet.getInt(1);  
	                // DB에서 자동으로 +1 증가하여 생성된 PK(noticeID)를 가져와 insertID에 저장
	                // CREATE TABLE NOTICE (noticeID INT PRIMARY KEY AUTO_INCREMENT);	                
	                // .getInt(1); 1번째 컬럼의 정수값 반환
	                // insertStatement.RETURN_GENERATED_KEYS
	            }
	        }	
		} catch (SQLException e) {
			e.printStackTrace();
			// 쿼리 실행 오류 발생 시 콘솔 출력
		}
		return insertedID; 
		// 공지글 등록 성공 시 해당 행의 noticeID(PK) 반환
		// 등록 실패 시 기본값인 -1 반환(초기값 그대로 유지, int insertedID = -1;)
	}

	// 게시판 공지글 수정
	public boolean updateNotice(int id, String title, String contents) {
	    String sql = "UPDATE NOTICE SET title = ?, contents = ?, updateDate = NOW() WHERE noticeID = ?";
		// NOTICE 테이블에 특정 일련번호의 공지글을 수정하기 위해 (글 제목, 내용, 수정일자)을 삽입하는 쿼리문
	    // 단, 수정일자는 NOW()를 통해 현재 시점으로 자동 설정
	    
	    try (Connection conn = DatabaseUtil.getConnection();
	         PreparedStatement updateStatement = conn.prepareStatement(sql)) {
	    	updateStatement.setString(1, title);
	    	updateStatement.setString(2, contents);
	    	updateStatement.setInt(3, id);
	    	// 수정대상 공지글 제목, 내용, 일련번호를 PreparedStatement를 통해 쿼리문 ?에 입력	    	
	        return updateStatement.executeUpdate() > 0;
	        // 공지글 수정 성공 여부 반환(true=1, false=0)
	    } catch (SQLException e) {
	        e.printStackTrace();
			// 쿼리 실행 오류 발생 시 콘솔 출력	        
	    }
	    return false;
	    // 실패 시 false 반환
	}
	
	// 게시판 공지글 삭제	
	public boolean deleteNotice(int id) {
	    String sql = "DELETE FROM NOTICE WHERE noticeID = ?";
		// NOTICE 테이블에 특정 일련번호의 공지글을 삭제하는 쿼리문

	    try (Connection conn = DatabaseUtil.getConnection();
	         PreparedStatement deleteStatement = conn.prepareStatement(sql)) {
	    	deleteStatement.setInt(1, id);
	    	// 삭제대상 공지글 일련번호를 PreparedStatement를 통해 쿼리문 ?에 입력
	        return deleteStatement.executeUpdate() > 0;
	        // 공지글 삭제 성공 여부 반환(true=1, false=0)
	    } catch (SQLException e) {
	        e.printStackTrace();
			// 쿼리 실행 오류 발생 시 콘솔 출력	        
	    }
	    return false;
	    // 실패 시 false 반환
	}
	
}
