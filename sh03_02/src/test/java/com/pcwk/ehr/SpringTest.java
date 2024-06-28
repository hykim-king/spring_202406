package com.pcwk.ehr;

import static org.junit.Assert.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class SpringTest {
	
	final static Logger  log = LogManager.getLogger(SpringTest.class);

	@Autowired //테스트 오브젝트가 만들어지고 나면 스프링 테스트 컨텍스트에 의해 자동으로 주입된다.
	ApplicationContext  context;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		log.debug("=========================");
		log.debug("=@BeforeClass=");
		log.debug("=========================");
	}

	@Before
	public void setUp() throws Exception {
		log.debug("=========================");
		log.debug("=@Before=");
		log.debug("=========================");		
	}

	@Test
	public void contextTest() {
		log.debug("=========================");
		log.debug("=1 contextTest()=");
		log.debug("=========================");		
		
		log.debug("context:"+context);
		assertNotNull("ApplicationContext not null", context);		
	}
	
	@Test
	public void beans() {
		log.debug("=========================");
		log.debug("=2beans()=");
		log.debug("=========================");
		
		log.debug("context:"+context);
		assertNotNull("ApplicationContext not null", context);
	}

}
