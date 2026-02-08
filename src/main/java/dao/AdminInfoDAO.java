package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.AdminPersonalInfoDTO;
import util.DatabaseUtil;

public class AdminInfoDAO {
	
	// userID를 통해 유저별 개인정보 페이지 정보 가져오기
		public AdminPersonalInfoDTO getUserInfo(String userID) {
			// 결과값 담기
			AdminPersonalInfoDTO admin = null;
			
			// USER 테이블에서 정보 가져오기
			String userInfoSQL = "SELECT * FROM USER WHERE userID = ?";
			
			try (Connection connection = DatabaseUtil.getConnection();
				PreparedStatement userInfoStatement = connection.prepareStatement(userInfoSQL)) {
				
				userInfoStatement.setString(1, userID);

				try (ResultSet resultSet = userInfoStatement.executeQuery()) {
		            if (resultSet.next()) {
		            	// 사용자 정보가 존재할 경우 StudentDTO 객체 생성
		            	admin = new AdminPersonalInfoDTO();
		                admin.setUserID(resultSet.getString("userID"));
		                admin.setUserPassword(resultSet.getString("userPassword"));
		                admin.setName(resultSet.getString("name"));
		                admin.setPhoneNumber(resultSet.getString("phoneNumber"));
		                admin.setOfficeNumber(resultSet.getString("officeNumber"));
		                admin.setEmail(resultSet.getString("email"));
		                admin.setUserRole(resultSet.getString("role"));
		            } else {
		            	// userID에 해당하는 사용자가 존재하지 않을 경우
		            	return null;
		            }
		        }			
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			
			
			// PERSONAL_INFO 테이블에서 정보 가져오기
			String personalInfoSQL = "SELECT * FROM PERSONAL_INFO WHERE userID = ?";
			
			try (Connection connection = DatabaseUtil.getConnection();
				 PreparedStatement personalInfoStatement = connection.prepareStatement(personalInfoSQL)) {
				 // 쿼리(where절 userID = ?)에 학번 포함
		         personalInfoStatement.setString(1, userID);
		        
		         try (ResultSet resultSet = personalInfoStatement.executeQuery()) {
		            if (resultSet.next()) {
		            	// PERSONAL_INFO가 있을 경우에만 학사 정보 세팅
		                admin.setCollege(resultSet.getString("college"));
		                admin.setMajor(resultSet.getString("major"));
		                admin.setAdmissionYear(resultSet.getInt("admissionYear"));
		                admin.setStatus(resultSet.getString("status"));
		                admin.setResidentNumber(resultSet.getString("residentNumber"));	                
		                admin.setAddress(resultSet.getString("address"));
		            }
		        }
					
				} catch (Exception e) {
					// 데이터베이스 오류 발생			
					e.printStackTrace();
				}
				

			// 관리자용 유저 페이지 데이터 객체 반환
			return admin;
			
		}
		

	public AdminPersonalInfoDTO updatePhoneNumber(String userID, String phoneNumber) {
		// 학번/사번(userID)으로 나의 개인정보 페이지에서 조회되는 정보를 수정하는 SQL 쿼리
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
	
	public AdminPersonalInfoDTO updateOfficeNumber(String userID, String officeNumber) {
		// 학번/사번(userID)으로 나의 개인정보 페이지에서 조회되는 정보를 수정하는 SQL 쿼리
		String updateQuery = "UPDATE USER SET officeNumber = ? WHERE userID = ?";
		
	    try (Connection connection = DatabaseUtil.getConnection();
	         PreparedStatement statement = connection.prepareStatement(updateQuery)) {
			statement.setString(1, officeNumber);
			statement.setString(2, userID);
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;	    
	}
	
	public AdminPersonalInfoDTO updateEmail(String userID, String email) {
		// 학번/사번(userID)으로 나의 개인정보 페이지에서 조회되는 정보를 수정하는 SQL 쿼리
		String updateQuery = "UPDATE USER SET email = ? WHERE userID = ?";
		
	    try (Connection connection = DatabaseUtil.getConnection();
		     PreparedStatement statement = connection.prepareStatement(updateQuery)) {
			statement.setString(1, email);
			statement.setString(2, userID);
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;	    
	}
	
	public AdminPersonalInfoDTO updateAddress(String userID, String address) {
		// 학번/사번(userID)으로 나의 개인정보 페이지에서 조회되는 정보를 수정하는 SQL 쿼리
		String updateQuery = "UPDATE personal_info SET address = ? WHERE userID = ?";
		
	    try (Connection connection = DatabaseUtil.getConnection();
		     PreparedStatement statement = connection.prepareStatement(updateQuery)) {
			statement.setString(1, address);
			statement.setString(2, userID);
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
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
				return dbPassword.equals(inputPassword);
			}
		} catch (Exception e) {
			// 예외 발생 시 에러 메시지 반환
			e.printStackTrace();
		}
		// 기본값은 false 반환
		return false;
	}	
	
	public AdminPersonalInfoDTO updatePassword(String userID, String userPassword) {
		// 학번/사번(userID)으로 나의 개인정보 페이지에서 조회되는 비밀번호를 변경하는 SQL 쿼리
		String updateQuery = "UPDATE USER SET userPassword = ? WHERE userID = ?";
		
	    try (Connection connection = DatabaseUtil.getConnection();
	         PreparedStatement statement = connection.prepareStatement(updateQuery)) {
			statement.setString(1, userPassword);
			statement.setString(2, userID);
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;	    
	}
	
	
}
