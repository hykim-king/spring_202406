package com.pcwk.ehr.user.service;

import java.sql.SQLException;
import java.util.List;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.user.domain.User;

public interface UserService  {
	/**
	 * id중복 체크 
	 * @param inVO
	 * @return 1(사용불가)/0(사용가능)
	 * @throws SQLException
	 */
	int idDuplicateCheck(User inVO) throws SQLException;
	
	/**
	 * 최초 가입시 Level이 Null이면 BASIC
	 * 
	 * @param user
	 * @return 1(성공)/0(실패)
	 * @throws SQLException
	 */
	int doSave(User user) throws SQLException;

	/**
	 * 전체 회원 등업
	 * 	// 1. 전체 회원 조회
		// 2. 등급: BASIC, SILVER, GOLD 등업 조건
	
		// 1. BASIC : 최초 가입
		// 2. SILVER : 가입 후 50회 이상 로그인
		// 3. GOLD : SILVER이면서 30회 이상 추천
		// 3. 조건에 해당되는 회원 등업
	
	 * @throws SQLException
	 */
	void upgradeLevelsTx() throws SQLException;

	/**
	 * 단건 삭제
	 * @param user
	 * @return 1(성공)/0(실패)
	 * @throws SQLException
	 */
	int doDelete(User inVO) throws SQLException;
	
	
	/**
	 * 목록 조회
	 */
	List<User>  doRetrieve(DTO search) throws SQLException;	
	
	/**
	 * 수정
	 * @param user
	 * @return 1(성공)/0(실패)
	 * @throws SQLException
	 */
	int doUpdate(User inVO) throws SQLException;	
	
	/**
	 * 정보 단건 조건 
	 * @param User
	 * @return User
	 * @throws SQLException
	 * @throws NullPointerException
	 */
	User doSelectOne(User inVO) throws SQLException;	
}