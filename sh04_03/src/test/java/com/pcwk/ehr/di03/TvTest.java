package com.pcwk.ehr.di03;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.pcwk.ehr.cmn.PLog;

@RunWith(SpringRunner.class) // 스프링 컨텍스트 프레임워크의 JUnit확장기능 지정
@ContextConfiguration(locations = { "classpath:/com/pcwk/ehr/di03/tv_applicationContext.xml" })
public class TvTest implements PLog {

	@Autowired
	ApplicationContext context;

	@Autowired
	SamsungTv samsungTv;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		log.debug("setUpBeforeClass:");
	}

	@Before
	public void setUp() throws Exception {
		log.debug("context:" + context);
	}

	@Test
	public void scope() {
		SamsungTv tv01 = (SamsungTv) context.getBean("tv");
		SamsungTv tv02 = (SamsungTv) context.getBean("tv");
		SamsungTv tv03 = (SamsungTv) context.getBean("tv");

		log.debug("tv01:" + tv01);
		log.debug("tv02:" + tv02);
		log.debug("tv03:" + tv03);
	}

	@Test
	public void beans() {
		log.debug("context:" + context);
		log.debug("samsungTv:" + samsungTv);
		assertNotNull(context);
		assertNotNull(samsungTv);
	}

}
