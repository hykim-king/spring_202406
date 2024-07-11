/**
 * Package Name : com.pcwk.ehr <br/>
 * Class Name: UserDao.java <br/>
 * Description: 회원 DAO <br/>
 * Modification imformation : <br/>
 * ------------------------------------------<br/>
 * 최초 생성일 : 2024.07.02<br/>
 *           2024.07.03: 유저 Level기능 추가  <br/>
 *
 *
 *
 * ------------------------------------------<br/>
 * @author : acorn
 * @since  : 2024.07.03
 * @version: 0.5
 */
package com.pcwk.ehr.user.dao;

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

import com.pcwk.ehr.user.domain.Level;
import com.pcwk.ehr.user.domain.User;

public class UserDaoJdbc implements UserDao {

	final Logger log = LogManager.getLogger(UserDaoJdbc.class);

	private RowMapper<User> userMapper = new RowMapper<User>() {

		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User outUser = new User();
			outUser.setUserId(rs.getString("user_id"));
			outUser.setName(rs.getString("name"));
			outUser.setPassword(rs.getString("password"));
			outUser.setBirthday(rs.getString("birthday"));

			outUser.setLevel(Level.valueOf(rs.getInt("u_level")));
			outUser.setLogin(rs.getInt("login"));
			outUser.setRecommend(rs.getInt("recommend"));
			outUser.setEmail(rs.getString("email"));
			outUser.setRegDt(rs.getString("reg_dt"));

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
	public UserDaoJdbc() {

	}

	public void setDataSource(DataSource dataSource) {

		this.dataSource = dataSource;

		// jdbcTemplate 객체 생성
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/**
	 * 회원 수정
	 * @param user
	 * @return 1(성공)/0(실패)
	 * @throws SQLException
	 */
	@Override
	public int doUpdate(User user) throws SQLException{
		int flag = 0;
		
		StringBuilder sb=new StringBuilder(300);
		sb.append(" UPDATE member              \n");
		sb.append(" SET                        \n");
		sb.append("     name       = ?,        \n");
		sb.append("     password   = ?,        \n");
		sb.append("     birthday   = ?,        \n");
		sb.append("     u_level    = ?,        \n");
		sb.append("     login      = ?,        \n");
		sb.append("     recommend  = ?,        \n");
		sb.append("     email      = ?,        \n");
		sb.append("     reg_dt     = SYSDATE   \n");
		sb.append(" WHERE                      \n");
		sb.append("         user_id = ?        \n");
		
		log.debug("1.sql:\n"+sb.toString());
		
		Object[] args = {
				user.getName(),
				user.getPassword(),
				user.getBirthday(),
				user.getLevel().intValue(),
				user.getLogin(),
				user.getRecommend(),
				user.getEmail(),
				user.getUserId()
		};
		
		for (Object obj : args) {
			log.debug("2.param:" + obj.toString());
		}
		
		flag = jdbcTemplate.update(sb.toString(), args);
		log.debug("3.flag:" + flag);
		
		return flag;
	}
	
	
	/**
	 * 단건 삭제
	 * @param user
	 * @return 1(성공)/0(실패)
	 * @throws SQLException
	 */
	@Override
	public int doDelete(User user) throws SQLException{
		int flag = 0;
		
		StringBuilder sb = new StringBuilder(200);
		sb.append(" DELETE FROM member  \n");
		sb.append(" WHERE user_id = ?   \n");
		log.debug("1.sql:\n" + sb.toString());
		
		Object[] args = {user.getUserId()};
		for (Object obj : args) {
			log.debug("2.param:" + obj.toString());
		}
		
		flag = jdbcTemplate.update(sb.toString(),args);
		log.debug("3.flag:" + flag);
		return flag;
	}
	
	
	@Override
	public List<User> getAll() {

		List<User> list = new ArrayList<User>();
		StringBuilder sb = new StringBuilder(300);
		sb.append("  SELECT                                             \n");
		sb.append("     user_id,                                        \n");
		sb.append("     name,                                           \n");
		sb.append("     password,                                       \n");
		sb.append("     birthday,                                       \n");
		sb.append("     u_level,                                        \n");
		sb.append("     login,                                          \n");
		sb.append("     recommend,                                      \n");
		sb.append("     email,                                          \n");
		sb.append("     TO_CHAR(reg_dt,'YYYY-MM-DD HH24:MI:SS') reg_dt  \n");
		sb.append(" FROM                                                \n");
		sb.append("     member                                          \n");
		sb.append("	ORDER BY  user_id	                                \n");

		log.debug("1.sql:\n" + sb.toString());

		list = this.jdbcTemplate.query(sb.toString(), userMapper);

		for (User user : list) {
			log.debug(user);
		}

		return list;
	}

	// 테스트용 등록된 데이터 건수:O
	@Override
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

	// 테스트용 전체 데이터 삭제:O
	@Override
	public int deleteAll() throws SQLException {
		int flag = 0;

		StringBuilder sb = new StringBuilder(200);
		sb.append(" DELETE FROM member \n");
		log.debug("1.sql:\n" + sb.toString());

		flag = jdbcTemplate.update(sb.toString());
		log.debug("2.flag:" + flag);

		return flag;
	}

	// 단건 등록:O
	@Override
	public int doSave(User user) throws SQLException {
		int flag = 0;

		StringBuilder sb = new StringBuilder(300);
		sb.append(" INSERT INTO member ( \n");
		sb.append("     user_id,         \n");
		sb.append("     name,            \n");
		sb.append("     password,        \n");
		sb.append("     birthday,        \n");
		sb.append("     u_level,         \n");
		sb.append("     login,           \n");
		sb.append("     recommend,       \n");
		sb.append("     email,           \n");
		sb.append("     reg_dt           \n");
		sb.append(" ) VALUES (           \n");
		sb.append("     ?,               \n");
		sb.append("     ?,               \n");
		sb.append("     ?,               \n");
		sb.append("     ?,               \n");
		sb.append("     ?,               \n");
		sb.append("     ?,               \n");
		sb.append("     ?,               \n");
		sb.append("     ?,               \n");
		sb.append("     SYSDATE          \n");
		sb.append(" )                    \n");

		log.debug("1.sql:\n" + sb.toString());

		Object[] args = { user.getUserId(), user.getName(), user.getPassword(), user.getBirthday(),
				user.getLevel().intValue(), user.getLogin(), user.getRecommend(), user.getEmail() };

		for (Object obj : args) {
			log.debug("2.param:" + obj.toString());
		}

		flag = jdbcTemplate.update(sb.toString(), args);
		log.debug("3.flag:" + flag);

		return flag;
	}

	// 단건 조회
	@Override
	public User doSelectOne(User user) throws SQLException, NullPointerException {
		User outVO = null;

		StringBuilder sb = new StringBuilder(500);
		sb.append("  SELECT                                             \n");
		sb.append("     user_id,                                        \n");
		sb.append("     name,                                           \n");
		sb.append("     password,                                       \n");
		sb.append("     birthday,                                       \n");
		sb.append("     u_level,                                        \n");
		sb.append("     login,                                          \n");
		sb.append("     recommend,                                      \n");
		sb.append("     email,                                          \n");
		sb.append("     TO_CHAR(reg_dt,'YYYY-MM-DD HH24:MI:SS') reg_dt  \n");
		sb.append(" FROM                                                \n");
		sb.append("     member                                          \n");
		sb.append(" WHERE user_id = ?                                   \n");
		log.debug("2.sql:\n" + sb.toString());
		log.debug("3.param:\n" + user);

		// param
		Object[] args = { user.getUserId() };

		outVO = this.jdbcTemplate.queryForObject(sb.toString(), userMapper, args);

		if (null == outVO) {
			throw new NullPointerException("조회된 데이터가 없습니다.");
		}

		log.debug("4.outVO:" + outVO);

		return outVO;
	}

}
