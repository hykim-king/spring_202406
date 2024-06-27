package com.pcwk.ehr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class UserDao {

	private ConnectionMaker connectionMaker;
	final Logger log = LogManager.getLogger(UserDao.class);
	//default 생성자 필수
	public UserDao() {}
	
	public UserDao(ConnectionMaker connectionMaker) {
		this.connectionMaker = connectionMaker;
	}
	
	
	public void setConnectionMaker(ConnectionMaker connectionMaker) {
		this.connectionMaker = connectionMaker;
	}

	
	public int getCount() throws SQLException, ClassNotFoundException {
		int count = 0;

		// 1.Connection연결
		// 2.PreparedStatement,Statement(X)
		// 3.SQL run
		// 4.결과
		// 5.자원반납

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder(100);

		conn = connectionMaker.makeConnection();
		sb.append(" SELECT                      \n");
		sb.append("     COUNT(*) cnt            \n");
		sb.append(" FROM                        \n");
		sb.append(" 	member                  \n");
		
		log.debug("1.sql   \n" + sb.toString());
		
		pstmt = conn.prepareStatement(sb.toString());
		

		
		rs = pstmt.executeQuery();
		
		if(rs.next() == true) {
			count=rs.getInt("cnt");
		}
		log.debug("3. count:" + count);
		
		
		// 자원 반납
		rs.close();
		pstmt.close();
		conn.close();

		return count;
	}
	
	public int doDelete() throws SQLException, ClassNotFoundException {
		int flag = 0;
		// 1.Connection연결
		// 2.PreparedStatement,Statement(X)
		// 3.SQL run
		// 4.결과
		// 5.자원반납

		Connection conn = null;
		PreparedStatement pstmt = null;
		StringBuilder sb = new StringBuilder(200);

		conn = connectionMaker.makeConnection();
		sb.append(" DELETE FROM member    \n");

		log.debug("1.sql   \n" + sb.toString());

		pstmt = conn.prepareStatement(sb.toString());

		flag = pstmt.executeUpdate();
		log.debug("3.flag " + flag);

		pstmt.close();
		conn.close();
		return flag;
	}
	
	//단건 등록
	public int doSave(User user) throws SQLException, ClassNotFoundException{
		int flag = 0;
		//1. DB연결을 위한 Connection
		//2. SQL을 담은 PreparedStatement,Statement를 생성
		//3. PreparedStatement를 실행한다.
		//4. 실행결과 받기 ResultSet 받아서 저장
		//5. Connection,PreparedStatement,ResultSet의 자원 반납.
		//6. JDBC API에 대한 예외 처리

		
		StringBuilder sb=new StringBuilder(200);
		sb.append(" INSERT INTO member VALUES (?, ?, ?, ?) \n");
		
		System.out.println("2.sql:\n"+sb.toString());
		System.out.println("3.user:"+user);
		
		Connection conn = connectionMaker.makeConnection();
		
		PreparedStatement pstmt = conn.prepareStatement(sb.toString());
		pstmt.setString(1, user.getUserId());
		pstmt.setString(2, user.getName());
		pstmt.setString(3, user.getPassword());
		pstmt.setString(4, user.getBirthday());
		
		flag = pstmt.executeUpdate();
		System.out.println("4.flag:"+flag);
		
		pstmt.close();
		conn.close();
		
		return flag;
	}
	//단건 조회
	public User doSelectOne(User user)throws SQLException, ClassNotFoundException{
		User outVO = null;
		//1. DB연결을 위한 Connection
		//2. SQL을 담은 PreparedStatement,Statement를 생성
		//3. PreparedStatement를 실행한다.
		//4. 실행결과 받기 ResultSet 받아서 저장
		//5. Connection,PreparedStatement,ResultSet의 자원 반납.
		//6. JDBC API에 대한 예외 처리
		
		Connection conn = this.connectionMaker.makeConnection();
		
		StringBuilder sb=new StringBuilder(200);
		sb.append(" SELECT user_id,                \n");
		sb.append("        name,                   \n");
		sb.append("        password,               \n");
		sb.append("        birthday                \n");
		sb.append("   FROM member t1               \n");
		sb.append("  WHERE t1.user_id = ?          \n");		
		System.out.println("2.sql:\n"+sb.toString());		
		System.out.println("3.param:\n"+user);
		
		PreparedStatement pstmt = conn.prepareStatement(sb.toString());
		pstmt.setString(1, user.getUserId());
		
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			outVO = new User();
			outVO.setUserId(rs.getString("user_id"));
			outVO.setName(rs.getString("name"));
			outVO.setPassword(rs.getString("password"));
			outVO.setBirthday(rs.getString("birthday"));
			
		}
		System.out.println("4.outVO:"+outVO);
		
		rs.close();
		pstmt.close();
		conn.close();
		return outVO;
	}
	
}
