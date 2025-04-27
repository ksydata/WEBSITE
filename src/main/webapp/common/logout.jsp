<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- 세션 해제 방식으로 로그아웃 기능 --%>
<%
session.invalidate(); 
// 세션 삭제
response.sendRedirect("./login.jsp"); 
// 로그인 페이지로 리다이렉트
%>