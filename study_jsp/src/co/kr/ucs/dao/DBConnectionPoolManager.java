package co.kr.ucs.dao;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * DBConnectionPool을 관리하는 객체
 * - 여러 DB 계정을 Pool Name 으로 관리 할 수 있다.
 * @author sdh
 *
 */
public class DBConnectionPoolManager {
	private static String DEFAULT_POOLNAME = "DEFAULT";
	private static Map<String, DBConnectionPool> dbPool = new HashMap<>();
	private static DBConnectionPoolManager instance = new DBConnectionPoolManager();
	/**
	 * DBConnectionPoolManager 객체 호출
	 * @return DBConnectionPoolManager
	 */
	// DBConnectionPoolManager 에 싱글턴 패턴을 적용하기 위해(인스턴스를 하나만 유지) static 으로 선언
	public static DBConnectionPoolManager getInstance() {
		System.out.println("getInstance():" + instance.hashCode());
		System.out.println("getInstance():" +"생성되어있으면 리턴 instance");

		return instance;
	}
	/**
	 * DB Pool 생성
	 * @param url
	 * @param id
	 * @param pw
	 */
	public void setDBPool(String url, String id, String pw) {
		setDBPool(DEFAULT_POOLNAME, url, id, pw, 1, 10);
		System.out.println("디비풀 셋팅시 따로 이름을 부여해 주지 않았다면 위에서 디폴트로 생성된 것을 사용");
		
	
	
	}
	/**
	 * DB Pool 생성
	 * @param poolName
	 * @param url
	 * @param id
	 * @param pw
	 */
	public void setDBPool(String poolName, String url, String id, String pw) {
		setDBPool(poolName, url, id, pw, 1, 10);
		System.out.println("풀네임 따로주면 여기로 만들어 지나???????????");
	}
	/**
	 * DB Pool 생성
	 * @param poolName
	 * @param url
	 * @param id
	 * @param pw
	 * @param initConns
	 */
	public void setDBPool(String poolName, String url, String id, String pw, int initConns) {
		setDBPool(poolName, url, id, pw, initConns, 10);
	
		System.out.println("풀네임 따로준게 이미 사용중이면 연결 순서를 매겨서 이곳으로 들어온다?????");
		System.out.println("풀네임 따로준게 이미 있으면  연결 순서를 매겨서 최대 풀 개수인 10개 까지");
	}
	/**
	 * DB Pool 생성
	 * @param poolName
	 * @param url
	 * @param id
	 * @param pw
	 * @param initConns
	 * @param maxConns
	 */
	public void setDBPool(String poolName, String url, String id, String pw, int initConns, int maxConns) {
		if(!dbPool.containsKey(poolName)) {//디비풀에 풀네임이 없을때
			DBConnectionPool connPool = new DBConnectionPool(url, id, pw, initConns, maxConns);
		    System.out.println("dbPool맵에 풀 이름과 풀에관한 정보를 담는다");
			dbPool.put(poolName, connPool);
		}
	}
	
	/**
	 * DB Pool 사용 호출
	 * @return DEFAULT_POLLNAME 의 DBConnectionPool
	 */
	public DBConnectionPool getDBPool() {
		System.out.println("이름을 따로 안준 디비풀의 호출");
		return getDBPool(DEFAULT_POOLNAME);
	}

	/**
	 * DB Pool 사용 호출
	 * @param poolName
	 * @return
	 */
	public DBConnectionPool getDBPool(String poolName) {
		System.out.println("이름을 설정해준 디비풀의 호출");
		return dbPool.get(poolName);
	}
	
}