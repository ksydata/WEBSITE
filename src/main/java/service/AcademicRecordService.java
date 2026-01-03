package service;
//AcademicRecord과 AcademicRecordDAO 사이에서 비즈니스 로직

import dao.AcademicRecordDAO;
import dto.AcademicRecordDTO;
import util.RoleEnum;


/*
 * 1. 기본 권한별 정보 조회기능 3가지 분기 외에 [v]
 * 2. 교수의 단과대학 학생 정보 수정 기능 및 
 * 3. 검증 기능 필요
 * 4. 페이징 로직 limit, offset 변수 추가 이후 academicRecordServlet, PagingService와 연계 필요 
 */

// [AS-IS] 개인정보 일부(전화번호, 주민등록번호 뒷자리) 마스킹할 때는 서비스단에서 처리
public class AcademicRecordService {
	private AcademicRecordDAO academicRecordDAO;
	// 개인정보 조회 메서드
	// 생성자: userInfoDAO 객체를 초기화 (DB 접근을 위해 필요)
	public AcademicRecordService() {
		academicRecordDAO = new AcademicRecordDAO();
	}

	
 // 학번/사번(userID)를 받아 학사정보를 가져오는 서비스 메서드
 public AcademicRecordDTO getAcademicRecord(String userID, RoleEnum role, String college, 
		 int limit, int offset) {
     if (userID == null || userID.isEmpty()) {
         // userID(사용자 인증을 위한 아이디_학번/사번)가 비어있으면 null 반환
         return null;
     }
 	
    // RoleEnum role_001 = RoleEnum.ROLE_001;
 	// String role_001 = Constants.ROLE_001;
 	// 사용자 권한별 역할 상수 불러오기 → ENUM은 독립된 객체로 필요없음
    // DAO를 통해 DB에서 사용자 권한별 학사정보 조회
    switch(role) {
    	case ROLE_001:	 
     	// case "student":	 
    		AcademicRecordDTO myRecord = (AcademicRecordDTO) academicRecordDAO.getRecordByStudent(userID);
    		return myRecord;
    	case ROLE_002:	
    		AcademicRecordDTO classRecords = (AcademicRecordDTO) academicRecordDAO.getRecordsByCollege(college, limit, offset);
    		return classRecords;
    	case ROLE_004:
    		AcademicRecordDTO studentsRecords = (AcademicRecordDTO) academicRecordDAO.getTotalRecords(limit, offset);
    		return studentsRecords;
    	default:
    		break;    		 
     }
    return null;
    }
 
	// 교수의 단과대학 학생 정보 수정 메서드
	public void updateRecord(String recordID, boolean PF, String grade, boolean passOrFail) {
		if (recordID == null || recordID.isEmpty()) {
			// recordID가 비어있으면 null 반환
			return;
		}
		
		// DAO를 통해 성적 수정
		AcademicRecordDAO dao = new AcademicRecordDAO();
		
		if (PF) {
			// PF 과목일 경우 PF 과목 성적 수정 로직 사용
			dao.updateRecordPF(passOrFail, recordID);
		} else {
			// 상대평가 과목 성적 수정 로직 사용
			if (recordID != null && grade != null) {
	 			dao.updateRecord(grade, convertRecord(grade), recordID);
	 		}
		}	
	}
	
	// 상대평가 과목 알파벳별 평점 숫자 부여 메서드
	// 상대평가 과목 : A+ = 4.5, A = 4.0, B+ = 3.5, B = 3.0, C+ = 2.5, C = 2.0, D+ = 1.5, D = 1.0, F = 0
	private float convertRecord(String grade) {
	    if (grade == null) {
	        return 0.0f;
	    }

	    switch (grade.trim().toUpperCase()) {
	        case "A+":
	            return 4.5f;
	        case "A":
	            return 4.0f;
	        case "B+":
	            return 3.5f;
	        case "B":
	            return 3.0f;
	        case "C+":
	            return 2.5f;
	        case "C":
	            return 2.0f;
	        case "D+":
	            return 1.5f;
	        case "D":
	            return 1.0f;
	        case "F":
	            return 0.0f;
	        default:
	            return 0.0f; // 알 수 없는 성적 처리
	    }
	}
	
}