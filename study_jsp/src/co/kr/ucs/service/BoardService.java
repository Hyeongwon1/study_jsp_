package co.kr.ucs.service;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;


import co.kr.ucs.bean.BoardBean;
import co.kr.ucs.bean.SearchBean;
import co.kr.ucs.dao.DBConnectionPool;
import co.kr.ucs.dao.DBConnectionPoolManager;
import co.kr.ucs.dao.DBManager;

public class BoardService {
	
	DBConnectionPoolManager dbPoolManager = DBConnectionPoolManager.getInstance();// 디비풀 매니저에 디비풀 생성 되어있나 확인
	DBConnectionPool dbPool; 
	
	public BoardService() {
		
		dbPoolManager.setDBPool("BOARDPOOL",DBManager.getOracleurl(), DBManager.getSid(), DBManager.getSpass());//디비풀 생성.
		dbPool = dbPoolManager.getDBPool("BOARDPOOL");//생성된 디비풀을 가져와서 전역으로 사용 하기 위함.
	}
	

	public int BoardWrite(BoardBean Bbean) throws ClassNotFoundException, SQLException {
		
		//DBManager DB = new DBManager(); dbpool을 사용하므로 다이렉트로 연결 안함. 풀에서 꺼내서 쓰는 방식이므로.
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		//ResultSet rs = null; //select 가져온 결과값을 하나씩 캐치해 낼때 사용 
		
		String InsertSql = "INSERT INTO BOARD(SEQ,TITLE,CONTENTS,REG_ID,REG_DATE,MOD_ID,MOD_DATE)values((SELECT(max(seq)+1)from board),?,?,?,sysdate,?,sysdate)";
		int flag = 0;
		try {
			connection = dbPool.getConnection();//생성된 디비풀에서 연결 커넥션을 가져옴
			//connection = DB.getConnection(); dbpool을 사용하므로 다이렉트로 연결 안함. 풀에서 꺼내서 쓰는 방식이므로.
			
			pstmt = connection.prepareStatement(InsertSql);
			if(!"null".equals(Bbean.getReg_id()) ){
				pstmt.setString(1, Bbean.getTitle());
				pstmt.setString(2, Bbean.getContents());
				pstmt.setString(3, Bbean.getReg_id());
				pstmt.setString(4, Bbean.getReg_id());

				flag = pstmt.executeUpdate();
				System.out.println("insert success");
				
			}
		
		} catch (SQLException e) {
			flag -= 1;
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (connection != null) {
					// Connection 를 풀로 복귀시킨다.
					dbPool.freeConnection(connection);
				}
			}
		}

