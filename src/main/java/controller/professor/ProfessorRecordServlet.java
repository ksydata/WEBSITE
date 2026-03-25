package controller.professor;

import service.AcademicRecordService;
import util.RoleEnum;
import dto.AcademicRecordDTO;
import dto.PagingDTO;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


// WebServlet 어노테이션으로 URL을 /classRecord로 매핑
@WebServlet("/classRecord")
public class ProfessorRecordServlet extends HttpServlet {
	// 역직렬화 시 해당하는 클래스의 버전이 맞는지를 확인하는 장치
	private static final long serialVersionUID = 1L;
	private AcademicRecordService academicRecordService = new AcademicRecordService();
	
	// userID별 성적 리스트 조회
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        // 세션에서 로그인된 아이디, 역할, 단과대학 가져오기 (LoginServlet에서 저장됨)
		HttpSession session = request.getSession();
		String userID = (String) session.getAttribute("userID");
		RoleEnum role = (RoleEnum) session.getAttribute("role");
		String college = (String) session.getAttribute("college");
		
		// 페이지 파라미터 처리
        int page = 1;
        try { 
        	page = Integer.parseInt(request.getParameter("page"));
        } catch(Exception ignored) {}
		
        // AcademicRecordService를 통해 소속 단과대학 학생 성적 데이터 가져오기
        PagingDTO<AcademicRecordDTO> pagingDTO = academicRecordService.getAcademicRecord(userID, role, college, page);

        // profileViewMap 구성 (academicRecord.jsp의 학적 정보 카드용)
        Map<String, String> profileViewMap = new LinkedHashMap<>();
        profileViewMap.put("이름", (String) session.getAttribute("userName"));
        profileViewMap.put("소속 단과대", college);

        request.setAttribute("profileViewMap", profileViewMap);
        request.setAttribute("pagingDTO", pagingDTO);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/common/info/academicRecord.jsp");
        dispatcher.forward(request, response);
	}
		
		/* [AS-IS] ProfessorDAO에서 직접 데이터를 가져옴
	    // 로그인되어 있을 경우에만 학사정보(성적) 조회
		if (userID != null) {
			// 1. 페이지 번호 파라미터 받기 (기본값은 1)
            int currentPage = 1;
            String pageParam = request.getParameter("page");
            if (pageParam != null) {
                try {
                    currentPage = Integer.parseInt(pageParam);
                } catch (NumberFormatException e) {
                    currentPage = 1; // 예외 시 1페이지로 fallback
                }
            }

         // 2. DAO 처리
            ProfessorDAO dao = new ProfessorDAO();
            int offset = (currentPage - 1) * 20;
            List<AcademicRecordDTO> records = dao.getAcademicRecordsByCollegeWithPaging(userID, 10, offset);
            int totalRecords = dao.getAcademicRecordsCountByCollege(userID);
            int totalPage = (int) Math.ceil((double) totalRecords / 20);

            // 3. 블록 계산
            int startPage = ((currentPage - 1) / 10) * 10 + 1;
            int endPage = startPage + 10 - 1;
            if (endPage > totalPage) {
                endPage = totalPage;
            }

            // 4. 교수 이름
            ProfessorDTO professor = dao.getMyInfo(userID);
            if (professor != null) request.setAttribute("userName", professor.getName());
            

            // 5. request 속성 전달
            request.setAttribute("classRecordsList", records);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPage", totalPage); // paging.jsp는 이 이름 사용
            request.setAttribute("startPage", startPage);
            request.setAttribute("endPage", endPage);
            request.setAttribute("pageURL", "classRecord"); // 기본 URL
            request.setAttribute("paramStr", ""); // 정렬이나 검색 조건 있다면 여기에 추가

            RequestDispatcher dispatcher = request.getRequestDispatcher("/professor/classAcademicRecord.jsp");
            dispatcher.forward(request, response);
           

		    
		} else {
            // 세션이 없으면 에러 메시지 출력 후 로그인 페이지로 리다이렉트						
			request.setAttribute("errorMessage", "해당 단과대학 전공생의 성적 정보가 없습니다.");
		}
		 */
	}	