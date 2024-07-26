package com.pcwk.ehr.sync.controller;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.pcwk.ehr.cmn.PLog;


@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class) // 스프링 컨텍스트 프레임워크의 JUnit확장기능 지정
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 메소드 수행 순서: method ASCENDING ex)a~z
public class SyncControllerTest implements PLog {

	@Autowired
	WebApplicationContext webApplicationContext;
	
	//브라우저 대신 Mock
	MockMvc mockMvc;
	
	
	@Before
	public void setUp() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ setUp()                                                 │");
		log.debug("└─────────────────────────────────────────────────────────┘");
				
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@After
	public void tearDown() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ tearDown()                                              │");
		log.debug("└─────────────────────────────────────────────────────────┘");		
	}

	@Test
	public void syncResult() throws Exception {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ syncResult()                             │");
		log.debug("└──────────────────────────────────────────┘");	
		
		//1. url호출, param set
		MockHttpServletRequestBuilder requestBuilder
		   = MockMvcRequestBuilders.get("/sync/sync_result.do")
		     .param("name", "홍길동")
		   ;		
		
		//2. 호출
		ResultActions resultActions =mockMvc.perform(requestBuilder)
		                               .andExpect(status().is2xxSuccessful());
		
		//3. ModelAndView
		MvcResult mvcResult = resultActions.andDo(print()).andReturn();
		
		//3.1. model
		Map<String, Object> modelMap = mvcResult.getModelAndView().getModel();
		String message = (String) modelMap.get("message");
		
		//3.2 view
		String viewName = mvcResult.getModelAndView().getViewName();
		
		assertEquals(" Hi 홍길동",message);
		assertEquals("sync/sync_result",viewName);
	}
	
	@Ignore
	@Test
	public void handlerSyncIndex() throws Exception {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ handlerSyncIndex()                       │");
		log.debug("└──────────────────────────────────────────┘");
		
		//1. url호출, param set
		MockHttpServletRequestBuilder requestBuilder
		   = MockMvcRequestBuilders.get("/sync/sync_index.do");
		
		
		//2. 호출
		ResultActions resultActions =mockMvc.perform(requestBuilder)
				.andExpect(status().is2xxSuccessful())
				;
		
		//3. ModelAndView
		MvcResult mvcResult = resultActions.andDo(print()).andReturn();
		
		String viewName = mvcResult.getModelAndView().getViewName();
		log.debug("viewName: "+viewName);
		assertEquals("sync/sync_index",viewName);
		
	}
	
	@Ignore
	@Test
	public void beans() {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ beans()                                  │");
		log.debug("└──────────────────────────────────────────┘");	
		
		log.debug("webApplicationContext:"+webApplicationContext);
		log.debug("mockMvc:"+mockMvc);
		
		assertNotNull(webApplicationContext);
		assertNotNull(mockMvc);		
	}

}
