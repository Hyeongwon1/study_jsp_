package co.kr.ucs.controller;


import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;
import co.kr.ucs.bean.UserBean;
import co.kr.ucs.service.SignService;


@SuppressWarnings("unchecked")
public class SignController extends HttpServlet {
	private static final long serialVersionUID = 1L;	
	
	SignService SignService;
       
	
    public SignController() {
        super();
    }
    @Override
   	public void init() throws ServletException {
   		System.out.println("init....");
   		super.init();
   	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response){
			response.setContentType("text/html;charset=UTF-8");
			doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response){
			response.setContentType("text/html;charset=UTF-8");
			doProcess(request, response);
	}
	
	private void doProcess(HttpServletRequest request, HttpServletResponse response){
		PrintWriter writer	= null;
		JSONObject jo		= new JSONObject();
		SignService 		= new SignService();
		
		try {
		String url = request.getRequestURI();
		writer = response.getWriter();
		HttpSession session = request.getSession(true);
		System.out.println("[SignController]접속 URI : " + url);
		
		String USER_ID   = request.getParameter("USER_ID");
		String USER_PW   = request.getParameter("USER_PW");
		String USER_PW2  = request.getParameter("USER_PW2");
		String USER_NM   = request.getParameter("USER_NM");
		String EMAIL  	 = request.getParameter("EMAIL");
		
			if (url.equalsIgnoreCase("/sign/signUp.do")) {
				UserBean ubean = new UserBean();
				ubean.setUser_id(USER_ID);
				ubean.setUser_nm(USER_NM);
				ubean.setUser_pw(USER_PW);
				ubean.setUser_pw1(USER_PW2);
				ubean.setEmail(EMAIL);
				
				int in = SignService.SignUp(ubean);
				if (in == 1) {
					System.out.println("in" + "입력했네........." + in);
					jo.put("success", in);
				} else {
					System.out.println("입력안됬네...........");
					jo.put("success", in);
				}
			}else if (url.equalsIgnoreCase("/sign/signIn.do")){
				UserBean ubean = new UserBean();
				ubean.setUser_id(USER_ID);
				ubean.setUser_nm(USER_NM);
				ubean.setUser_pw(USER_PW);
				Map<String, String> SImap ;
				SImap = SignService.SignIn(ubean);
					
				if(USER_ID.equals(SImap.get("USER_ID")) && USER_PW.equals(SImap.get("USER_PW"))){
					session.setAttribute("loginId",SImap.get("USER_ID"));
					jo.put("success", "1");
				}else {
					jo.put("success", "0");
				}
			}
		
		}catch (Exception e) {
			jo.put("error", e.getMessage());
		}finally{
			writer.print(jo.toJSONString());
		}
	}
}