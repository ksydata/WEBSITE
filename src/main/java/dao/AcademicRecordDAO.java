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
	// 1. 학생 본인의 학사정보 조회
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
				AcademicRecordDTO record = new AcademicRecordDTO();
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
				
				// 학사정보 배열에 저장
				recordList.add(record);	
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
				AcademicRecordDTO record = new AcademicRecordDTO();
				// AcademicRecordDTO record = mapRow(resultSet);
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
				
				// 학사정보 배열에 저장
				recordList.add(record);	
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
				SELECT A.*
				FROM ACADEMIC_RECORD
				ORDER BY A.academicYear DESC, P.semester DESC
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
				AcademicRecordDTO record = new AcademicRecordDTO();
				// AcademicRecordDTO record = mapRow(resultSet);
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
				
				// 학사정보 배열에 저장
				recordList.add(record);	
			}
			
		} catch (SQLException e) {
			// 데이터베이스 오류 발생			
			e.printStackTrace();
		}
    	// 학사 데이터 배열 객체 반환
		return recordList;
	}
}
