<%@ page import="user.UserDAO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 처리</title>
<%
    request.setCharacterEncoding("UTF-8");

    String userID = request.getParameter("userID");
    String userPassword = request.getParameter("userPassword");

    UserDAO userDAO = new UserDAO();
    int result = userDAO.login(userID, userPassword);

    if (result == 1) {
        session.setAttribute("userID", userID);
        response.sendRedirect("./index.jsp");
    } else {
        String message = "";
        if (result == 0) {
            message = "비밀번호가 일치하지 않습니다.";
        } else if (result == -1) {
            message = "존재하지 않는 아이디입니다.";
        } else if (result == -2) {
            message = "데이터베이스 오류가 발생했습니다.";
        } else {
            message = "알 수 없는 오류가 발생했습니다.";
        }
%>
        <script>
            alert("<%= message %>");
            history.back();
        </script>
<%
    }
%>
</head>
</html>
