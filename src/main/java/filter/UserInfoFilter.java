package filter;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;

// @https://velog.io/@hameee/Servlet-Filter

@WebFilter("/userinfo")
// 요청과 응답을 동적으로 가로채어 포함된 정보를 변형 / 인증 및 권한 부여 / 로깅 및 감시
public abstract class UserInfoFilter implements Filter {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, 
			FilterChain filterChain) throws IOException, ServletException {
		// 1. 필터 통과 시 UTF-8로 인코딩 설정 (한글 깨짐 방지)
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
		
		HttpServletRequest loginRequest = (HttpServletRequest) request;
		HttpServletResponse accessResponse = (HttpServletResponse) response;
		HttpSession session = loginRequest.getSession(false);
		//기존 세션값만 사용하며, 없으면 Null 반환
		
		// 2. 로그인 인증 확인
        if (session == null || session.getAttribute("userID") == null) {
        // 세션이 없으면 로그인 페이지로 리다이렉트
        	accessResponse.sendRedirect(loginRequest.getContextPath() + "/login.jsp");
        	return;
        }
    
        String role = (String) session.getAttribute("role");
		
        // 3. 역할(학생/교수/교직원/관리자) 유효성 검증
        if (!isValidRole(role)) {
        	accessResponse.sendError(403);
        	// accessResponse.sendError(HttpServlet.SC_FORBIDDEN, "접근 권한이 없는 페이지입니다."); 
        	return;
        }
        
        // 4. 요청 → Servlet
        filterChain.doFilter(request,response);
	}
	
	private boolean isValidRole(String role) {
		return role != null && (
				role.equals("student") || role.equals("professor") || 
				role.equals("admin") || role.equals("employee") 
		);		
	}	
}
