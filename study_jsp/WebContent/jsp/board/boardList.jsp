<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="co.kr.ucs.bean.BoardBean"%>
<%@ page import="co.kr.ucs.bean.SearchBean"%>
<%@ page import="co.kr.ucs.service.BoardService"%>
 
 
<%
//response.setHeader("Content-Type", "application/xml");
//response.setContentType("text/xml;charset=UTF-8");
response.setCharacterEncoding("UTF-8");

// 	List<BoardBean> boardList = (List<BoardBean>)request.getAttribute("list");
// 	SearchBean searchBean = (SearchBean)request.getAttribute("SearchBean");
// 	int totalRows = (Integer)request.getAttribute("totalRows");
// 	String searchKey = searchBean.getSearchKey();
// 	String searchWord = searchBean.getSearchWord();
// 	int cPage =searchBean.getCpage();


// 	int pageBlock = 10; // 페이지 블럭 개수
//    	int pageGroup ; //pageGroup
// 	int startRow = (cPage - 1) * pageBlock + 1;// 1,11,21,31... 리스트 블럭 시작
// 	int endRow = startRow + pageBlock - 1;// 10,20,30,40... 리스트 블럭 끝	
// 	int totalPages; 		// 전체페이지
	
// 	boolean firstGo;	//첫페이지레이어 노출여부(true, false)
// 	boolean lastGo;		//마지막페이지레이어 노출여부(true, false)
// 	boolean nextGo;		//다음페이지레이어 노출여부(true, false)
// 	boolean prevGo;		//이전페이지레이어 노출여부(true, false)
	
