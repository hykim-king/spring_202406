package com.pcwk.ehr.di03;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class TvMain {

	public static void main(String[] args) {
		
		//BeanFactory factory=new BeanFactory();
		ApplicationContext context=new GenericXmlApplicationContext("/com/pcwk/ehr/di03/tv_applicationContext.xml");
		
		Tv tv=(Tv)context.getBean("tv");
		
		    
		tv.powerOn();
		tv.volumeUp();
		tv.volumeDown();
		tv.powerOff();
	}

}
