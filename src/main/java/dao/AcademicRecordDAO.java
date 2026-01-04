package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.AcademicRecordDTO;
import util.DatabaseUtil;

public class AcademicRecordDAO {
	// 1. 학생 본인의 학사정보 조회(페이징 없음)
	public List<AcademicRecordDTO> getRecordByStudent(String userID) {
		// 학번(userID)으로 나의 학사정보 페이지에서 조회할 정보 불러오는 SQL 쿼리
		// 학사정보 객체를 받기 위한 빈 배열 객체 생성 
		List<AcademicRecordDTO> recordList = new ArrayList<>();
		String viewQuery = "SELECT * FROM ACADEMIC_RECORD WHERE userID = ?";

		// ACADEMIC_RECORD 테이블에서 학사정보 추출
		try (Connection connection = DatabaseUtil.getConnection();
			 PreparedStatement checkStatement = connection.prepareStatement(viewQuery)) {
			// 쿼리(where절 userID = ?)에 학번 포함
			checkStatement.setString(1, userID);
			ResultSet resultSet = checkStatement.executeQuery();

			while (resultSet.next()) {
			    AcademicRecordDTO record = mapRow(resultSet);
				// AcademicRecordDTO record = new AcademicRecordDTO();
			    recordList.add(record);				
			    // recordList.add(record);	
				// 학사정보 배열에 저장
			}

		} catch (SQLException e) {
			// 데이터베이스 오류 발생			
			e.printStackTrace();
		}
    	// 학사 데이터 배열 객체 반환
		return recordList;
	}


	// 2. 교수의 단과대학 학생의 학사정보 조회(페이징 적용)
    public List<AcademicRecordDTO> getRecordsByCollege(String college, int limit, int offset) {
		// 학사정보 객체를 받기 위한 빈 배열 객체 생성 
		List<AcademicRecordDTO> recordList = new ArrayList<>();
		// 단과대학 학생 학사정보 페이지에서 조회할 정보 불러오는 SQL 쿼리
		String viewQuery = """
				SELECT A.*
				FROM ACADEMIC_RECORD A 
				JOIN PERSONAL_INFO P ON A.userID = P.userID
				WHERE P.college ?
				ORDER BY A.academicYear DESC, P.semester DESC
				LIMIT ? OFFSET ?
		""";

		// ACADEMIC_RECORD 테이블에서 학사정보 추출
		try (Connection connection = DatabaseUtil.getConnection();
			 PreparedStatement checkStatement = connection.prepareStatement(viewQuery)) {
			// 쿼리(where절 college = ?)에 단과대학명 포함
			checkStatement.setString(1, college);
			// 학사정보 리스트 페이징을 위한 limit(한 페이지에 보여줄 최대 개수), offset(그 개수만큼 건너뛸 행의 수)
			checkStatement.setInt(2, limit);
			checkStatement.setInt(3, offset);

			ResultSet resultSet = checkStatement.executeQuery();

			while (resultSet.next()) {
			    AcademicRecordDTO record = mapRow(resultSet);
				// AcademicRecordDTO record = new AcademicRecordDTO();
			    recordList.add(record);				
			    // recordList.add(record);	
				// 학사정보 배열에 저장
			}

		} catch (SQLException e) {
			// 데이터베이스 오류 발생			
			e.printStackTrace();
		}
    	// 학사 데이터 배열 객체 반환
		return recordList;
	}	


	// 3. 관리자의 전체 학생의 학사정보 조회(페이징 적용)
    public List<AcademicRecordDTO> getTotalRecords(int limit, int offset) {
		// 학사정보 객체를 받기 위한 빈 배열 객체 생성 
		List<AcademicRecordDTO> recordList = new ArrayList<>();
		// 단과대학 학생 학사정보 페이지에서 조회할 정보 불러오는 SQL 쿼리
		String viewQuery = """
				SELECT *
				FROM ACADEMIC_RECORD
				ORDER BY academicYear DESC, semester DESC
				LIMIT ? OFFSET ?
		""";

		// ACADEMIC_RECORD 테이블에서 학사정보 추출
		try (Connection connection = DatabaseUtil.getConnection();
			 PreparedStatement checkStatement = connection.prepareStatement(viewQuery)) {
			// 학사정보 리스트 페이징을 위한 limit(한 페이지에 보여줄 최대 개수), offset(그 개수만큼 건너뛸 행의 수)
			checkStatement.setInt(1, limit);
			checkStatement.setInt(2, offset);

			ResultSet resultSet = checkStatement.executeQuery();

			while (resultSet.next()) {
			    AcademicRecordDTO record = mapRow(resultSet);
				// AcademicRecordDTO record = new AcademicRecordDTO();
			    recordList.add(record);				
			    // recordList.add(record);	
				// 학사정보 배열에 저장
			}

		} catch (SQLException e) {
			// 데이터베이스 오류 발생			
			e.printStackTrace();
		}
    	// 학사 데이터 배열 객체 반환
		return recordList;
	}

