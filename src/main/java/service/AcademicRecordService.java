package service;
//AcademicRecord과 AcademicRecordDAO 사이에서 비즈니스 로직

import dao.AcademicRecordDAO;
import dto.AcademicRecordDTO;
import dto.PagingDTO;
import util.RoleEnum;


/*
 * 1. 기본 권한별 정보 조회기능 3가지 분기 외에 [v]
 * 2. 교수의 단과대학 학생 성적정보(상대평가/PF과목) 수정 기능 [v]
 * 3. 검증 기능 필요 [v]
 * 4. 페이징 로직 limit, offset 변수 추가 이후 academicRecordServlet, PagingService와 연계 필요 [v]
 */

// [TO-BE] 개인정보 일부(전화번호, 주민등록번호 뒷자리) 마스킹할 때는 서비스단에서 처리
public class AcademicRecordService {
	private AcademicRecordDAO academicRecordDAO;
	// AcademicRecord를 CRUD(조회/수정)하기 위한 접근 객체
	private PagingService pagingService;
	// Notice와 같이 AcademicRecord 비즈니스 로직 처리 시 페이징 추가
	// pagingService.getPage()를 역할별로 어떻게 활용할지
	
	// 생성자: DAO/Service 객체를 초기화 (DB 접근을 위해 필요)
	public AcademicRecordService() {
		academicRecordDAO = new AcademicRecordDAO();
		pagingService = new PagingService();
	}
	
	// 학번/사번(userID)를 받아 학사정보 페이지를 가져오는 서비스 메서드
	public PagingDTO<AcademicRecordDTO> getAcademicRecord(String userID, RoleEnum role, String college, 
			int currentPage) { 
			// int limit, int offset
		
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
	    		getStudentRecord(userID);
	    		// AcademicRecordDTO myRecord = (AcademicRecordDTO) academicRecordDAO.getRecordByStudent(userID);
	    		// return myRecord;
	    	case ROLE_002:
	    		getClassRecords(college, currentPage);
	    		// AcademicRecordDTO classRecords = (AcademicRecordDTO) academicRecordDAO.getRecordsByCollege(college, limit, offset);
	    		// return classRecords;
	    	case ROLE_004:
	    		getTotalRecords(currentPage);
	    		// AcademicRecordDTO studentsRecords = (AcademicRecordDTO) academicRecordDAO.getTotalRecords(limit, offset);
	    		// return studentsRecords;
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
		if (PF) {
			// PF 과목일 경우 PF 과목 성적 수정 로직 사용
			academicRecordDAO.updateRecordPF(passOrFail, recordID);
		} else {
			// 상대평가 과목 성적 수정 로직 사용
			if (recordID != null && grade != null) {
				academicRecordDAO.updateRecord(grade, convertRecord(grade), recordID);
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
	
	
	/*
	 * [TO-BE] 공통 오류
	 * The method getPage(String, String, String, int, PagingDAO.RowMapper<T>) 
	 * in the type PagingService is not applicable for the arguments (String, null, String, int, AcademicRecordDAO.RowMapper<AcademicRecordDTO>)
	 */
	// 학생 본인의 학사정보 조회하는 정적 메서드
	private PagingDTO<AcademicRecordDTO> getStudentRecord(String userID) {
		if (userID == null || userID.isEmpty()) {
			// @WebFilter(gate keeper)에서 접근 가능성 검증하지만, Service에서 또 쓰이는 이유는 
			// Service는 권한별 비즈니스 로직 실행에 대해 다른 Servlet 등에서도 호출될 수 있기 때문에 의도적으로 중복			
			throw new IllegalArgumentException("학번이 유효하지 않습니다.");
		}
		
		String TABLE = "ACADEMIC_RECORD";
		// DB 테이블
		String WHERE = "userID = '" + userID + "'";
		// SELECT 쿼리 조건절(학번 검색)
		String ORDER = "academicYear DESC, semester DESC";
		// 학사연도, 수강연도 순으로 내림차순 정렬
		
		return pagingService.getPage(
				TABLE, WHERE, ORDER, 1, AcademicRecordDAO.ROW_MAPPER);
		// 학생은 페이징 기능 필요하지 않으나 형식의 일관성을 위해 통일	
	}
	
    // 교수의 단과대학 학생의 학사정보 조회하는 정적 메서드(페이징 적용)
	private PagingDTO<AcademicRecordDTO> getClassRecords(String college, int currentPage) {
		if (college == null || college.isEmpty()) {
			// @WebFilter(gate keeper)에서 해당 레코드의 college와 교수의 college 검증 완료
			// DAO에서는 college가 세션값에 있는지만 확인
			throw new IllegalArgumentException("단과대학 정보가 유효하지 않습니다.");
		}
		
		String TABLE = "ACADEMIC_RECORD A JOIN PERSONAL_INFO P ON A.userID = P.userID";
		// DB 테이블(조인)
		String WHERE = "P.college = '" + college + "'";
		// SELECT 쿼리 조건절(단과대학 검색)
		String ORDER = "academicYear DESC, semester DESC";
		// 학사연도, 수강연도 순으로 내림차순 정렬
		
		return pagingService.getPage(
				TABLE, WHERE, ORDER, currentPage, AcademicRecordDAO.ROW_MAPPER);
	}
		
    // 관리자의 전체 학생의 학사정보 조회하는 정적 메서드(페이징 적용)
	private PagingDTO<AcademicRecordDTO> getTotalRecords(int currentPage) {
		
		String TABLE = "ACADEMIC_RECORD";
		// DB 테이블
		String ORDER = "academicYear DESC, semester DESC";
		// 학사연도, 수강연도 순으로 
		
		return pagingService.getPage(
				TABLE, null, ORDER, currentPage, AcademicRecordDAO.ROW_MAPPER);
		// 학생은 페이징 기능 필요하지 않으나 형식의 일관성을 위해 통일	
	}
	
	
}