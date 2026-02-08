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
import javax.servlet.http.HttpSession;

import util.RoleEnum;

@WebFilter("/post/*")
public class PostFilter implements Filter {
	
	@Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 초기화 로직 필요 시 작성
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(false);

        String userID = null;
        RoleEnum roleEnum = null;

        // session에 userRole 있는지 확인
        if (session != null) {
            userID = (String) session.getAttribute("userID");
            String userRole = (String) session.getAttribute("userRole"); // "student", "admin" 등

            if (userRole != null) {
                roleEnum = parseRole(userRole);
            }
        }

        // 로그인 여부, 관리자 권한 여부 확인
        boolean isLogin = userID != null;
        boolean isAdmin = roleEnum != null && roleEnum.isROLE_004();


        // JSP / Servlet 공통 사용 속성 규정하여 전달
        req.setAttribute("roleEnum", roleEnum);
        req.setAttribute("isLogin", isLogin);
        req.setAttribute("isAdmin", isAdmin);

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // 종료 시 처리
    }

    /**
     * String → RoleEnum 매핑
     */
    private RoleEnum parseRole(String role) {
        for (RoleEnum r : RoleEnum.values()) {
            if (r.getRole().equals(role)) {
                return r;
            }
        }
        return null;
    }
}
