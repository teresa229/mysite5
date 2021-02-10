<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!-- css 끼리 -->
<link href="${pageContext.request.contextPath}/assets/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/assets/css/guestbook.css" rel="stylesheet" type="text/css">

<!-- 자바스크립트 끼리 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.12.4.js"></script>  <!-- 제이쿼리가 위에 와야 한다. -->
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/bootstrap/js/bootstrap.js"></script>

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
				<h3>일반방명록</h3>
				<div id="location">
					<ul>
						<li>홈</li>
						<li>방명록</li>
						<li class="last">일반방명록</li>
					</ul>
				</div>
				<div class="clear"></div>
			</div>
			<!-- //content-head -->

			<div id="guestbook">
				<!-- <form action="" method=""> -->
				<table id="guestAdd">
					<colgroup>
						<col style="width: 70px;">
						<col>
						<col style="width: 70px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th><label class="form-text" for="input-uname">이름</label>
							</td>
							<td><input id="input-uname" type="text" name="name"></td>
							<th><label class="form-text" for="input-pass">패스워드</label>
							</td>
							<td><input id="input-pass" type="password" name="pass"></td>
						</tr>
						<tr>
							<td colspan="4"><textarea name="content" cols="72" rows="5"></textarea></td>
						</tr>
						<tr class="button-area">
							<td colspan="4"><button id="btnSubmit" type="submit">등록</button></td>
							<!-- id="btnSubmit" -->
						</tr>
					</tbody>

				</table>
				<!-- //guestWrite -->
				<input type="hidden" name="action" value="add">

				<!-- </form> -->

				<div id="guestbookListArea">
					<!-- 방명록 글 리스트. 출력영역 -->
					<!-- 삭제의 부모로 선택 -->
				</div>

			</div>
			<!-- //guestbook -->
		</div>
		<!-- //content  -->
		<div class="clear"></div>

		<!-- footer -->
		<c:import url="/WEB-INF/views/include/footer.jsp"></c:import>

	</div>
	<!-- //wrap -->

	<!-- 모달창 영역 -->
	<div class="modal fade" id="delModal">
		<!-- id="delModal 정한 이름 추가-->
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">방명록 삭제</h4>
				</div>
				<div class="modal-body">

					<label>비밀번호</label>
					<input id="modalPassword" type="text" name="password" value="">  <!-- password --> <!-- id -->
					
					<!-- no 히든 처리 : [삭제]를 눌렀을 때.값을 읽어오게-->
					<input id="modalNo" type="text" name="no" value="" >

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
					<button id="modalBtnDel" type="button" class="btn btn-primary">삭제</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->



</body>

<script type="text/javascript">
//브라우저 준비가 끝났을 때 = DOM이 생성되면 
$("document").ready(function(){
	console.log("ready");
	
	//리스트 출력
	fetchList();
	
});

