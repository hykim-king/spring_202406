/**
 * Package Name : com.pcwk.ehr.code.service <br/>
 * Class Name: CodeService.java <br/>
 * Description: <br/>
 * Modification imformation :
 * ------------------------------------------
 * 최초 생성일 : 2024.07.19
 *
 * ------------------------------------------
 * author : acorn
 * since  : 2020.11.23
 * version: 0.5
 * see    : <br/>
 * Copyright (C) by KandJang All right reserved.
 */
package com.pcwk.ehr.code.service;

import java.sql.SQLException;
import java.util.List;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.WorkDiv;
import com.pcwk.ehr.code.domain.Code;

/**
 * @author acorn
 *
 */
public interface CodeService extends WorkDiv<Code> {

	
	//doRetrieveIn
	/**
	 * 
	 * @param codeList(COM_PAGE_SIZE,MEMBER_SEARCH)
	 * @return  List<Code>
	 * @throws SQLException
	 */
	public List<Code> doRetrieveIn(List<String> codeList) throws SQLException;	
}
