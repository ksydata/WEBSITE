package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.UserInfoService;


@WebServlet("/userPassword")
public class UserPWServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	// 비밀번호 변경 메서드
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 세션값에 저장된 학번/사번 불러오기
        String userID = (String) request.getSession().getAttribute("userID");
        // 사번이 없으면 로그인 페이지로 리다이렉트
        if (userID == null) {
	        response.sendRedirect(request.getContextPath() + "/index.jsp");
	        return;
        }
		
        // 사용자에게 입력받은 파라미터인 현재 비밀번호, 변경대상 비밀번호, 확인 비밀번호 불러오기
        String currentPassword = request.getParameter("currentPassword");    // 현재 비밀번호    
        String newPassword = request.getParameter("newPassword");	// 변경 비밀번호
        String confirmPassword = request.getParameter("confirmPassword");	// 확인 비밀번호
        
        // 비즈니스 로직을 정의한 ProfessorService 계층 호출하여 DB 테이블에 사용자에 의해 수정된 개인정보 업데이트
        UserInfoService userInfoService = new UserInfoService();
        
        // i. 사용자가 입력한 '현재 비밀번호'와 DB에 있는 실제 비밀번호가 동일한지 확인
        if (!userInfoService.verifyCurrentPassword(userID, currentPassword)) {
        	request.setAttribute("error", "현재 비밀번호와 일치하지 않습니다.");
		    // JSP 페이지로 포워딩        	
        	request.getRequestDispatcher("/common/updateMyPassword.jsp").forward(request, response);
        	return ;
        }
        
        // ii. 사용자가 입력한 '현재 비밀번호'와 '변경 비밀번호'가 동일한 지 확인
        if (currentPassword.equals(newPassword)) {
        	request.setAttribute("error", "현재와 다른 비밀번호를 입력해주세요.");
        	// JSP 페이지로 포워딩        	
        	request.getRequestDispatcher("/common/updateMyPassword.jsp").forward(request, response);
        	return ;
        }
        
        // iii. 사용자가 입력한 '변경 비밀번호'와 '확인 비밀번호'가 동일한 지 확인
        if (!newPassword.equals(confirmPassword)) {
        	request.setAttribute("error", "새 비밀번호가 일치하지 않습니다.");
		    // JSP 페이지로 포워딩        	
        	request.getRequestDispatcher("/common/updateMyPassword.jsp").forward(request, response);
        	return ;
        }
        
        // iiii. 변경 비밀번호를 DB에 업데이트
        userInfoService.updateUserInfoPW(userID, newPassword);   
        // 비밀번호 변경 완료 후 알림
        request.setAttribute("success", "비밀번호가 성공적으로 변경되었습니다.");
        request.getRequestDispatcher("/common/updateMyPassword.jsp").forward(request, response);
	}

}

