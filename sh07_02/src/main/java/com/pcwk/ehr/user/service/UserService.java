package com.pcwk.ehr.user.service;

import java.sql.SQLException;

import com.pcwk.ehr.user.domain.User;

public interface UserService {

	/**
	 * 최초 가입시 Level이 Null이면 BASIC
	 * 
	 * @param user
	 * @return
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
	void upgradeLevels() throws SQLException;

}