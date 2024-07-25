package com.pcwk.ehr.user.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.Message;
import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.cmn.Search;
import com.pcwk.ehr.cmn.StringUtil;
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

	@RequestMapping( value ="/doRetrieve.do"
			, method = RequestMethod.POST)	
	public String  doRetrieve(Model model, HttpServletRequest req) throws SQLException{
		// /WEB-INF/views+ viewName + ".jsp
		// /WEB-INF/views/user/user_list.jsp
		String viewName = "user/user_list";
		
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ doRetrieve()                             │");
		log.debug("└──────────────────────────────────────────┘");		
		//searchDiv = ""
		//searchWord = ""
		//pageSize=10,
		//pageNo=1
		
		Search search =new Search();
		
		//검색구분
		////검색 null처리 : null -> ""
		String  searchDiv = StringUtil.nvl(req.getParameter("searchDiv"),"");
		String  searchWord = StringUtil.nvl(req.getParameter("searchWord"),"");
		
		search.setSearchDiv(searchDiv);
		search.setSearchWord(searchWord);
		
		//브라우저에서 숫자 : 문자로 들어 온다.
		//페이지 사이즈: null -> 10
		//페이지 번호: null -> 1		
		String pageSize = StringUtil.nvl(req.getParameter("pageSize"),"10");
		String pageNo = StringUtil.nvl(req.getParameter("pageNo"),"1");
		
		search.setPageSize(Integer.parseInt(pageSize));
		search.setPageNo(Integer.parseInt(pageNo));
		
		// 1.
		log.debug("1.param search:" + search);		
		
		List<User> list = this.userService.doRetrieve(search);
		
		//2. 화면 전송 데이터
		model.addAttribute("list", list);//조회 데이터
		
		model.addAttribute("search", search); //검색조건
		
		
		//model
		return viewName;
	}
	
	@RequestMapping( value ="/doUpdate.do"
			, method = RequestMethod.POST
			, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String doUpdate(User inVO) throws SQLException {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ doUpdate()                               │");
		log.debug("└──────────────────────────────────────────┘");
		
		String jsonString = "";		
		// 1.
		log.debug("1.param user:" + inVO);
		
		int flag = userService.doUpdate(inVO);
		
		// 2.
		log.debug("2.flag:" + flag);		
		String message = "";
		if (1 == flag) {
			message = inVO.getUserId() + " 님이 수정 되었습니다.";
		} else {
			message = inVO.getUserId() + " 님 수정 실패!";
		}
		
		Message messageObj = new Message(flag, message);
		
		Gson gson = new Gson();
		jsonString = gson.toJson(messageObj);
		// 3.
		log.debug("3.jsonString:" + jsonString);

		return jsonString;
	}
	
	@RequestMapping(value = "/doDelete.do"
			, method = RequestMethod.GET
			, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String doDelete(User inVO) throws SQLException {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ doDelete()                               │");
		log.debug("└──────────────────────────────────────────┘");
		
		String jsonString = "";
		log.debug("1. param: " + inVO);
		
		int flag = userService.doDelete(inVO);
		String message = "";
		if(1==flag) {
			message =  inVO.getUserId()+"님이 삭제 되었습니다.";
		}else {
			message =  inVO.getUserId()+"님 삭제 실패!";
		}
		
		jsonString = new Gson().toJson(new Message(flag, message));
		log.debug("3.jsonString:" + jsonString);
		
		
		return jsonString;
	}

	@RequestMapping(value = "/doSelectOne.do", 
			method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String doSelectOne(User inVO) throws SQLException {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ doSelectOne()                            │");
		log.debug("└──────────────────────────────────────────┘");
		String jsonString = "";

		log.debug("1. param: " + inVO);
		User outVO = userService.doSelectOne(inVO);

		String message = "";
		int flag = 0;
		if (null != outVO) {
			message = inVO.getUserId() + "님이 조회 되었습니다.";
			flag = 1;
		} else {
			message = inVO.getUserId() + " 조회 실패!";
		}

		jsonString = new Gson().toJson(new Message(flag, message));
		log.debug("3.jsonString:" + jsonString);

		return jsonString;
	}

	/**
	 * 단건 등록
	 * 
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping(value = "/doSave.do"
			, method = RequestMethod.POST
			, produces = "text/plain;charset=UTF-8") // produces:																					// encoding
	@ResponseBody
	public String doSave(User user) throws SQLException {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ doSave()                                 │");
		log.debug("└──────────────────────────────────────────┘");
		String jsonString = "";

		// 1.
		log.debug("1.param user:" + user);

		int flag = userService.doSave(user);

		// 2.
		log.debug("2.flag:" + flag);

		String message = "";

		if (1 == flag) {
			message = user.getUserId() + " 님이 등록되 었습니다.";
		} else {
			message = user.getUserId() + " 님 등록 실패!";
		}

		Message messageObj = new Message(flag, message);

		Gson gson = new Gson();
		jsonString = gson.toJson(messageObj);
		// 3.
		log.debug("3.jsonString:" + jsonString);

		return jsonString;
	}

}
