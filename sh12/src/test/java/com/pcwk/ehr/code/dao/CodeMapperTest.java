package com.pcwk.ehr.code.dao;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import com.pcwk.ehr.code.domain.Code;
import com.pcwk.ehr.mapper.CodeMapper;
import com.pcwk.ehr.mapper.UserMapper;

@RunWith(SpringRunner.class) //스프링 컨텍스트 프레임워크의 JUnit확장기능 지정
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class CodeMapperTest implements PLog {

	@Autowired
	ApplicationContext context;
	
	
	@Autowired
	CodeMapper codeMapper;
	
	
	Code  code;
	ArrayList<String>   list;
	@Ignore
	@Test
	public void doRetrieveIn() throws SQLException {

		List<Code> codeList = codeMapper.doRetrieveIn(list);
		for(Code vo  :codeList) {
			log.debug(vo);
		}
		assertEquals(8, codeList.size());
	}
	
	@Before
	public void setUp() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ setUp()                                                 │");
		log.debug("└─────────────────────────────────────────────────────────┘");
	
		code = new Code();
		list = new ArrayList<String>();
		list.add("MEMBER_SEARCH");
		list.add("COM_PAGE_SIZE");
	}

	@After
	public void tearDown() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ tearDown()                                              │");
		log.debug("└─────────────────────────────────────────────────────────┘");
	}
	//@Ignore
	@Test
	public void doRetrieve() throws SQLException {
		code.setMstCode("MEMBER_SEARCH");
		List<Code> codeList = codeMapper.doRetrieve(code);
		for(Code vo  :codeList) {
			log.debug(vo);
		}
		assertEquals(3, codeList.size());
	}
	
	@Ignore
	@Test
	public void beans() {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ beans()                                  │");
		log.debug("└──────────────────────────────────────────┘");
		log.debug("context:" + context);
		log.debug("codeMapper:" + codeMapper);
		
		assertNotNull(context);
		assertNotNull(codeMapper);
	}

}
