<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%--HTML 5 문서유형 선언--%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<!-- 요건 아이콘을 사용하기 위한 하이퍼링크 import -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css"/>
</head>

<body>
	<!-- view단 텍스트를 입력할 수 있는 input태그 생성 -->
	<form id="searchForm" action="/search" method="GET">
		<!-- input태그 내 키보드의 엔터키를 눌렀다 뗐을 때 사용자가 입력한 값을 컨트롤러를 보낼 수 있도록 form 태그 생성 -->
		<input onkeyup="enterkey()" type="search" name="searchValue"/>
		
		<!-- 검색을 대신하는 요건 아이콘  -->		
		<button type="submit">검색
		<i class="fa-solid fa-magnifying-glass"></i>
		</button>
	</form>
	
	<script>
	// 키보드가 keyCode==13, 즉 엔터키를 누를 때 반응하는 함수
	function enterkey() {
		if (window.event.keyCode == 13) {
			// 기본 엔터 제출 방지
			event.preventDefault();
			// form태그의 submit를 실행하기 위해 리모컨 버튼 누르듯이 조종
			document.getElementById("searchForm").submit();		}
	}
	</script>
</body>

</html>

<!-- 
	// 검색창에 임시로 채워넣는 값 설정
	<div class="col-md-3">
		<input type="text" name="searchValue" class="form-control" placeholder="검색어 입력">
	</div>
 -->