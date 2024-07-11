package com.pcwk.ehr.mapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pcwk.ehr.board.domain.Board;
import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.WorkDiv;
import com.pcwk.ehr.code.domain.Code;

@Mapper
public interface BoardMapper extends WorkDiv<Board> {

	/**
	 * 테스트용 전체 데이터 삭제
	 * @return
	 * @throws SQLException
	 */
	int deleteAll() throws SQLException;
	
	/**
	 * 최신 sequence 조회
	 * @return
	 * @throws SQLException
	 */
	int getSequence() throws SQLException;		
}
