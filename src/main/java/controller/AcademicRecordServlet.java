package controller;

import dao.AcademicRecordDAO;
import dto.AcademicRecordDTO;
import service.AcademicRecordService;
import util.RoleEnum;

import java.util.List;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// 목표 1: UserAcademicRecordServlet, ProfessorRecordServlet, StudentRecordServlet 3개를 1개 Servlet으로 합쳐서 구현하기
// 목표 2: session 가져오기 파트를 AcademicRecordListFilter로 이관하기 (service와 webfilter 맞물려 적용)
// 목표 3: 페이징 로직을 BoardServlet과 유사한 형태로 PagingDAO, pagingService.getPage() 를 활용하여 가져오기
// 목표 4: 현재 student, professor, admin에 분산된 myAcademicRecord.jsp 를 common/info/academicRecord.jsp 로 통합하기
// 목표 5(희망사항): AcademicRecordService에 기존 교수/학생/관리자 권한 외에 정보 접근 권한을 세부적으로 추가해서 다루기

//중요: 목표 1~4를 달성한 뒤, AcademicRecord의 결과를 Info, Notice 등 다른 기능에 유사하게 적용할 것


// [TO-BE] 학사정보(성적) 조회 통합 서블릿
@WebServlet("/academicRecord")
public class AcademicRecordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// 역직렬화 시 해당하는 클래스의 버전이 맞는지를 확인하는 장치

	private AcademicRecordDAO academicRecordDAO;
	@Override
	public void init() throws ServletException {
		academicRecordDAO = new AcademicRecordDAO();
		// DAO 객체 초기화
	}
	
	// 역할별 학사정보(성적) 조회 통합 get 메서드	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
        try {
			// 세션에서 로그인된 학번/사번, 역할 정보 가져오기 (@WebFilter에서 검증완료)
			HttpSession session = request.getSession();
			String userID = (String) session.getAttribute("userID");
			RoleEnum role = (RoleEnum) session.getAttribute("role");
			
			// RoleEnum의 메서드를 활용한 분기 처리
			if (role.isROLE_001()) {
				handleStudentRequest(request, response, userID);
			} else if (role.isROLE_002()) {
				handleProfessorRequest(request, response, session);
			} else if (role.isROLE_004()) {
				handleAdminRequest(request, response);
			} else {
				response.sendError(HttpServletResponse.SC_FORBIDDEN, "접근 권한이 없습니다.");
				// Status code (403) indicating the server understood the request, but refused to fulfill it.
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "학사정보 조회 중 오류가 발생했습니다.");
			request.getRequestDispatcher("/common/error.jsp").forward(request, response);
			// [AS-IS] error.jsp 오류 페이지 개요 구성
		}
	}

    // @Override        
	private void handleStudentRequest(HttpServletRequest request, HttpServletResponse response, 
			String userID) throws ServletException, IOException {
	}
	
    // @Override    
	private void handleProfessorRequest(HttpServletRequest request, HttpServletResponse response, 
			HttpSession session) throws ServletException, IOException {
	}
	
    // @Override    
	private void handleAdminRequest(HttpServletRequest request, HttpServletResponse response
		) throws ServletException, IOException {
	
	}
}

	/* 교수 학사정보(성적) 수정 post 메서드	
	protected void doPost(doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		// UTF-8 인코딩을 통해 한글 처리
		request.setCharacterEncoding("UTF-8");
		
		 // JSP에서 전달된 값 (모두 String)
	    String recordID = request.getParameter("recordID");
	    String pfParam = request.getParameter("PF");
	    String grade = request.getParameter("grade");
	    String passOrFailParam = request.getParameter("passOrFail");
	    
	    // String → boolean 변환
	    boolean PF = Boolean.parseBoolean(pfParam);
	    boolean passOrFail = Boolean.parseBoolean(passOrFailParam);
		
		// 해당 과목별 입력값 가져와서 수정 작업 진행
		AcademicRecordService service = new AcademicRecordService();
		service.updateRecord(recordID, PF, grade, passOrFail);
		
		// 수정이후 원래 페이지 보여주기
		response.sendRedirect(request.getContextPath());
		
	}
	*/


/* [AS-IS]
 * 	// userID별 성적 리스트 조회
		// 로그인되어 있을 경우에만 학사정보(성적) 조회
		if (userID != null) {
			// DAO 객체를 이용해 해당 학생의 학사정보 리스트 조회 
			AcademicRecordDAO dao = new AcademicRecordDAO();
		    List<AcademicRecordDTO> records = dao.getRecordByStudent(userID);
		    // 조회된 학사정보를 HTTP 요청에 저장
		    request.setAttribute("recordList", records);
		    
		    // 결과값을 표시할 jsp 페이지로 포워딩
		    RequestDispatcher dispatcher = request.getRequestDispatcher("/common/info/academicRecord.jsp");
		    dispatcher.forward(request, response);
		    
		} else {
            // 세션이 없으면 에러 메시지 출력 후 로그인 페이지로 리다이렉트						
			request.setAttribute("errorMessage", "해당 학생의 성적 정보가 없습니다.");
		}
	}	
 */