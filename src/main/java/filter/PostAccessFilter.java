package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.RoleEnum;

@WebFilter(urlPatterns = {
	    "/EditServlet",
	    "/DeleteServlet"
	})
	public class PostAccessFilter implements Filter {

	    @Override
	    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	            throws IOException, ServletException {

	        HttpServletRequest req = (HttpServletRequest) request;
	        HttpServletResponse resp = (HttpServletResponse) response;
	        HttpSession session = req.getSession(false);

	        // ️로그인 여부 확인
	        if (session == null || session.getAttribute("userID") == null) {
	            resp.sendRedirect(req.getContextPath() + "/login.jsp");
	            return;
	        }

	        String userRole = (String) session.getAttribute("userRole");
	        RoleEnum roleEnum = parseRole(userRole);

	        if (roleEnum == null) {
	            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
	            return;
	        }

	        // URI 추출
	        String uri = req.getRequestURI();

	        // URI 단위 접근 정책
	        // 삭제 버튼 URL이 /DeleteServlet
	        // 관리자 권한 보유 혹은 작성자 본인일 시 삭제 가능하게 하기
	        if (uri.endsWith("/DeleteServlet")) {
	            // → 작성자 검증은 Servlet에서
	            if (!roleEnum.isROLE_004() && !isUser(roleEnum)) {
	                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
	                return;
	            }
	        }

	        // 수정 페이지 진입 버튼 URL은 /EditServlet?id=123 의 형태
	        // 로그인한 작성자 본인일 시 수정 페이지 진입 가능하게 함
	        if (uri.endsWith("/EditServlet")) {
	            // 수정은 로그인 사용자만 허용
	            if (!isUser(roleEnum)) {
	                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
	                return;
	            }
	        }

	        // 필터 통과 이후 Servlet으로 요청 전달
	        chain.doFilter(request, response);
	    }

	    // Session의 RoleEnum 보유 여부 검증
	    private boolean isUser(RoleEnum role) {
	        return role.isROLE_001() || role.isROLE_002()
	            || role.isROLE_003() || role.isROLE_004();
	    }

	    // Session에서 RoleEnum 추출
	    private RoleEnum parseRole(String role) {
	        if (role == null) return null;
	        for (RoleEnum r : RoleEnum.values()) {
	            if (r.getRole().equals(role)) {
	                return r;
	            }
	        }
	        return null;
	    }

		@Override
		public void init(FilterConfig filterConfig) throws ServletException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void destroy() {
			// TODO Auto-generated method stub
			
		}
	}