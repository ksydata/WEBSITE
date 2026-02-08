<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>학사정보 수정</title>
</head>
<body>

<!-- 공통 상단 메뉴 -->
<jsp:include page="/include/header.jsp" />

<div class="container mt-5">
	<h2 class="text-center mb-4">성적 수정</h2>
	
	<!-- 사용자 이름, 단과대학, 전공 등 학적 정보 카드 -->
    <div class="card mb-4">
        <div class="card-header bg-primary text-white">
            학적 정보
        </div>
        <div class="card-body">
            <p><strong>이름:</strong> ${userName}</p>
            <p><strong>단과대학:</strong> ${recordList[0].college}></p>
            <p><strong>전공:</strong> ${recordList[0].major}</p>
        </div>
    </div>
    
    <!-- [TO-BE] 학적 정보 카드 -->
	<%-- <div class="card mb-4">
	    <div class="card-header bg-primary text-white">
	        학적 정보
	    </div>
	    <div class="card-body">
	        <c:forEach var="entry" items="${profileViewMap}">
	            <p>
	                <strong>${entry.key}:</strong>
	                ${entry.value}
	            </p>
	        </c:forEach>
	    </div>
	</div> --%>
    
    <!-- 학기별 성적 수정 form -->
    <!-- '조회'에 있던 데이터를 기본 호출하기 (드롭다운 버튼부 포함) -->
    <form action="${pageContext.request.contextPath}/academicRecord"
          method="post">
    <div class="card">
        <div class="card-header bg-secondary text-white">
            성적 정보
        </div>
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-bordered table-hover text-center align-middle">
                    <thead class="thead-dark">
                        <tr>
                            <th>수강연도</th>
                            <th>학기</th>
                            <th>과목명</th>
                            <th>이수구분</th>
                            <th>성적(등급)</th>
                            <th>성적(평점)</th>
                            <th>P/F 여부</th>
                            <th>P/F</th>
                            <th>재수강 년도</th>
                            <th>재수강 학기</th>
                            <th>재수강 사유</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="record" items="${recordList}">
                            <tr>
                                <td>${record.academicYear}</td>
                                <td>${record.semester}</td>
                                <td>${record.courseName}</td>
                                <td>${record.courseType}</td>
                                <!-- 성적 수정 드롭다운 버튼 -->
                                <td>
								    <select name="grade_${record.recordID}" class="form-select" 
								    		${record.coursePF ? 'disabled' : ''}>
								        <option value="">선택</option>
								        <option value="A+" ${record.grade == 'A+' ? 'selected' : ''}>A+</option>
								        <option value="A"  ${record.grade == 'A'  ? 'selected' : ''}>A</option>
								        <option value="B+" ${record.grade == 'B+' ? 'selected' : ''}>B+</option>
								        <option value="B"  ${record.grade == 'B'  ? 'selected' : ''}>B</option>
								        <option value="C+" ${record.grade == 'C+' ? 'selected' : ''}>C+</option>
								        <option value="C"  ${record.grade == 'C'  ? 'selected' : ''}>C</option>
								        <option value="D+" ${record.grade == 'D+' ? 'selected' : ''}>D+</option>
								        <option value="D"  ${record.grade == 'D'  ? 'selected' : ''}>D</option>
								        <option value="F"  ${record.grade == 'F'  ? 'selected' : ''}>F</option>
								    </select>
								
								    <!-- recordID를 함께 넘기기 -->
								    <input type="hidden" name="recordID" value="${record.recordID}">
								</td>
                                <td>${record.gradePoint}</td>
                                
                                <!-- PF 여부 -->
                                <td>${record.coursePF}</td>

                                <!-- PF 합/불 -->
                                <td>
                                    <select name="passOrFail_${record.recordID}"
                                            class="form-select"
                                            ${!record.coursePF ? 'disabled' : ''}>
                                        <option value="true"  ${record.passOrFail?'selected':''}>Pass</option>
                                        <option value="false" ${!record.passOrFail?'selected':''}>Fail</option>
                                    </select>
                                </td>

                                <!-- hidden 값 -->
                                <input type="hidden"
                                       name="pf_${record.recordID}"
                                       value="${record.coursePF}" />
                            </tr>
                        </c:forEach>
                        <c:if test="${empty recordList}">
                            <tr>
                                <td colspan="13">성적 정보가 없습니다.</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
            
            <!-- 저장 버튼 -->
            <div class="text-end mt-3">
                <button type="submit" class="btn btn-primary">
                    성적 저장
                </button>
            </div>
        </div>
    </div>
   </form>

</div>

<!-- 공통 푸터 -->
<jsp:include page="/include/footer.jsp" />


</body>
</html>