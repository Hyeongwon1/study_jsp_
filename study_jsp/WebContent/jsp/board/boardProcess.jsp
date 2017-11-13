<%@page import="co.kr.ucs.service.BoardService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@ page import="java.sql.*"%>

<%
// 	request.setCharacterEncoding("UTF-8");

// 	String process	 = request.getParameter("process");
// 	String TITLE  = request.getParameter("title");
// 	String CONTENTS  = request.getParameter("contents");
// 	String REG_ID   = request.getParameter("reg_id");
// 	System.out.println("레그아이디");
// 	System.out.println(REG_ID);

// 	if("write".equals(process)){
// 		if(!"null".equals(REG_ID) ){//세션값 잃어버렸을때를 대비
// 			BoardService bs = new BoardService();
// 			//int in = bs.BoardWrite(TITLE, CONTENTS, REG_ID);

// 			if (in == 1) {
// 			System.out.println("in" + "입력했네........." + in);
// 			response.sendRedirect("boardList.jsp?cpage=1");
// 			} else {
// 			System.out.println("입력안됬네...........");
// 			response.sendRedirect("boardWrite.jsp");
// 			}
// 		}else if("null".equals(REG_ID)){
// 		System.out.println("insert fail");
// 		response.sendRedirect("../login/signIn.jsp");
// 		}
	
// 	}
%>

