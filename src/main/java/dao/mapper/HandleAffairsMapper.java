package dao.mapper;

import dto.UserInfoDTO;
import util.MaskingUtil;


public class HandleAffairsMapper {
	public static final ColumnMapper<UserInfoDTO> HANDLE_AFFAIRS = (resultSet, dto) -> {		
		dto.setUserID(resultSet.getString("userID"));
		dto.setName(resultSet.getString("name"));
		// dto.setPhoneNumber(resultSet.getString("phoneNumber"));
		dto.setOfficeNumber(resultSet.getString("officeNumber"));	            	
		dto.setEmail(resultSet.getString("email"));		
		// User 테이블 필드(개인정보)
	
		dto.setCollege(resultSet.getString("college"));
		dto.setMajor(resultSet.getString("major"));
		dto.setStatus(resultSet.getString("status"));
		dto.setAddress(resultSet.getString("address"));		
		// dto.setResidentNumber(resultSet.getString("residentNumber"));	                
		// PERSONAL_INFO 테이블 필드(학사정보): 마스킹
		
		dto.setPhoneNumber(MaskingUtil.maskPhoneNumber(dto.getPhoneNumber()));
		dto.setResidentNumber(MaskingUtil.maskResidentNumber(dto.getResidentNumber()));		
		// 개인정보 마스킹
	};
}