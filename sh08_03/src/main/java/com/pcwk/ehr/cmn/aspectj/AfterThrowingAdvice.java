package com.pcwk.ehr.cmn.aspectj;

import com.pcwk.ehr.cmn.PLog;

public class AfterThrowingAdvice implements PLog {
	
	public void exceptionLog(Exception e) { 

		System.out.println("┌++++++++++++++++++++++++++++++++++++++┐");
		System.out.println("│ AfterThrowingAdvice logging()        │"+e.getMessage());
		System.out.println("└++++++++++++++++++++++++++++++++++++++┘");
		
	}	

}
