package com.pcwk.ehr.user.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.pcwk.ehr.cmn.Message;
import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.user.domain.User;
import com.pcwk.ehr.user.service.UserService;

@Controller
@RequestMapping("user")
public class UserController implements PLog {

	@Autowired
	@Qualifier("userServiceImpl")
	UserService userService;

	public UserController() {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ UserController()                         │");
		log.debug("└──────────────────────────────────────────┘");	
	}
	
	
	public String doDelete(User inVO) throws SQLException{
	   String jsonString = "";
	   
	   return jsonString;	
	}
	
	@RequestMapping(value="/doSelectOne.do"
			,method = RequestMethod.GET
			,produces = "text/plain;charset=UTF-8"
			)
	@ResponseBody
	public String doSelectOne(User inVO) throws SQLException{
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ doSelectOne()                            │");
		log.debug("└──────────────────────────────────────────┘");
		String jsonString = "";
		
		log.debug("1. param: "+inVO);
		User outVO = userService.doSelectOne(inVO);
		
		String message = "";
		int   flag = 0;
		if(null != outVO) {
			message = inVO.getUserId()+"님이 조회 되었습니다.";
			flag = 1;
		}else {
			message = inVO.getUserId()+" 조회 실패!";
		}
		
		
		jsonString = new Gson().toJson(new Message(flag, message));
		log.debug("3.jsonString:"+jsonString);
		
		return jsonString;
	}
	
	/**
	 * 단건 등록
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping(value = "/doSave.do"
			, method = RequestMethod.POST
			, produces = "text/plain;charset=UTF-8"
			) //produces: 화면으로 전송 encoding
	@ResponseBody
	public String doSave(User user) throws SQLException{
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ doSave()                                 │");
		log.debug("└──────────────────────────────────────────┘");	
		String jsonString = "";
		
		//1.
		log.debug("1.param user:"+user);
		
		int flag = userService.doSave(user);
		
		//2.
		log.debug("2.flag:"+flag);
		
		
		String message = "";
		
		if(1==flag) {
			message = user.getUserId()+" 님이 등록되 었습니다.";
		}else {
			message = user.getUserId()+" 님 등록 실패!";
		}
		
		Message  messageObj=new Message(flag, message);
		
		Gson gson=new Gson();
		jsonString = gson.toJson(messageObj);
		//3.
		log.debug("3.jsonString:"+jsonString);
		
		return jsonString;
	}
	
	
}
