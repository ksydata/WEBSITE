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
	
	<script>
		// 회원 유형별로 상태가 다르게 보이도록 설정하는 Javascript
		function updateStatusOptions() {
			const role = document.getElementById("role").value;
			const statusSelect = document.getElementById("status");
		    const collegeDiv = document.getElementById("collegeDiv");
		    const majorDiv = document.getElementById("majorDiv");
		    const admissionYearDiv = document.getElementById("admissionYearDiv");			
			
			// 상태 옵션 드롭다운 초기화
			statusSelect.innerHTML = "";
			let options = [];
			
            // 학생, 교수, 교직원, 관리자별로 각 상태 옵션 다르게 설정
		    if (role === "학생") {
		        options = ["재학", "수료", "휴학", "졸업", "기타"];
		        collegeDiv.style.display = "block";
		        majorDiv.style.display = "block";
		        admissionYearDiv.style.display = "block";		        
		    } else if (role === "교수") {
		        options = ["재직", "휴직", "퇴직", "안식년", "기타"];
		        collegeDiv.style.display = "block";
		        majorDiv.style.display = "block";
		        admissionYearDiv.style.display = "none";		        
		    } else if (role === "교직원") {
		        options = ["재직", "휴직", "퇴직", "기타"];
		        collegeDiv.style.display = "none";
		        majorDiv.style.display = "none";
		        admissionYearDiv.style.display = "none";			        
		    } else if (role === "관리자") {
		        options = ["활성화", "비활성화"];
		        collegeDiv.style.display = "none";
		        majorDiv.style.display = "none";
		        admissionYearDiv.style.display = "none";		        
		    } else {
		        options = ["회원 유형을 먼저 선택하세요"];
		    }
            
            // options 배열을 통해 상태 옵션 드롭다운을 업데이트
	        options.forEach(function(opt) {
	            const optionElement = document.createElement("option");
	            optionElement.value = opt;
	            optionElement.text = opt;
	            statusSelect.appendChild(optionElement);
	        });
	    }
		            
        // 자바스크립트에서 처음 페이지 로드할 때 <body>태그 본문의 <select id="role">의 "onchange" 이벤트 걸어주기(1번만 호출)
        // role 셀렉트박스 바꿀 때마다 updateStatusOptions()가 호출하면 change 이벤트가 중첩 등록되어 메모리 누수 위험    
	    window.onload = function() {
	        document.getElementById("role").addEventListener("change", updateStatusOptions);
	        updateStatusOptions();
	    }
	</script>
