<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <title>나의 개인정보</title>
    <link rel="stylesheet" href="../css/bootstrap.min.css">    
</head>

<body>
<!-- 공통 상단 메뉴 -->
<jsp:include page="/include/header.jsp" />

<div class="container mt-5">
    <h2 class="text-center mb-4">사용자 개인정보 리스트</h2>
    
    <table class="table">
    	<thead>
    		<tr>
    			<th>아이디</th>
    			<th>이름</th>
    			<th>생년월일</th>
    			<th>단과대학</th>
    			<th>전공</th>
    			<th>상태</th>    			
    			<th>휴대전화번호</th>
    			<th>이메일</th>
    			<th>주소</th>
    		</tr>
    	</thead>
    	<tbody>
    		<%-- [AS-IS] <c:forEach var="user" items="${userList.items}"> --%>
    		<c:forEach var="user" items="${userList.pagingDataList}">
    			<tr>
    				<td>${user.userID}</td>
    				<td>${user.name}</td>
    				<td>${user.residentNumber}</td>
    				<td>${user.college}</td>
    				<td>${user.major}</td>
    				<td>${user.status}</td>
    				<td>${user.phoneNumber}</td>
    				<td>${user.email}</td>
    				<td>${user.address}</td>
    			
    				<%-- [AS-IS]
    				<td>${userlist.userID}</td>
    				<td>${userlist.name}</td>
    				<td>${userlist.residentNumber}</td>
    				<td>${userlist.college}</td>
    				<td>${userlist.major}</td>
    				<td>${userlist.status}</td>
    				<td>${userlist.phoneNumber}</td>
    				<td>${userlist.email}</td>
    				<td>${userlist.address}</td>  --%>	
    			</tr>
    		</c:forEach>
    	</tbody>
    </table>
     
</div>
   
  
<!-- 하단 영역의 메인 페이지로 돌아가는 링크 -->
<br>
<a href="<c:url value='/main.jsp' />">메인으로 돌아가기</a>
<!-- 공통 푸터 -->
<jsp:include page="/include/footer.jsp" />
</body>

</html>