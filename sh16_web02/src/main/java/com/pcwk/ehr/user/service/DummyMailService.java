package com.pcwk.ehr.user.service;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import com.pcwk.ehr.cmn.PLog;

public class DummyMailService implements MailSender, PLog {

	@Override
	public void send(SimpleMailMessage simpleMessage) throws MailException {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ DummyMailService send()                  │");
		log.debug("│ 개발에서는 이메일이 전송되지 않습니다.                │");
		log.debug("└──────────────────────────────────────────┘");

	}

	@Override
	public void send(SimpleMailMessage... simpleMessages) throws MailException {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ DummyMailService send()                  │");
		log.debug("│ 개발에서는 이메일이 전송되지 않습니다.                │");
		log.debug("└──────────────────────────────────────────┘");

	}

}
