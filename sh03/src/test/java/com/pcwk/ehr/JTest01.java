package com.pcwk.ehr;

import static org.junit.Assert.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class JTest01 {

	final Logger  log = LogManager.getLogger(getClass()); 
	
	/**
	 *  1. 테스트 메서드에 public : OK
		2. 메서드에 @Test를 붙인다.: OK
		3. return void        : OK
		4. 파라메터 사용할 수 없다.  : OK
	 */
	@Test
	public void test() {
		log.debug("==================");
		log.debug("=test()=");
		log.debug("==================");
		
	}
	
	@Test
	public void pcwkTest() {
		log.debug("==================");
		log.debug("=pcwkTest()=");
		log.debug("==================");
		
	}	

}
