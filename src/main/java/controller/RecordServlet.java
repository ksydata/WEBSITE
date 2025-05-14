package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.RecordDAO;
import dto.RecordDTO;

@WebServlet("/record")
//@WebServlet("/student/myAcademicRecord.jsp")
public class RecordServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	// userID별 성적 리스트 조회
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userID = (String) session.getAttribute("userID");
				
		if (userID != null) {
	        RecordDAO dao = new RecordDAO();
	        List<RecordDTO> records = dao.getRecordsByStudent(userID);
	        request.setAttribute("recordList", records);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("/student/myAcademicRecord.jsp");
	        dispatcher.forward(request, response);
	    } else {
	        request.setAttribute("errorMessage", "해당 학생의 성적 정보가 없습니다.");
	    }
	}
}
