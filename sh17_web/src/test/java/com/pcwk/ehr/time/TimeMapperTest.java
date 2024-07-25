package com.pcwk.ehr.time;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.time.dao.TimeMapper;

@RunWith(SpringRunner.class) //스프링 컨텍스트 프레임워크의 JUnit확장기능 지정
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class TimeMapperTest implements PLog {
	@Autowired
	ApplicationContext context;
	
	
	@Autowired
	TimeMapper timeMapper;
	
	@Before
	public void setUp() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ setUp()                                                 │");
		log.debug("└─────────────────────────────────────────────────────────┘");		
	}

	@After
	public void tearDown() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ tearDown()                                              │");
		log.debug("└─────────────────────────────────────────────────────────┘");
	}

	@Test
	public void getPcwkDateTime() {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ getPcwkDateTime()                        │");
		log.debug("└──────────────────────────────────────────┘");
		
		log.debug("getPcwkDateTime():"+timeMapper.getPcwkDateTime());
	}
	
	@Ignore
	@Test
	public void getDateTime() {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ getDateTime()                            │");
		log.debug("└──────────────────────────────────────────┘");
		
		log.debug("getDateTime():"+timeMapper.getDateTime());
	}
	
	@Ignore
	@Test
	public void beans() {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ beans()                                  │");
		log.debug("└──────────────────────────────────────────┘");
		log.debug("context:" + context);		
		log.debug("timeMapper:" + timeMapper);	
		
		assertNotNull(context);
		//assertNotNull(timeMapper);
		
	}

}
