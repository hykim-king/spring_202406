package com.pcwk.ehr;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) //Instance는 한 개씩만 생성 
class JupiterTest {

	final static Logger log = LogManager.getLogger(SpringTest.class);

	@BeforeAll
	public void beforeAllTests() {
		log.debug("=========================");
		log.debug("=@BeforeAll=");
		log.debug("=========================");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		log.debug("=========================");
		log.debug("=@AfterAll=");
		log.debug("=========================");
	}

	@BeforeEach
	void setUp() throws Exception {
		log.debug("=========================");
		log.debug("=@BeforeEach=");
		log.debug("=========================");
	}

	@AfterEach
	void tearDown() throws Exception {
		log.debug("=========================");
		log.debug("=@AfterEach=");
		log.debug("=========================");
	}

	@Test
	void test() {
		log.debug("-------------------------");
		log.debug("=test()=");
		log.debug("-------------------------");
	}

	@Test
	void test2() {
		log.debug("-------------------------");
		log.debug("=test2()=");
		log.debug("-------------------------");
	}

}