		return flag;
	}
	
	public List<BoardBean> BoardRead(BoardBean Bbean) throws ClassNotFoundException, SQLException {
		
		List<BoardBean> list = new ArrayList<>();
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; //select 가져온 결과값을 하나씩 캐치해 낼때 사용 
		String ReadSql = "select * from board where seq=?";
		
		try {
			connection = dbPool.getConnection();
			pstmt = connection.prepareStatement(ReadSql);
			pstmt.setInt(1, Bbean.getSeq());
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				BoardBean brbean = new BoardBean();
				
				brbean.setSeq(Integer.parseInt(rs.getString("SEQ")));
				brbean.setReg_id(rs.getString("REG_ID"));
				brbean.setReg_date( rs.getString("REG_DAtE"));
				brbean.setTitle(rs.getString("TITLE"));
				brbean.setContents(rs.getString("CONTENTS"));
				
				list.add(brbean);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				dbPool.freeConnection(connection);
			}
		}

		return list;

	}
	
	public List<BoardBean> BoardList(int cPage, SearchBean searchBean) throws ClassNotFoundException, SQLException {
			
			List<BoardBean> list = new ArrayList<>();
			
			int pageBlock = 10; // 페이지 블럭 개수
			int startRow = (cPage - 1) * pageBlock + 1;// 1,11,21,31... 리스트 블럭 시작
			int endRow = startRow + pageBlock - 1;// 10,20,30,40... 리스트 블럭 끝
			
			
			Connection connection = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null; //select 가져온 결과값을 하나씩 캐치해 낼때 사용 
			
			//SQL문
//			String SelectSql = "SELECT * FROM (SELECT ROWNUM RNUM,a.SEQ,a.TITLE,a.CONTENTS,a.reg_id,a.REG_DATE,a.MOD_ID,a.MOD_DATE FROM BOARD a INNER JOIN CM_USER b ON a.REG_ID = b.USER_ID";
//			String SelectSql ="select * from (select rownum rnum, SEQ,TITLE,CONTENTS, REG_ID ,REG_DATE,(SELECT USER_NM FROM CM_USER WHERE USER_ID = MOD_ID) as mod_id, "
//					+ "MOD_DATE From ((select * from board order by seq desc))";
			String SelectSql = "Select * from ( Select rownum rnum ,SEQ,TITLE,CONTENTS, REG_ID ,REG_DATE,MOD_DATE, B.USER_NM as MOD_ID from(Select * from board c ";
			// 검색 파라미터 초기화
			if (searchBean.getSearchWord() == "") {
				searchBean.setSearchKey("");
				searchBean.setSearchWord("");
			}else if (searchBean.getSearchWord() == null) {
				searchBean.setSearchKey("");
				searchBean.setSearchWord("");
			}
			try {
				connection = dbPool.getConnection();
				//다음 셀렉트문을 연다/
				System.out.println(startRow);
				System.out.println(endRow);
				if(!searchBean.getSearchKey().equals("")){
					if(searchBean.getSearchKey().equals("title")){
						SelectSql += " WHERE title like ? order by seq desc ) A LEFT\r\n" + 
								"     JOIN CM_USER B\r\n" + 
								"     ON B.USER_ID = A.MOD_ID )" ; 
					}else if(searchBean.getSearchKey().equals("reg_id")){
						SelectSql  += " WHERE reg_id like ? order by seq desc ) A LEFT\r\n" + 
								"     JOIN CM_USER B\r\n" + 
								"     ON B.USER_ID = A.MOD_ID )" ; 
					}
				}else{
					SelectSql +=" order by seq desc ) A\r\n" + 
							"     LEFT\r\n" + 
							"     JOIN CM_USER B\r\n" + 
							"     ON B.USER_ID = A.MOD_ID) ";
				}
				SelectSql +=" WHERE rnum BETWEEN ? and ? ";
				
				System.out.println(SelectSql);
				pstmt = connection.prepareStatement(SelectSql);
				if(!searchBean.getSearchKey().equals("")){
					pstmt.setString(1, "%" + searchBean.getSearchWord() + "%");
					pstmt.setInt(2, startRow);
					pstmt.setInt(3, endRow);
				}else{
					pstmt.setInt(1, startRow);
					pstmt.setInt(2, endRow);
				}
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					BoardBean bbean = new BoardBean();
					
					bbean.setSeq(rs.getInt("SEQ"));
					bbean.setReg_id(rs.getString("REG_ID"));
					bbean.setReg_date( rs.getString("REG_DAtE"));
					bbean.setMod_id( rs.getString("MOD_ID"));
					bbean.setMod_date( rs.getString("MOD_DAtE"));
					bbean.setTitle(rs.getString("TITLE"));
					bbean.setContents(rs.getString("CONTENTS"));
					
					list.add(bbean);					
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (connection != null) {
					dbPool.freeConnection(connection);
				}
			}

			return list;
	}
	
	public int getTotalRows(int cPage,SearchBean searchBean) throws ClassNotFoundException, SQLException {
		
		int totalRows = 0;
		int pageBlock = 10; // 페이지 블럭 개수
		int startRow = (cPage - 1) * pageBlock + 1;// 1,11,21,31... 리스트 블럭 시작
		int endRow = startRow + pageBlock - 1;// 10,20,30,40... 리스트 블럭 끝
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; //select 가져온 결과값을 하나씩 캐치해 낼때 사용 
		
		//SQL문
		//String CountSql = "SELECT COUNT(SEQ) AS TOTALROWS FROM BOARD a INNER JOIN CM_USER b ON a.MOD_ID = b.USER_ID";
		String CountSql = "select COUNT(*) TOTALROWS from ( Select rownum rnum ,SEQ,TITLE,CONTENTS, REG_ID ,REG_DATE,MOD_DATE, B.USER_NM as MOD_ID from(Select * from board c ";
		
		// 검색 파라미터 초기화
		if (searchBean.getSearchWord() == "") {
			searchBean.setSearchKey("");
			searchBean.setSearchWord("");
		}else if (searchBean.getSearchWord() == null) {
			searchBean.setSearchKey("");
			searchBean.setSearchWord("");
		}
		
		try {
			
			connection = dbPool.getConnection();
			if(!searchBean.getSearchKey().equals("")){
				if(searchBean.getSearchKey().equals("title")){
					CountSql += " WHERE title like ? order by seq desc ) A LEFT\r\n" + 
							"     JOIN CM_USER B\r\n" + 
							"     ON B.USER_ID = A.MOD_ID )" ; 
				}else if(searchBean.getSearchKey().equals("reg_id")){
					CountSql  += " WHERE reg_id like ? order by seq desc ) A LEFT\r\n" + 
							"     JOIN CM_USER B\r\n" + 
							"     ON B.USER_ID = A.MOD_ID )" ; 
				}
			}else{
				CountSql +=" order by seq desc ) A\r\n" + 
						"     LEFT\r\n" + 
						"     JOIN CM_USER B\r\n" + 
						"     ON B.USER_ID = A.MOD_ID) ";
			}
			//CountSql +=" WHERE rnum BETWEEN ? and ? ";
			System.out.println(CountSql);
			pstmt = connection.prepareStatement(CountSql);
			System.out.println(searchBean.getSearchWord());
			
			if(!searchBean.getSearchKey().equals("")){
				pstmt.setString(1, "%" + searchBean.getSearchWord() + "%");
			}
			
			System.out.println(CountSql);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				totalRows = rs.getInt("TOTALROWS"); //전체 row
			}

		} catch (SQLException e) {
			System.out.println("select failed...");
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				dbPool.freeConnection(connection);
			}
		}

		return totalRows;
	}
}
