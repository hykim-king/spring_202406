package com.pcwk.ehr.board.dao;

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

import com.pcwk.ehr.board.domain.Board;
import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.cmn.Search;
import com.pcwk.ehr.code.domain.Code;
import com.pcwk.ehr.mapper.BoardMapper;
import com.pcwk.ehr.mapper.CodeMapper;
import com.pcwk.ehr.mapper.UserMapper;
import com.pcwk.ehr.user.domain.User;

@RunWith(SpringRunner.class) //스프링 컨텍스트 프레임워크의 JUnit확장기능 지정
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class BoardMapperTest implements PLog {

	@Autowired
	ApplicationContext context;
	
	@Autowired
	BoardMapper boardMapper;
	
	Board  board01;
	Board  board02;
	Board  board03;
	
	Search search;
	@Before
	public void setUp() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ setUp()                                                 │");
		log.debug("└─────────────────────────────────────────────────────────┘");
	
		board01 = new Board(1, "10", "제목_01", "내용_01", 0, "ADMIN", "사용않함", "ADMIN", "사용않함");
		board02 = new Board(2, "10", "제목_02", "내용_02", 0, "ADMIN", "사용않함", "ADMIN", "사용않함");
		board03 = new Board(3, "10", "제목_03", "내용_03", 0, "ADMIN", "사용않함", "ADMIN", "사용않함");
		boardMapper.deleteAll();
		
		search = new Search();
	}

	@After
	public void tearDown() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ tearDown()                                              │");
		log.debug("└─────────────────────────────────────────────────────────┘");
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
	
	
	@Test
	public void doRetrieve() throws SQLException {
		
		boardMapper.multipleSave();
		
		search.setPageNo(1);
		search.setPageSize(10);
		
		//search.setSearchDiv("30");
		//제목0000080
		//내용0000090
		//search.setSearchWord("내용000009");
		
		List<Board> list = boardMapper.doRetrieve(search);
		assertEquals(10, list.size());		
	}
	
	//@Ignore
	@Test
	public void doUpdate() throws SQLException{
		//0.전체삭제
		//1.등록: seq identity사용 중
		//2.시퀀스 조회
		//3.단건조회
		//4.조회 데이타 수정
		//5.update
		//6.수정 데이터 조회
		//7.비교
		
		//1
		int flag = boardMapper.doSave(board01);
		log.debug("flag:"+flag);
		assertEquals(1, flag);
		
		//2
		//등록 seq조회 : 등록 당시에는 SEQ를 알지 못함!
		int seq = boardMapper.getSequence();
		log.debug("seq:"+seq);
		board01.setSeq(seq);
		
		//3
		Board outVO01 = boardMapper.doSelectOne(board01);
		log.debug("outVO01:"+outVO01);
		assertNotNull(outVO01);
		
		isSameBoard(board01,outVO01);
		
		//4
		String upStr = "_U";
		outVO01.setDiv("20");
		outVO01.setTitle(outVO01.getTitle()+upStr);
		outVO01.setContents(outVO01.getContents()+upStr);
		outVO01.setReadCnt(1);
		outVO01.setModId(outVO01.getModId()+upStr);
		
		//5
		flag = boardMapper.doUpdate(outVO01);
		log.debug("flag:"+flag);
		assertEquals(1, flag);
		
		//6
		Board outVO01Update = boardMapper.doSelectOne(outVO01);
		log.debug("outVO01Update:"+outVO01Update);
		assertNotNull(outVO01Update);
		
		//7
		isSameBoard(outVO01Update,outVO01);
	}
	
	//@Ignore
	@Test
	public void addAndGet() throws SQLException {
		//전체 삭제: setUp()
		
		
		
		//등록
		int flag = boardMapper.doSave(board01);
		log.debug("flag:"+flag);
		assertEquals(1, flag);
		
		//등록 seq조회 : 등록 당시에는 SEQ를 알지 못함!
		int seq = boardMapper.getSequence();
		log.debug("seq:"+seq);
		board01.setSeq(seq);
		
		//단건조회
		Board outVO01 = boardMapper.doSelectOne(board01);
		log.debug("outVO01:"+outVO01);
		assertNotNull(outVO01);
		
		isSameBoard(board01,outVO01);
		
		//
		flag = boardMapper.doSave(board02);
		log.debug("flag:"+flag);
		assertEquals(1, flag);
		
		seq = boardMapper.getSequence();
		log.debug("seq:"+seq);
		board02.setSeq(seq);
				
		
		//단건조회
		Board outVO02 = boardMapper.doSelectOne(board02);
		log.debug("outVO02:"+outVO02);
		assertNotNull(outVO02);
		
		isSameBoard(board02,outVO02);		
		
		flag = boardMapper.doSave(board03);
		log.debug("flag:"+flag);
		assertEquals(1, flag);
		
		seq = boardMapper.getSequence();
		log.debug("seq:"+seq);
		board03.setSeq(seq);		
	
		//단건조회
		Board outVO03 = boardMapper.doSelectOne(board03);
		log.debug("outVO03:"+outVO03);
		assertNotNull(outVO03);
		
		isSameBoard(board03,outVO03);	
		
		//단건 삭제
		flag = boardMapper.doDelete(outVO01);
		assertEquals(1, flag);	
	
	}
	
	
	
	
	@Ignore
	@Test
	public void beans() {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ beans()                                  │");
		log.debug("└──────────────────────────────────────────┘");
		log.debug("context:" + context);
		log.debug("boardMapper:" + boardMapper);
		
		assertNotNull(context);
		assertNotNull(boardMapper);
	}

}
