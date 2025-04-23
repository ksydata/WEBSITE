<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%--HTML 5 문서유형 선언--%>
<!DOCTYPE html>
<html>

<head>
	<%-- 브라우저 탭에 표시될 웹사이트 제목 --%>
	<title>눈송여자대학교 홈페이지</title>
	<%-- 문자 인코딩 설정 (Unicode Transformation Format–8bit) --%>
	<meta charset="UTF-8">
	<%-- 모바일 웹 반응형 지원(뷰 포트에 맞는 설정 추가) --%>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<!-- 부트스트랩 스타일 시트 연결 href="./css/bootstrap.min.css?ver=1" -->
	<link rel="stylesheet" href="./css/bootstrap.min.css">
	<!-- 사용자 정의(커스텀) 스타일 시트 연결 -->
	<link rel="stylesheet" href="./css/custom.css">
</head>

<body>
	<%-- 네비게이션 바(반응형 디자인을 위한 설정, 하얀색 배경) 추가 --%>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<a class="navbar-brand" href="index.jsp">눈송여자대학교 홈페이지</a>
		<%-- 모바일 메뉴 토글 버튼(화면 크기가 작아질 때 메뉴 축소/확장) --%>
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar">
			<span class="navbar-toggler-icon"></span>
		</button>		
		<%-- 네비게이션 항목들 --%>		
		<div id="navbar" class="collapse navbar-collapse">
			<%-- 목록(list)를 가지는 요소인 ul 태그 --%>	
			<ul class="navbar-nav mr-auto">
                <%-- 메인 페이지 링크(현재 웹사이트의 홈 화면으로 이동하는 항목) --%>                				
				<li class="nav-item active">
					<a class="nav-link" href="index.jsp">
					메인
					</a>
				</li>
				<%-- 회원관리 드롭다운(추가적인 사용자 관리 메뉴를 포함할 공간) --%>				
				<li class="nav-item dropdown">
					<a class="nav-link dropdown-toggle" id="dropdown" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					회원관리
					</a>
					<%-- 드롭다운 메뉴 컨테이너(드롭다운 항목 포함할 공간) --%>
					<div class="dropdown-menu" aria-labelledby="dropdown">
						<a class="dropdown-item" href="#">로그인</a>
						<a class="dropdown-item" href="#">회원가입</a>
						<a class="dropdown-item" href="#">로그아웃</a>
					</div>
				</li>
			</ul>
			<%-- 상단 검색폼과 검색전송 버튼 --%>
			<form class="form-inline my-2 my-lg-0">
				<input class="form-control mr-sm-2" type="search" placeholder="검색어를 입력하세요" aria-label="Search"></input>
				<button class="btn btn-outline-success my-2 my-sm-0 btn-custom-blue" type="submit">
				검색
				</button>
			</form>
		</div>
    </nav>
    
	<%-- 컨테이너(본문 콘텐츠를 담는 영역) --%>
	<section class="container">
		<%-- 필터 및 버튼이 포함된 검색 폼 --%>
		<form method="get" action="./index.jsp" class="form-inline mt-3">
			<select name="lecturnDivide" class="form-control mx-1 mt-2">
				<option value="전체">전체</option>
				<option value="전공">전공</option>
				<option value="교양">교양</option>
				<option value="기타">기타</option>
			</select>
			<input type="text" name="search" class="form-control mx-1 mt-2" placeholder="검색어를 입력하세요"></input>
			<button type="submit" class="btn btn-primary mx-1 mt-2">검색</button>
			<a class="btn btn-primary mx-1 mt-2" data-toggle="modal" href="#registerModal">등록</a>
			<a class="btn btn-primary mx-1 mt-2" data-toggle="modal" href="#reportModal">신고</a>
		</form>
			<%-- 평가 등록 모달(modal) 창 --%>			
			<div class="modal fade" id="registerModal" tabindex="-1" role="dialog" aria-labelledby="modal" aria-hidden="true"></div>
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
			                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
			                    <span aria-hidden="true">&times;</span>
			                </button>
						</div>
						
						<div class="modal-body">
							<form action="./registerClass.jsp" method="post">
								<div class="form-group col-sm-6">
									<label>강의명</label>
									<input type="text" name="lectureName" class="form-control" maxlength="20">
								</div>
								<div class="form-group col-sm-6">
									<label>교수명</label>
									<input type="text" name="professorName" class="form-control" maxlength="20">
								</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			<%-- 각각의 강의평가 카드 구성 --%>
			<%-- 1번째 강의평가 카드 --%>
			<%-- 2번째 강의평가 카드 --%>
			<%-- 3번째 강의평가 카드 --%>
			
			<%-- 페이지네이션 --%>
	</section>
	
    <%-- 푸터(웹사이트 최하단 영역) --%>
    <%-- 어두운 배경(dark background)에 흰색 텍스트로 푸터 설정 --%>
    <footer class="bg-dark mt-4 p-5 text-center" style="color: #FFFFFF;">
    </footer>
    
    <%-- 필수 자바스크립트 라이브러리들 추가 --%>
    <script src="./js/jquery.min.js"></script>
    <script src="./js/popper.min.js"></script>
    <script src="./js/bootstrap.min.js"></script>
</body>

</html>


<%-- 
	<form action="./userJoinAction.jsp" method="post">
		<input type="text" name="userID">
		<input type="password" name="userPassword">
		<input type="submit" value="회원가입">
	</form>
--%>