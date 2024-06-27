package com.pcwk.ehr;

public class DaoFactory {
	
	public UserDao  userDao() {
		UserDao dao=new UserDao(connectionMaker());
		return dao;
	}

	
//	public BoardDao  boardDao() {
//		ConnectionMaker connectionMaker = new NConnectionMaker();
//		BoardDao dao=new BoardDao(connectionMaker());
//		return dao;
//	}	
	
	
	
	public ConnectionMaker connectionMaker() {
		return new NConnectionMaker();
	}
	
}          
