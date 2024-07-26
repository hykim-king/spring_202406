package com.pcwk.ehr.time.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TimeMapper {

	
	@Select("SELECT SYSDATE||'수요일' FROM dual") //가능하지만 사용 금지!
	public String getDateTime();
	
	
	public String getPcwkDateTime();
	
}
