/**
 * Package Name : com.pcwk.ehr.template.controller <br/>
 * Class Name: BootStrapController.java <br/>
 * Description: 브트스트랩 템플릿 컨트롤러<br/>
 * Modification imformation :
 * ------------------------------------------
 * 최초 생성일 : 2024.07.18
 *
 * ------------------------------------------
 * author : acorn
 * since  : 2020.11.23
 * version: 0.5
 * see    : <br/>
 * Copyright (C) by KandJang All right reserved.
 */
package com.pcwk.ehr.template.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pcwk.ehr.cmn.PLog;

/**
 * @author acorn
 *
 */
@Controller
@RequestMapping("template")
public class BootStrapController implements PLog {

	
	public BootStrapController() {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ BootStrapController()                    │");
		log.debug("└──────────────────────────────────────────┘");
	}
	@GetMapping("/list.do")
	public String list() {
		String viewName = "template/list";
		
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ viewName:"+viewName);
		log.debug("└──────────────────────────────────────────┘");
		
		return viewName;
	}
	
	
	@GetMapping("/reg.do")
	public String reg() {
		String viewName = "template/reg";
		
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ viewName:"+viewName);
		log.debug("└──────────────────────────────────────────┘");
		
		return viewName;
	}
	
	

}
