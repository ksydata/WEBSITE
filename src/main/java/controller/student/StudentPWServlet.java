package controller.student;

// import dto.StudentDTO;
import service.StudentService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
// import javax.servlet.http.HttpSession;


//WebServlet 어노테이션으로 url 매핑
@WebServlet("/studentPassword")
public class StudentPWServlet extends HttpServlet {
	// 역직렬화 시 해당하는 클래스의 버전이 맞는지를 확인하는 장치
	private static final long serialVersionUID = 1L;
	
	// 비밀번호 변경 메서드
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
		
        // 사용자에게 입력받은 파라미터인 현재 비밀번호, 변경대상 비밀번호, 확인 비밀번호 불러오기
        String currentPassword = request.getParameter("currentPassword");        
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        
        // 비즈니스 로직을 정의한 StudentService 계층 호출하여 DB 테이블에 사용자에 의해 수정된 개인정보 업데이트
        StudentService studentService = new StudentService();
        
        // i. 현재 비밀번호 확인
        if (!studentService.verifyCurrentPassword(userID, currentPassword)) {
        	request.setAttribute("message", "현재 비밀번호와 일치하지 않습니다.");
		    // JSP 페이지로 포워딩        	
        	request.getRequestDispatcher("/student/updateMyPassword.jsp").forward(request, response);
        	return ;
        }
        // ii. 새 비밀번호 한번 더 입력받은 값과 일치하는지 확인
        if (!newPassword.equals(confirmPassword)) {
        	request.setAttribute("message", "새 비밀번호가 일치하지 않습니다.");
		    // JSP 페이지로 포워딩        	
        	request.getRequestDispatcher("/student/updateMyPassword.jsp").forward(request, response);
        	return ;
        }
        // iii. 변경 비밀번호를 DB에 업데이트
        studentService.updateStudentPW(userID, newPassword);
        
        // 비밀번호 변경 완료 후 알림
        request.setAttribute("message", "비밀번호가 성공적으로 변경되었습니다.");
	}
}