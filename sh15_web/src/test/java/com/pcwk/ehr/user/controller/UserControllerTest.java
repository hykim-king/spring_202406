package com.pcwk.ehr.user.controller;

import static com.pcwk.ehr.user.service.UserServiceImpl.MIN_LOGINCOUNT_FOR_SILVER;
import static com.pcwk.ehr.user.service.UserServiceImpl.MIN_RECOMMEND_FOR_GOLD;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.pcwk.ehr.cmn.Message;
import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.mapper.UserMapper;
import com.pcwk.ehr.user.domain.Level;
import com.pcwk.ehr.user.domain.User;


@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class) // 스프링 컨텍스트 프레임워크의 JUnit확장기능 지정
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 메소드 수행 순서: method ASCENDING ex)a~z
public class UserControllerTest implements PLog {

	@Autowired
	WebApplicationContext webApplicationContext;
	
	//브라우저 대신 Mock
	MockMvc mockMvc;
	
	List<User> users;
	
	@Autowired
	UserMapper userMapper;//
	
	
	@Before
	public void setUp() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ setUp()                                                 │");
		log.debug("└─────────────────────────────────────────────────────────┘");
		// 기존 데이터 삭제
		//userMapper.deleteAll();
		
		
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
		
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
				
	}

	@After
	public void tearDown() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ tearDown()                                              │");
		log.debug("└─────────────────────────────────────────────────────────┘");		
	}
	
	@Test
	public void doSelectOne() throws Exception{
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ *doSelectOne()*                          │");
		log.debug("└──────────────────────────────────────────┘");	
		
		User newUser01=users.get(0);
		
		//1. url, param 설정
		MockHttpServletRequestBuilder requestBuilder
			= MockMvcRequestBuilders.get("/user/doSelectOne.do")
					.param("userId", newUser01.getUserId())
		;
		
		//2. 호출
		//호출 및 결과 
		ResultActions resultActions = mockMvc.perform(requestBuilder)
		//return encoding		
		.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
		//Web상태
		.andExpect(status().is2xxSuccessful());
		
		
		String jsonResult=resultActions.andDo(print())
				.andReturn()
				.getResponse().getContentAsString();
				;
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ jsonResult:"+jsonResult);      
		log.debug("└──────────────────────────────────────────┘");	
		
		
		Message resultMessage=new Gson().fromJson(jsonResult, Message.class);
		assertEquals(1, resultMessage.getMessagId());
		assertEquals(newUser01.getUserId()+"님이 조회 되었습니다.",resultMessage.getMessageContents());
	}
	
	
	@Ignore
	@Test
	public void doSave() throws Exception{
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ *doSave()*                               │");
		log.debug("└──────────────────────────────────────────┘");			
		//1. url호출, param전달
		//2. return
		//3. 비교
		
		User newUser01=users.get(0);
		//1
		MockHttpServletRequestBuilder requestBuilder
					= MockMvcRequestBuilders.post("/user/doSave.do")
					.param("userId", newUser01.getUserId())
					.param("name", newUser01.getName())
					.param("password", newUser01.getPassword())
					.param("birthday", newUser01.getBirthday())
					.param("level", newUser01.getLevel()+"")
					.param("login", newUser01.getLogin()+"")
					.param("recommend", newUser01.getRecommend()+"")
					.param("email", newUser01.getEmail())
					;
		//호출 및 결과 
		ResultActions resultActions = mockMvc.perform(requestBuilder)
				//Controller produces =  "text/plain;charset=UTF-8"
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
				//Web상태
				.andExpect(status().is2xxSuccessful());
		
		
		//Mock 로그: print()
		//json문자열 
		String jsonResult=resultActions.andDo(print())
							.andReturn()
							.getResponse().getContentAsString();
							;
							
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ jsonResult:"+jsonResult);
		log.debug("└──────────────────────────────────────────┘");							
		//json 문자열을 Message로 변환
		Message resultMessage=new Gson().fromJson(jsonResult, Message.class);
		
		//비교
		assertEquals(1, resultMessage.getMessagId());
		assertEquals(newUser01.getUserId()+" 님이 등록되 었습니다.",resultMessage.getMessageContents());
	}

	@Ignore
	@Test
	public void beans() {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ beans()                                  │");
		log.debug("└──────────────────────────────────────────┘");	
		
		log.debug("webApplicationContext:"+webApplicationContext);
		assertNotNull(webApplicationContext);
	}

}
