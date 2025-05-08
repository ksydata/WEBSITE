package controller.student;

import dao.StudentDAO;
import dto.StudentDTO;
import user.UserDAO;
import user.UserDTO;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//WebServlet 어노테이션으로 url 매핑
@WebServlet("/StudentInfoServlet")
public class StudentInfoServlet extends HttpServlet {
	// 역직렬화 시 해당하는 클래스의 버전이 맞는지를 확인하는 장치
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 요청 파라미터를 UTF-8로 인코딩 (한글 깨짐 방지)		
		request.setCharacterEncoding("UTF-8");
		
	    // 데이터접근객체 클래스의 인스턴스 생성 후 로그인 메서드를 활용하여 결과값 받아오기
		StudentDAO studentDAO = new StudentDAO();
		StudentDTO result = StudentDAO.post();
		
		if (result != null) {
			System.out.print(false);
		} else {
			System.out.print(false);			
		}
	}
}