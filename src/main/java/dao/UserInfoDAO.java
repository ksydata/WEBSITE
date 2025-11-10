package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.UserInfoDTO;
import dto.AcademicRecordDTO;
import util.DatabaseUtil;

// 사용자 로그인 시 아이디로 받아온 학번/사번(userID)으로 DB에 접근하여 사용자 1명의 정보를 UserInfoDTO에 담아 반환
public class UserInfoDAO {
	public UserInfoDTO getMyInfo(String userID) {
		// 학번/사번(userID)으로 나의 개인정보 페이지에서 조회 또는 수정할 정보 불러오는 SQL 쿼리 
		String userInfoSQL = "SELECT * FROM USER WHERE userID = ?";
		String personalInfoSQL = "SELECT * FROM PERSONAL_INFO WHERE userID = ?";

		// 로그인 성공했을 때 사용자 정보 활용
		UserInfoDTO userInfo = null;
    	
    	// USER 테이블에서 로그인 인증 관련 정보 추출
		try (Connection connection = DatabaseUtil.getConnection();
			 PreparedStatement userInfoStatement = connection.prepareStatement(userInfoSQL)) {	
			// 쿼리(where절 userID = ?)에 사번 포함
			userInfoStatement.setString(1, userID);
			
			try (ResultSet resultSet = userInfoStatement.executeQuery()) {
	            if (resultSet.next()) {
	            	// 사용자 정보가 존재할 경우 UserInfoDTO 객체 생성
	            	userInfo = new UserInfoDTO();
	            	userInfo.setUserID(resultSet.getString("userID"));
	            	userInfo.setUserPassword(resultSet.getString("userPassword"));
	            	userInfo.setName(resultSet.getString("name"));
	            	userInfo.setPhoneNumber(resultSet.getString("phoneNumber"));
	            	userInfo.setOfficeNumber(resultSet.getString("officeNumber"));	            	
	            	userInfo.setEmail(resultSet.getString("email"));
	            } else {
	            	// userID에 해당하는 사용자가 존재하지 않을 경우
	            	return null;
	            }
	        }				

		} catch (Exception e) {
			// 데이터베이스 오류 발생			
			e.printStackTrace();
			return null;
		}
		
		// PERSONAL_INFO 테이블에서 개인정보, 학사정보 추출
		try (Connection connection = DatabaseUtil.getConnection();
			 PreparedStatement personalInfoStatement = connection.prepareStatement(personalInfoSQL)) {
			// 쿼리(where절 userID = ?)에 사번 포함
	        personalInfoStatement.setString(1, userID);
	        
	        try (ResultSet resultSet = personalInfoStatement.executeQuery()) {
	            if (resultSet.next()) {
	            	// PERSONAL_INFO가 있을 경우에만 학사 정보 세팅
	            	userInfo.setCollege(resultSet.getString("college"));
	            	userInfo.setMajor(resultSet.getString("major"));
	            	userInfo.setStatus(resultSet.getString("status"));
	            	userInfo.setResidentNumber(resultSet.getString("residentNumber"));	                
	            	userInfo.setAddress(resultSet.getString("address"));
	            }
	        }
			
		} catch (Exception e) {
			// 데이터베이스 오류 발생			
			e.printStackTrace();
			return null;
		}
    	// 사용자 데이터 객체 반환
    	return userInfo;
	}

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

	
	public UserInfoDTO updateOfficeNumber(String userID, String officeNumber) {
		// 학번/사번(userID)으로 나의 개인정보 페이지에서 조회되는 정보(사무실 호실번호)를 수정하는 SQL 쿼리
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
	
	
	public UserInfoDTO updateEmail(String userID, String email) {
		// 학번/사번(userID)으로 나의 개인정보 페이지에서 조회되는 정보(이메일 주소)를 수정하는 SQL 쿼리
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
	
	public UserInfoDTO updateAddress(String userID, String address) {
		// 학번/사번(userID)으로 나의 개인정보 페이지에서 조회되는 정보(주소)를 수정하는 SQL 쿼리
		String updateQuery = "UPDATE USER SET address = ? WHERE userID = ?";
		
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
	
	public UserInfoDTO updatePassword(String userID, String userPassword) {
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
    
    public List<AcademicRecordDTO> getAcademicRecordsByCollege(String userInfoID)
    	//	, String sortColumn, String sortOrder) {
    {
        // [수정 진행중] 정렬 파리미터인 sortColumn, sortOrder 반영
    	// List<String> sortColumns = List.of("userID", "grade", "gradePoint", "academicYear", "semester");
    	// 허용되지 않은 컬럼으로 정렬 시에는 기본값으로 수강학기로 필터링되도록 설정
    	// if (!sortColumns.contains(sortColumn)) sortColumn = "semester";

        // 동일 단과대학에 해당하는 수강생 정보를 가져오는 쿼리    	
    	String recordQuery = """
			SELECT
				U.userID, U.name, P.college, P.major,
			    R.courseName, R.grade, R.gradePoint, R.academicYear, R.semester
			FROM USER U
			JOIN PERSONAL_INFO P ON U.userID = P.userID
			JOIN ACADEMIC_RECORD R ON U.userID = R.userID
			WHERE P.college IN (
				SELECT DISTINCT college FROM PERSONAL_INFO WHERE userID = ?
			) 
			ORDER BY R.semester DESC, U.name ASC""";
        List<AcademicRecordDTO> classRecordsList = new ArrayList<>();
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(recordQuery)) {
            
            preparedStatement.setString(1, userInfoID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                AcademicRecordDTO dto = new AcademicRecordDTO();
                dto.setUserID(resultSet.getString("userID"));
                dto.setName(resultSet.getString("name"));
                dto.setCollege(resultSet.getString("college"));
                dto.setMajor(resultSet.getString("major"));
                dto.setCourseName(resultSet.getString("courseName"));
                dto.setGrade(resultSet.getString("grade"));
                dto.setAcademicYear(resultSet.getInt("academicYear"));
                dto.setGradePoint(resultSet.getInt("gradePoint"));
                dto.setSemester(resultSet.getString("semester"));
                classRecordsList.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classRecordsList;
    }
    
    public List<AcademicRecordDTO> getAcademicRecordsByCollegeWithPaging(String userInfoID, int limit, int offset) {
        String recordQuery = """
            SELECT
                U.userID, U.name, P.college, P.major,
                R.courseName, R.grade, R.gradePoint, R.academicYear, R.semester
            FROM USER U
            JOIN PERSONAL_INFO P ON U.userID = P.userID
            JOIN ACADEMIC_RECORD R ON U.userID = R.userID
            WHERE P.college IN (
                SELECT DISTINCT college FROM PERSONAL_INFO WHERE userID = ?
            )
            ORDER BY R.semester DESC, U.name ASC
            LIMIT ? OFFSET ?
        """;

        List<AcademicRecordDTO> classRecordsList = new ArrayList<>();
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(recordQuery)) {
            
            preparedStatement.setString(1, userInfoID);
            preparedStatement.setInt(2, limit);
            preparedStatement.setInt(3, offset);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                AcademicRecordDTO dto = new AcademicRecordDTO();
                dto.setUserID(resultSet.getString("userID"));
                dto.setName(resultSet.getString("name"));
                dto.setCollege(resultSet.getString("college"));
                dto.setMajor(resultSet.getString("major"));
                dto.setCourseName(resultSet.getString("courseName"));
                dto.setGrade(resultSet.getString("grade"));
                dto.setAcademicYear(resultSet.getInt("academicYear"));
                dto.setGradePoint(resultSet.getInt("gradePoint"));
                dto.setSemester(resultSet.getString("semester"));
                classRecordsList.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classRecordsList;
    }
    
    public int getAcademicRecordsCountByCollege(String userInfoID) {
        String countQuery = """
            SELECT COUNT(*) AS total
            FROM USER U
            JOIN PERSONAL_INFO P ON U.userID = P.userID
            JOIN ACADEMIC_RECORD R ON U.userID = R.userID
            WHERE P.college IN (
                SELECT DISTINCT college FROM PERSONAL_INFO WHERE userID = ?
            )
        """;

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(countQuery)) {

            preparedStatement.setString(1, userInfoID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0; // 기본값
    }
}