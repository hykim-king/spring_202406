package com.pcwk.ehr.user.service;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.user.dao.UserDao;
import com.pcwk.ehr.user.domain.Level;
import com.pcwk.ehr.user.domain.User;

@RunWith(SpringRunner.class) // 스프링 컨텍스트 프레임워크의 JUnit확장기능 지정
@ContextConfiguration(locations = { "classpath:/applicationContext.xml" })
public class UserServiceTest implements PLog {

	@Autowired
	ApplicationContext context;

	@Autowired
	UserDao userDao;
	
	@Autowired
	UserService userService;

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
		// user 5명 생성
		// 1 BASIC
		// 2 BASIC -> SILVER(O)
		// 3.SILVER
		// 4.SILVER -> GOLD(O)
		// 5.GOLD
		users = Arrays.asList(
				new User("james01", "이상무01", "4321", "2002/12/31", Level.BASIC, 49, 0, "jamesol@paran.com",
						"sysdate사용"),
				new User("james02", "이상무02", "4321", "2002/12/30", Level.BASIC, 50, 2, "jamesol@paran.com",
						"sysdate사용"),
				new User("james03", "이상무03", "4321", "2002/12/29", Level.SILVER, 51, 29, "jamesol@paran.com",
						"sysdate사용"),
				new User("james04", "이상무04", "4321", "2002/12/28", Level.SILVER, 52, 30, "jamesol@paran.com",
						"sysdate사용"),
				new User("james05", "이상무05", "4321", "2002/12/27", Level.GOLD, 55, 32, "jamesol@paran.com",
						"sysdate사용"));
	}

	@After
	public void tearDown() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ tearDown()                                              │");
		log.debug("└─────────────────────────────────────────────────────────┘");
	}


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

		// A. 전체 데이터 삭제
		// B. users 모두 데이터 입력
		// C. 등업
		// D. 등업 데이터 비교 james02(BASIC->SILVER) james04(SILVER->GOLD)

		// A.
//		for (User userVO : users) {
//			userDao.doDelete(userVO);
//		}
		
		userDao.deleteAll();
		assertEquals(0,userDao.getCount() );
		
		// B.
		for (User userVO : users) {
			userDao.doSave(userVO);
		}
		assertEquals(5,userDao.getCount());
		
		// C
		userService.upgradeLevels();
		
		// D
		checkLevel(users.get(0), Level.BASIC);
		checkLevel(users.get(1), Level.SILVER);
		checkLevel(users.get(2), Level.SILVER);
		checkLevel(users.get(3), Level.GOLD);
		checkLevel(users.get(4), Level.GOLD);

	}

	
	private void checkLevel(User userVO, Level expectedLevel) throws NullPointerException, SQLException {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ checkLevel()                             │");
		log.debug("└──────────────────────────────────────────┘");		
		User userUpdate = userDao.doSelectOne(userVO);
		assertEquals(userUpdate.getLevel(), expectedLevel);
	}
	
	

	@Test
	public void beans() {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ beans()                                  │");
		log.debug("└──────────────────────────────────────────┘");
		log.debug("context:" + context);
		log.debug("userDao:" + userDao);
		log.debug("userService:" + userService);
		assertNotNull(context);
		assertNotNull(userDao);
		assertNotNull(userService);
		// assertNull(context);
	}

}
