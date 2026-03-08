package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import dao.PagingDAO.RowMapper;
import dao.mapper.UserInfoRowMapper;
import dao.mapper.HandleAffairsMapper;
import dao.mapper.AdminViewMapper;

import dto.UserInfoDTO;
import util.DatabaseUtil;
import util.ValidatePassword;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// [AS-IS] 사용자 로그인 시 아이디로 받아온 학번/사번(userID)으로 DB에 접근하여 사용자 1명의 정보를 UserInfoDTO에 담아 반환
// [TO-BE] DAO는 단일 책임 원칙 유지하면서 역할과 목적 차이에 따라 쿼리와 매핑 전략으로 분리
public class UserInfoDAO {
	// 1. 사용자의 본인 개인정보 조회(페이징 없음)
	public UserInfoDTO getMyInfo(String userID, RowMapper<UserInfoDTO> mapper) {
		// List<UserInfoDTO> userInfoList = new ArrayList<>();
		// 로그인 성공했을 때 사용자 개인정보를 받기 위한 빈 배열 객체 생성(초기화)		
		
		// 학번/사번(userID)으로 나의 개인정보 페이지에서 조회 또는 수정할 정보 불러오는 SQL 쿼리 
		String userInfoSQL = """
				SELECT U.*, P.*
				FROM USER U 
				LEFT JOIN PERSONAL_INFO P
				ON U.userID = P.userID 
				WHERE U.userID = ?
		""";
		// String userInfoSQL = "SELECT * FROM USER WHERE userID = ?";
		// String personalInfoSQL = "SELECT * FROM PERSONAL_INFO WHERE userID = ?";
    	
    	// USER 테이블에서 로그인 인증 관련 정보 추출 & PERSONAL_INFO 테이블에서 개인정보, 학사정보 추출
		try (Connection connection = DatabaseUtil.getConnection();
			 PreparedStatement userInfoStatement = connection.prepareStatement(userInfoSQL)) {	
			userInfoStatement.setString(1, userID);
			ResultSet resultSet = userInfoStatement.executeQuery();
			// 쿼리(where절 userID = ?)에 사번 포함하여 쿼리 실행결과 담을 객체 생성
			
			if (resultSet.next()) {
				return mapper.mapRow(resultSet);
	        } else {
	        	throw new NoSuchElementException("해당 사용자가 존재하지 않습니다.");
	        	// userID에 해당하는 사용자가 존재하지 않을 경우
	        }
		} catch (Exception e) {
			// 데이터베이스 오류 발생			
			throw new RuntimeException("사용자 정보를 조회할 수 없습니다.", e);
		}
	}
	
	// 2. 관리자의 전체 사용자 개인정보 조회(페이징 적용)
	// DAO와 Service 중복으로 삭제
	
	// 3. Row-to-DTO 매핑을 분리하는 RowMapper 패턴
	// DAO의 구조를 유지하면서 Service(Paging, UserInfo)에서 RowMapper 재사용
	public static final RowMapper<UserInfoDTO> COMMON_ROW_MAPPER = 
			new UserInfoRowMapper(List.of(
					HandleAffairsMapper.HANDLE_AFFAIRS)
	);
	
	public static final RowMapper<UserInfoDTO> FULL_ROW_MAPPER = 
			new UserInfoRowMapper(List.of(
					HandleAffairsMapper.HANDLE_AFFAIRS,
				    AdminViewMapper.ADMIN_VIEW)
	);
    
