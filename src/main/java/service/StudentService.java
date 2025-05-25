package service;
//StudentInfoServlet과 StudentDAO 사이에서 비즈니스 로직

import dao.StudentDAO;
import dto.StudentDTO;

// 개인정보 일부(전화번호, 주민등록번호 뒷자리) 마스킹할 때는 서비스단에서 처리
public class StudentService {
	private StudentDAO studentDAO;
    
	// 1. 개인정보 조회 메서드
	// 생성자: StudentDAO 객체를 초기화 (DB 접근을 위해 필요)
	// 단, 데이터 추출하는 핵심 로직은 데이터 접근객체인 DAO에서 수행
	public StudentService() {
		studentDAO = new StudentDAO();
	}
	
	// 학번/사번(userID)를 받아 개인정보를 가져오는 서비스 메서드
    public StudentDTO getStudentInfo(String userID) {
        if (userID == null || userID.isEmpty()) {
            // userID가 비어있으면 null 반환
            return null;
        }
        // DAO를 통해 DB에서 학생 정보 조회
        StudentDTO student = studentDAO.getMyInfo(userID);

        // 학생 정보가 존재할 경우, 민감 정보 일부를 마스킹 처리
        if (student != null) {
            // 전화번호 뒷 4자리 마스킹 (예: 010-1234-****)
            // StudentDTO.getter method            
        	String phone = student.getPhoneNumber();
            if (phone != null && phone.length() >= 4) {
                String maskedPhoneNum = phone.substring(0, phone.length() - 4) + "****";
                // StudentDTO.setter method
                student.setPhoneNumber(maskedPhoneNum);
            }

            // 주민등록번호 뒷 6자리 마스킹 (예: 010101-1******)
            // StudentDTO.getter method            
            String resident = student.getResidentNumber();
            if (resident != null && resident.length() >= 7) {
                String maskedResidentNum = resident.substring(0, 7) + "******";
                // StudentDTO.setter method
                student.setResidentNumber(maskedResidentNum);
            }
            
            // 비밀번호 전체 마스킹 (예: ********)
            // StudentDTO.getter method            
            String password = student.getUserPassword();
            if (password != null) {
            	// ** 수정 예정 **
                String maskedPassword = password.substring(0, 9) + "********";
                // StudentDTO.setter method
                student.setUserPassword(maskedPassword);
            }
            
        }
        
        // StudentDTO의 객체인 학생 1명의 정보를 리턴
        return student;
    }
    
    // 개인정보 수정 메서드
    public void updateStudentInfo(String userID, String phoneNumber, String email, String address) {    	    	
    	// 휴대전화번호 수정
    	if (phoneNumber != null && !phoneNumber.trim().isEmpty()) {
    		studentDAO.updatePhoneNumber(userID, phoneNumber);
    	}
    	// 이메일 수정
    	if (email != null && !email.trim().isEmpty()) {
    		studentDAO.updateEmail(userID, email);
    	}
    	// 주소 수정
    	if (address != null && !address.trim().isEmpty()) {
    		studentDAO.updateAddress(userID, address);
    	}
    }
}