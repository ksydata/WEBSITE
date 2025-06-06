package controller.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.AdminPersonalInfoDTO;
import service.AdminService;

@WebServlet("/adminUserListSort")
public class UserListSortServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String role = request.getParameter("role"); // "전체", "학생", "교수" 등
        String sortOrder = request.getParameter("sort"); // "asc" or "desc"
        String pageStr = request.getParameter("page");
        String orderField = request.getParameter("field");

        int page = (pageStr != null) ? Integer.parseInt(pageStr) : 1;
        int pageSize = 20;

        AdminService service = new AdminService();
        List<AdminPersonalInfoDTO> userList = service.getUserListWithSorting(role, page, pageSize, sortOrder, orderField);
        int totalUsers = service.getTotalUserCount(role);
        int totalPage = (int) Math.ceil((double) totalUsers / pageSize);

        // 페이지 블록 설정
        int blockSize = 5; // 블록당 페이지 수
        int startPage = ((page - 1) / blockSize) * blockSize + 1;
        int endPage = Math.min(startPage + blockSize - 1, totalPage);

        // JSP 전달
        request.setAttribute("userList", userList);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalUsers", totalUsers);
        request.setAttribute("totalPage", totalPage); // <-- 수정된 부분 (totalPages → totalPage)
        request.setAttribute("startPage", startPage);
        request.setAttribute("endPage", endPage);
        request.setAttribute("sortOrder", sortOrder);
        request.setAttribute("orderField", orderField);
        request.setAttribute("role", role);

        request.getRequestDispatcher("/admin/userInfoList.jsp").forward(request, response);
    }
}