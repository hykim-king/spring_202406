package com.pcwk.ehr.di;

import com.pcwk.ehr.cmn.PLog;

public class SonySpeaker implements PLog, Speaker {

	public SonySpeaker() {
		log.debug("SonySpeaker 생성자!");
	}
	
	@Override
	public void playSound() {
		log.debug("play sound from 소니 스피커!");
	}

	
	
}