	/* [AS-IS] 확장 불가 RowMapper 패턴
	public static final RowMapper<UserInfoDTO> ROW_MAPPER = resultSet -> {
		UserInfoDTO userInfo = new UserInfoDTO();
		// 개인정보 테이블 연결 객체 생성
		    	
    	userInfo.setUserID(resultSet.getString("userID"));
    	userInfo.setUserPassword(resultSet.getString("userPassword"));
    	userInfo.setName(resultSet.getString("name"));
    	userInfo.setPhoneNumber(resultSet.getString("phoneNumber"));
    	userInfo.setOfficeNumber(resultSet.getString("officeNumber"));	            	
    	userInfo.setEmail(resultSet.getString("email"));		
    	// User 테이블 필드(개인정보)
    	
    	userInfo.setCollege(resultSet.getString("college"));
    	userInfo.setMajor(resultSet.getString("major"));
    	userInfo.setStatus(resultSet.getString("status"));
    	userInfo.setResidentNumber(resultSet.getString("residentNumber"));	                
    	userInfo.setAddress(resultSet.getString("address"));
    	// PERSONAL_INFO 테이블 필드(학사정보)
    	
    	return userInfo;
    	// 개인정보 배열 객체 반환    	
	};
	*/

	
	// 4. 사용자의 본인 개인정보 수정
	// [TO-BE] 하나의 동적 쿼리로 변경된 필드만 업데이트(Java Persistence API [X] → Eclipse + JDBC)
	// @SuppressWarnings("finally")
	public void updateUserInfo(String userID, String phoneNumber, String officeNumber, 
            String email, String address) {
		Connection connection = null;
		// DB 연결 객체 초기화
		
		// 두 테이블 업데이트를 단일 트랜잭션으로 처리
		try {
			connection = DatabaseUtil.getConnection();
			connection.setAutoCommit(false);
			// 트랜잭션 시작하여 자동 커밋 비활성화(같은 연결 객체로 실행되는 모든 DML문 커밋 또는 롤백 대상)
			
			List<String> setClauses = new ArrayList<>();
			// 수정대상 개인정보별 update 쿼리 형식 자체는 동일함
			// UPDATE TABLE SET {parameters} = ? WHERE userID = ?
			List<Object> parameters = new ArrayList<>();
			// 파라미터 빈 배열 리스트 생성
			
			// USER 테이블: 나의 개인정보 페이지에서 조회되는 정보(휴대전화 번호, 사무실 내선번호, 이메일)
			if (phoneNumber != null && !phoneNumber.trim().isEmpty()) {
				setClauses.add("phoneNumber = ?");
				parameters.add(phoneNumber);
			}
			// 각 변경대상 입력값이 유효하면 SET절에 추가
			
			if (officeNumber != null && !officeNumber.trim().isEmpty()) {
				setClauses.add("officeNumber = ?");
				parameters.add(officeNumber);
			}
			
			if (email != null && !email.trim().isEmpty()) {
				setClauses.add("email = ?");
				parameters.add(email);
			}
			// 프로젝트 규모가 확대되면, UserUpdateField 클래스 신규 생성하고 List에 각 컬럼명을 조건절 리스트 및 파라미터 리스트에 추가하는 반복문 필요
			// List<UserUpdateField> fields = List.of(
				    // new UserUpdateField("phoneNumber", phoneNumber),
				    // new UserUpdateField("officeNumber", officeNumber),
				    // new UserUpdateField("email", email) );
			
			if (!setClauses.isEmpty()) {
				// USER 테이블에서 수정한 컬럼이 있는 경우
				String userInfoUpdateSQL = "UPDATE USER SET " + String.join(", ", setClauses) + "WHERE userID = ?";
				// 해당 학번/사번(userID) 사용자 개인정보 수정하는 동적 쿼리 실행
				
				parameters.add(userID);
				// WHERE절 바인딩 파라미터 추가
				
				try (PreparedStatement updateStatement = connection.prepareStatement(userInfoUpdateSQL)) {
					for (int i = 0; i < parameters.size(); i++) {
						// for문 활용하여 파라미터 바인딩
						updateStatement.setObject(
								i+1, 
								parameters.get(i));
						// UPDATE USER SET phoneNumber = ?, officeNumber = ?, email = ? WHERE userID = ?
						// ["010-1234-5678", "123", "user@univ.com", "2025001"]
					}
					updateStatement.executeUpdate();
					// 쿼리 실행
				}
			}
			
			// PERSONAL_INFO 테이블: 나의 개인정보 페이지에서 조회되는 정보(휴대전화 번호, 사무실 내선번호, 이메일)
			if (address != null && !address.trim().isEmpty()) {
				String addressUpadteSQL = "UPDATE PERSONAL_INFO SET address = ? WHERE userID = ?";
				
				try (PreparedStatement updateStatement = connection.prepareStatement(addressUpadteSQL)) {
					updateStatement.setString(1, address);
					updateStatement.setString(2, userID);
					updateStatement.executeUpdate();
				}
			}
			connection.commit();
			// 모든 DML 쿼리 업데이트

		} catch(Exception e) {
			try { if (connection != null) connection.rollback(); }
			catch (SQLException ignore) {}
			// 예외 발생 시 트랜잭션 롤백
			throw new RuntimeException("개인정보 수정에 실패하였습니다.", e);

		} finally {
			try { if (connection != null) connection.close(); }
			catch (SQLException ignore) {}
			// 트랜잭션 종료 후 자원 해제
		}
	}	
	
