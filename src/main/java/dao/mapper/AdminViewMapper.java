package dao.mapper;

import dto.UserInfoDTO;

public class AdminViewMapper {
	public static final ColumnMapper<UserInfoDTO> ADMIN_VIEW = (resultSet, dto) -> {
		dto.setUserPassword(resultSet.getString("userPassword"));
		// User 테이블 필드(개인정보)
	
		dto.setResidentNumber(resultSet.getString("residentNumber"));	                
		// PERSONAL_INFO 테이블 필드(학사정보)
	};
}