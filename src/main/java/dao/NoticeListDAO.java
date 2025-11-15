package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.NoticeDTO;
import util.DatabaseUtil;

// NoticeDAO의 “SELECT” 기능과 “UPDATE, INSERT, DELETE” 기능을 분리
// 공지 조회 전용 DAO 클래스
public class NoticeListDAO {

	// 게시글 리스트 조회
	public List<NoticeDTO> getAllNotices() {
		List<NoticeDTO> noticeList = new ArrayList<>();
		// NOTICE 테이블 데이터통신객체를 통해 데이터에 접근하기 위한 배열리스트 객체 noticelist 생성 
		String sql = "SELECT * FROM NOTICE";
		// NOTICE 테이블에서 전체 공지사항 게시글 리스트 조회하는 쿼리
		
		try (Connection connection = DatabaseUtil.getConnection();
			PreparedStatement noticeListStatement = connection.prepareStatement(sql)) {
			ResultSet resultSet = noticeListStatement.executeQuery();
			
			while (resultSet.next()) {
				NoticeDTO notice = new NoticeDTO();
				notice.setNoticeID(resultSet.getInt("noticeID"));
				// 공지글 일련번호
				notice.setUserID(resultSet.getString("userID"));
				// 사용자 로그인 계정 아이디
				notice.setTitle(resultSet.getString("title"));
				// 게시판 공지글 제목
				notice.setContents(resultSet.getString("contents"));
				// 게시판 공지글 내용 
				notice.setCreateDate(resultSet.getTimestamp("createDate"));
				// 게시판 공지글 작성일자
				notice.setUpdateDate(resultSet.getTimestamp("updateDate"));
				// 게시판 공지글 수정일자
				notice.setEndDate(resultSet.getTimestamp("endDate"));
				// 게시판 공지글 게시 종료일자
				notice.setPermissionRole(resultSet.getString("permissionRole"));
				// 게시판 공지글 작성, 수정, 삭제 권한정보(글쓴 교수, 교직원/전체 관리자만 권한 보유)
				noticeList.add(notice);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return noticeList;
	    // 전체 공지글 리스트 DTO 반환		
	}
	
	// offset부터 limit 수만큼 게시글 목록 조회
	public List<NoticeDTO> getNoticesWithPaging(int offset, int limit) {
	    List<NoticeDTO> noticeList = new ArrayList<>();
		// NOTICE 테이블 데이터통신객체를 통해 데이터에 접근하기 위한 배열리스트 객체 noticelist 생성 	    
	    String sql = "SELECT * FROM NOTICE ORDER BY createDate DESC LIMIT ? OFFSET ?";
		// NOTICE 테이블에서 전체 게시판 공지글을 작성일자 내림차순 정렬하여 페이징 처리된 리스트를 조회하는 쿼리문

	    try (Connection connection = DatabaseUtil.getConnection();
	         PreparedStatement noticePagingStatement = connection.prepareStatement(sql)) {
	    	noticePagingStatement.setInt(1, limit);
	        // LIMIT: 한 페이지당 공지글을 몇개까지 가져올지 입력값을 통해 결정
	        // limit: 
	    	noticePagingStatement.setInt(2, offset);
	        // OFFSET: 현재 페이지에서 공지글을 몇번째부터 가져올지 입력값을 통해 결정
	        // offset: NoticeService.getPagedNotices(
	        ResultSet resultSet = noticePagingStatement.executeQuery();

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
				// 공지글 일련번호, 사용자 로그인 계정 아이디, 게시판 공지글 제목, 내용, 작성일자, 수정일자, 종료일자, 공지권한정보
	            noticeList.add(notice);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return noticeList;
	    // 페이징된 공지글 리스트 DTO 반환
	}
	
	// 특정 게시글 조회 - 게시글 표 & 게시글 작성자 조회 (학생/교수/관리자 여부와 함께 조회)
	public NoticeDTO getNoticeByID(int noticeID) {
		NoticeDTO notice = new NoticeDTO();
		// NOTICE 테이블 데이터통신객체를 통해 데이터에 접근하기 위한 배열리스트 객체 notice 생성 		
		String sql = "SELECT * FROM NOTICE WHERE noticeID = ?";
		// NOTICE 테이블에서 글쓴이 아이디에 따라 특정 게시판 공지글 관련 데이터를 조회하는 쿼리문    		

		try (Connection connection = DatabaseUtil.getConnection();
			PreparedStatement noticeCheckStatement = connection.prepareStatement(sql)) {
			noticeCheckStatement.setInt(1, noticeID);
	        // noticeID: 어떤 공지사항 글 일련번호를 입력값을 통해 결정		
			// noticeID: 
			ResultSet resultSet = noticeCheckStatement.executeQuery();
			
			if (resultSet.next()) {
				notice.setNoticeID(resultSet.getInt("noticeID"));
				notice.setUserID(resultSet.getString("userID"));
				notice.setTitle(resultSet.getString("title"));
				notice.setContents(resultSet.getString("contents"));
				notice.setCreateDate(resultSet.getTimestamp("createDate"));
				notice.setUpdateDate(resultSet.getTimestamp("updateDate"));
				notice.setEndDate(resultSet.getTimestamp("endDate"));
				notice.setPermissionRole(resultSet.getString("permissionRole"));
				// 공지글 일련번호, 사용자 로그인 계정 아이디, 게시판 공지글 제목, 내용, 작성일자, 수정일자, 종료일자, 공지권한정보				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return notice;
		// 조회하려는 특정 공지글 DTO 반환(없으면 빈 객체)		
	}
}

/*
 * 	// 전체 게시글 수 반환
	public int getTotalNoticeCount() {
	    String sql = "SELECT COUNT(*) FROM NOTICE";
		// NOTICE 테이블에서 전체 게시판 공지글 수를 집계한 값을 조회하는 쿼리문	    
	    try (Connection connection = DatabaseUtil.getConnection();
	         PreparedStatement noticeCountStatement = connection.prepareStatement(sql)) {
	        ResultSet resultSet = noticeCountStatement.executeQuery();
	        if (resultSet.next()) {
	            return resultSet.getInt(1);
	            // .getInt(1); 1번째 컬럼의 정수값 반환
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // 쿼리 실행 관련 예외 발생 시 콘솔 출력
	    }
	    return 0;
	    // 예외 발생 또는 결과값이 없을 경우 0을 반환
	}
*/	
