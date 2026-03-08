package controller;

import user.UserDAO;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//WebServlet 어노테이션으로 url 매핑
@WebServlet("/UserJoinServlet")
public class UserJoinServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 요청 파라미터를 UTF-8로 인코딩 (한글 깨짐 방지)
        request.setCharacterEncoding("UTF-8");

        // 사용자로부터 전달받은 아이디와 비밀번호 파라미터 가져오기
        String userID = request.getParameter("userID");
        String userPassword = request.getParameter("userPassword");
        String role = request.getParameter("role");
        String status = request.getParameter("status");
        String name = request.getParameter("name");
        String residentNumberFront = request.getParameter("residentNumberFront");
        String residentNumberBack = request.getParameter("residentNumberBack");
        String gender = request.getParameter("gender");
        String address = request.getParameter("address");
        String phoneNumber = request.getParameter("phoneNumber");
        String officeNumber = request.getParameter("officeNumber");
        String email = request.getParameter("email");
        String college = request.getParameter("college");
        String major = request.getParameter("major");
        
        // 응답의 콘텐츠 타입을 UTF-8로 설정
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        
        // 입학년도 컬럼의 값을 문자열로 받아 숫자 검증
        // role별(교수/교직원/관리자)로 분기 처리(admissionYear 개념이 없으므로, 값을 NULL 또는 0으로 저장)
        int admissionYear = 0;
        String admissionYearParam = request.getParameter("admissionYear");
        if ("학생".equals(role)) {
            // 학생인 경우에만 입학년도 숫자 검증
            try {
                admissionYear = Integer.parseInt(admissionYearParam);
            } catch (NumberFormatException e) {
                writer.println("<script>");
                writer.println("alert('입학년도는 숫자로 입력해주세요.');");
                writer.println("history.back();");
                writer.println("</script>");
                return;
            }
        }
        // 학생이 아닌 경우(교수/교직원/관리자)는 admissionYear = 0으로 유지 → UserDAO에서 NULL로 저장

        // 아이디 또는 비밀번호가 비어 있거나 null인 경우를 고려해 필수값 체크
        if (userID == null || userPassword == null || userID.trim().isEmpty() || userPassword.trim().isEmpty()) {
            writer.println("<script>");
            writer.println("alert('아이디와 비밀번호를 모두 입력해주세요.');");
         	// 이전 페이지로 이동
            writer.println("history.back();"); 
            writer.println("</script>");
            return;
        }

        // DB에 접근하기 위한 UserDAO 객체 생성
        UserDAO userDAO = new UserDAO();

        // join 메서드를 호출해 회원가입 처리
        int result = userDAO.join(
        	userID, userPassword, email, name, phoneNumber, officeNumber, role, address, 
        	residentNumberFront, residentNumberBack, gender, college, major, admissionYear, status); 

        // 회원가입 결과에 따른 분기 처리
        if (result == 1) {
            // 회원가입 성공
            writer.println("<script>");
            writer.println("alert('회원가입에 성공했습니다. 로그인 페이지로 이동합니다.');");
            writer.println("location.href = '" + request.getContextPath() + "/common/login.jsp';");
            writer.println("</script>");
        } else if (result == 2) {
            // 중복 아이디
            writer.println("<script>");
            writer.println("alert('이미 존재하는 아이디입니다.');");
            writer.println("history.back();");
            writer.println("</script>");
        } else if (result == -3) {
            // 비밀번호 정책 위반
            writer.println("<script>");
            writer.println("alert('비밀번호가 정책을 충족하지 않습니다.\\n"
                    + "※ 8자 이상, 대/소문자·숫자·특수문자 중 3종 이상 조합\\n"
                    + "※ 아이디·이메일·전화번호·생년월일 포함 불가');");
            writer.println("history.back();");
            writer.println("</script>");
        } else if (result == -1) {
            // 기타 실패 사유
            writer.println("<script>");
            writer.println("alert('이미 존재하는 아이디이거나 오류가 발생했습니다.');");
            writer.println("history.back();");
            writer.println("</script>");
        } else {
            // 그 외의 예외 상황
            writer.println("<script>");
            writer.println("alert('회원가입 중 오류가 발생했습니다. 결과 코드: " + result + "');");
            writer.println("history.back();");
            writer.println("</script>");
        }
    }
}
