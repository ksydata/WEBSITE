package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.PagingDTO;
import util.DatabaseUtil;
import java.util.List;

//[TO-BE] 전체 데이터 조회하는 데이터접근객체(공통 모듈)
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
	        int OFFSET, int pageSize, RowMapper<T> mapper) {

		// 대상 테이블의 전체 컬럼 조회
	    StringBuilder sql = new StringBuilder("SELECT * FROM " + TABLE_NAME);

	    // 검색/필터 조건이 있을 때 WHERE 절을 추가
	    if (WHERE_CLAUSE != null && !WHERE_CLAUSE.isEmpty()) {
	        sql.append(" WHERE ").append(WHERE_CLAUSE);
	    }

	    // 정렬 조건이 있으면 ORDER BY 절을 추가 (ex: id DESC)
	    if (ORDER_BY != null && !ORDER_BY.isEmpty()) {
	        sql.append(" ORDER BY ").append(ORDER_BY);
	    }

	    // 페이징 구현: LIMIT = 페이지 크기, OFFSET = 시작 행 번호
	    sql.append(" LIMIT ? OFFSET ?");

	    try (Connection connection = DatabaseUtil.getConnection();
	         PreparedStatement stmt = connection.prepareStatement(sql.toString())) {

	    	// LIMIT/OFFSET을 PreparedStatement 파라미터로 안전하게 바인딩
	        stmt.setInt(1, pageSize);
	        stmt.setInt(2, OFFSET);
	        // 쿼리 실행 및 결과값 획득
	        ResultSet rs = stmt.executeQuery();

	        // 제네릭 리스트 생성: mapper가 반환하는 DTO를 담을 컨테이너
	        List<T> list = new java.util.ArrayList<>();

	        // RowMapper<T> 인터페이스에 따라 한 행씩 DTO 변환
	        while (rs.next()) {
	            T item = mapper.mapRow(rs);
	            list.add(item);
	        }

	        // 결과 리스트 반환
	        return list;

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return java.util.Collections.emptyList();
	}
	
	// 인터페이스
	public interface RowMapper<T> {
		T mapRow(ResultSet resultSet) throws SQLException;
	// [AS-IS] 데이터베이스 쿼리 결과(ResultSet)를 사용자가 원하는 자바 객체로 변환
	}
}

