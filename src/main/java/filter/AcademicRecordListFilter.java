package filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;

import util.RoleEnum;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


// 교수의 소속 단과대학 학생 성적 조회 및 관리자의 전체 학생 성적 조회 접근권한 인증 필터 
@WebFilter("/academicRecord")
public abstract class AcademicRecordListFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}
	// 필터 초기화
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, 
			FilterChain filterChain) throws IOException, ServletException {
		// 1. 필터 통과 시 UTF-8로 인코딩 설정 (한글 깨짐 방지)
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession(false);
		//기존 세션값만 사용하며, 세션에서 사용자 정보 확인하고 없으면 Null 반환
		
		// 2. 로그인 인증 확인
        if (session == null || session.getAttribute("userID") == null) {
        // 세션이 없으면 로그인 페이지로 리다이렉트
        	httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
        	return;
        }
    
        RoleEnum role = (RoleEnum) session.getAttribute("role");
		// 세션에서 역할(role) 정보 확인
        /* 세션에 저장된 role의 타입이 String일까 아니면 RoleEnum일까
         * Object roleObject = session.getAttribute("role");
         * if (roleObject instanceof RoleEnum) {role = (RoleEnum) roleObject;}
         * else if (roleObject instanceof String) {
         * 		String roleString = (String) roleObject;
         * 		role = convertStringToRoleEnum(roleString);
         * }
         */
        
        // 3. 역할(학생/교수/관리자) 유효성 검증
        if (!isValidRole(role)) {
        	httpResponse.sendError(403);
        	// accessResponse.sendError(HttpServlet.SC_FORBIDDEN, "접근 권한이 없는 페이지입니다."); 
        	return;
        }
        
        // 4. 교수 역할의 소속 단과대학 정보 확인
        if (RoleEnum.ROLE_002.equals(role)) {
        	String college = (String) session.getAttribute("college");
        	if (college == null || college.isEmpty()) {
        		httpResponse.sendError(403);
        		return;
        	}
        }
        
        // 5. 필터 통과 → Servlet(다음 체인)으로 요청 전달
        filterChain.doFilter(request,response);
	}
	
	// 4. 유효한 역할인지 검증하는 메서드
	private boolean isValidRole(RoleEnum role) {
		return role != null && (
				role.equals(RoleEnum.ROLE_001) || 
				role.equals(RoleEnum.ROLE_002) || 
				role.equals(RoleEnum.ROLE_004)
		);		
	}	
}