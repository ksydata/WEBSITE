package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.PagingDTO;
import util.DatabaseUtil;
import java.util.List;

// [TO-BE] 전체 데이터 조회하는 데이터접근객체(공통 모듈)
public class PagingDAO {
	
	// 전체 데이터 수 집계하는 메서드	
	public int getTotalCount(String TABLE_NAME, String WHERE_CLAUSE) {
		String sql = "SELECT COUNT(*) FROM " + TABLE_NAME;
		// 입력받은 DB테이블에서 전체 게시판 공지글 수를 집계한 값을 조회하는 쿼리문
		if (WHERE_CLAUSE != null && !WHERE_CLAUSE.isEmpty()) {
			// 조건절이 null값이거나 공백이 아닐 경우
			sql += " WHERE" + WHERE_CLAUSE;
			// 데이터 행수 집계하는 쿼리문에 조건절 추가
		}
		try (Connection connection = DatabaseUtil.getConnection();
			PreparedStatement totalCountStatement = connection.prepareStatement(sql)) {
			ResultSet resultSet = totalCountStatement.executeQuery();
		    
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
	
	// 페이지별 데이터 리스트 조회하는 메서드(공통 모듈)
	public <T> List<T> getPagingDataList(
			String TABLE_NAME, String WHERE_CLAUSE, String ORDER_BY,
			int OFFSET, int pageSize, RowMapper<T> Mapper) {
		// [AS-IS] 
		return null;
	}
	
	// 인터페이스
	public interface RowMapper<T> {
		T mapRow(ResultSet resultSet) throws SQLException;
	}
}