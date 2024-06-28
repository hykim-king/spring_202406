package com.pcwk.ehr;

import static org.junit.Assert.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class JLifeCycle {
	
	final static Logger  log = LogManager.getLogger(JLifeCycle.class); 
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		log.debug("=============");
		log.debug("=@BeforeClass=");
		log.debug("=============");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		log.debug("=============");
		log.debug("=@AfterClass=");
		log.debug("=============");		
	}

	@Before
	public void setUp() throws Exception {
		log.debug("***************");
		log.debug("=@Before=");
		log.debug("***************");
	}

	@After
	public void tearDown() throws Exception {
		log.debug("***************");
		log.debug("=@After=");
		log.debug("***************");		
	}

	@Test
	public void test() {
		log.debug("***************");
		log.debug("=test()=");
		log.debug("***************");			
	}
	
	@Test
	public void test2() {
		log.debug("***************");
		log.debug("=test2()=");
		log.debug("***************");			
	}	

}
