package dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import dao.PagingDAO.RowMapper;
import dto.UserInfoDTO;

// PagingDAO와 UserInfo 연결하는 RowMapper
public class UserInfoRowMapper implements RowMapper<UserInfoDTO> {
	// RowMapper<UserInfoDTO> 인터페이스를 구현, DB 조회결과(ResultSet)DTO로 변환
	private final List<ColumnMapper<UserInfoDTO>> mappers;
	// 여러 컬럼을 DTO에 매핑하기 위한 ColumnMapper 리스트
	// ColumnMapper: 특정 컬럼을 DTO의 특정 필드에 매핑하기 위한 객체
	
	public UserInfoRowMapper(List<ColumnMapper<UserInfoDTO>> mappers) {
		this.mappers = mappers;
		// 컬럼 매퍼 리스트를 받아서 초기화하는 생성자
	}
	
	public UserInfoDTO mapRow(ResultSet resultSet) throws SQLException {
		UserInfoDTO userInfo = new UserInfoDTO();
		// DTO 객체 생성
		
		for (ColumnMapper<UserInfoDTO> mapper : mappers) {
			mapper.map(resultSet, userInfo);
			// 모든 ColumnMapper를 순회하여 DTO에 값 매핑
		}
		return userInfo;
		// 매핑완료된 DTO 객체 반환
	}
}
