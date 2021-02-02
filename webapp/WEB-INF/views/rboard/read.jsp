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
				<div id="read">
					<form action="${pageContext.request.contextPath}/rboard/" method="get">
						<!-- 작성자 -->
						<div class="form-group">
							<span class="form-text">작성자</span>
							<span class="form-value">${bVo.name}</span>
						</div>
						
						<!-- 조회수 -->
						<div class="form-group">
							<span class="form-text">조회수</span>
							<span class="form-value">${bVo.hit}</span>
						</div>
						
						<!-- 작성일 -->
						<div class="form-group">
							<span class="form-text">작성일</span>
							<span class="form-value">${bVo.regDate}</span>
						</div>
						
						<!-- 제목 -->
						<div class="form-group">
							<span class="form-text">제 목</span>
							<span class="form-value">${bVo.title}</span>
						</div>
					
						<!-- 내용 -->
						<div id="txt-content">
							<span class="form-value" >
										${bVo.content}
							</span>
						</div>

						<!-- 조건필요 : 로그인 상태와 아닌 상태 -->
						<!-- 로그인 상태: authUser.no - sessionScope.authUser.no -->
						<!-- 로그인 아닌 상태: bVo.userNo - requestScope.bVo.userNo -->

						<c:if test ="${sessionScope.authUser.no == requestScope.bVo.userNo}">
							<a id="btn_modify" href="${pageContext.request.contextPath}/rboard/modifyForm?no=${bVo.no}">수정</a>	
						</c:if>
						
						<c:if test = "${authUser.no != null}">
						    <a id="btn_modify" href="${pageContext.request.contextPath}/rboard/commentForm? ">댓글쓰기</a>
						</c:if>
						
							<a id="btn_modify" href="${pageContext.request.contextPath}/rboard/list">목록</a>

						
	
					</form>
	                <!-- //form -->
				</div>
				<!-- //read -->
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
