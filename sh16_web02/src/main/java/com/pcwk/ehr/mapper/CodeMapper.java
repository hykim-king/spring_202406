package com.pcwk.ehr.mapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.WorkDiv;
import com.pcwk.ehr.code.domain.Code;

@Mapper
public interface CodeMapper extends WorkDiv<Code> {

	
	/**
	 * 코드 목록 조회 with In
	 */
	List<Code>  doRetrieveIn(List search) throws SQLException;	
}