</head>
<body onload="updateStatusOptions()">
    <div class="container mt-5">
        <h3 class="text-center mb-4">회원가입</h3>
        <%-- 사용자가 입력한 값을 <form> 태그를 통해 userJoinAction.jsp에서 처리하도록 설정 --%>
        <form action="<%=request.getContextPath()%>/UserJoinServlet" method="post">
            <%-- 로그인 인증 정보 --%>
            <div class="form-group mb-3">
                <label for="userID">아이디</label>
                <input type="text" class="form-control" name="userID" id="userID" required>
            </div>
            <div class="form-group mb-3">
            	<label for="userPassword">비밀번호</label>
            	<input type="password" class="form-control" name="userPassword" id="userPassword" required>
            </div>
            
            <%-- 가입자 회원 유형과 상태값 --%>
            <%-- 회원 유형 변경 시 상태 옵션 셀렉트박스가 바뀌도록 자바스크립트 함수를 호출 --%>
            <div class="form-group mb-4">
            	<label for="role">회원 유형</label>
            	<select class="form-select" name="role" id="role" required onchange="updateStatusOptions()">
			        <option value="">선택</option>
			        <option value="학생">학생</option>
			        <option value="교수">교수</option>
			        <option value="교직원">교직원</option>
			        <option value="관리자">관리자</option>
            	</select>
            </div>
            <div class="form-group mb-3">
                <label for="status">상태</label>
                <select class="form-select" name="status" id="status" required>
                    <option value="">회원 유형을 먼저 선택하세요</option>
                </select>
            </div>
            
            <%-- 개인식별정보 및 고유식별정보 --%>
            <div class="form-group mb-3">
            	<label for="name">이름</label>
            	<input type="text" class="form-control" name="name" id="name" required></input>
            </div>
			<%-- 생년월일(주민등록번호 앞 6자리) + 성별과 출생지정보(주민등록번호 뒤 7자리 별도로 입력받기) > 앞자리를 DB에 birthDate으로 별도로 저장 --%>
            <div class="form-group mb-3">
            	<div class="d-flex">
            	<label for="residentNumber">주민등록번호</label>
            		<input type="text" class="form-control me-2" name="residentNumberFront" id="residentNumberFront" placeholder="앞 6자리" required></input>
            		<span class="mt-2">-</span>
            		<input type="text" class="form-control me-2" name="residentNumberBack" id="residentNumberBack" placeholder="뒤 7자리" required></input>            	
            	</div>
            </div>
	        <div class="form-group mb-3">
	            <label>성별</label><br>
	            <div class="form-check form-check-inline">
	                <input class="form-check-input" type="radio" name="gender" id="genderMale" value="M" required></input>
	                <label class="form-check-label" for="genderMale">남성</label>
	            </div>
	            <div class="form-check form-check-inline">
	                <input class="form-check-input" type="radio" name="gender" id="genderFemale" value="F" required></input>
	                <label class="form-check-label" for="genderFemale">여성</label>
	            </div>
	            <div class="form-check form-check-inline">
	            	<input class="form-check-input" type="radio" name="gender" id="genderOther" value="Other" required></input>
	            	<label class="form-check-label" for="genderOther">기타</label>
	            </div>
	        </div>
            <div class="form-group mb-3">
                <label for="address">주소</label>
                <input type="text" class="form-control" name="address" id="address" required>
            </div>
                        
            <%-- 일반개인정보(연락처 관련) --%>
            <div class="form-group mb-3">
            	<label for="phoneNumber">휴대전화번호</label>
            	<input type="tel" class="form-control" name="phoneNumber" id="phoneNumber" required></input>
            </div> 
            <div class="form-group mb-3">
                <label for="officeNumber">사무실 전화번호(선택)</label>
                <input type="tel" class="form-control" name="officeNumber" id="officeNumber">
            </div>                        
            <div class="form-group mb-3">
            	<label for="email">이메일</label>
            	<input type="email" class="form-control" name="email" id="email" required></input>
            </div>
            
            <%-- 학생, 교수 입력 폼: 학사정보 --%>            
            <div class="form-group mb-3" id="collegeDiv">
            	<label for="college">단과대학</label>
            	<select class="form-select" name="college" id="college">
            		<option value="">선택</option>
            		<option value="경영대학">경영대학</option>
            		<option value="공과대학">공과대학</option>
            		<option value="문과대학">문과대학</option>
            		<option value="사회과학대학">사회과학대학</option>
            		<option value="소프트웨어융합대학">소프트웨어융합대학</option>
            		<option value="자연과학대학">자연과학대학</option>
            	</select>            		    		
            </div>
            <div class="form-group mb-3" id="majorDiv">
            	<label for="major">전공</label>
            	<select class="form-select" name="major" id="major">
            		<option value="">선택</option>
            		<option value="데이터사이언스학과">데이터사이언스학과</option>
            		<option value="경영학과">경영학과</option>
            		<option value="수학과">수학과</option>
            		<option value="심리학과">심리학과</option>
            		<option value="영어영문학과">영어영문학과</option>
            		<option value="전자공학과">전자공학과</option>
            		<option value="컴퓨터공학과">컴퓨터공학과</option>
            	</select>
            </div>
            <div class="form-group mb-4" id="admissionYearDiv">
                <label for="admissionYear">입학년도</label>
                <input type="number" class="form-control" name="admissionYear" id="admissionYear">
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