package com.pcwk.ehr.di02;

public class TvMain {

	public static void main(String[] args) {
		
		BeanFactory factory=new BeanFactory();
		Tv tv=(Tv)factory.getBean(args[0]);
		
		
		tv.powerOn();
		tv.volumeUp();
		tv.volumeDown();
		tv.powerOff();
	}

}