	// 4. Row-to-DTO 매핑을 분리하는 RowMapper 패턴
    private AcademicRecordDTO mapRow(ResultSet resultSet) throws SQLException {
    	AcademicRecordDTO record = new AcademicRecordDTO();
    	// 학사정보 테이블 연결 객체 생성
		record.setUserID(resultSet.getString("userID"));
		record.setCollege(resultSet.getString("college"));
		record.setMajor(resultSet.getString("major"));
		record.setAcademicYear(resultSet.getInt("academicYear"));
		record.setSemester(resultSet.getString("semester"));
		record.setCourseID(resultSet.getInt("courseID"));
		record.setCourseName(resultSet.getString("courseName"));
		record.setCourseType(resultSet.getString("courseType"));
		record.setCoursePF(resultSet.getString("coursePF"));
		record.setPassOrFail(resultSet.getBoolean("pass_or_fail"));
		record.setGrade(resultSet.getString("grade"));
		record.setGradePoint(resultSet.getFloat("gradePoint"));
		record.setRetakeYear(resultSet.getInt("retakeYear"));
		record.setRetakeSemester(resultSet.getString("retakeSemester"));
		record.setRetakeCourseID(resultSet.getInt("retakeCourseID"));
		record.setEnrollmentReason(resultSet.getString("enrollmentReason"));
    	// 테이블 내 학생별 학사정보 필드 설정
    	return record;
    }
    
    
    // 5. 교수의 단과대학 학생 정보 수정 메서드
    /*
     * 성적 정보 수정 예시 쿼리: 
     * - 일반적인 A~F 판정 과목 : UPDATE academic_record SET grade = 'A', gradePoint = 4.0 WHERE recordID = 12345;
     * - P/F 과목 : UPDATE academic_record SET pass_or_fail = 'P', grade = NULL, gradePoint = NULL WHERE userID = 'U20230001' AND courseID = 'PE001';
     * - WHERE userID = 'U20230001' AND courseID = 'PE001' 과 같은 형태로 과목을 분류할 수도 있겠으나 recordID를 가져오는 게 가장 명확함
     */
    
    /*
     * < 추가 제언 >
     * 1. DTO에 recordID 넣어서 성적 판정 건별 구별 가능하게 하기 (V)
     * 2. 수정대상 성적인 ACADEMIC_RECORD에서 grade 알파벳 (A, B, B+, C 등)에 따라 gradePoint 숫자가 매겨지는 로직을 매겨야 함. 
     * - 이를 위해 특정 알파벳에 특정 숫자를 매기는 규칙을 만들어 정해야 함 
     * -> 성적 부여를 할 때 버튼으로 A+, A, B+ 등 학점을 선택하게 하고, 그 선택한 결과를 서비스로 받아와서 서비스에서 DAO를 통해 학점 수정을 하도록 함
     * 
     */
    // 상대평가 과목 (A, B, C 등) 성적 수정 코드
    public boolean updateRecord(String grade, float gradePoint, String recordID) {
    	String sql = "UPDATE ACADEMIC_RECORD SET grade = ?, gradePoint = ? where recordID = ?";
    	
    	try (Connection conn = DatabaseUtil.getConnection();
    		 PreparedStatement updateStatement = conn.prepareStatement(sql)) {
    		updateStatement.setString(1, grade);
    		updateStatement.setDouble(2, gradePoint);
    		updateStatement.setString(3, recordID);
    		
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return false;
    }
    
    // P/F 과목 성적 수정 코드
    public boolean updateRecordPF(boolean passOrFail, String recordID) {
    	String sql = "UPDATE ACADEMIC_RECORD SET pass_or_fail = ? WHERE recordID = ?";
    	
    	try (Connection conn = DatabaseUtil.getConnection();
    		 PreparedStatement updateStatement = conn.prepareStatement(sql)) {
    		updateStatement.setBoolean(1, passOrFail);
    		updateStatement.setString(2, recordID);
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
    			
    }
}

/*
 * public interface RowMapper<T> {
	T mapRow(ResultSet resultSet, int rowNum) throws SQLException;
	// [AS-IS] 데이터베이스 쿼리 결과(ResultSet)를 사용자가 원하는 자바 객체로 변환
}
 */    