package com.pcwk.ehr.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.pcwk.ehr.User;
import com.pcwk.ehr.UserDao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserDaoJUnitTest {
	final Logger LOG = LogManager.getLogger(UserDaoJUnitTest.class);
	UserDao dao;
	//등록
	User  userVO01;
	User  userVO02;
	User  userVO03;
	
	//getCount에 사용
	User  searchVO;
	ApplicationContext context ;


    @BeforeAll
    public void beforeAllTests() {
        // context는 모든 테스트 전에 한 번만 로드됩니다.
        context = new GenericXmlApplicationContext("applicationContext.xml");
        dao = context.getBean("userDao", UserDao.class);
    }
    @BeforeEach
    public void beforeEachTest() {
		///sh03_01/src/main/java/applicationContext.xml
        // context는 모든 테스트 전에 한 번만 로드됩니다.
		
		//등록
		userVO01 = new User("james01-01","이상무01-01","4321","2002/12/31");;
		userVO02 = new User("james01-02","이상무01-02","4321","2002/12/30");;
		userVO03 = new User("james01-03","이상무01-03","4321","2002/12/29");;
		
		//getCount에 사용
		searchVO = new User("james01", "", "","");
		
		LOG.debug("====================");
		LOG.debug("=context="+context);
		LOG.debug("=dao="+dao);
		LOG.debug("====================");		
    }

    @AfterEach
    public void afterEachTest() {
        LOG.debug("After each test");
    }

    @Test
    public void addAndGet() throws ClassNotFoundException, SQLException {
    	dao.doDelete();
    	
		//2.
		int flag = dao.doSave(userVO01);
		int count = dao.getCount();
		assertEquals(flag, 1);
		assertEquals(count, 1);
		//UserVO02등록
		flag = dao.doSave(userVO02);
		count = dao.getCount();		
		assertEquals(flag, 1);
		assertEquals(count, 2);		
		
		//UserVO03등록
		flag = dao.doSave(userVO03);
		count = dao.getCount();
		assertEquals(flag, 1);
		assertEquals(count, 3);			
    }
    
    
	@Test
	@Ignore
	void test() {
		//fail("Not yet implemented");
		assertTrue(true);
		 LOG.debug("test()");
	}

}
