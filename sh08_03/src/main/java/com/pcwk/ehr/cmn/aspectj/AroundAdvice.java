package com.pcwk.ehr.cmn.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;

import com.pcwk.ehr.cmn.PLog;

public class AroundAdvice implements PLog {

	
	public Object aroundLog(ProceedingJoinPoint pjp) throws Throwable {
		Object retObj = null;
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ aroundLog()                              │");		
		
		//method명
		String method = pjp.getSignature().getName();
		
		//class명
		String className = pjp.getTarget().getClass().getName();
		
		
		retObj = pjp.proceed();
		
		log.debug("│ className                                │"+className);
		log.debug("│ method                                   │"+method);
		log.debug("└──────────────────────────────────────────┘");		
		return retObj;
	}
	
}
