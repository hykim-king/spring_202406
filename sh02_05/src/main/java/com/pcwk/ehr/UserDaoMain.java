package com.pcwk.ehr;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

public class UserDaoMain {

	UserDao dao;
	User user;
	
	public UserDaoMain() {
        //DaoFactory처럼 @Configuration이 붙은 자바 코드를 설정정보로 사용 하려면 AnnotationConfigApplicationContext를 이용
		ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
		System.out.println("context:"+context);
		//객체 생성
		dao = context.getBean("userDao", UserDao.class);
		System.out.println("dao:"+dao);
		
		user= new User("james01","이상무01","4321","2002/12/31");
	}

	public void doSave() {
		System.out.println("doSave()");
		try {
			int flag=dao.doSave(user);
			
			if(1== flag) {
				System.out.println("---------------");				
				System.out.println("doSave 성공");
				System.out.println("---------------");
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
				System.out.println("---------------");				
				System.out.println("doSelectOne 성공");
				System.out.println("---------------");
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
