package com.pcwk.ehr.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pcwk.ehr.cmn.Search;
import com.pcwk.ehr.cmn.WorkDiv;
import com.pcwk.ehr.user.domain.User;

@Mapper
public interface UserMapper extends  WorkDiv<User> {

	/**
	 * id중복 체크 
	 * @param inVO
	 * @return 1(사용불가)/0(사용가능)
	 * @throws SQLException
	 */
	int idDuplicateCheck(User inVO) throws SQLException;
	
	
	/**
	 * 다건 입력
	 * @return int
	 * @throws SQLException
	 */
	int multipleSave() throws SQLException;


	/**
	 * 전체 회원 조회
	 * @return List<User>
	 */
	List<User> getAll();


	/**
	 * 테스트용 전체 회원 수
	 * @return int
	 * @throws SQLException
	 */
	int getCount() throws SQLException;


	/**
	 * 테스트용 전체 데이터 삭제
	 * @return
	 * @throws SQLException
	 */
	int deleteAll() throws SQLException;




}
