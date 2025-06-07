package controller.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.AdminPersonalInfoDTO;
import service.AdminService;

@WebServlet("/studentRecordList")
public class UserRecordListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final int BLOCK_SIZE = 10;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        int currentPage = 1;
        if (request.getParameter("page") != null) {
            try {
                currentPage = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                currentPage = 1;
            }
        }

        String role = "학생";
        AdminService service = new AdminService();

        // 학생 목록 및 총 페이지 수 조회
        List<AdminPersonalInfoDTO> studentList = service.getPagedUserList(role, currentPage);
        int totalPage = service.getTotalPageCount(role);

        // 페이징 블록 계산
        int startPage = ((currentPage - 1) / BLOCK_SIZE) * BLOCK_SIZE + 1;
        int endPage = Math.min(startPage + BLOCK_SIZE - 1, totalPage);

        // 데이터 설정
        request.setAttribute("studentList", studentList);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("startPage", startPage);
        request.setAttribute("endPage", endPage);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/userRecordList.jsp");
        dispatcher.forward(request, response);
    }
}