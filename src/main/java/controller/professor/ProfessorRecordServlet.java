package controller.professor;

import dao.ProfessorDAO;
import dto.ProfessorDTO;
import dto.UserInfoDTO;
import service.AcademicRecordService;
import service.UserInfoService;
import util.RoleEnum;
import dto.AcademicRecordDTO;
import dto.PagingDTO;

import java.util.List;
import java.io.IOException;

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
        // 요청 파라미터를 UTF-8로 인코딩 (한글 깨짐 방지)
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        // 세션에서 로그인된 아이디 가져오기
		HttpSession session = request.getSession();
		String userID = (String) session.getAttribute("userID");
		RoleEnum role = (RoleEnum) session.getAttribute("role");
		
		// 교수 소속 단과대 가져오기
		UserInfoService userInfoService = new UserInfoService();
		UserInfoDTO professorInfo = userInfoService.getUserInfo(userID, role, 1);
		String college = professorInfo.getCollege();
		
		// [TO-BE] AcademicRecordService에서 메서드 가져와서 실행
		// 페이지 파라미터 처리
        int page = 1;
        // 첫 페이지로 변수 초기화
        try { 
        	page = Integer.parseInt(request.getParameter("page"));
        	// http 요청에서 받은 페이지 파라미터 정수로 반환
        	// getAttribute();는 object(Data, File 타입)형으로 값 반환하여 추후 활용
        } catch(Exception ignored) {}
		
        // AcademicRecordService를 통해 학생 성적 데이터 가져오기
        PagingDTO<AcademicRecordDTO> recordsObject = academicRecordService.getAcademicRecord(userID, role, college, page);
		
        // request 속성 전달
        request.setAttribute("classRecordsList", recordsObject.getPagingDataList());
        request.setAttribute("currentPage", recordsObject.getCurrentPage());
        request.setAttribute("totalPage", recordsObject.getTotalPage()); // paging.jsp는 이 이름 사용
        request.setAttribute("startPage", recordsObject.getStartPage());
        request.setAttribute("endPage", recordsObject.getEndPage());
        request.setAttribute("pageURL", "classRecord"); // 기본 URL
        request.setAttribute("paramStr", ""); // 정렬이나 검색 조건 있다면 여기에 추가

        RequestDispatcher dispatcher = request.getRequestDispatcher("/professor/classAcademicRecord.jsp");
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