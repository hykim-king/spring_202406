package com.pcwk.ehr.di;

import com.pcwk.ehr.cmn.PLog;

public class BoseSpeaker implements Speaker, PLog {
	
	public BoseSpeaker() {
		log.debug("BoseSpeaker 생성자!");
	}

	@Override
	public void playSound() {
		log.debug("play sound from 보스 스피커!");
	}

}
