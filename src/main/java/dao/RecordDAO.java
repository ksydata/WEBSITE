package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.RecordDTO;
import util.DatabaseUtil;

public class RecordDAO {
	
	// 학생별 공지 리스트 조회
	public List<RecordDTO> getRecordsByStudent(String userID) {
		List<RecordDTO> recordList = new ArrayList<>();
		String sql = "SELECT * FROM ACADEMIC_RECORD WHERE userID = ?";
		
		try (Connection connection = DatabaseUtil.getConnection();
			PreparedStatement checkStatement = connection.prepareStatement(sql)) {
			checkStatement.setString(1, userID);
			ResultSet resultSet = checkStatement.executeQuery();
			
			while (resultSet.next()) {
				RecordDTO record = new RecordDTO();
				record.setRecordID(resultSet.getInt("recordID"));
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
				
				recordList.add(record);
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return recordList;
	}

}
