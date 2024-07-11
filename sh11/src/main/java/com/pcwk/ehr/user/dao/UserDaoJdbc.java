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
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.cmn.Search;
import com.pcwk.ehr.user.domain.Level;
import com.pcwk.ehr.user.domain.User;

@Repository("userDao") //<bean id="userDao"
public class UserDaoJdbc implements UserDao, PLog {

	final String NAMESPACE = "com.pcwk.ehr.user";
	final String DOT = ".";

	@Autowired
	SqlSessionTemplate sqlSessionTemplate;// DB연결

	// default 생성자 필수
	public UserDaoJdbc() {

	}

	/**
	 * 회원 수정
	 * 
	 * @param user
	 * @return 1(성공)/0(실패)
	 * @throws SQLException
	 */
	@Override
	public int doUpdate(User user) throws SQLException {
		int flag = 0;

		String statement = NAMESPACE + DOT + "doUpdate";
		log.debug("1. param:" + user);
		log.debug("2. statement:" + statement);

		flag = sqlSessionTemplate.update(statement, user);
		log.debug("3.flag:" + flag);

		return flag;
	}

	/**
	 * 단건 삭제
	 * 
	 * @param user
	 * @return 1(성공)/0(실패)
	 * @throws SQLException
	 */
	@Override
	public int doDelete(User user) throws SQLException {
		int flag = 0;

		String statement = NAMESPACE + DOT + "doDelete";

		log.debug("1. param:" + user);
		log.debug("2. statement:" + statement);

		flag = sqlSessionTemplate.delete(statement, user);
		log.debug("3.flag:" + flag);
		return flag;
	}

	@Override
	public List<User> getAll() {

		List<User> list = new ArrayList<User>();

		
		String statement = NAMESPACE + DOT + "getAll";
		log.debug("1. param: 없음");
		log.debug("2. statement:" + statement);		
		
		
		list = sqlSessionTemplate.selectList(statement);
		for (User user : list) {
			log.debug(user);
		}

		return list;
	}

	// 테스트용 등록된 데이터 건수:O
	@Override
	public int getCount() throws SQLException {
		int count = 0;

		String statement = NAMESPACE + DOT + "getCount";
		log.debug("1. param: 없음!");
		log.debug("2. statement:" + statement);

		count = sqlSessionTemplate.selectOne(statement);

		log.debug("3.count:" + count);

		return count;
	}

	// 테스트용 전체 데이터 삭제:O
	@Override
	public int deleteAll() throws SQLException {
		int flag = 0;

		String statement = NAMESPACE + DOT + "deleteAll";
		log.debug("1. param: 없음!");
		log.debug("2. statement:" + statement);

		flag = sqlSessionTemplate.delete(statement);
		log.debug("3.flag:" + flag);

		return flag;
	}

	// 단건 등록:O
	@Override
	public int doSave(User user) throws SQLException {
		int flag = 0;

		String statement = NAMESPACE + DOT + "doSave";
		log.debug("1. param:" + user);
		log.debug("2. statement:" + statement);

		flag = sqlSessionTemplate.insert(statement, user);
		log.debug("3.flag:" + flag);

		return flag;
	}

	// 단건 조회
	@Override
	public User doSelectOne(User user) throws SQLException, NullPointerException {
		User outVO = null;

		String statement = NAMESPACE + DOT + "doSelectOne";
		log.debug("1. param:" + user);
		log.debug("2. statement:" + statement);
		outVO = sqlSessionTemplate.selectOne(statement, user.getUserId());

		if (null == outVO) {
			throw new NullPointerException("조회된 데이터가 없습니다.");
		}

		log.debug("3.outVO:" + outVO);

		return outVO;
	}

	@Override
	public List<User> doRetrieve(Search search) throws SQLException {
		List<User> list = new ArrayList<User>();

		
		String statement = NAMESPACE + DOT + "doRetrieve";
		log.debug("1.param:" + search);
		log.debug("2.statement = " + search);

		list = sqlSessionTemplate.selectList(statement, search);

		for (User user : list) {
			log.debug(user);
		}

		return list;
	}

	@Override
	public int multipleSave() throws SQLException {
		int count = 0;
		String statement = NAMESPACE + DOT + "multipleSave";
		log.debug("1. param: 없음!"  );
		log.debug("2. statement:" + statement);

		count = sqlSessionTemplate.insert(statement);
		log.debug("3.count:" + count);
		
		return count;
	}

}
