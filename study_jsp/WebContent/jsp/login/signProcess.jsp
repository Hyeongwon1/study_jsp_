<%@page import="co.kr.ucs.service.SignService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.sql.*"%>
<%@ page import="java.util.Map"%>


<%
// 	response.setCharacterEncoding("UTF-8");
// 	String PROCESS	 = request.getParameter("process");
// 	String USER_ID   = request.getParameter("user_id");
// 	String USER_PW   = request.getParameter("user_pw");
// 	String USER_PW1  = request.getParameter("user_pw1");
// 	String USER_NM   = request.getParameter("user_nm");
// 	String EMAIL  	 = request.getParameter("email");

// 	if("signup".equals(PROCESS)){
// 		SignService ss= new SignService();
		
// 		int in = ss.SignUp(USER_ID, USER_PW, USER_NM,EMAIL);

// 		if (in == 1) {
// 		System.out.println("in" + "입력했네........." + in);
// 		response.sendRedirect("signIn.jsp");
// 		} else {
// 		System.out.println("입력안됬네...........");
// 		response.sendRedirect("signUp.jsp");
// 		}
	
		
// 	}else if("signin".equals(PROCESS)){
		
// 		SignService ss= new SignService();
		
// 		Map<String, String> SImap = ss.SignIn(USER_ID,USER_PW);
			
// 		if(USER_ID.equals(SImap.get("USER_ID")) && USER_PW.equals(SImap.get("USER_PW"))){
		
// 			session.setAttribute("loginId",SImap.get("USER_ID"));
// 			System.out.println("logIn success");
// 			response.sendRedirect("../board/boardList.jsp?cpage=1&pageblock=10");
			
// 		  }
		
		

// 	}

		

%>