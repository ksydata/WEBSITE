<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="main.java.user.UserDAO" %>
<%@ page import="java.io.PrintWriter" %>

<%
    // 요청 파라미터를 UTF-8로 인코딩 (한글 깨짐 방지)
    request.setCharacterEncoding("UTF-8");

    // 사용자로부터 전달받은 아이디와 비밀번호 파라미터 가져오기
    String userID = request.getParameter("userID");
    String userPassword = request.getParameter("userPassword");

    // 응답의 콘텐츠 타입을 UTF-8로 설정
    response.setContentType("text/html;charset=UTF-8");
 	// HTML 출력을 위한 PrintWriter 객체 생성
    PrintWriter writer = response.getWriter();

    // 아이디 또는 비밀번호가 비어 있거나 null이면 에러 메시지 출력 후 이전 페이지로 이동
    if (userID == null || userPassword == null || userID.trim().isEmpty() || userPassword.trim().isEmpty()) {
        writer.println("<script>");
        writer.println("alert('아이디와 비밀번호를 모두 입력해주세요.');"); // 경고창
     	// 이전 페이지로 이동
        writer.println("history.back();"); 
        writer.println("</script>");
     	// 이후 코드 실행하지 않고 종료
        return; 
    }

    // DB에 접근하기 위한 UserDAO 객체 생성
    UserDAO userDAO = new UserDAO();

    // join 메서드를 호출해 회원가입 처리 (성공 시 1, 실패 시 -1 등 반환)
    int result = userDAO.join(userID, userPassword);

    // 회원가입 성공 시
    if (result == 1) {
        writer.println("<script>");
        writer.println("alert('회원가입에 성공했습니다.');");
        // 메인 페이지로 이동
        writer.println("location.href = 'index.jsp';");
        writer.println("</script>");
    } else {
        // 회원가입 실패 시
        writer.println("<script>");
        writer.println("alert('회원가입에 실패했습니다. 이미 존재하는 아이디일 수 있습니다.');");
     	// 이전 페이지로 이동
        writer.println("history.back();"); 
        writer.println("</script>");
    }
%>