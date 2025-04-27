<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%-- 문자 인코딩 설정 (Unicode Transformation Format–8bit) --%>
	<meta charset="UTF-8">
	<%-- DB를 통한 회원가입을 위한 입력폼 페이지 --%>
	<title>회원가입 폼</title>
	<link rel="stylesheet" href="../css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h3 class="text-center mb-4">회원가입</h3>
        <%-- 사용자가 입력한 값을 <form> 태그를 통해 userJoinAction.jsp에서 처리하도록 설정 --%>
        <form action="<%=request.getContextPath()%>/userJoinAction" method="post">
            <div class="form-group mb-3">
                <label for="userID">아이디</label>
                <input type="text" class="form-control" name="userID" id="userID" required>
            </div>
            
            <div class="form-group mb-4">
            	<label for="userPassword">비밀번호</label>
            	<input type="password" class="form-control" name="userPassword" required>
            </div>
            
            <%-- 회원가입 버튼과 취소 버튼(취소 시 로그인 페이지로 이동) --%>
            <div class="d-grid gap-2">
            	<button type="submit" class="btn btn-success">회원가입</button>
            	<a href="./login.jsp" class="btn btn-secondary">취소</a>
            </div>
         </form>
     </div>
</body>
</html>