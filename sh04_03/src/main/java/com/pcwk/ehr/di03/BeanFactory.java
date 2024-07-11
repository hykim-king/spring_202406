package com.pcwk.ehr.di03;

public class BeanFactory {

	public Object getBean(String beanName) {
		
		if(beanName.equalsIgnoreCase("samsung")) {
			return new SamsungTv();
		}else if (beanName.equalsIgnoreCase("lg")) {
			return new LgTv();
		}
		
		return null;
	}
	
}
