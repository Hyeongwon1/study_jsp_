package co.kr.ucs.controller;


import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import co.kr.ucs.bean.BoardBean;
import co.kr.ucs.bean.SearchBean;
import co.kr.ucs.service.BoardService;



@SuppressWarnings("unchecked")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	BoardService BoardService;

    public BoardController() {
        super();
    }
    
    @Override
	public void init() throws ServletException {
		System.out.println("init....");
		super.init();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		System.out.println("get..........");
		doProcess(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		System.out.println("POST");
		doProcess(request, response);
	}
	private void doProcess(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter writer = null;
		JSONObject jo = new JSONObject();
		BoardService 		= new BoardService();
		
		try {
			String uri = request.getRequestURI();
			writer = response.getWriter();
			System.out.println("[BoardController]접속 URI : " + uri);
			
			String TITLE  = request.getParameter("title");
			String CONTENTS  = request.getParameter("contents");
			String REG_ID   = request.getParameter("reg_id");
			
			if (uri.equalsIgnoreCase("/board/BoardWrite.do")) {
						BoardBean bbean = new BoardBean();
						bbean.setTitle(TITLE);
						bbean.setContents(CONTENTS);
						bbean.setReg_id(REG_ID);
						bbean.setMod_id(REG_ID);
						
						System.out.println("레그아이디");
						System.out.println("레그아이디:"+REG_ID);
						
					if(!"null".equals(REG_ID) ){//세션값 잃어버렸을때를 대비
							int in = BoardService.BoardWrite(bbean);
							if (in == 1) {
								jo.put("success", in);
							} else {
								jo.put("success", in);
							}
					}else if("null".equals(REG_ID)){
						jo.put("success", 7);
					}
			}else if (uri.equalsIgnoreCase("/board/boardRead.do")) {
				String seq = request.getParameter("seq");
				List<BoardBean> list = null;
				BoardBean brbean = new BoardBean();
				brbean.setSeq(Integer.parseInt(seq));
				JSONArray jArr = new JSONArray();
			
					list = BoardService.BoardRead(brbean);
					
					for(int i=0;i<list.size();i++){
							JSONObject jsonObj = new JSONObject();
							jsonObj.put("seq", list.get(i).getSeq());
							jsonObj.put("reg_id", list.get(i).getReg_id());
							jsonObj.put("title", list.get(i).getTitle());
							jsonObj.put("reg_date", list.get(i).getReg_date());
							jsonObj.put("mod_id", list.get(i).getMod_id());
							jsonObj.put("mod_date", list.get(i).getMod_date());
							jsonObj.put("contents", list.get(i).getContents());
							jArr.add(jsonObj);
					}
					jo.put("brb", jArr );
			}else if (uri.equalsIgnoreCase("/board/boardList.do")) {
				JSONArray jArr = new JSONArray();
				List<BoardBean> list = null;
				int totalRows = 0;
				SearchBean Searchbean = new SearchBean();
				String searchKey = request.getParameter("searchKey");
				String searchWord = request.getParameter("searchWord");
				System.out.println("searchKey:"+searchKey);
				System.out.println("searchWord:"+searchWord);
				String cpage = "1";
				if(request.getParameter("cpage") != null){
					cpage = request.getParameter("cpage");
				}
				Searchbean.setSearchKey(searchKey);
				Searchbean.setSearchWord(searchWord);
				Searchbean.setCpage(Integer.parseInt(cpage));
				
				list = BoardService.BoardList(Integer.parseInt(cpage),Searchbean);
					for(int i=0;i<list.size();i++){
							JSONObject jsonObj = new JSONObject();
							jsonObj.put("seq", list.get(i).getSeq());
							jsonObj.put("reg_id", list.get(i).getReg_id());
							jsonObj.put("title", list.get(i).getTitle());
							jsonObj.put("reg_date", list.get(i).getReg_date());
							jsonObj.put("mod_id", list.get(i).getMod_id());
							jsonObj.put("mod_date", list.get(i).getMod_date());
							jsonObj.put("contents", list.get(i).getContents());
							jArr.add(jsonObj);
					}
				totalRows = BoardService.getTotalRows(Integer.parseInt(cpage),Searchbean);
				jo.put("totlaRows", totalRows);
				jo.put("list",jArr);
				jo.put("cPage", cpage);
				System.out.println(totalRows);
		}
		}catch (Exception e) {
			jo.put("error", e.getMessage());
		}finally {
			writer.print(jo.toJSONString());
		}
	}
		
}



