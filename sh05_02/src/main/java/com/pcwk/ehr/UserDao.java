package com.pcwk.ehr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class UserDao {

	final Logger log = LogManager.getLogger(UserDao.class);

	private RowMapper<User> userMapper = new RowMapper<User>() {

		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User outUser = new User();
			outUser.setUserId(rs.getString("user_id"));
			outUser.setName(rs.getString("name"));
			outUser.setPassword(rs.getString("password"));
			outUser.setBirthday(rs.getString("birthday"));
			return outUser;
		}

	};
	
	/**
	 * JdbcTemplate Spring Framework에서 제공하는 유틸리티 클래스 입니다. 이 클래스는 JDBC API를 사용한
	 * 데이터베이스 작업을 쉽게 처리할 수 있도록 도와 줍니다.
	 */
	private JdbcTemplate jdbcTemplate;

	private DataSource dataSource;

	// default 생성자 필수
	public UserDao() {

	}

	public void setDataSource(DataSource dataSource) {

		this.dataSource = dataSource;

		// jdbcTemplate 객체 생성
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<User> getAll() {

		List<User> list = new ArrayList<User>();
		StringBuilder sb = new StringBuilder(300);
		sb.append("	SELECT user_id,         \n");
		sb.append("	       name,            \n");
		sb.append("	       password,        \n");
		sb.append("	       birthday         \n");
		sb.append("	  FROM member t1        \n");
		sb.append("	 ORDER BY  t1.user_id	\n");

		log.debug("1.sql:\n" + sb.toString());

		list = this.jdbcTemplate.query(sb.toString(), userMapper);

		for (User user : list) {
			log.debug(user);
		}

		return list;
	}

	// 테스트용 등록된 데이터 건수
	public int getCount() throws SQLException {
		int count = 0;

		StringBuilder sb = new StringBuilder(200);
		sb.append(" SELECT COUNT(*) cnt \n");
		sb.append(" FROM member         \n");

		log.debug("1.sql:\n" + sb.toString());

		count = this.jdbcTemplate.queryForObject(sb.toString(), Integer.class);

		log.debug("2.count:" + count);

		return count;
	}

	// 테스트용 전체 데이터 삭제
	public int deleteAll() throws SQLException {
		int flag = 0;

		StringBuilder sb = new StringBuilder(200);
		sb.append(" DELETE FROM member \n");
		log.debug("1.sql:\n" + sb.toString());

		flag = jdbcTemplate.update(sb.toString());
		log.debug("2.flag:" + flag);

		return flag;
	}

	// 단건 등록
	public int doSave(User user) throws SQLException {
		int flag = 0;

		StringBuilder sb = new StringBuilder(200);
		sb.append(" INSERT INTO member VALUES (?, ?, ?, ?) \n");

		log.debug("1.sql:\n" + sb.toString());

		Object[] args = { user.getUserId(), user.getName(), user.getPassword(), user.getBirthday() };

		for (Object obj : args) {
			log.debug("2.param:" + obj.toString());
		}

		flag = jdbcTemplate.update(sb.toString(), args);
		log.debug("3.flag:" + flag);

		return flag;
	}

	// 단건 조회
	public User doSelectOne(User user) throws SQLException, NullPointerException {
		User outVO = null;

		StringBuilder sb = new StringBuilder(200);
		sb.append(" SELECT user_id,                \n");
		sb.append("        name,                   \n");
		sb.append("        password,               \n");
		sb.append("        birthday                \n");
		sb.append("   FROM member t1               \n");
		sb.append("  WHERE t1.user_id = ?          \n");
		log.debug("2.sql:\n" + sb.toString());
		log.debug("3.param:\n" + user);

		// param
		Object[] args = { user.getUserId() };

		outVO = this.jdbcTemplate.queryForObject(sb.toString(), userMapper, args);

		if(null == outVO) {
			throw new NullPointerException("조회된 데이터가 없습니다.");
		}
		
		log.debug("4.outVO:" + outVO);

		return outVO;
	}

}
