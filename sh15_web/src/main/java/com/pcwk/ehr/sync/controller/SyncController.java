package com.pcwk.ehr.sync.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.sync.domain.Sync;

@Controller
public class SyncController implements PLog {

	public SyncController() {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ SyncController()                         │");
		log.debug("└──────────────────────────────────────────┘");	
	}

	
	
//	/WEB-INF/views/sync/sync_index.jsp
//	/WEB-INF/views/sync/sync_result.jsp
	
	//http://localhost:8080/ehr/sync/sync_index.do
	@RequestMapping(value = "/sync/sync_index.do"
			,method = RequestMethod.GET)
	public String handlerSyncIndex() {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ handlerSyncIndex()                       │");
		log.debug("└──────────────────────────────────────────┘");			
		
		//prefix+viewName+suffix ->/WEB-INF/views/sync/sync_index.jsp
		String viewName = "sync/sync_index";
		log.debug("viewName:"+viewName);
		//메시지 파일 [/WEB-INF/views/sync/sync_index.jsp]을(를) 찾을 수 없습니다.

		return viewName;
	}
	
	
	@RequestMapping(value ="/sync/sync_result.do"
			, method = RequestMethod.GET )
	public ModelAndView syncResult(Sync sync, ModelAndView modelAndView ) {
		
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ syncResult()                             │");
		log.debug("└──────────────────────────────────────────┘");		
		

		log.debug("sync:"+sync);
		String viewName = "sync/sync_result";
		log.debug("viewName:"+viewName);
		
		//데이터를 화면에 전달
		modelAndView.addObject("message", " Hi "+sync.getName());
		//화면 
		modelAndView.setViewName(viewName);
		return modelAndView;
	}
	
	
	
}