//모달창 삭제버튼 클릭할 때 -> 삭제 프로세스
$("#modalBtnDel").on("click",function(){
	console.log("모달창 삭제 버튼 클릭");
	
	//추가: no: $("#modalNo").val();
	
	//추가: 모달창 비밀번호, no는 다른 것으로 사용해서 위로 빼었다.
	/* var guestVo ={
		password: $("#modalPassword").val();
	}; */
	
	console.log(guestVo);
	
	//모달창 비밀번호, no 수집
	var password = $("#modalPassword").val();
	var no = $("#modalNo").val();
	
	console.log(password); /* ""삭제 */
	console.log(no);
	
	$.ajax({
			
			url : "${pageContext.request.contextPath }/api/guestbook/remove",		
			type : "post",
			//contentType : "application/json",
			data : {password: password, no: no},
	
			dataType : "json",
			success : function(count){
				/*성공시 처리해야될 코드 작성*/
				console.log(count);
				
				if(count==1){ //삭제 작업
					//모달창 닫고, 번호에 해당하는 테이블(글)이 화면에서 없어야 한다.
					$("#delModal").modal("hide");
				
					$("#t-"+ no).remove();

				}else{        //count == 0 -->모델창만 닫는다.
					//모달창 닫기
					alert("비밀번호가 틀렸습니다.");
					$("#madalPassword").val("");
					//$("#delModal").modal("hide");
				}
				
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
	});
	
});



//삭제 버튼 클릭할 때 --> 비밀번호 입력창 호출
$("#guestbookListArea").on("click", "a", function(){  //.btnDel ->guestbookListArea 로 영역을 넓힌다. 부모 영역 선택
	event.preventDefault();   //기본 기능을 막아버린다. event 막아버린다.
	console.log("삭제버튼 클릭-> 모달창 호출");
	
	//비밀번호 필드 초기화
	$("#modalPassword").val("");
	
	
	//번호 읽어오기 -> this 본인을 출력하기
	var no = $(this).data("no"); //다른 방법 : var $that = $(this)
	console.log($(this));
	console.log($(this).data("no"));
	console.log(no);                 //소문자만 가능하다. data-no
	
	$("#modalNo").val(no);    //파라미터 값 넘기듯이..필요
		
	$("#delModal").modal();   //모달창 호출
});

//방명록 버튼 등록
$("#btnSubmit").on("click",function(){
 	console.log("방명록 등록");
	 
	//방명록 데이터 등록
	
	//추가: 정리
	var guestVo = {
			name: $("[name='name']").val(),
			password: $("[name='pass']").val(),
			content: $("[name='content']").val()
	};
 	//{"name":1, "password":}
	console.log(guestVo);
	
	/*정리 작업*/
	//var name = $("[name='name']").val(); //값을 가져오는 val.. 자바스크립트에 글자만 담는..var name
	//var password = $("[name='pass']").val();
	//var content = $("[name='content']").val();
	//console.log(name);
	//console.log(password);
	//console.log(content);
	
	//ajax방식으로 요청(저장)
	$.ajax({
		
		url : "${pageContext.request.contextPath}/api/guestbook/write2",		
		type : "post",
		contentType : "application/json",  //JSON.stringify(guestVo)과 짝으로 사용한다.
		data : JSON.stringify(guestVo), //guestVo,   //정리 작업: {name:name, password:password, content:content}, //객체의 필드명: 실제값
		
		dataType : "json",
		success : function(guestVo){  /*성공시 처리해야 될 코드 작성*/
			console.log(guestVo);
			//{no: 125, name: "부엉이", password: null, content: "1234", regDate: "2021-02-09"}
			render(guestVo, "up"); //게스트북 정보 출력
			
			/*입력폼 비우기 */
			$("[name='name']").val(""); //값을 넣어준다.
			$("[name='pass']").val("");
			$("[name='content']").val("");
		},
		error : function(XHR, status, error) {
			console.error(status + " : " + error);
		}
	});
 })

 //방명록 글 정보 + html 조합하여 화면에 출력
 function render(guestVo, updown){  //up,down에 따라 다르게 실행되게 한다. 
	 
	 var str="";
	 str += '<table id="t-'+guestVo.no+'" class="guestRead">';
	 str += '   <colgroup>';
	 str += '           <col style="width: 10%;">';
	 str += '           <col style="width: 40%;">';
	 str += '           <col style="width: 40%;">';
	 str += '           <col style="width: 10%;">';
	 str += '   </colgroup>';
	 str += '   <tr>';
	 str += '       <td>'+ guestVo.no +'</td>';
	 str += '       <td>'+ guestVo.name +'</td>';
	 str += '       <td>'+ guestVo.regDate +'</td>';
	 str += '       <td><a href="" ﻿data-no="'+ guestVo.no +'">[삭제]</a></td>';  /* class는 여러개 와도 된다. data 다음에 소문자만 인식한다. 여러개가 올 수 있다. */
	 str += '   </tr>';
	 str += '   <tr>';
	 str += '      <td colspan=4 class="text-left">방명록 글입니다. 방명록 글입니다.</td>';
	 str += '   </tr>';
	 str += '</table>';
	 
	 if(updown =="down"){        //리스트 뿌리는 것은 다운
		 console.log("down");
		 $("#guestbookListArea").append(str);
	 }else if(updown =="up"){    //저장할 떄는 상단에 up
		 $("#guestbookListArea").prepend(str);
	 }else{
		 console.log("방향 미지정");
	 }
	 
	// $("#guestbooklistArea").prepend(str);            /* append는 뒤에 붙는다. */
 }

//전체 리스트 출력
function fetchList(){
	$.ajax({
			
			url : "${pageContext.request.contextPath}/api/guestbook/list",		
			type : "post",
			//contentType : "application/json",
			//data : {name: "홍길동"},
	
			dataType : "json",
			success : function(guestbookList){ //guestbookList : 적당한 이름은 만들어 준다.  success에서 받아서 넣어준다.
				/*성공시 처리해야될 코드 작성*/
				console.log(guestbookList);
				
				for(i=0; i<guestbookList.length; i++){
					render(guestbookList[i], "down");   //"up/down --내가 만들어서 정의해 준다. TIP"
				}
				
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
	
	
	
	
	
}


</script>

</html>