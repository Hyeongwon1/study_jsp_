<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> Signln</title>
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
		<form action="/sign/signIn.do" method="post">
		<input type="hidden" name="process" id="process" value="signin" />
		<table border="1">
			<tr>
				<td><label for="USER_ID">아이디:</label></td> 
				<td><input type="text"class="" name="user_id" id="user_id" size="10"placeholder="Enter ID"></td>
			</tr>
			<tr>
				<td><label for="USER_PW">비밀번호:</label></td>
				<td><input type="password" class="" name="user_pw" id="user_pw" size="10" placeholder="Enter password"></td>
			</tr>

		</table>
				<div id="button">
					 <input type="submit" id="" value="로그인">
					 <input type="button" onclick="location.href='signUp.jsp'" id="signUp" value="회원가입" />
				</div>
		</form>
	</div>





</body>
</html>