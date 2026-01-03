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
	// AcademicRecord를 CRUD(조회/수정)하기 위한 접근 객체
	private PagingService pagingService;
	// Notice와 같이 AcademicRecord 비즈니스 로직 처리 시 페이징 추가 
	
	// 생성자: DAO/Service 객체를 초기화 (DB 접근을 위해 필요)
	public AcademicRecordService() {
		academicRecordDAO = new AcademicRecordDAO();
		pagingService = new PagingService();
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
	public void updateRecord(String recordID, boolean PF, String grade, float gradePoint, boolean passOrFail) {
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
			if (recordID != null && grade != null && gradePoint != 0) {
	 			dao.updateRecord(grade, gradePoint, recordID);
	 		}
		}	
	}
}