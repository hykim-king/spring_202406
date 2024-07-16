package com.pcwk.ehr.user.service;

import static org.junit.Assert.*;


import static com.pcwk.ehr.user.service.UserServiceImpl.MIN_LOGINCOUNT_FOR_SILVER;
import static com.pcwk.ehr.user.service.UserServiceImpl.MIN_RECOMMEND_FOR_GOLD;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;

import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.mapper.UserMapper;
import com.pcwk.ehr.user.domain.Level;
import com.pcwk.ehr.user.domain.User;

@RunWith(SpringRunner.class) //스프링 컨텍스트 프레임워크의 JUnit확장기능 지정
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml",
                                   "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" 		
})
@FixMethodOrder(MethodSorters.NAME_ASCENDING) //메소드 수행 순서: method ASCENDING ex)a~z
public class UserServiceImplTest implements PLog {

	@Autowired
	ApplicationContext context;

	@Autowired
	UserMapper userMapper;//

	@Autowired
	@Qualifier("userServiceImpl")
	UserService userService;
	
	@Autowired
	TestUserService  testUserService;


	List<User> users;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ setUpBeforeClass()                                      │");
		log.debug("└─────────────────────────────────────────────────────────┘");
	}

	@Before
	public void setUp() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ setUp()                                                 │");
		log.debug("└─────────────────────────────────────────────────────────┘");
		
		// 긴존 데이터 삭제
		userMapper.deleteAll();
		
		// user 5명 생성
		// 1 BASIC
		// 2 BASIC -> SILVER(O)
		// 3.SILVER
		// 4.SILVER -> GOLD(O)
		// 5.GOLD
		users = Arrays.asList(
				new User("james01", "이상무01", "4321", "2002/12/31", Level.BASIC, MIN_LOGINCOUNT_FOR_SILVER -1, MIN_RECOMMEND_FOR_GOLD-30, "jamesol@paran.com",
						"sysdate사용"),
				new User("james02", "이상무02", "4321", "2002/12/30", Level.BASIC, MIN_LOGINCOUNT_FOR_SILVER, MIN_RECOMMEND_FOR_GOLD-28, "jamesol@paran.com",
						"sysdate사용"),
				new User("james03", "이상무03", "4321", "2002/12/29", Level.SILVER, MIN_LOGINCOUNT_FOR_SILVER+1, MIN_RECOMMEND_FOR_GOLD-1, "jamesol@paran.com",
						"sysdate사용"),
				new User("james04", "이상무04", "4321", "2002/12/28", Level.SILVER, MIN_LOGINCOUNT_FOR_SILVER+2, MIN_RECOMMEND_FOR_GOLD, "jamesol@paran.com",
						"sysdate사용"),
				new User("james05", "이상무05", "4321", "2002/12/27", Level.GOLD, MIN_LOGINCOUNT_FOR_SILVER+5, MIN_RECOMMEND_FOR_GOLD+2, "jamesol@paran.com",
						"sysdate사용"));
	}

	@After
	public void tearDown() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ tearDown()                                              │");
		log.debug("└─────────────────────────────────────────────────────────┘");
	}

	

	//upgradeAllOrNothing
	//@Ignore
	@Test
	public void upgradeAllOrNothing() {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ upgradeAllOrNothing()                    │");
		log.debug("└──────────────────────────────────────────┘");	
		
		//1. 기존데이터 모두 삭제
		//2. 5명 등록
		//3. 등업
		//4. checkLevel(users.get(1), false);
		
		
		try {

			assertEquals(0, userMapper.getCount());
			
			//2.
			for(User userVO   :users) {
				userMapper.doSave(userVO);
			}
			assertEquals(5, userMapper.getCount());
			
			
			testUserService.upgradeLevelsTx();
		
			checkLevel(users.get(1), false);
			
		} catch (Exception e) {
			log.debug("┌──────────────────────────────────────────┐");
			log.debug("│ RuntimeException()                       │"+e.getMessage());
			log.debug("└──────────────────────────────────────────┘");			
			e.printStackTrace();

		}
	}
	
	@Ignore
	@Test
	public void doSave() throws SQLException {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ doSave()                                 │");
		log.debug("└──────────────────────────────────────────┘");

		// 1. 기존 데이터 삭제
		// 2. 등급있는 경우(GOLD), 등급이 Null
		// 3. 2명 등록
		// 4. 데이터 조회
		// 5. 비교



		// 2
		User userWithLevel = users.get(4);// GOLD

		User userWithOutLevel = users.get(0);
		userWithOutLevel.setLevel(null);// Level null

		// 3
		int flag = userService.doSave(userWithLevel);
		assertEquals(1, flag);

		flag = userService.doSave(userWithOutLevel);
		assertEquals(1, flag);

		// 4
		User userWithLevelRead = userMapper.doSelectOne(userWithLevel);

		User userWithoutLevelRead = userMapper.doSelectOne(userWithOutLevel);

		assertEquals(userWithLevelRead.getLevel(), userWithLevel.getLevel());
		assertEquals(userWithoutLevelRead.getLevel(), Level.BASIC);

	}

	@Ignore
	@Test
	public void upgradeLevels() throws SQLException {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ upgradeLevels()                          │");
		log.debug("└──────────────────────────────────────────┘");

		// user 5명 생성
		// 1 BASIC
		// 2 BASIC -> SILVER(O)
		// 3.SILVER
		// 4.SILVER -> GOLD(O)
		// 5.GOLD

		// B. users 모두 데이터 입력
		// C. 등업
		// D. 등업 데이터 비교 james02(BASIC->SILVER) james04(SILVER->GOLD)

		// A.
//		for (User userVO : users) {
//			userMapper.doDelete(userVO);
//		}

		assertEquals(0, userMapper.getCount());

		// B.
		for (User userVO : users) {
			userMapper.doSave(userVO);
		}
		assertEquals(5, userMapper.getCount());

		// C
		userService.upgradeLevelsTx();

		// D
		checkLevel(users.get(0), false);
		checkLevel(users.get(1), true);
		checkLevel(users.get(2), false);
		checkLevel(users.get(3), true);
		checkLevel(users.get(4), false);

	}

	/**
	 * 
	 * @param userVO
	 * @param upgraded(true: 다음 레벨로 등업)
	 * @throws NullPointerException
	 * @throws SQLException
	 */
	private void checkLevel(User userVO, boolean upgraded) throws NullPointerException, SQLException {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ checkLevel()                             │");
		log.debug("└──────────────────────────────────────────┘");

		User userUpdate = userMapper.doSelectOne(userVO);

		if (true == upgraded) {
			// 등업
			assertEquals(userUpdate.getLevel(), userVO.getLevel().nextLevel());
		} else {
			// 등업 없음
			assertEquals(userUpdate.getLevel(), userVO.getLevel());
		}

	}

	@Ignore
	@Test
	public void beans() {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ beans()                                  │");
		log.debug("└──────────────────────────────────────────┘");
		log.debug("context:" + context);
		log.debug("userMapper:" + userMapper);
		log.debug("userService:" + userService);
		log.debug("testUserService:" + testUserService);
		
		assertNotNull(context);
		assertNotNull(userMapper);
		assertNotNull(userService);
		assertNotNull(testUserService);
		// assertNull(context);
	}

}