	/* [AS-IS]
	public UserInfoDTO updatePhoneNumber(String userID, String phoneNumber) {
		// 학번/사번(userID)으로 나의 개인정보 페이지에서 조회되는 정보(휴대전화번호)를 수정하는 SQL 쿼리
		String updateQuery = "UPDATE USER SET phoneNumber = ? WHERE userID = ?";
		
	    try (Connection connection = DatabaseUtil.getConnection();
	         PreparedStatement statement = connection.prepareStatement(updateQuery)) {
			statement.setString(1, phoneNumber);
			statement.setString(2, userID);
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;	    
	}
	*/
	
	// 5. 사용자의 본인 비밀번호 변경 전 검증
	public boolean checkCurrentPassword(String userID, String inputPassword) {
		// 비밀번호 변경 전 현재 비밀번호에 대한 확인용 메서드
		String checkQuery = "SELECT userPassword FROM USER WHERE userID = ?";
		
		try (Connection connection = DatabaseUtil.getConnection();
			 PreparedStatement statement = connection.prepareStatement(checkQuery)) {
			// SQL쿼리 조건절에 ID값을 삽입하여 사번 설정
			statement.setString(1, userID);
			// 쿼리를 실행하고 결과를 저장
			ResultSet resultSet = statement.executeQuery();
			// 현재 비밀번호와 입력된 비밀번호 비교
			if (resultSet.next()) {
				// 해당 사번(아이디)에 매칭되는 비밀번호를 변수로 저장
				String dbPassword = resultSet.getString("userPassword");
				// 사용자에게 입력받은 비밀번호와 데이터베이스에 저장된 비밀번호 같은지 비교하여 T/F 반환
				return dbPassword.equals(hashPassword(inputPassword));
			}
		} catch (Exception e) {
			// 예외 발생 시 에러 메시지 반환
			e.printStackTrace();
		}
		// 기본값은 false 반환
		return false;
	}	
	
	// 6. 사용자의 본인 비밀번호 변경
	public UserInfoDTO updatePassword(String userID, String newPassword) {
        if (!ValidatePassword.isValidByRegex(newPassword) || !ValidatePassword.isValidById(userID, newPassword)) {
            throw new IllegalArgumentException("새 비밀번호가 정책에 맞지 않습니다.");
        }
		// 학번/사번(userID)으로 나의 개인정보 페이지에서 조회되는 비밀번호를 변경하는 SQL 쿼리
		String updateQuery = "UPDATE USER SET userPassword = ? WHERE userID = ?";
		
	    try (Connection connection = DatabaseUtil.getConnection();
	         PreparedStatement statement = connection.prepareStatement(updateQuery)) {
			statement.setString(1, hashPassword(newPassword));
			statement.setString(2, userID);
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;	    
	}

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }
}
