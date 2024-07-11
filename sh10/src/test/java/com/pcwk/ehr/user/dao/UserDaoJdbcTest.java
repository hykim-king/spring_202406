package com.pcwk.ehr.user.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.pcwk.ehr.cmn.Search;
import com.pcwk.ehr.user.dao.UserDao;
import com.pcwk.ehr.user.domain.Level;
import com.pcwk.ehr.user.domain.User;

@RunWith(SpringRunner.class) //스프링 컨텍스트 프레임워크의 JUnit확장기능 지정
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class UserDaoJdbcTest {

	final static Logger  log = LogManager.getLogger(UserDaoJdbcTest.class); 
	
	@Autowired
	ApplicationContext context;
	
	@Autowired
	@Qualifier("userDao")
	UserDao dao;
	
	User    userVO01;
	User    userVO02;
	User    userVO03;
	
	Search  search;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		log.debug("setUpBeforeClass:");
		
	}

	@Before
	public void setUp() throws Exception {
		//@Test 메소드 수행시 1회 수행 : @Test메서드가 수행될 때마다 읽고 있음!: spring-test lib추가
		log.debug("context:"+context);
		log.debug("dao:"+dao);
		/*
		 * 	public User(String userId, String name, String password, String birthday, Level level, int login, int recommend,
			String email, String regDt) {
		 */
		userVO01= new User("james01","이상무01","4321","2002/12/31",Level.BASIC,1,0,"jamesol@paran.com","sysdate사용");
		userVO02= new User("james02","이상무02","4321","2002/12/30",Level.SILVER,50,2,"jamesol@paran.com","sysdate사용");
		userVO03= new User("james03","이상무03","4321","2002/12/29",Level.GOLD,100,31,"jamesol@paran.com","sysdate사용");
		
		search = new Search();
	}

	//@Ignore
	@Test
	public void doRetrieve() throws SQLException {
		dao.deleteAll();
		
		//다건 입력
		dao.multipleSave();
		
//		//건수 조회
		int count = dao.getCount();
		assertEquals(101,count);		

		
		search.setPageNo(2);
		search.setPageSize(10);
		List<User> list = dao.doRetrieve(search);
		assertEquals(10, list.size());
	}
	
	@Ignore
	@Test
	public void doUpdate() throws SQLException{
		//1. 전체삭제
		//2. 데이터 1건 입력
		//3. 단건 조회
		//4. 조회 데이터로 데이터 수정및 Update
		//5. 수정데이터 조회
		//6. 비교
		
		//1
		dao.deleteAll();
		 
		int count = dao.getCount();
		assertEquals(0,count);		
		
		//2 등록
		int flag = dao.doSave(userVO01);
		assertEquals(1, flag);
		assertEquals(dao.getCount(), 1);		
		
		//3
		User outVO = dao.doSelectOne(userVO01);
		assertNotNull(outVO);//return User Null check
		isSameUser(userVO01, outVO);
		
		//4
		String updateStr = "_U";
		//이름,비번,생일,등급,로그인, 추천, 이메일
		outVO.setName(outVO.getName()+updateStr);
		outVO.setPassword(outVO.getPassword()+updateStr);
		outVO.setBirthday(outVO.getBirthday());
		outVO.setLevel(Level.SILVER);
		outVO.setLogin(outVO.getLogin()+10);
		outVO.setRecommend(outVO.getRecommend()+20);
		outVO.setEmail(outVO.getEmail()+updateStr);
		
		//5
		flag = dao.doUpdate(outVO);
		assertEquals(1, flag);
		
		User upOutVO = dao.doSelectOne(outVO);
		
		//6
		isSameUser(upOutVO, outVO);
		
	}
	
	
	@Ignore
	@Test
	public void getAll() throws SQLException {
		//매번 동일 결과 도출
		//1.전체 삭제
		//2.데이터 3건입력
		//3.전체 조회=3건
		
		
		dao.deleteAll();
		
		
		//건수 조회
		int count = dao.getCount();
		assertEquals(0,count);		
		
		// 등록
		int flag = dao.doSave(userVO01);
		assertEquals(1, flag);
		assertEquals(dao.getCount(), 1);
		
		// 등록
		flag = dao.doSave(userVO02);
		assertEquals(1, flag);
		assertEquals(dao.getCount(), 2);
		
		// 등록
		flag = dao.doSave(userVO03);
		assertEquals(1, flag);
		assertEquals(dao.getCount(), 3);
		
		// 목록조회
		List<User> list = dao.getAll();
		assertNotNull(list);
		assertEquals(list.size(), 3);
	}	
	
	@Ignore
	//3건의 데이터를 등록 조회 테스트
	@Test(timeout = 5000) //메소드 수행 시간 : 1/1000 초
	public void addAndGet() throws SQLException {
		//0. 전체 삭제
		//1. 등록
		//2. 한건조회
		//3. 비교
		dao.doDelete(userVO01);
		dao.deleteAll();
		
		
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
		
		assertEquals(userVO.getLevel(), outVO.getLevel());
		assertEquals(userVO.getLogin(), outVO.getLogin());
		assertEquals(userVO.getRecommend(), outVO.getRecommend());
		assertEquals(userVO.getEmail(),outVO.getEmail());
		
	}
	
	@Ignore
	@Test
	public void beans() {
		assertNotNull(context);
		assertNotNull(dao);
		log.debug("beans()");
	}
	

}
