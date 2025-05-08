package dao;

import java.sql.Time;
import java.sql.Time;

import dto.StudentDTO;
import user.UserDTO;
import util.DatabaseUtil;

public class StudentDAO {
	public StudentDTO post(String userID, String college, String major, Time admissionYear, String status, 
			String userPaswword, String name, String residentNumber, String phoneNumber, String email, String address) {
		// 로그인 성공했을 때 사용자 정보 활용
    	StudentDTO student = new StudentDTO();
	}
}