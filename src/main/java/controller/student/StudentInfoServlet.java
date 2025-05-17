package controller.student;

import dto.StudentDTO;
import service.StudentService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//WebServlet 어노테이션으로 url 매핑
@WebServlet("/studentInfo")
// @WebServlet("/StudentInfoServlet")
public class StudentInfoServlet extends HttpServlet {
	// 역직렬화 시 해당하는 클래스의 버전이 맞는지를 확인하는 장치
	private static final long serialVersionUID = 1L;
	
	// 개인정보 조회 메서드
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 요청 파라미터를 UTF-8로 인코딩 (한글 깨짐 방지)
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        // 로그인 시 세션에 저장된 학번 불러오기
        HttpSession session = request.getSession();
        String userID = (String) session.getAttribute("userID");
        // [LoginServlet.java] String userID = request.getParameter("userID");
		
        if (userID == null) {
            // 세션이 없으면 로그인 페이지로 리다이렉트
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
		// [TO-BE] StudentDAO를 직접 부르는 게 아니라 StudentService 통해 데이터 전송
		StudentService studentService = new StudentService();
		StudentDTO studentInfo = studentService.getStudentInfo(userID);
	    // [AS-IS] 데이터접근객체 클래스의 인스턴스 생성 후 로그인 메서드를 활용하여 결과값 받아오기
			// StudentDAO studentDAO = new StudentDAO();
			// StudentDTO studentInfo = studentDAO.getMyInfo(userID);
		
		// 세션이 초기화되어 userID를 받아오지 못하고, studentInfo 객체를 가져오지 못하는 Null 오류 발생
		// [TEST-CODE] 
		// System.out.println("userID from session: " + userID);
		// System.out.println("studentInfo: " + studentInfo);
		if (studentInfo != null) {
		    // [TEST-CODE] System.out.println("학생 정보가 존재합니다: " + studentInfo.getName());			
		    request.setAttribute("studentInfo", studentInfo);
		    
		    // JSP 페이지로 포워딩
			request.getRequestDispatcher("/student/myPersonalInfo.jsp").forward(request, response);
		    // [또 다시 서블릿을 호출하는 잘못된 경로로 무한 루프 오류 발생] 
			// request.getRequestDispatcher("student/studentInfo").forward(request, response);

		} else {
			// 아이디로 받아 세션에 저장된 학번으로 학생 1명의 정보를 가져오지 못한 경우 메인으로 이동
			response.sendRedirect(request.getContextPath() + "/student/main.jsp");
		}
	}
	
	// 개인정보 수정 메서드
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 요청 파라미터를 UTF-8로 인코딩 (한글 깨짐 방지)
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        // 세션값에 저장된 학번 불러오기
        String userID = (String) request.getSession().getAttribute("userID");
        // 학번이 없으면 로그인 페이지로 리다이렉트
        if (userID == null) {
	        response.sendRedirect(request.getContextPath() + "/index.jsp");
	        return;
        }
		
        // 수정대상 개인정보(전화번호, 이메일, 주소)
        String phoneNumber = request.getParameter("phoneNumber");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        
        // 비즈니스 로직을 정의한 StudentService에서 DB 테이블에 사용자에 의해 수정된 정보 업데이트
        // ** 수정 예정 **
        // studentService.method(userID, phoneNumber, residentNumber);
        
        // 개인정보 수정 완료 후 알림
        request.setAttribute("message", "개인정보가 성공적으로 수정되었습니다.");
        // 수정된 정보로 HTTP 웹에 다시 GET 메서드 수행 요청(데이터 조회)
        doGet(request, response);
	}
}