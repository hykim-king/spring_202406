package com.pcwk.ehr.naver.service;

import java.io.IOException;

import com.pcwk.ehr.cmn.Search;

public interface NaverService {
	
	public String doNaverSearch(Search search) throws IOException;
}
