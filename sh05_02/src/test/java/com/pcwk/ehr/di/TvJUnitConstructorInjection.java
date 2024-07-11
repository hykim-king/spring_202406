package com.pcwk.ehr.di;

import static org.junit.Assert.*;

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

@RunWith(SpringRunner.class) //스프링 컨텍스트 프레임워크의 JUnit확장기능 지정
@ContextConfiguration(locations = {"classpath:/com/pcwk/ehr/di/constructor_applicationContext.xml"})
public class TvJUnitConstructorInjection implements PLog {

	@Autowired
	ApplicationContext context;
	
	Tv tv01;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		log.debug("^^^setUpBeforeClass:");
	}

	@Before
	public void setUp() throws Exception {
		tv01 = (LGTv)context.getBean("lgTv");
	}

	@Ignore
	@Test
	public void beans() {
		log.debug("context:"+context);
		log.debug("tv01:"+tv01);
		assertNotNull(context);
		assertNotNull(tv01);
	}
	
	
	@Test
	public void tvTest() {
		log.debug("====================");
		log.debug("=tvTest()=");
		log.debug("====================");
		
		tv01.turnOn();
		tv01.playSound();
		tv01.turnOff();
	}
}


