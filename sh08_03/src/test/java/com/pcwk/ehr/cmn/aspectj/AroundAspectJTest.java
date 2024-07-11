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
@ContextConfiguration(locations = { "classpath:/com/pcwk/ehr/cmn/aspectj/around_aspectj_context.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)//@Test 메소드를 오름차순으로 정렬한 순서대로 실행
public class AroundAspectJTest implements PLog {

	@Autowired
	ApplicationContext context;
	
	@Autowired
	Member member;
	
	@Before
	public void setUp() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ setUp()                                                 │");
		log.debug("└─────────────────────────────────────────────────────────┘");		
	}

	@After
	public void tearDown() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ tearDown()                                              │");
		log.debug("└─────────────────────────────────────────────────────────┘");		
	}

	@Test
	public void afterAspectJ() {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ afterAspectJ()                           │");
		log.debug("└──────────────────────────────────────────┘");
		
		//member.doSave();
		member.delete();
		member.doUpdate();
	}
	
	@Ignore
	@Test
	public void beans() {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ beans()                                  │");
		log.debug("└──────────────────────────────────────────┘");
		
		log.debug("context:"+context);
		log.debug("member:"+member);
		assertNotNull(context);
		assertNotNull(member);
	}

}
//(AroundAspectJTest.java:32) - ┌─────────────────────────────────────────────────────────┐
//(AroundAspectJTest.java:33) - │ setUp()                                                 │
//(AroundAspectJTest.java:34) - └─────────────────────────────────────────────────────────┘
//(DefaultCacheAwareContextLoaderDelegate.java:103) - Retrieved ApplicationContext [2029680286] from cache with key [[MergedContextConfiguration@700fb871 testClass = AroundAspectJTest, locations = '{classpath:/com/pcwk/ehr/cmn/aspectj/around_aspectj_context.xml}', classes = '{}', contextInitializerClasses = '[]', activeProfiles = '{}', propertySourceLocations = '{}', propertySourceProperties = '{}', contextCustomizers = set[[empty]], contextLoader = 'org.springframework.test.context.support.DelegatingSmartContextLoader', parent = [null]]]
//(DefaultContextCache.java:290) - Spring test ApplicationContext cache statistics: [DefaultContextCache@40f70521 size = 1, maxSize = 32, parentContextCount = 0, hitCount = 3, missCount = 1]
//(AroundAspectJTest.java:46) - ┌──────────────────────────────────────────┐
//(AroundAspectJTest.java:47) - │ afterAspectJ()                           │
//(AroundAspectJTest.java:48) - └──────────────────────────────────────────┘
//(MemberImpl.java:25) - ┌++++++++++++++++++┐
//(MemberImpl.java:26) - │ delete()         │
//(MemberImpl.java:27) - └++++++++++++++++++┘
//(AroundAdvice.java:12) - ┌──────────────────────────────────────────┐
//(AroundAdvice.java:13) - │ aroundLog()                              │
//(MemberImpl.java:17) - ┌++++++++++++++++++┐
//(MemberImpl.java:18) - │ doUpdate()       │
//(MemberImpl.java:19) - └++++++++++++++++++┘
//(AroundAdvice.java:24) - │ className                                │com.pcwk.ehr.cmn.aspectj.MemberImpl
//(AroundAdvice.java:25) - │ method                                   │doUpdate
//(AroundAdvice.java:26) - └──────────────────────────────────────────┘
//(DefaultCacheAwareContextLoaderDelegate.java:103) - Retrieved ApplicationContext [2029680286] from cache with key [[MergedContextConfiguration@700fb871 testClass = AroundAspectJTest, locations = '{classpath:/com/pcwk/ehr/cmn/aspectj/around_aspectj_context.xml}', classes = '{}', contextInitializerClasses = '[]', activeProfiles = '{}', propertySourceLocations = '{}', propertySourceProperties = '{}', contextCustomizers = set[[empty]], contextLoader = 'org.springframework.test.context.support.DelegatingSmartContextLoader', parent = [null]]]
//(DefaultContextCache.java:290) - Spring test ApplicationContext cache statistics: [DefaultContextCache@40f70521 size = 1, maxSize = 32, parentContextCount = 0, hitCount = 4, missCount = 1]
//(AroundAspectJTest.java:39) - ┌─────────────────────────────────────────────────────────┐
//(AroundAspectJTest.java:40) - │ tearDown()                                              │
//(AroundAspectJTest.java:41) - └─────────────────────────────────────────────────────────┘