package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.NoticeDAO;
import dto.NoticeDTO;

@WebServlet("/BoardServlet")
public class BoardServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		NoticeDAO dao = new NoticeDAO();
        List<NoticeDTO> list = dao.getAllNotices();  // 모든 게시글 조회
        
        request.setAttribute("noticeList", list);    // request에 저장
        RequestDispatcher dispatcher = request.getRequestDispatcher("/common/postlist.jsp");
        dispatcher.forward(request, response);       // JSP로 전달
	}
}
