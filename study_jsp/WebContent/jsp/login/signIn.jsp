<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> Signln</title>

<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript">

	$(document).ready(function(){
		$("#submit").on("click",function(){
			
			if(!$("#USER_ID")){ // 아이디 검사
	         alert('ID를 입력해 주세요.');
	         userId.focus();
	         return false;
	         
	     	}else if(!$("#USER_PW")){ // 비밀번호 검사
	         alert('PW를 입력해 주세요.');
	         userPw.focus();
	         return false;
	     	}
			
			var frm = $('.loginform :input');
			var param = frm.serialize();
			$.ajax({
			    url : "/sign/signIn.do",
			    dataType: "json",
			    type: "POST",
			    data : param,
			    success: function(data)
			    {
			    	console.log(data.success)
			    	if (data.success == 1 ) {
						location.href = "/jsp/board/boardList.jsp";
					}else if(data.success == 0 ) {
				    	alert("아이디나 비밀번호가 다릅니다.");
					}
			    },
			    error: function (errorThrown)
			    {
			    console.log("에러");
			    }
			});
		});
	});

</script>

</head>

 	<header align="center">
		<h1>로그인 입니당!</h1>
	</header>
<body id="mybody">
	<br>
	<br>
	<br>
	<br>
	<br>
	<div id="head"></div>
	<div class="container" id="mycon" style="height: 100%;" align="center">
		<form class="loginform">
			<table border="1">
				<tr>
					<td><label for="USER_ID">아이디:</label></td> 
					<td><input type="text"class="" name="USER_ID" id="USER_ID" size="10"placeholder="Enter ID"></td>
				</tr>
				<tr>
					<td><label for="USER_PW">비밀번호:</label></td>
					<td><input type="password" class="" name="USER_PW" id="USER_PW" size="10" placeholder="Enter password"></td>
				</tr>
			</table>
		</form>
				<div >
					 <input type="submit" id="submit" value="로그인">
					 <input type="button" onclick="location.href='/jsp/login/signUp.jsp'" id="signUp" value="회원가입" />
				</div>
	</div>





</body>
</html>