// 	int firstPage;		//첫페이지 번호 : 무조건1
// 	int nextPage;		//다음페이지 번호
// 	int prevPage;		//이전페이지 번호
// 	int lastPage;		//마지막페이지 번호 : totalpage와 동일
	
	
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SELECT PAGE</title>
<style>
/* 기본 설정*/
 u{text-decoration:none; color:#000000;}	
 u:hover{color:#ff0000; cursor: pointer;} 
 strong {color:#ff0000; cursor: pointer; font:bold 15px Dotum;} 
 /* nav tag */ 
 .pg li{display:inline; /* 세로나열을 가로나열로 변경 */ 
			border-left:1px solid #999; /* 각 메뉴의 왼쪽에 "|" 표시(분류 표시) */ 
			 font:bold 12px Dotum; /* 폰트 설정 - 12px의 돋움체 굵은 글씨로 표시 */ 
			 padding:0 10px; /* 각 메뉴 간격 */ 
 			} 
.pg li:first-child{border-left:none;} /* 메뉴 분류중 제일 왼쪽의 "|"는 삭제
</style>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	search(1);
	$('#searchbtn').on('click', function(){
		search(1);
	})

});	
	
	function search(cPage) {
	
		console.log(cPage)
		var cPage ;

		console.log(cPage)
		var param = {
	
			'searchKey'  : $("#searchKey").val(),
			'searchWord' : $("#searchWord").val(),
			'cpage'   : cPage
		}
	
		$.ajax({
			url : "/board/boardList.do" ,
			dataType : "json",
			data:  param,
			type : "POST",
			success : function(data) {
	              console.log(data)
	              console.log(data.list)
	              console.log(data.totlaRows)
	              console.log(data.cPage)
	              
	             var totalRows = data.totlaRows
	             var cPage = data.cPage
	             
	             listfn(data.list);
	             console.log("totalRows")
	             console.log(totalRows)
	             pagerfn(totalRows,cPage);
			
				
			   
							
			
	// 			alert("셀렉완료");
				
				},
				error : function(errorThrown) {
					alert("실패");
				}
			});
}

function listfn(data) {
	$('#list').empty();
	$.each(data, function(index, item) {
		$('#list').append(
				"<tr><td>"
				+ item.seq 
				+ "</td><td>" 
				+ "<a href ="+ "/jsp/board/boardRead.jsp?seq=" + item.seq + ">"+ item.title + "</a>" 
				+ "</td><td>"
				+ item.reg_id
				+ "</td><td>" 
				+ item.mod_id
				+ "</td><td>" 
				+ item.reg_date
				
				+ "</td></tr>")

	})
}


function pagerfn(data,cppage) {
	$('#pager').empty();
	//var totalRows = data
	
	var totalRows = data; //총 row 수
	var cPage = cppage;
	var pageBlock = 10;     // 페이지 블럭 개수
	//var pageGroup ;         //pageGroup
 	var totalPages ; 		// 전체페이지
	var cPageGroup = parseInt((cPage - 1) / pageBlock) + 1; // 현재 페이지 그룹
	var pageGroup  = Math.floor(( totalPages /pageBlock)) + 1; // 전체 페이지 그룹 (1~10 : 1그룹, 11 ~20:2그룹)
    
   //나머지 여부확인 : 나머지가 있으면 +1
	var mod = totalRows % pageBlock;
	console.log("mod:"+mod)
	if (mod > 0) { 
		 totalPages = Math.ceil(totalRows / pageBlock) ;
		 pageGroup  = Math.ceil(( totalPages /pageBlock)); // 전체 페이지 그룹 (1~10 : 1그룹, 11 ~20:2그룹) 나머지에 따라 페이지그룹도 달라져야함. 
	} else {
		 totalPages = (totalRows / pageBlock) ;
	     pageGroup  = ( totalPages /pageBlock) + 1; // 전체 페이지 그룹 (1~10 : 1그룹, 11 ~20:2그룹)
	}
	
	 console.log("cpage:"+ cPage)  
	 console.log("totalPage:"+ totalPages )
	 
	// 자바에서는 소숫점으로 안떨어 졌으나 스크립트에서는 소수점으로 떨어져서 보정이 필요했음. 그것이 오류의 원인
	// parseInt () 함수는 문자열을 구문 분석하고 정수를 반환합니다.
	// Math.ceil() : 소수점 올림, 정수형 반환 
	// Math.floor() : 소수점 버림, 정수형 반환 
	// Math.round() : 소수점 반올림, 정수형 반환
	
	var firstGo = (cPage <= pageBlock) ? false : true; // 첫페이지 이동 설정(현재페이지가 1그룹안에 있으면 false)
	var prevGo 	= (cPageGroup == 1) ? false : true; 	// 이전페이지 이동(현재페이지가 1그룹에 있으면 false)
	var nextGo	= (cPageGroup == pageGroup) ? false : true ; // 다음페이지 이동(현재페이지가 마지막 그룹에 속해있으면 false)
	var lastGo 	= (cPage >= pageBlock && cPageGroup == pageGroup) ? false : true; // 라스트페이지 이동  설정
	 
 	 console.log("pageGroup:"+pageGroup) 
 	 console.log("cPageGroup:"+cPageGroup)
 	 
	var firstPage = 1; // 첫페이지값
	var prevPage = (cPageGroup - 1) * pageBlock - (pageBlock-1); // 이전페이지값
	var nextPage = (cPageGroup + 1) * pageBlock - (pageBlock-1); // 다음페이지값
	var lastPage = totalPages; // 마지막페이지값
 	
	 
	var startRow = (cPage <= pageBlock) ? 1 : Math.floor( cPage / pageBlock) * pageBlock + 1; // 페이지 리스트 첫번째 배열값 현재페이지보다 페이지 블럭이 적으면 1 : 블럭을 현재페이지로 나누어서 
  	var endRow =  startRow + pageBlock - 1;// 10,20,30,40... 리스트 블럭 끝	
  	if(endRow > totalPages){endRow = totalPages;} 
//   	var endRow = (cPageGroup < pageGroup) ? startRow + pageBlock - 1 : totalPages; // 페이지 리스트 마지막 배열값
	console.log("startRow:"+startRow)   
 	console.log("endRow  :"+endRow)
		if(firstGo == true){ 
			$('#pager').append("<li onclick='search(" + firstPage + ")'>"+"<u>"+ "처음" +"</u>"  + "</li>")
		}
		if(prevGo == true){ 
			$('#pager').append("<li onclick='search(" + prevPage + ")'>"+"<u>"+ "이전" +"</u>"  + "</li>")
		}
		for(var i = startRow ; i<= endRow ; i++){
			if (i==cPage) {
			$('#pager').append("<li onclick='search(" + i + ")'>"+"<u>"+ "<strong>"+ i + "</strong>"+"</u>"  + "</li>")
			}else{ 
			$('#pager').append("<li onclick='search(" + i + ")'>"+"<u>"+ i +"</u>"  + "</li>")
			}
		} 
		if(nextGo == true ){
			$('#pager').append("<li onclick='search(" + nextPage + ")'>"+"<u>"+ "다음" +"</u>"  + "</li>")
		}
		if(lastGo == true ){
			$('#pager').append("<li onclick='search(" + lastPage + ")'>"+"<u>"+ "마지막" +"</u>"  + "</li>")
		}
	
}

function fn_logout() {
	
		
	location.href='/jsp/login/signIn.jsp'
}


</script>
</head>
<body>
	<header align="center">
		<h1>SELECT PAGE</h1>
	</header>

	<section>
	<div align="center"><button type="button" id="logout" onclick="fn_logout();" >로그아웃</button></div>
			<table  border="1" align="center">
				<tr>
					<td colspan="5">
						<select name="searchKey" id="searchKey">
							<option value="title">제목</option>
							<option value="reg_id">아이디</option>
						</select> 
						<input type="text" name="searchWord" id="searchWord" value="" />
						<button id="searchbtn" type="submit" value="검색" >검색</button>
						<button type="button"  onclick="location.href='/jsp/board/boardWrite.jsp'">글쓰기</button>
					</td>
				</tr>
			</table>
		<table style="table-layout:fixed" border="1" align="center">
				<tr>
					<th>글번호</th>
					<th width="200">제목</th>
					<th>작성자ID</th>
					<th>작성자이름</th>
					<th>작성일</th>
				</tr>
			<tbody id="list">
			
			</tbody>
			
		</table>

	</section>
	<div id ="pager" class="pg" align="center"></div>

</body>
</html>