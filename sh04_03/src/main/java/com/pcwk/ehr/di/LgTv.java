package com.pcwk.ehr.di;

import com.pcwk.ehr.cmn.PLog;

public class LgTv implements PLog {

	final String brand = "LG Tv";
	
	public LgTv() {
		log.debug("SamsungTv() 생성자 호출!");
	}
	
	public void turnOn() {
		log.debug(brand+" turn on!");
	}
	
	public void turnOff() {
		log.debug(brand+" turn off!");
	}
	
	public void soundUp() {
		log.debug(brand +" sound up!");
	}
	
	
	public void soundDown() {
		log.debug(brand +" sound down!");
	}	
	
	
}
