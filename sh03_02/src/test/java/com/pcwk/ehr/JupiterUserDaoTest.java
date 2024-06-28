package com.pcwk.ehr;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml" })
class JupiterUserDaoTest {

	final static Logger log = LogManager.getLogger(JupiterUserDaoTest.class);

	@Autowired
	ApplicationContext context;

	@Autowired
	UserDao dao;

	User userVO01;
	User userVO02;
	User userVO03;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		log.debug("===============");
		log.debug("=@BeforeAll=");
		log.debug("===============");
	}

	@BeforeEach
	void setUp() throws Exception {
		log.debug("===============");
		log.debug("=@BeforeEach=");
		log.debug("===============");

		log.debug("context:" + context);
		log.debug("dao:" + dao);

		userVO01 = new User("james01", "이상무01", "4321", "2002/12/31");
		userVO02 = new User("james02", "이상무02", "4321", "2002/12/30");
		userVO03 = new User("james03", "이상무03", "4321", "2002/12/29");

	}

	@Test
	void beans() {
		log.debug("-----------------------------------------------------------");
		log.debug("=beans()=");
		log.debug("-----------------------------------------------------------");
		assertNotNull(context, "ApplicationContext not null");
		assertNotNull(dao, "UserDao not null");
	}

	
	@Test
	public void getFailure() throws SQLException {
		log.debug("===============");
		log.debug("=getFailure()=");
		log.debug("===============");

		dao.deleteAll();

		int flag = dao.doSave(userVO01);
		assertEquals(1, flag);

		userVO01.setUserId("user_id없음");
		
		//NullPointerException 예외가 발생하면 성공
		assertThrows(NullPointerException.class, () -> {
			User outVO = dao.doSelectOne(userVO01);
		});
	}

	
	@Test
	@Timeout(value = 3)
	public void addAndGet() throws SQLException {
		int cnt = dao.deleteAll();
		log.debug("cnt:" + cnt);

		int count = dao.getCount();
		assertEquals(0, count);

		int flag = dao.doSave(userVO01);
		log.debug("flag:" + flag);
		assertEquals(1, flag);

		count = dao.getCount();
		log.debug("count:" + count);
		assertEquals(1, count);

		User outVO = dao.doSelectOne(userVO01);
		assertNotNull(outVO);
		isSameUser(userVO01, outVO);

		flag = dao.doSave(userVO02);
		assertEquals(1, flag);

		count = dao.getCount();
		assertEquals(2, count);

		User outVO02 = dao.doSelectOne(userVO02);
		assertNotNull(outVO02);
		isSameUser(userVO02, outVO02);

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
        assertEquals(userVO.getBirthday(), outVO.getBirthday());
    }
}
