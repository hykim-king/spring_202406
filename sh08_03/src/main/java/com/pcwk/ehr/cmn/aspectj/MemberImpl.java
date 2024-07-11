package com.pcwk.ehr.cmn.aspectj;

import com.pcwk.ehr.cmn.PLog;

public class MemberImpl implements Member,PLog {

	@Override
	public int doSave() {
		log.debug("┌++++++++++++++++++┐");
		log.debug("│ doSave()         │");
		log.debug("└++++++++++++++++++┘");
		return 0;
	}

	@Override
	public int doUpdate() {
		log.debug("┌++++++++++++++++++┐");
		log.debug("│ doUpdate()       │");
		log.debug("└++++++++++++++++++┘");
		return 0;
	}

	@Override
	public int delete() {
		log.debug("┌++++++++++++++++++┐");
		log.debug("│ delete()         │");
		log.debug("└++++++++++++++++++┘");
		return 0;
	}

	@Override
	public void doRetrieve(int num) {
		log.debug("┌++++++++++++++++++┐");
		log.debug("│ delete()         │");
		log.debug("└++++++++++++++++++┘");

	}

	@Override
	public void doInsert(int num) throws IllegalArgumentException {
		if(0 == num) {
			log.debug("┌++++++++++++++++++┐");
			log.debug("│ doInsert()       │");
			log.debug("│ IllegalArgumentException       │");
			log.debug("└++++++++++++++++++┘");
			
			throw new IllegalArgumentException(num+"은 등록 불가");
		}

	}

}
