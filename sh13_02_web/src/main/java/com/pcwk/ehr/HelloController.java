package com.pcwk.ehr;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HelloController {

	
	@RequestMapping(value = "/hello.do",method =  RequestMethod.GET)
	public String hello(Model model) {
		model.addAttribute("message", "Hello, Spring MVC!");
		
		//  prefix         화면       suffix
		// /WEB-INF/views/ :hello:.jsp
		return "hello";
	}
	
}
