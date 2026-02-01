package dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;


/*
 * UserInfoRowMapper: 여러 Mapper를 조합해서 실행하는 컨테이너 
 * HandleAffairsMapper: 누구나 볼 수 있는 공통 필드 
 * AdminViewMapper: 관리자만 볼 수 있는 전용 필드
 */

@FunctionalInterface
public interface ColumnMapper<T> {
	// T 형태의 인자값을 받는 함수형 인터페이스 정의
	void map(ResultSet resultSet, T target) throws SQLException;
	// Dao에서 RowMapper 분리, Service 계층에서 실행
}

/*
public interface RowMapper<T> {
	T mapRow(ResultSet resultSet) throws SQLException;
}
*/
