package com.pcwk.ehr.user.service;

import java.sql.SQLException;
import java.util.List;

import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.user.dao.UserDao;
import com.pcwk.ehr.user.domain.Level;
import com.pcwk.ehr.user.domain.User;

public class UserService implements PLog {

	UserDao userDao;

	public UserService() {

	}

	/**
	 * setter 주입
	 * @param userDao
	 */
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * 전체 회원 등업
	 * @throws SQLException 
	 */
	public void upgradeLevels() throws SQLException {
		//1. 전체 회원 조회
		//2. 등급: BASIC, SILVER, GOLD 등업 조건
		//		1. BASIC :  최초 가입
		//		2. SILVER : 가입 후 50회 이상 로그인
		//		3. GOLD   : SILVER이면서 30회 이상 추천		
		//3. 조건에 해당되는 회원 등업
		
		//1
		List<User> users = userDao.getAll();
		
		//2.
		for(User user:users) {
			
			Boolean  changed = null; //Level변화 상태 저장
			
			//등업 대상 선정
			//BASIC -> SILVER
			if(Level.BASIC == user.getLevel() && user.getLogin() >=50) {
				user.setLevel(Level.SILVER);
				changed = true;
			}//SILVER -> GOLD
			else if(Level.SILVER == user.getLevel() && user.getRecommend() >= 30) {
				user.setLevel(Level.GOLD);
				changed = true;				
			}//GOLD
			else if(Level.GOLD == user.getLevel()){
				changed = false;
			}
			else {
				changed = false;
			}
			
			
			//등업: Level의 변경이 있는 경우만 doUpdate호출
			if(true == changed) {
				userDao.doUpdate(user);
			}
			
			
		}
		
		
	}

}














