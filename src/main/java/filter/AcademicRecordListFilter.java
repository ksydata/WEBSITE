package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// 교수의 소속 단과대학 학생 성적 조회 및 관리자의 전체 학생 성적 조회 접근권한 인증 필터 
public abstract class AcademicRecordListFilter implements Filter {
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
		
        // 3. 역할(학생/교수/관리자) 유효성 검증
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
				role.equals("student") || 
				role.equals("professor") || 
				role.equals("admin")
		);		
	}	
}