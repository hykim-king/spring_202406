package com.pcwk.ehr.di;

import com.pcwk.ehr.cmn.PLog;

public class LGTv implements Tv, PLog {

	
	final String brand  = "LG Tv";

	private Speaker speaker;
	private double price;
		
	
	
	public LGTv() {
		log.debug(brand+ " default생성자!");
	}

	public LGTv(Speaker speaker, double price) {
		super();
		this.speaker = speaker;
		this.price = price;
	}


	public void setSpeaker(Speaker speaker) {
		this.speaker = speaker;
	}


	@Override
	public void turnOn() {
		log.debug(brand+ " turnOn()!");
		log.debug("가격:"+price+" 원");

	}

	@Override
	public void turnOff() {
		log.debug(brand+ " turnOff()!");

	}

	@Override
	public void playSound() {
		log.debug("Lg Tv plaSound()");
		speaker.playSound();
	}

}
