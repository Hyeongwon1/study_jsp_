package co.kr.ucs.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import co.kr.ucs.bean.UserBean;
import co.kr.ucs.dao.DBConnectionPool;
import co.kr.ucs.dao.DBConnectionPoolManager;
import co.kr.ucs.dao.DBManager;

public class SignService {
	
	DBConnectionPoolManager dbPoolManager = DBConnectionPoolManager.getInstance();
	DBConnectionPool dbPool;
	
	public SignService() {
		dbPoolManager.setDBPool(DBManager.getOracleurl(), DBManager.getSid(), DBManager.getSpass());
		dbPool = dbPoolManager.getDBPool();
	}
	
	public int SignUp(UserBean ubean) throws ClassNotFoundException {

		System.out.println("ubean.getUser_id())");
		System.out.println(ubean.getUser_id());
		Connection connection = null;
		PreparedStatement pstmt = null;
		//ResultSet rs = null; //select 가져온 결과값을 하나씩 캐치해 낼때 사용 
		String InsertSql = "INSERT INTO CM_USER(USER_ID,USER_PW,USER_NM,EMAIL)values(?,?,?,?)";
		int flag = 0;
		try {
			connection = dbPool.getConnection();
			pstmt = connection.prepareStatement(InsertSql);
			
				pstmt.setString(1, ubean.getUser_id());
				pstmt.setString(2, ubean.getUser_pw());
				pstmt.setString(3, ubean.getUser_nm());
				pstmt.setString(4, ubean.getEmail());
				flag = pstmt.executeUpdate();
				System.out.println("insert success");
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
	
public Map<String, String> SignIn(UserBean ubean) throws ClassNotFoundException {
		
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; //select 가져온 결과값을 하나씩 캐치해 낼때 사용 
		String CheckSql = "SELECT * FROM CM_USER WHERE USER_ID=? AND USER_PW=?";
		
		Map<String, String> SImap = new HashMap<>();
		try {
			connection = dbPool.getConnection();
			pstmt = connection.prepareStatement(CheckSql);
				pstmt.setString(1, ubean.getUser_id());
				pstmt.setString(2, ubean.getUser_pw());
				rs = pstmt.executeQuery();
			while (rs.next()) {
				SImap.put("USER_ID",rs.getString("user_id"));
				SImap.put("USER_PW",rs.getString("user_pw"));
			}
		} catch (SQLException e) {
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
				// Connection 를 풀로 복귀시킨다.
				dbPool.freeConnection(connection);
			}
		}
		return SImap;
	}

}
