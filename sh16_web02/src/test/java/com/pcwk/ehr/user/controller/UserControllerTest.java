package com.pcwk.ehr.user.controller;

import static com.pcwk.ehr.user.service.UserServiceImpl.MIN_LOGINCOUNT_FOR_SILVER;
import static com.pcwk.ehr.user.service.UserServiceImpl.MIN_RECOMMEND_FOR_GOLD;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.pcwk.ehr.cmn.Message;
import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.cmn.Search;
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
	
	Search search;
	
	
	@Before
	public void setUp() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ setUp()                                                 │");
		log.debug("└─────────────────────────────────────────────────────────┘");
		// 기존 데이터 삭제
		userMapper.deleteAll();
		
		search = new Search();
		
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
	
	@Ignore
	@Test
	public void doDelete() throws Exception {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ *doDelete()*                             │");
		log.debug("└──────────────────────────────────────────┘");
		
		//한건 등록
		User newUser01=users.get(0);
		

		int flag = userMapper.doSave(newUser01);
		assertEquals(1, flag);
		
		
		//1. url, param 설정
		//get/post -> control method = RequestMethod.GET/RequestMethod.POST
		MockHttpServletRequestBuilder requestBuilder
			= MockMvcRequestBuilders.get("/user/doDelete.do")
					.param("userId", newUser01.getUserId())
		;		
		
		//2. 호출
		//호출 및 결과 
		ResultActions resultActions = mockMvc.perform(requestBuilder)
		//return encoding		
		.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
		//Web상태
		.andExpect(status().is2xxSuccessful());				
		
		//3. JSON -> Message
		String jsonResult = resultActions.andDo(print())
							.andReturn()
							.getResponse().getContentAsString();
		
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ jsonResult:"+jsonResult);      
		log.debug("└──────────────────────────────────────────┘");	
		
		Message resultMessage = new Gson().fromJson(jsonResult, Message.class);
		assertEquals(1, resultMessage.getMessagId());
		assertEquals(newUser01.getUserId()+"님이 삭제 되었습니다.",resultMessage.getMessageContents());
		
	}
	
	@Test
	public void  doRetrieve() throws Exception{
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ *doRetrieve()*                           │");
		log.debug("└──────────────────────────────────────────┘");		
		
		//다건 데이터 입력 
		userMapper.multipleSave();
		
		//james_000000
		//search.setSearchDiv("30");
		//search.setSearchWord("jamesol000001");
		
		search.setPageNo(2);
		//search.setPageSize(20);
		//1. url, param 설정
		MockHttpServletRequestBuilder requestBuilder
			= MockMvcRequestBuilders.get("/user/doRetrieve.do")
					.param("searchDiv", search.getSearchDiv())
					.param("searchWord", search.getSearchWord())
					.param("pageSize", search.getPageSize()+"")
					.param("pageNo", search.getPageNo()+"")
					
					;		
		
		//2. 호출
		//호출 및 결과 
		ResultActions resultActions = mockMvc.perform(requestBuilder)
				.andExpect(status().is2xxSuccessful());
		//Model
		MvcResult mvcResult = resultActions.andDo(print()).andReturn();
		
		
		Map<String, Object> modelMap = mvcResult.getModelAndView().getModel();
		
		List<User> list = (List<User>) modelMap.get("list");
		
		for(User vo   :list) {
			log.debug(vo);
		}
		
		String viewName = mvcResult.getModelAndView().getViewName();
		
		// user/user_list
		assertEquals("user/user_list", viewName);
		
	}
	
	@Ignore
	@Test
	public void doUpdate() throws Exception {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ *doUpdate()*                             │");
		log.debug("└──────────────────────────────────────────┘");
		
		//1. 단건 등록
		//2. 단건 조회
		//2.1 비교
		//3. 조회 데이터 수정
		//4. 수정+controller호출
		//5. 단건 조회
		//6. 비교
		
		//1.
		User newUser01=users.get(0);
		
		int flag = userMapper.doSave(newUser01);
		assertEquals(1, flag);	
		
		//2.
		User outVO = userMapper.doSelectOne(newUser01);
		
		//2.1
		isSameUser(newUser01,outVO);
		
		//4
		String updateStr = "_U";
		//이름,비번,생일,등급,로그인, 추천, 이메일
		outVO.setName(outVO.getName()+updateStr);
		outVO.setPassword(outVO.getPassword()+updateStr);
		outVO.setBirthday(outVO.getBirthday());
		outVO.setLevel(Level.SILVER);
		outVO.setLogin(outVO.getLogin()+10);
		outVO.setRecommend(outVO.getRecommend()+20);
		outVO.setEmail(outVO.getEmail()+updateStr);		
		
		
		//----------------------------------------------------------------------
		MockHttpServletRequestBuilder requestBuilder
		= MockMvcRequestBuilders.post("/user/doUpdate.do")
		.param("userId", outVO.getUserId())
		.param("name", outVO.getName())
		.param("password", outVO.getPassword())
		.param("birthday", outVO.getBirthday())
		.param("level", outVO.getLevel()+"")
		.param("login", outVO.getLogin()+"")
		.param("recommend", outVO.getRecommend()+"")
		.param("email", outVO.getEmail())
		;
		
		
		//호출 및 결과 
		ResultActions resultActions = mockMvc.perform(requestBuilder)
				//Controller produces =  "text/plain;charset=UTF-8"
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
		//----------------------------------------------------------------------
		
		//5.
		User updateUser = userMapper.doSelectOne(outVO);
		
		//6. 
		isSameUser(outVO,updateUser);
	}
	
	public void isSameUser(User userVO, User outVO) {
		assertEquals(userVO.getUserId(), outVO.getUserId());
		assertEquals(userVO.getName(), outVO.getName());
		assertEquals(userVO.getPassword(), outVO.getPassword());
		assertEquals(userVO.getBirthday(),outVO.getBirthday());	
		
		assertEquals(userVO.getLevel(), outVO.getLevel());
		assertEquals(userVO.getLogin(), outVO.getLogin());
		assertEquals(userVO.getRecommend(), outVO.getRecommend());
		assertEquals(userVO.getEmail(),outVO.getEmail());
		
	}
	
	
	@Ignore
	@Test
	public void doSelectOne() throws Exception{
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ *doSelectOne()*                          │");
		log.debug("└──────────────────────────────────────────┘");	
		
		User newUser01=users.get(0);
		
		//한건 등록
		int flag = userMapper.doSave(newUser01);
		assertEquals(1, flag);		
		
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
