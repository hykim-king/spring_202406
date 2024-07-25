package com.pcwk.ehr.board.controller;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.SQLException;
import java.util.List;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.pcwk.ehr.board.domain.Board;
import com.pcwk.ehr.cmn.Message;
import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.cmn.Search;
import com.pcwk.ehr.mapper.BoardMapper;
import com.pcwk.ehr.user.domain.User;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class) // 스프링 컨텍스트 프레임워크의 JUnit확장기능 지정
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 메소드 수행 순서: method ASCENDING ex)a~z
public class BoardControllerTest implements PLog {
	@Autowired
	WebApplicationContext webApplicationContext;
	
	//브라우저 대신 Mock
	MockMvc mockMvc;
	
	@Autowired
	BoardMapper boardMapper;
	
	Board  board01;
	Board  board02;
	Board  board03;
	Search search;
	
    static class Response {
        Board board;
        Message message;
    }
    
	@Before
	public void setUp() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ setUp()                                                 │");
		log.debug("└─────────────────────────────────────────────────────────┘");
		// 기존 데이터 삭제	
		boardMapper.deleteAll();
		
		board01 = new Board(1, "10", "제목_01", "내용_01", 0, "ADMIN", "사용않함", "ADMIN", "사용않함");
		board02 = new Board(2, "10", "제목_02", "내용_02", 0, "ADMIN", "사용않함", "ADMIN", "사용않함");
		board03 = new Board(3, "10", "제목_03", "내용_03", 0, "ADMIN", "사용않함", "ADMIN", "사용않함");		
		
		search = new Search();
		
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@After
	public void tearDown() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ tearDown()                                              │");
		log.debug("└─────────────────────────────────────────────────────────┘");		
	}
	
	
	@Test
	public void doUpdate() throws Exception{
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ *doUpdate()*                             │");
		log.debug("└──────────────────────────────────────────┘");			
	
		//1
		int flag = boardMapper.doSave(board01);
		log.debug("flag:"+flag);
		assertEquals(1, flag);
		
		//2
		//등록 seq조회 : 등록 당시에는 SEQ를 알지 못함!
		int seq = boardMapper.getSequence();
		log.debug("seq:"+seq);
		board01.setSeq(seq);
		
		//3 조회
		Board selectOneVO=boardMapper.doSelectOne(board01);
		MockHttpServletRequestBuilder requestBuilder
								= MockMvcRequestBuilders.post("/board/doUpdate.do")
								.param("seq", selectOneVO.getSeq()+"")
								.param("div", selectOneVO.getDiv())
								.param("title", selectOneVO.getTitle()+"_U")
								.param("contents", selectOneVO.getContents()+"_U")
								.param("modId", selectOneVO.getModId())
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
		assertEquals(board01.getTitle()+" 이 수정 되었습니다.",resultMessage.getMessageContents());
	}
	
	@Ignore
	@Test
	public void doRetrieve() throws Exception{
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ *doRetrieve()*                           │");
		log.debug("└──────────────────────────────────────────┘");		
		
		//다건 등록
		boardMapper.multipleSave();
		
		search.setPageNo(1);
		search.setPageSize(10);
		
		MockHttpServletRequestBuilder requestBuilder
						= MockMvcRequestBuilders.get("/board/doRetrieve.do")
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
		
		List<Board> list = (List<Board>) modelMap.get("list");
		for(Board vo   :list) {
			log.debug(vo);
		}
		
		int totalCnt =(Integer)modelMap.get("totalCnt");
		String viewName = mvcResult.getModelAndView().getViewName();
		
		// user/user_list
		assertEquals(101, totalCnt);
		assertEquals("board/board_list", viewName);
	}
	
	
	@Ignore
	@Test
	public void doDelete() throws Exception{
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ *doDelete()*                             │");
		log.debug("└──────────────────────────────────────────┘");
		
		//1
		int flag = boardMapper.doSave(board01);
		log.debug("flag:"+flag);
		assertEquals(1, flag);
		
		//2
		//등록 seq조회 : 등록 당시에는 SEQ를 알지 못함!
		int seq = boardMapper.getSequence();
		log.debug("seq:"+seq);
		board01.setSeq(seq);
		
		//3. 삭제
		MockHttpServletRequestBuilder requestBuilder
		= MockMvcRequestBuilders.get("/board/doDelete.do")
		.param("seq", board01.getSeq()+"")
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
		assertEquals(board01.getSeq()+" 이 삭제 었습니다.",resultMessage.getMessageContents());
		
	}	
	
	
	@Ignore	
	@Test
	public void doSelectOne() throws Exception{
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ *doSelectOne()*                          │");
		log.debug("└──────────────────────────────────────────┘");			
		
		//1
		int flag = boardMapper.doSave(board01);
		log.debug("flag:"+flag);
		assertEquals(1, flag);
		
		//2
		//등록 seq조회 : 등록 당시에는 SEQ를 알지 못함!
		int seq = boardMapper.getSequence();
		log.debug("seq:"+seq);
		board01.setSeq(seq);
		
		MockHttpServletRequestBuilder requestBuilder
		= MockMvcRequestBuilders.get("/board/doSelectOne.do")
		.param("seq", board01.getSeq()+"")
		.param("regId", board01.getRegId())
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
        // Gson 객체 생성
        Gson gson = new Gson();
        
        // 전체 JSON 파싱
        Response response = gson.fromJson(jsonResult, Response.class);

        // message 객체 추출
        Message message = response.message;

        // Board 객체 추출
        Board board = response.board;        
        // message 객체 출력
        log.debug("Message : " + message);	
        log.debug("Board : " + board);	
        
        assertEquals(1, message.getMessagId());
        assertEquals(board.getTitle()+" 이 조회 었습니다.", message.getMessageContents());
        
        isSameBoard(board,board01);
	}
	
	public void isSameBoard(Board boardIn, Board boardOut) {
		assertEquals(boardIn.getSeq(), boardOut.getSeq());
		assertEquals(boardIn.getDiv(), boardOut.getDiv());
		assertEquals(boardIn.getTitle(), boardOut.getTitle());
		assertEquals(boardIn.getContents(), boardOut.getContents());
		assertEquals(boardIn.getReadCnt(), boardOut.getReadCnt());
		assertEquals(boardIn.getRegId(), boardOut.getRegId());
		assertEquals(boardIn.getModId(), boardOut.getModId());
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
		MockHttpServletRequestBuilder requestBuilder
								= MockMvcRequestBuilders.post("/board/doSave.do")
								.param("div", board01.getDiv())
								.param("title", board01.getTitle())
								.param("contents", board01.getContents())
								.param("regId", board01.getRegId())
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
		assertEquals(board01.getTitle()+" 이 등록되 었습니다.",resultMessage.getMessageContents());
	}

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
