package co.kr.ucs.dao;

public class DBManager {
	
	public final static String OracleURL = "jdbc:oracle:thin:@220.76.203.39:1521:UCS";
	//oracle 연결스트링      ip주소:포트명 / DB명
	public final static String sID = "UCS_STUDY";
	public final static String sPass = "qazxsw";

	
	public static String getOracleurl() {
		return OracleURL;
	}

	public static String getSid() {
		return sID;
	}

	public static String getSpass() {
		return sPass;
	}

	
//아래 사항을 디비 커넥션 풀로 이동 DBManager는 그져 커넥션 가져오는 아이디 비번 패스워드 값만 가지고 있으면 된다.	
	
//	public Connection getConnection()throws SQLException, ClassNotFoundException{
//		Connection connection = null;
//		
//		Class.forName("oracle.jdbc.driver.OracleDriver");
//		connection = DriverManager.getConnection(OracleURL, sID, sPass);//DB url에 접속
//		
//		System.out.println("diver load success");
//		
//		return connection;
//	}

	
//닫는개념이 아니고 반환의 개념이라 따로 닫는것은 없음.	
//	public void close(ResultSet rs, PreparedStatement pstmt, Connection connection) {
//		
//		if (rs != null ) try{rs.close();}catch(Exception ex){}
//		if (pstmt != null) try{pstmt.close();}catch(Exception ex){}
//		if (connection != null) try{connection.close(); }catch(Exception ex){}
//	}

}