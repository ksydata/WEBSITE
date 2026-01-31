package dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

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
