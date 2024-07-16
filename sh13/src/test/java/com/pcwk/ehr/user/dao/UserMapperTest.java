package com.pcwk.ehr.user.dao;

import static org.junit.Assert.*;

import java.sql.SQLException;
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
import com.pcwk.ehr.cmn.Search;
import com.pcwk.ehr.mapper.UserMapper;
import com.pcwk.ehr.user.domain.Level;
import com.pcwk.ehr.user.domain.User;

@RunWith(SpringRunner.class) //스프링 컨텍스트 프레임워크의 JUnit확장기능 지정
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class UserMapperTest implements PLog {

	@Autowired
	ApplicationContext context;
	
	@Autowired
	UserMapper userMapper;
	
	
	User    userVO01;
	User    userVO02;
	User    userVO03;
	
	Search  search;
	
	
	@Before
	public void setUp() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ setUp()                                                 │");
		log.debug("└─────────────────────────────────────────────────────────┘");
		
		//데이터 삭제
		userMapper.deleteAll();
		
		userVO01= new User("james01","이상무01","4321","2002/12/31",Level.BASIC,1,0,"jamesol@paran.com","sysdate사용");
		userVO02= new User("james02","이상무02","4321","2002/12/30",Level.SILVER,50,2,"jamesol@paran.com","sysdate사용");
		userVO03= new User("james03","이상무03","4321","2002/12/29",Level.GOLD,100,31,"jamesol@paran.com","sysdate사용");
		
		search = new Search();		
	}

	@Test
	public void doUpdate() throws SQLException{
		//2. 데이터 1건 입력
		//3. 단건 조회
		//4. 조회 데이터로 데이터 수정및 Update
		//5. 수정데이터 조회
		//6. 비교
		
		//1
		userMapper.deleteAll();
		 
		int count = userMapper.getCount();
		assertEquals(0,count);		
		
		//2 등록
		int flag = userMapper.doSave(userVO01);
		assertEquals(1, flag);
		assertEquals(userMapper.getCount(), 1);		
		
		//3
		User outVO = userMapper.doSelectOne(userVO01);
		assertNotNull(outVO);//return User Null check
		isSameUser(userVO01, outVO);
		
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
		
		//5
		flag = userMapper.doUpdate(outVO);
		assertEquals(1, flag);
		 
		User upOutVO = userMapper.doSelectOne(outVO);
		
		//6
		isSameUser(upOutVO, outVO);
		
	}	
	
	@Ignore
	@Test
	public void doRetrieve() throws SQLException {
		userMapper.deleteAll();		
		//다건 입력
		userMapper.multipleSave();		
//		//건수 조회
		int count = userMapper.getCount();
		assertEquals(101,count);		
		
		search.setPageNo(1);
		search.setPageSize(10);
		//james_0000079
		search.setSearchDiv("10");
		search.setSearchWord("james");
		
		List<User> list = userMapper.doRetrieve(search);
		assertEquals(10, list.size());
	}	
	
	//@Ignore
	@Test
	public void addAndGet() throws SQLException {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ addAndGet()                              │");
		log.debug("└──────────────────────────────────────────┘");	

		//1. 등록
		//2. 한건조회
		//3. 비교		
		
		// 1건 등록
		int flag = userMapper.doSave(userVO01);
		log.debug("flag:"+flag);
		assertEquals(1, flag);		
		
		int count = userMapper.getCount();
		log.debug("count:"+count);
		assertEquals(1, count);		
		
		User outVO = userMapper.doSelectOne(userVO01);
		assertNotNull(outVO);//return User Null check
		isSameUser(userVO01, outVO);		
		
		//2건 등록
		flag = userMapper.doSave(userVO02);
		assertEquals(1, flag);
		
		count = userMapper.getCount();
		assertEquals(2, count);
		
		User outVO02 = userMapper.doSelectOne(userVO02);
		assertNotNull(outVO02);
		isSameUser(userVO02, outVO02);		
		
		//3건 등록
		flag = userMapper.doSave(userVO03);
		assertEquals(1, flag);

		count = userMapper.getCount();
		assertEquals(3, count);		
		
		User outVO03 = userMapper.doSelectOne(userVO03);
		assertNotNull(outVO03);
		isSameUser(userVO03, outVO03);
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
	
	@After
	public void tearDown() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ tearDown()                                              │");
		log.debug("└─────────────────────────────────────────────────────────┘");		
	}

	@Ignore
	@Test
	public void beans() {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ beans()                                  │");
		log.debug("└──────────────────────────────────────────┘");
		log.debug("context:" + context);
		log.debug("userMapper:" + userMapper);
		
		assertNotNull(context);
		assertNotNull(userMapper);
	}

}
