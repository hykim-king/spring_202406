package com.pcwk.ehr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserDao {
	final Logger log = LogManager.getLogger(UserDao.class);

	private DataSource dataSource;

	// default 생성자 필수
	public UserDao() {
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	// 테스트용 등록된 데이터 건수
	public int getCount() throws SQLException {
		int count = 0;

		Connection conn = dataSource.getConnection();
		StringBuilder sb = new StringBuilder(200);
		sb.append(" SELECT COUNT(*) cnt \n");
		sb.append(" FROM member         \n");

		log.debug("2.sql:\n" + sb.toString());
		PreparedStatement pstmt = conn.prepareStatement(sb.toString());
		log.debug("3.pstmt:" + pstmt);

		ResultSet rs = pstmt.executeQuery();
		log.debug("4.rs:" + rs);

		if (rs.next()) {
			count = rs.getInt("cnt");
		}
		log.debug("5.count:" + count);

		rs.close();
		pstmt.close();
		conn.close();

		return count;
	}

	private PreparedStatement makeStatement(Connection conn)throws SQLException{
		PreparedStatement pstmt = null;
		
		StringBuilder sb = new StringBuilder(200);
		sb.append(" DELETE FROM member \n");			
		log.debug("2.sql:\n" + sb.toString());			
		
		pstmt = conn.prepareStatement(sb.toString());
		log.debug("4.PreparedStatement:" + pstmt);
		
		return pstmt;
	}
	// 테스트용 전체 데이터 삭제
	public int deleteAll() throws SQLException {
		int flag = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			log.debug("3.Connection:" + conn);
			
			//--------------------------------------
			pstmt = makeStatement(conn);
			//--------------------------------------
			
			flag = pstmt.executeUpdate();

			log.debug("4.flag:" + flag);
		} catch (SQLException e) {
			log.debug("SQLException:" + e.getMessage());
			throw e;
		} finally {
			if (null != pstmt) {
				try {
					pstmt.close();
				} catch (SQLException e) {

				}
			}
			
			if (null != conn) {
				try {
					conn.close();
				} catch (SQLException e) {

				}
			}			
			
		}

		return flag;
	}

	// 단건 등록
	public int doSave(User user) throws SQLException {
		int flag = 0;
		// 1. DB연결을 위한 Connection
		// 2. SQL을 담은 PreparedStatement,Statement를 생성
		// 3. PreparedStatement를 실행한다.
		// 4. 실행결과 받기 ResultSet 받아서 저장
		// 5. Connection,PreparedStatement,ResultSet의 자원 반납.
		// 6. JDBC API에 대한 예외 처리

		StringBuilder sb = new StringBuilder(200);
		sb.append(" INSERT INTO member VALUES (?, ?, ?, ?) \n");

		log.debug("2.sql:\n" + sb.toString());
		log.debug("3.user:" + user);

		Connection conn = dataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement(sb.toString());
		pstmt.setString(1, user.getUserId());
		pstmt.setString(2, user.getName());
		pstmt.setString(3, user.getPassword());
		pstmt.setString(4, user.getBirthday());

		flag = pstmt.executeUpdate();
		log.debug("4.flag:" + flag);

		pstmt.close();
		conn.close();

		return flag;
	}

	// 단건 조회
	public User doSelectOne(User user) throws SQLException, NullPointerException {
		User outVO = null;
		// 1. DB연결을 위한 Connection
		// 2. SQL을 담은 PreparedStatement,Statement를 생성
		// 3. PreparedStatement를 실행한다.
		// 4. 실행결과 받기 ResultSet 받아서 저장
		// 5. Connection,PreparedStatement,ResultSet의 자원 반납.
		// 6. JDBC API에 대한 예외 처리

		Connection conn = dataSource.getConnection();

		StringBuilder sb = new StringBuilder(200);
		sb.append(" SELECT user_id,                \n");
		sb.append("        name,                   \n");
		sb.append("        password,               \n");
		sb.append("        birthday                \n");
		sb.append("   FROM member t1               \n");
		sb.append("  WHERE t1.user_id = ?          \n");
		log.debug("2.sql:\n" + sb.toString());
		log.debug("3.param:\n" + user);

		PreparedStatement pstmt = conn.prepareStatement(sb.toString());
		pstmt.setString(1, user.getUserId());

		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			outVO = new User();
			outVO.setUserId(rs.getString("user_id"));
			outVO.setName(rs.getString("name"));
			outVO.setPassword(rs.getString("password"));
			outVO.setBirthday(rs.getString("birthday"));

		}
		log.debug("4.outVO:" + outVO);

		// nullPointerException
		if (null == outVO) {
			throw new NullPointerException(user.getUserId() + "를 확인 하세요.");
		}

		rs.close();
		pstmt.close();
		conn.close();
		return outVO;
	}

}
