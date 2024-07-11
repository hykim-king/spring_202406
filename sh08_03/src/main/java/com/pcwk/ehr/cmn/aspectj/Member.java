package com.pcwk.ehr.cmn.aspectj;

public interface Member {

	/**
	 * 단건 저장
	 * @return
	 */
	int doSave();
	
	
	/**
	 * 수정
	 * @return
	 */
	int doUpdate();
	
	/**
	 * 삭제
	 * @return
	 */
	int delete();
	
	/**
	 * 목록조회
	 * @param num
	 */
	void doRetrieve(int num);
	
	/**
	 * doInsert 등록
	 * @param num
	 * @throws IllegalArgumentException
	 */
	void doInsert(int num) throws IllegalArgumentException;
}
