<%--
/**
	Class Name: list.jsp
	Description: 부트스트랩 템플랫 list
	Author: acorn
	Modification information
	
	수정일         수정자      수정내용
    -----   -----  -------------------------------------------
    2024. 7. 18        최초작성 
    
    
    author eclass 개발팀
    since 2020.11.23
    Copyright (C) by KandJang All right reserved.
*/
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<c:set var="CP"  value="${pageContext.request.contextPath}"  />
 
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%-- favicon  --%>
<link rel="shortcut icon" href="${CP}/resources/img/favicon.ico" type="image/x-icon">

<%-- bootstrap css --%>
<link rel="stylesheet" href="${CP}/resources/css/bootstrap.css">

<%-- jquery --%>
<script src="${CP}/resources/js/jquery_3_7_1.js"></script>

<%-- common js --%>
<script src="${CP}/resources/js/common.js"></script> 

<%-- google Nanum+Gothic --%>
<link rel="stylesheet"  href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap">

<%-- FontAwesome for icons --%>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

<title>오늘 사람 프로그램</title>
<script>
   document.addEventListener("DOMContentLoaded", function(){
       console.log("DOMContentLoaded");
       
       //조회버튼
       const doRetrieveBtn = document.querySelector("#doRetrieve");
       //등록
       const moveToRegBtn = document.querySelector("#moveToReg");
       //검색어
       const searchWordInput = document.querySelector("#searchWord");
       //구분
       const searchDivSelect = document.querySelector("#searchDiv");
       //페이지사이즈
       const pageSizeSelect = document.querySelector("#pageSize");
       
       
       //Event처리
       moveToRegBtn.addEventListener("click",function(event){
    	   console.log("moveToRegBtn click");
    	   moveToReg();
       });
       
       searchDivSelect.addEventListener("change",function(event){
    	   
    	   if("" === searchDivSelect.value){
    		   searchWordInput.value = "";//검색어
    		   pageSizeSelect.value  = 10;//페이지 사이즈
    	   }
       });
       
       
       searchWordInput.addEventListener("keydown",function(event){
    	   console.log("searchWordInput keydown");
    	   if(event.key === 'Enter' && event.keyCode === 13){
    		   event.stopPropagation();
    		   doRetrieve(1);
    	   }
    	   
       });
       
       doRetrieveBtn.addEventListener("click",function(event){
           console.log("doRetrieveBtn click");
           event.stopPropagation();
           doRetrieve(1);
       });
       //Event처리 end-------------------------------------------------
       
      
   });
   
   function moveToReg(){
       const frm      = document.querySelector("#boardForm");

       //frm.pageNo.value = 1;
       frm.action = "/ehr/board/moveToReg.do";
       frm.submit();
   }
   
   
   
   function doRetrieve(pageNo){
       console.log("doRetrieve()");
       const frm      = document.querySelector("#boardForm");
       let searchDiv  = frm.searchDiv.value;
       let searchWord = frm.searchWord.value;
       let pageSize   = frm.pageSize.value;
       frm.pageNo.value = pageNo;
       let div  = frm.div.value;
       console.log("searchDiv:"+searchDiv);
       console.log("searchWord:"+searchWord);
       console.log("pageSize:"+pageSize);
       console.log("pageNo:"+pageNo);
       console.log("div:"+div);
       
       frm.action = "/ehr/board/doRetrieve.do";
       frm.submit();
   } 
   
   
   
</script>
</head>
<body>
<!-- container -->
<div class="container">
  <!-- 제목 -->
  <div class="page-header  mb-4">
    <h2>
        <c:choose>
            <c:when test="${ '10'==search.getDiv() }">공지사항-목록</c:when>
            <c:when test="${ '20'==search.getDiv() }">자유게시판-목록</c:when>
            <c:otherwise>
                                 공지사항/자유게시판
            </c:otherwise>
        </c:choose>
    </h2>
  </div>
  <!--// 제목 end ------------------------------------------------------------->
  
  <!-- 버튼 -->
  <div class="mb-2 d-grid gap-2 d-md-flex justify-content-md-end">
      <input type="button" value="조회" id="doRetrieve" class="btn btn-primary">
      <input type="button" value="등록" id="moveToReg"  class="btn btn-primary">
  </div>
  <!--// 버튼 ----------------------------------------------------------------->
  
  <!-- 검색 -->
    <form action="#" name="boardForm" class="row g-2 align-items-center" id="boardForm">
        <input type="hidden" name="div"    id="div" value="${search.getDiv() }">
        <input type="hidden" name="pageNo" id="pageNo" value="${ search.pageNo}">
        <div class="col-sm-3">
        </div>
        <div class="col-sm-2 text-end g-2">
            <label for="searchDiv" class="form-label ">구분</label>
        </div>

        <div class="col-sm-2">
            <select name="searchDiv" class="form-select" id="searchDiv">
                <option value="">전체</option>
                <c:forEach var="item" items="${BOARD_SEARCH }">
                   <option value="${item.detCode}"  <c:if test="${item.detCode == search.searchDiv }">selected</c:if>    >${ item.detNm}</option>
                </c:forEach>
            </select>  
        </div>
        <div class="col-sm-4">
            <input type="search" name="searchWord" class="form-control" id="searchWord"
             value="${search.searchWord }"
             placeholder="검색어">
        </div>
        <div class="col-sm-1">
            <select name="pageSize" id="pageSize" class="form-select">
                <c:forEach var="item" items="${COM_PAGE_SIZE }">
                   <option value="${item.detCode}"   <c:if test="${item.detCode == search.pageSize }">selected</c:if> >${ item.detNm}</option>
                </c:forEach>            
            </select>
        </div> 
    </form>
  <!--// 검색 end ------------------------------------------------------------->
  
  <!-- table -->
    <table class="table table-striped table-hover table-bordered">
      <thead >
        <tr class="table-success">
          <th class="text-center col-sm-1">no</th>
          <th class="text-center col-sm-6">제목</th>
          <th class="text-center col-sm-2">등록자</th>
          <th class="text-center col-sm-2">등록일</th>
          <th class="text-center col-sm-1">조회수</th>
        </tr>
      </thead>  
      <tbody>
          <c:choose>
            <c:when test="${list.size() > 0 }">
              <c:forEach var="vo" items="${list }">
		          <tr>
		            <td class="text-center" ><c:out value="${vo.no }"></c:out></td>
		            <td class="text-left" ><c:out value="${vo.title }"></c:out></td>
		            <td class="text-center" ><c:out value="${vo.modId }"></c:out></td>
		            <td class="text-center" ><c:out value="${vo.modDt }"></c:out></td>
		            <td class="text-end" ><c:out value="${vo.readCnt }"></c:out></td>
		          </tr>
	          </c:forEach>            
            </c:when>
            <c:otherwise>
                <tr><td class="text-center" colspan="99" >No data found!</td></tr>
            </c:otherwise>
          </c:choose>

                      
      </tbody>
     </table> 
  <!--// table end ------------------------------------------------------------->
  
</div>
<!--// container end ---------------------------------------------------------->

<%-- bootstrap js --%>
<script src="${CP}/resources/js/bootstrap.bundle.js"></script> 
</body>
</html>