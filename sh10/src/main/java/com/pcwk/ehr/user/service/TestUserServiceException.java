package com.pcwk.ehr.user.service;

import com.pcwk.ehr.cmn.PLog;

public class TestUserServiceException extends RuntimeException implements PLog {

	public TestUserServiceException() {

	}

	public TestUserServiceException(String message) {
		super(message);
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ TestUserServiceException()               │" + message);
		log.debug("└──────────────────────────────────────────┘");
	}

}
