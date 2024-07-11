package com.pcwk.ehr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class) //스프링 컨텍스트 프레임워크의 JUnit확장기능 지정
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class UserDaoTest {

	final static Logger  log = LogManager.getLogger(UserDaoTest.class); 
	
	@Autowired
	ApplicationContext context;
	
	@Autowired
	UserDao dao;
	
	User    userVO01;
	User    userVO02;
	User    userVO03;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		log.debug("setUpBeforeClass:");
		
	}

	@Before
	public void setUp() throws Exception {
		//@Test 메소드 수행시 1회 수행 : @Test메서드가 수행될 때마다 읽고 있음!: spring-test lib추가
		log.debug("context:"+context);
		log.debug("dao:"+dao);
		
		userVO01= new User("james01","이상무01","4321","2002/12/31");
		userVO02= new User("james02","이상무02","4321","2002/12/30");
		userVO03= new User("james03","이상무03","4321","2002/12/29");
	}

	//expected = 예외, 특정예외가 발생하면 성공!
	@Ignore //Test code skip
	@Test(expected = NullPointerException.class)
	public void getFailure() throws SQLException {
		log.debug("===============");
		log.debug("=getFailure()=");
		log.debug("===============");
		
		//1. 전체 데이터 삭제
		//2. 한건 등록
		//3. 한건(없는 user_id) 조회
		
		//1.
		dao.deleteAll();
		
		//2.
		int flag = dao.doSave(userVO01);
		assertEquals(1, flag);
		
		//3.
		userVO01.setUserId("user_id없음");
		
		User outVO = dao.doSelectOne(userVO01);
	}
	
	
	//3건의 데이터를 등록 조회 테스트
	@Test(timeout = 3000) //메소드 수행 시간 : 1/1000 초
	public void addAndGet() throws SQLException {
		//0. 전체 삭제
		//1. 등록
		//2. 한건조회
		//3. 비교
		int cnt = dao.deleteAll();
		log.debug("cnt:"+cnt);
		
		//건수 조회
		int count = dao.getCount();
		assertEquals(0,count);
		
		// 1건 등록
		int flag = dao.doSave(userVO01);
		log.debug("flag:"+flag);
		assertEquals(1, flag);
		
		count = dao.getCount();
		log.debug("count:"+count);
		assertEquals(1, count);
		
		User outVO = dao.doSelectOne(userVO01);
		assertNotNull(outVO);//return User Null check
		isSameUser(userVO01, outVO);

		//2건 등록
		flag = dao.doSave(userVO02);
		assertEquals(1, flag);
		
		count = dao.getCount();
		assertEquals(2, count);
		
		User outVO02 = dao.doSelectOne(userVO02);
		assertNotNull(outVO02);
		isSameUser(userVO02, outVO02);
		
		
		//3건 등록
		flag = dao.doSave(userVO03);
		assertEquals(1, flag);

		count = dao.getCount();
		assertEquals(3, count);		
		
		User outVO03 = dao.doSelectOne(userVO03);
		assertNotNull(outVO03);
		isSameUser(userVO03, outVO03);
	}
	
	
	public void isSameUser(User userVO, User outVO) {
		assertEquals(userVO.getUserId(), outVO.getUserId());
		assertEquals(userVO.getName(), outVO.getName());
		assertEquals(userVO.getPassword(), outVO.getPassword());
		assertEquals(userVO.getBirthday(),outVO.getBirthday());		
	}
	
	
	@Ignore
	@Test
	public void beans() {
		assertNotNull(context);
		assertNotNull(dao);
		log.debug("beans()");
	}
	

}
