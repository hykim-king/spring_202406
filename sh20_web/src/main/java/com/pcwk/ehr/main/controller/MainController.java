package com.pcwk.ehr.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pcwk.ehr.cmn.PLog;

@Controller
@RequestMapping("main")
public class MainController implements PLog {

	public MainController() {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ MainController()                        │");
		log.debug("└──────────────────────────────────────────┘");	
	}

	@GetMapping("main.do")
	public String main() {
		String viewName = "main/main";
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ main()                                   │");
		log.debug("└──────────────────────────────────────────┘");
		///WEB-INF/views/+viewName+.jsp
		return viewName;
	}
	
	
}
