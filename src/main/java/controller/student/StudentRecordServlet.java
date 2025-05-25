package controller.student;

import dao.StudentDAO;
import dto.StudentDTO;
// import service.StudentService;

import java.util.List;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


// WebServlet 어노테이션으로 URL을 /studentRecord로 매핑
@WebServlet("/studentRecord")
public class StudentRecordServlet extends HttpServlet {
	// 역직렬화 시 해당하는 클래스의 버전이 맞는지를 확인하는 장치
	private static final long serialVersionUID = 1L;
	
	// userID별 성적 리스트 조회
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 요청 파라미터를 UTF-8로 인코딩 (한글 깨짐 방지)
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        // 세션에서 로그인된 학번 가져오기
		HttpSession session = request.getSession();
		String userID = (String) session.getAttribute("userID");

		// 로그인되어 있을 경우에만 학사정보(성적) 조회
		if (userID != null) {
			// DAO 객체를 이용해 해당 학생의 학사정보 리스트 조회 
			StudentDAO dao = new StudentDAO();
		    List<StudentDTO> records = dao.getRecordsByStudent(userID);
		    // 조회된 학사정보를 HTTP 요청에 저장
		    request.setAttribute("recordList", records);
		    
		    // DTO에서 이름을 추출하여 전달
		    StudentDTO student = dao.getMyInfo(userID);
		    if (student != null) request.setAttribute("userName", student.getName());
		    
		    // 결과값을 표시할 jsp 페이지로 포워딩
		    RequestDispatcher dispatcher = request.getRequestDispatcher("/student/myAcademicRecord.jsp");
		    dispatcher.forward(request, response);
		    
		} else {
            // 세션이 없으면 에러 메시지 출력 후 로그인 페이지로 리다이렉트						
			request.setAttribute("errorMessage", "해당 학생의 성적 정보가 없습니다.");
		}
	}	
}