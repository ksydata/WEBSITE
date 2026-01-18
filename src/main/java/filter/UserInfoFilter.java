package filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;

import util.RoleEnum;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;


// @https://velog.io/@hameee/Servlet-Filter
// @https://blog.naver.com/redsuit/120118985886

@WebFilter("/userinfo")
// 요청과 응답을 동적으로 가로채어 포함된 정보를 변형 / 인증 및 권한 부여 / 로깅 및 감시
public abstract class UserInfoFilter implements Filter {
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
		// 기존 세션값만 사용하며, 세션에서 사용자 정보 확인하고 없으면 Null 반환
		
		// 2. 로그인 인증 확인
        if (session == null || session.getAttribute("userID") == null) {
        // 세션이 없으면 로그인 페이지로 리다이렉트
        	accessResponse.sendRedirect(loginRequest.getContextPath() + "/login.jsp");
        	return;
        }
    
        RoleEnum role = (RoleEnum) session.getAttribute("role");
		
        // 3. 역할(학생/교수/교직원/교직원/관리자) 유효성 검증
        if (!isValidRole(role)) {
        	accessResponse.sendError(403);
        	// accessResponse.sendError(HttpServlet.SC_FORBIDDEN, "접근 권한이 없는 페이지입니다."); 
        	return;
        }
        
        // 4. 필터 통과 → Servlet(다음 체인)으로 요청 전달
        filterChain.doFilter(request,response);
	}
	
	// 3. 유효한 역할인지 검증하는 메서드
	private boolean isValidRole(RoleEnum role) {
		return role != null && (
				role.equals(RoleEnum.ROLE_001) || 
				role.equals(RoleEnum.ROLE_002) || 
				role.equals(RoleEnum.ROLE_003) || 	
				role.equals(RoleEnum.ROLE_004) 
		);		
	}
	
	@Override
	public void destroy() {}
	// 객체/리소스 종료
}

//관리자의 학사관리시스템 전체 사용자 개인정보 조회 접근권한 인증 필터 
