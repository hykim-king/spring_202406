package com.pcwk.ehr;

import java.sql.SQLException;

public class UserDaoMain {

	UserDao dao;
	User user;
	
	public UserDaoMain() {

		dao = new DaoFactory().userDao();
		user= new User("james01","이상무01","4321","2002/12/31");
	}

	public void doSave() {
		System.out.println("doSave()");
		try {
			int flag=dao.doSave(user);
			
			if(1== flag) {
				System.out.println("doSave 성공");
			}else {
				System.out.println("doSave 실패");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void doSelectOne(){
		System.out.println("doSelectOne()");
		
		try {
			User outVO = dao.doSelectOne(user);
			if(null != outVO) {
				System.out.println("doSelectOne 성공");
			}else {
				System.out.println("doSelectOne 실패");
			}			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		UserDaoMain main=new UserDaoMain();
		main.doSave();
		main.doSelectOne();
	}

}
