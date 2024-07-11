package com.pcwk.ehr.cmn.aspectj;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.pcwk.ehr.cmn.PLog;

@RunWith(SpringRunner.class) // 스프링 컨텍스트 프레임워크의 JUnit확장기능 지정
@ContextConfiguration(locations = { "classpath:/com/pcwk/ehr/cmn/aspectj/afterthrowing_aspectj_context.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // @Test 메소드를 오름차순으로 정렬한 순서대로 실행
public class AfterThrowingAspectJTest implements PLog {

	@Autowired
	ApplicationContext context;

	@Autowired
	Member member;

	@Before
	public void setUp() throws Exception {
		System.out.println("┌─────────────────────────────────────────────────────────┐");
		System.out.println("│ setUp()                                                 │");
		System.out.println("└─────────────────────────────────────────────────────────┘");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("┌─────────────────────────────────────────────────────────┐");
		System.out.println("│ tearDown()                                              │");
		System.out.println("└─────────────────────────────────────────────────────────┘");
	}

	
	@Test
	public void aspectJThrowing(){
		System.out.println("┌─────────────────────────────────────────────────────────┐");
		System.out.println("│ aspectJThrowing()                                       │");
		System.out.println("└─────────────────────────────────────────────────────────┘");
		
		//member.doSave();
		//member.delete();
		try {
			member.doInsert(0);
		} catch (Exception e) {
			System.out.println("┌─────────────────────────────────────────────────────────┐");
			System.out.println("│ getMessage()                                       │"+e.getMessage());
			System.out.println("└─────────────────────────────────────────────────────────┘");
		}
		
	}

	@Ignore
	@Test
	public void beans() {
		System.out.println("┌──────────────────────────────────────────┐");
		System.out.println("│ beans()                                  │");
		System.out.println("└──────────────────────────────────────────┘");

		System.out.println("context:" + context);
		System.out.println("member:" + member);
		assertNotNull(context);
		assertNotNull(member);
	}

}
