package com.pcwk.ehr.naver.service;

import static org.junit.Assert.*;

import java.io.IOException;

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
import com.pcwk.ehr.cmn.Search;

@RunWith(SpringRunner.class) // 스프링 컨텍스트 프레임워크의 JUnit확장기능 지정
@ContextConfiguration(locations = { "classpath:/applicationContext.xml" })
public class NaverServiceImplTest implements PLog {
	@Autowired
	ApplicationContext context;
	
	@Autowired
	NaverService naverService;
	
	Search search;
	
	@Before
	public void setUp() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ setUp()                                                 │");
		log.debug("└─────────────────────────────────────────────────────────┘");
		
		search = new Search();
	}

	@After
	public void tearDown() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ tearDown()                                              │");
		log.debug("└─────────────────────────────────────────────────────────┘");
		
	}

	@Test
	public void doNaverSearch() throws IOException {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ doNaverSearch()                          │");
		log.debug("└──────────────────────────────────────────┘");		
		search.setSearchWord("홍대 에이콘");
		
		String jsonString = naverService.doNaverSearch(search);
		log.debug("jsonString                         │"+jsonString);
	}
	
	@Ignore
	@Test
	public void beans() {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ beans()                                  │");
		log.debug("└──────────────────────────────────────────┘");
		log.debug("context:" + context);
		log.debug("naverService:" + naverService);
		
		assertNotNull(context);
		assertNotNull(naverService);
	}

}
