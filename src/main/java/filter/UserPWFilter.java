package filter;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;

// @https://drg2524.tistory.com/206

@WebFilter("/userPassword")
public abstract class UserPWFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}
	// 필터 초기화
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, 
			FilterChain filterChain) throws IOException, ServletException {
		// 1. 필터 통과 시 UTF-8로 인코딩 설정 (한글 깨짐 방지)
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
		
		HttpServletRequest loginRequest = (HttpServletRequest) request;
		HttpServletResponse accessResponse = (HttpServletResponse) response;
		HttpSession session = loginRequest.getSession(false);
		// 기존 세션값만 사용하며, 없으면 Null 반환
		
		// 2. 로그인 인증 확인
        if (session == null || session.getAttribute("userID") == null) {
        // 세션이 없으면 로그인 페이지로 리다이렉트
        	accessResponse.sendRedirect(loginRequest.getContextPath() + "/login.jsp");
        	return;
        }
    		
        // 3. 요청 → Servlet
        filterChain.doFilter(request,response);
	}
}
