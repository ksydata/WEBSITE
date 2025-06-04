package controller.professor;

// import dao.ProfessorDAO;
import dto.ProfessorDTO;
import service.ProfessorService;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


//WebServlet 어노테이션으로 url 매핑
@WebServlet("/professorInfo")
public class ProfessorInfoServlet extends HttpServlet {
	// 역직렬화 시 해당하는 클래스의 버전이 맞는지를 확인하는 장치
	private static final long serialVersionUID = 1L;
	
	// 개인정보 조회 메서드
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 요청 파라미터를 UTF-8로 인코딩 (한글 깨짐 방지)
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        // 로그인 시 세션에 저장된 사번 불러오기
        HttpSession session = request.getSession();
        String userID = (String) session.getAttribute("userID");
		
        if (userID == null) {
            // 세션이 없으면 로그인 페이지로 리다이렉트
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
		// ProfessorDAO를 직접 부르는 게 아니라 ProfessorService 통해 데이터 전송
        ProfessorService ProfessorService = new ProfessorService();
        ProfessorDTO professorInfo = ProfessorService.getProfessorInfo(userID);
		
		// 세션이 초기화되어 userID를 받아오지 못하고, professorInfo 객체를 가져오지 못하는 Null 오류 발생
		if (professorInfo != null) {
		    request.setAttribute("professorInfo", professorInfo);
		    // JSP 페이지로 포워딩
			request.getRequestDispatcher("/professor/myPersonalInfo.jsp").forward(request, response);
			
		} else {
			// 아이디로 받아 세션에 저장된 사번으로 교수 1명의 정보를 가져오지 못한 경우 메인으로 이동
			response.sendRedirect(request.getContextPath() + "/professor/main.jsp");
		}
	}
	
	// 개인정보 수정 메서드
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 요청 파라미터를 UTF-8로 인코딩 (한글 깨짐 방지)
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        // 세션값에 저장된 사번 불러오기
        String userID = (String) request.getSession().getAttribute("userID");
        // 사번이 없으면 로그인 페이지로 리다이렉트
        if (userID == null) {
	        response.sendRedirect(request.getContextPath() + "/index.jsp");
	        return;
        }
		
        // 수정대상 개인정보(휴대전화번호, 사무실전화번호, 이메일, 주소)
        String phoneNumber = request.getParameter("phoneNumber");
        String officeNumber = request.getParameter("officeNumber");        
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        
        // 비즈니스 로직을 정의한 StudentService 계층 호출하여 DB 테이블에 사용자에 의해 수정된 개인정보 업데이트
        ProfessorService professorService = new ProfessorService();
        professorService.updateProfessorInfo(userID, phoneNumber, officeNumber, email, address);
        
        // 개인정보 수정 완료 후 알림
        request.setAttribute("message", "개인정보가 성공적으로 수정되었습니다.");
        // 수정된 정보로 HTTP 웹에 다시 GET 메서드 수행 요청(데이터 조회)
        doGet(request, response);
	}
}