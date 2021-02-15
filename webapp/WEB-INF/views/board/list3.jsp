<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="${pageContext.request.contextPath}/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/assets/css/board.css" rel="stylesheet" type="text/css">

</head>


<body>
	<div id="wrap">

		<c:import url="/WEB-INF/views/include/header.jsp"></c:import>
		<!-- //header -->
		<!-- //nav -->

		<c:import url="/WEB-INF/views/include/aside.jsp"></c:import>
		<!-- aside로 옮겼음 -->

		<div id="content">

			<div id="content-head">
				<h3>게시판</h3>
				<div id="location">
					<ul>
						<li>홈</li>
						<li>게시판</li>
						<li class="last">일반게시판</li>
					</ul>
				</div>
				<div class="clear"></div>
			</div>
			<!-- //content-head -->

			<div id="board">
				<div id="list">
					<form action="${pageContext.request.contextPath}/board/list3" method="get">
						<div class="form-group text-right">
							<input type="text" name="keyword" value="">
							<button type="submit" id=btn_search>검색</button>
						</div>
					</form>
					<table >
						<thead>
							<tr>
								<th>번호</th>
								<th>제목</th>
								<th>글쓴이</th>
								<th>조회수</th>
								<th>작성일</th>
								<th>관리</th>
							</tr>
						</thead>
						<tbody>
						<c:forEach items="${pMap.boardList}" var="bvo"> 
								<tr>
									<td>${bvo.no}</td>
									<td class="text-left"><a href="${pageContext.request.contextPath}/board/read?no=${bvo.no}">${bvo.title}</a></td>
									<td>${bvo.name}</td>
									<td>${bvo.hit}</td>
									<td>${bvo.regDate}</td>
									
									<td>
									<c:if test="${authUser.no == bvo.userNo}">  <!--주의: bvo-bList에 붙여준 이름 -->
										<a href="${pageContext.request.contextPath}/board/remove?no=${bvo.no}">[삭제]</a>
									</c:if>
									</td>
								</tr>
						</c:forEach>
						
						</tbody>
					</table>
		
					<div id="paging">
						<ul>
						
							<c:if test="${pMap.prev == true}">
								<li><a href="${pageContext.request.contextPath}/board/list3?crtPage=${pMap.startPageBtnNo-1}&keyword=${param.keyword}">◀</a></li>
							</c:if>
						
							<c:forEach begin="${pMap.startPageBtnNo}" end="${pMap.endPageBtnNo}" step="1" var="page">
								<li><a href="${pageContext.request.contextPath}/board/list3?crtPage=${page}&keyword=${param.keyword}">${page} </a></li>  <!-- 상단의 var="page" -->
							</c:forEach>
														
							<c:if test="${pMap.next == true}">
								<li><a href="${pageContext.request.contextPath}/board/list3?crtPage=${pMap.endPageBtnNo+1}&keyword=${param.keyword}">▶</a></li>
							</c:if>
							
						</ul>
						
						
						<div class="clear"></div>
					</div>
					<c:if test="${!empty authUser}">
						<a id="btn_write" href="${pageContext.request.contextPath}/board/writeForm">글쓰기</a>
					</c:if>
				</div>
				<!-- //list -->
			</div>
			<!-- //board -->
		</div>
		<!-- //content  -->
		<div class="clear"></div>

		<c:import url="/WEB-INF/views/include/footer.jsp"></c:import>
		<!-- footer로 옮김 -->
	</div>
	<!-- //wrap -->

</body>

</html>