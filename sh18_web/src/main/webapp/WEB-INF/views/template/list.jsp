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
<title>오늘 사람 프로그램</title>
</head>
<body>
<!-- container -->
<div class="container">
  <!-- 제목 -->
  <div class="page-header  mb-4">
    <h2>게시-목록</h2>
  </div>
  <!--// 제목 end ------------------------------------------------------------->
  
  <!-- 버튼 -->
  <div class="mb-2 d-grid gap-2 d-md-flex justify-content-md-end">
      <input type="button" value="목록" class="btn btn-primary">
      <input type="button" value="등록" class="btn btn-primary">
  </div>
  <!--// 버튼 ----------------------------------------------------------------->
  
  <!-- 검색 -->
    <form action="#" class="row g-2 align-items-center">
        <div class="col-sm-4">
        </div>
        <div class="col-sm-2 text-end g-2">
            <label for="search_div" class="form-label ">구분</label>
        </div>
        <div class="col-sm-1">
            <select name="search_div" class="form-select" id="search_div">
                <option value="">전체</option>
                <option value="10">제목</option>
                <option value="20">내용</option>
                <option value="30">아이디</option>
                <option value="40">제목+내용</option>
                <option value="50">SEQ</option>
            </select>
        </div>
        <div class="col-sm-3">
            <input type="search" name="search_word" class="form-control" id="search_word" placeholder="검색어">
        </div>
        <div class="col-sm-2">
            <select name="page_size" id="page_size" class="form-select">
                <option value="10">10페이지</option>
                <option value="20">20페이지</option>
                <option value="50">50페이지</option>
                <option value="100">100페이지</option>
                <option value="200">200페이지</option>
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
          <tr>
            <td class="text-center" >1</td>
            <td class="text-left" >제목1</td>
            <td class="text-center" >이상무</td>
            <td class="text-center" >11:38</td>
            <td class="text-end" >0</td>
          </tr>
          <tr>
            <td class="text-center" >2</td>
            <td class="text-left" >제목2</td>
            <td class="text-center" >이상무</td>
            <td class="text-center" >11:38</td>
            <td class="text-end" >0</td>
          </tr>
          <tr>
            <td class="text-center" >3</td>
            <td class="text-left" >제목3</td>
            <td class="text-center" >이상무</td>
            <td class="text-center" >11:38</td>
            <td class="text-end" >0</td>
          </tr>                       
      </tbody>
     </table> 
  <!--// table end ------------------------------------------------------------->
  
</div>
<!--// container end ---------------------------------------------------------->
<%-- bootstrap js --%>
<script src="${CP}/resources/js/bootstrap.bundle.js"></script> 
</body>
</html>