package com.pcwk.ehr.cmn.aspectj;

import com.pcwk.ehr.cmn.PLog;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;

public class LoggingAop implements PLog {

	
	public void logging(JoinPoint joinPoint) {
		
		Signature signature =joinPoint.getSignature();
		
		String methodName = signature.getName();//메서드 이름
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ logging()                                │");
		log.debug("└──────────────────────────────────────────┘");	
	}
	
}
