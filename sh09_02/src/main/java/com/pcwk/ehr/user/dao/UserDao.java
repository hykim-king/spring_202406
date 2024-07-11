package com.pcwk.ehr.user.dao;

import java.sql.SQLException;
import java.util.List;

import com.pcwk.ehr.cmn.Search;
import com.pcwk.ehr.user.domain.User;

public interface UserDao {

	/**
	 * 목록 조회
	 */
	List<User>  doRetrieve(Search search) throws SQLException;
	
	/**
	 * 회원 수정
	 * @param user
	 * @return 1(성공)/0(실패)
	 * @throws SQLException
	 */
	int doUpdate(User user) throws SQLException;

	/**
	 * 단건 삭제
	 * @param user
	 * @return 1(성공)/0(실패)
	 * @throws SQLException
	 */
	int doDelete(User user) throws SQLException;

	/**
	 * 전체 회원 조회
	 * @return List<User>
	 */
	List<User> getAll();


	/**
	 * 테스트용 전체 회원 수
	 * @return int
	 * @throws SQLException
	 */
	int getCount() throws SQLException;


	/**
	 * 테스트용 전체 데이터 삭제
	 * @return
	 * @throws SQLException
	 */
	int deleteAll() throws SQLException;


	/**
	 * 회원 정보 단건 등록
	 * @param user
	 * @return 1(성공)/0(실패)
	 * @throws SQLException
	 */
	int doSave(User user) throws SQLException;

	// 단건 조회
	/**
	 * 회원 정보 단건 조건 
	 * @param User
	 * @return User
	 * @throws SQLException
	 * @throws NullPointerException
	 */
	User doSelectOne(User user) throws SQLException, NullPointerException;

}