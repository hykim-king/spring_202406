package com.pcwk.ehr.di;

import org.springframework.stereotype.Component;

import com.pcwk.ehr.cmn.PLog;

@Component("boseSpeaker")
public class BoseSpeaker implements Speaker, PLog {
	
	public BoseSpeaker() {
		log.debug("BoseSpeaker 생성자!");
	}

	@Override
	public void playSound() {
		log.debug("play sound from 보스 스피커!");
	}

}
