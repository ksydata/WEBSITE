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

@WebServlet("/studentRecordListSort")
public class UserRecordListSortServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final int PAGE_SIZE = 20;
    private static final int BLOCK_SIZE = 10;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // 파라미터 수집
        String pageStr = request.getParameter("page");
        String sortOrder = request.getParameter("sort");
        String orderField = request.getParameter("field");

        int currentPage = 1;
        if (pageStr != null) {
            try {
                currentPage = Integer.parseInt(pageStr);
            } catch (NumberFormatException e) {
                currentPage = 1;
            }
        }

        String role = "학생";
        AdminService service = new AdminService();

        // 정렬된 학생 목록 가져오기
        List<AdminPersonalInfoDTO> studentList = service.getUserListWithSorting(role, currentPage, PAGE_SIZE, sortOrder, orderField);
        int totalUsers = service.getTotalUserCount(role);
        int totalPage = (int) Math.ceil((double) totalUsers / PAGE_SIZE);

        // 페이징 블록 계산
        int startPage = ((currentPage - 1) / BLOCK_SIZE) * BLOCK_SIZE + 1;
        int endPage = Math.min(startPage + BLOCK_SIZE - 1, totalPage);

        // 파라미터 문자열 구성 → &page=1
        StringBuilder paramBuilder = new StringBuilder();
        paramBuilder.append("&page=").append(currentPage);

        // 필요한 경우, 추가 필터링 조건 (ex. status, year 등)도 여기 추가 가능
        String paramStr = paramBuilder.toString();

        // JSP에 전달할 공통 속성
        request.setAttribute("pageURL", "studentRecordListSort");
        request.setAttribute("paramStr", paramStr);

        // 데이터 설정
        request.setAttribute("studentList", studentList);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("startPage", startPage);
        request.setAttribute("endPage", endPage);
        request.setAttribute("sortOrder", sortOrder);
        request.setAttribute("orderField", orderField);

        // JSP로 포워딩
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/userRecordList.jsp");
        dispatcher.forward(request, response);
    }
}