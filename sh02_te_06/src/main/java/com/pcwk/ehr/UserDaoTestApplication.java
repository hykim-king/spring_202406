package com.pcwk.ehr;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class UserDaoTestApplication {

	UserDao dao;
	User    userVO;
	
	ApplicationContext context;
	
	public UserDaoTestApplication() {
		///sh02_06/src/main/java/applicationContext.xml
		context = new GenericXmlApplicationContext("applicationContext.xml");
		System.out.println("context:"+context);
		
		dao = context.getBean("userDao", UserDao.class);
		System.out.println("dao:"+dao);
		
	}
	
	
	public static void main(String[] args) {
		UserDaoTestApplication main=new UserDaoTestApplication();

	}

}
