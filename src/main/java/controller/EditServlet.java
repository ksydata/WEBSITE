package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.NoticeDAO;
import dto.NoticeDTO;

@WebServlet("/EditServlet")
public class EditServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // GET 요청: 게시글 수정 페이지 진입
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();
        String sessionUserID = (String) session.getAttribute("userID");

        NoticeDAO dao = new NoticeDAO();
        NoticeDTO post = dao.getNoticeByID(id);

        // 작성자 검증
        if (sessionUserID == null || !sessionUserID.equals(post.getUserID())) {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().println("<script>alert('올바른 계정으로 접근하십시오.'); history.back();</script>");
            return;
        }

        request.setAttribute("post", post);

        // editPost.jsp로 포워딩
        RequestDispatcher dispatcher = request.getRequestDispatcher("/common/editPost.jsp");
        dispatcher.forward(request, response);
    }

    // POST 요청: 수정 처리
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String contents = request.getParameter("contents");

        HttpSession session = request.getSession();
        String sessionUserID = (String) session.getAttribute("userID");

        NoticeDAO dao = new NoticeDAO();
        NoticeDTO originalPost = dao.getNoticeByID(id);

        if (sessionUserID == null || !sessionUserID.equals(originalPost.getUserID())) {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().println("<script>alert('올바른 계정으로 접근하십시오.'); history.back();</script>");
            return;
        }

        dao.updateNotice(id, title, contents);

        // 수정 후 상세 페이지로 이동
        response.sendRedirect(request.getContextPath() + "/post?id=" + id);
    }
}