package com.pcwk.ehr.board.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pcwk.ehr.board.domain.Board;
import com.pcwk.ehr.board.service.BoardService;
import com.pcwk.ehr.cmn.Message;
import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.cmn.Search;
import com.pcwk.ehr.cmn.StringUtil;
import com.pcwk.ehr.code.domain.Code;
import com.pcwk.ehr.code.service.CodeService;
import com.pcwk.ehr.user.domain.User;

@Controller
@RequestMapping("board")
public class BoardController implements PLog {

	@Autowired
	BoardService boardService;
	
	@Autowired
	CodeService  codeService;

	public BoardController() {
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ BoardController()                        │");
		log.debug("└──────────────────────────────────────────┘");
	}
	
	/**
	 * 
	 * @param inVO
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping(value = "/doUpdate.do"
			, method = RequestMethod.POST            //textarea post로 
			, produces = "text/plain;charset=UTF-8") //json encoding
	@ResponseBody//json
	public String doUpdate(Board inVO) throws SQLException{
		String jsonString = "";
		// 1.
		log.debug("1.param inVO:" + inVO);
		int flag = boardService.doUpdate(inVO);
		
		// 2.
		log.debug("2.flag:" + flag);
		String message = "";

		if (1 == flag) {
			message = inVO.getTitle() + " 이 수정 되었습니다.";
		} else {
			message = inVO.getTitle() + " 수정 실패!";
		}

		Message messageObj = new Message(flag, message);
		jsonString = new GsonBuilder().setPrettyPrinting().create().toJson(messageObj);
		log.debug("3.jsonString:" + jsonString);
		
		return jsonString;
	}
	
	
    //화면 + data(조회,검색조건,페이지 사이즈)
	@RequestMapping( value ="/doRetrieve.do"
			, method = RequestMethod.GET)	
	public String  doRetrieve(Model model, HttpServletRequest req) throws SQLException{
		String viewName = "board/board_list";
		log.debug("┌──────────────────────────────────────────┐");
		log.debug("│ doRetrieve()                             │");
		log.debug("└──────────────────────────────────────────┘");		
		
		Search search =new Search();
		
		//검색구분
		String  searchDiv  = StringUtil.nvl(req.getParameter("searchDiv"),"");
		String  searchWord = StringUtil.nvl(req.getParameter("searchWord"),"");
		
		search.setSearchDiv(searchDiv);
		search.setSearchWord(searchWord);
		
		//브라우저에서 숫자 : 문자로 들어 온다.	
		String pageSize = StringUtil.nvl(req.getParameter("pageSize"),"10");
		String pageNo = StringUtil.nvl(req.getParameter("pageNo"),"1");
		
		search.setPageSize(Integer.parseInt(pageSize));
		search.setPageNo(Integer.parseInt(pageNo));
		
		// 1.
		log.debug("1.param search:" + search);		
		
		List<Board> list = this.boardService.doRetrieve(search);
		
		//2. 화면 전송 데이터
		model.addAttribute("list", list);//조회 데이터
		model.addAttribute("search", search); //검색조건
		
		int totalCnt = 0;
		//페이징:totalcnt
		if(null != list && list.size() > 0) {
			Board firstVO = list.get(0);
			totalCnt = firstVO.getTotalCnt();
		}
		model.addAttribute("totalCnt", totalCnt); //검색조건
		
		//----------------------------------------------------------------------
		Code code =new Code();
		code.setMstCode("BOARD_SEARCH");//회원검색 조건
		List<Code> memberSearch = this.codeService.doRetrieve(code);
		model.addAttribute("BOARD_SEARCH", memberSearch); //검색조건
		
        code.setMstCode("COM_PAGE_SIZE");//회원검색 조건
		List<Code> pageSizeSearch = this.codeService.doRetrieve(code);
		model.addAttribute("COM_PAGE_SIZE", pageSizeSearch); //페이지 사이즈

		//----------------------------------------------------------------------
		
		//model
		return viewName;
	}
	
	
	//단건조회
	@RequestMapping(value = "/doDelete.do"
			, method = RequestMethod.GET
			, produces = "text/plain;charset=UTF-8") // produces:																					// encoding
	@ResponseBody	
	public String doDelete(Board inVO) throws SQLException{
		String jsonString = "";
		log.debug("1.param inVO:" + inVO);		
		
		
		int flag = boardService.doDelete(inVO);
		
		// 2.
		log.debug("2.flag:" + flag);
		String message = "";

		if (1 == flag) {
			message = inVO.getSeq() + " 이 삭제 었습니다.";
		} else {
			message = inVO.getSeq() + " 삭제 실패!";
		}

		Message messageObj = new Message(flag, message);
		jsonString = new GsonBuilder().setPrettyPrinting().create().toJson(messageObj);
		log.debug("3.jsonString:" + jsonString);
		
		return jsonString;
	}

	//단건조회
	@RequestMapping(value = "/doSelectOne.do"
			, method = RequestMethod.GET
			, produces = "text/plain;charset=UTF-8") // produces:																					// encoding
	@ResponseBody	
	public String doSelectOne(Board inVO) throws SQLException{
		String jsonString = "";
		log.debug("1.param inVO:" + inVO);
		
		Board outVO = boardService.doSelectOne(inVO);
		// 2.
		log.debug("2.outVO:" + outVO);		
		
		String message = "";
		int flag = 0;
		if (null != outVO) {
			message = outVO.getTitle() + " 이 조회 었습니다.";
			flag = 1;
		} else {
			message = inVO.getTitle() + " 조회 실패!";
		}
		
		Message messageObj = new Message(flag, message);
		jsonString = new GsonBuilder().setPrettyPrinting().create().toJson(messageObj);
		log.debug("3.jsonString:" + jsonString);
		
		String jsonBoard = new GsonBuilder().setPrettyPrinting().create().toJson(outVO);
		log.debug("3.1 jsonBoard:" + jsonBoard);
		
		String jsonAll   = "{\"board\":"+jsonBoard+",\"message\":"+ jsonString+"}";
		
		log.debug("4 jsonAll:" + jsonAll);
		return jsonAll;
	}
	
	
	
	/**
	 * 
	 * @param inVO
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping(value = "/doSave.do"
			, method = RequestMethod.POST
			, produces = "text/plain;charset=UTF-8") // produces:																					// encoding
	@ResponseBody
	public String doSave(Board inVO) throws SQLException{
		String jsonString = "";
		// 1.
		log.debug("1.param inVO:" + inVO);
		int flag = boardService.doSave(inVO);
		
		// 2.
		log.debug("2.flag:" + flag);
		String message = "";

		if (1 == flag) {
			message = inVO.getTitle() + " 이 등록되 었습니다.";
		} else {
			message = inVO.getTitle() + " 등록 실패!";
		}

		Message messageObj = new Message(flag, message);
		jsonString = new GsonBuilder().setPrettyPrinting().create().toJson(messageObj);
		log.debug("3.jsonString:" + jsonString);
		
		return jsonString;
	}	
}
