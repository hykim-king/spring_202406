package com.pcwk.ehr.di;

import com.pcwk.ehr.cmn.PLog;

public class SamsungTv implements PLog {

	final String brand = "삼성 Tv";
	
	public SamsungTv() {
		log.debug("SamsungTv() 생성자 호출!");
	}
	
	public void powerOn() {
		log.debug(brand+" power on!");
	}
	
	public void powerOff() {
		log.debug(brand+" power off!");
	}
	
	public void volumeUp() {
		log.debug(brand +" volume up!");
	}
	
	
	public void volumeDown() {
		log.debug(brand +" volume down!");
	}	
	
	
}
