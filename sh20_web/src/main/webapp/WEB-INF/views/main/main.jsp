<%--
/**
	Class Name: main.jsp
	Description: 메인 페이지
	Author: acorn
	Modification information
	
	수정일         수정자      수정내용
    -----   -----  -------------------------------------------
    2024. 7. 24        최초작성 
    
    
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
<style>
    body {
	   font-family: 'Nanum Gothic', sans-serif;
    }
    
    header {
       background-color: #005b9f;
       color: white; /*글자색 */
       line-height: 50px; 
    }
    
    a {
        color: white; /*글자색 */
    }
    
    .navbar-nav .nav-link:hover {
        background: #d26060;
        border-radius: 4px;
    }
    
    article {
        background-color: #f8f9fa;
        border : 1px solid #dee2e6;
        padding: 1rem;
        border-radius: 5px;
    }
    
    aside {
        background-color: #e9ecef;
        border : 1px solid #dee2e6;    
        padding: 1rem;
        border-radius: 5px;        
    }
    
    footer {
       background-color: #616161;
       color: white;
       text-align: center;
    }
    
    .content_wrap {
        min-height: calc(100vh - 200px);
    }
</style>
<title>오늘 사람 프로그램</title>
</head>
<body>
    <header class="container-fluid py-2">
        <div class="container">
            <h1 class="m-0">오늘 사람 프로그램</h1>
        </div>
    </header>

    <nav class="navbar  navbar-expand-lg bg-dark navbar-dark ">
        <div class="container-fluid">
           <a class="navbar-brand" href="#">PCWK</a>
            <!-- 햄버거 -->
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
          
           <div class="collapse navbar-collapse " id="navbarNav">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="${CP}/main/main.do">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link " aria-current="page" href="${CP}/user/doRetrieve.do">Member</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link " aria-current="page" href="${CP}/board/doRetrieve.do?div=10">Board</a>
                    </li>  
                    <li class="nav-item">
                        <a class="nav-link "  href="${CP}/board/doRetrieve.do?div=20"">Free Board</a>
                    </li>  
                    <li class="nav-item">
                        <a class="nav-link " aria-current="page" href="${CP}/template/list.do">Template List</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link " aria-current="page" href="${CP}/template/reg.do">Template Reg</a>
                    </li>                                         
                                                                                                                      
                </ul> 
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="#"><i class="fab fa-twitter"></i></a>
                    </li>                
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="#"><i class="fab fa-facebook"></i></a>
                    </li>                 
                </ul>   
           </div>
           
        </div>
    </nav>
    
    <div class="content_wrap container my-4">
        <div class="row">
            <article class="col-12 col-md-9">
                <h2>Article</h2>
            </article>
            <aside class="col-12 col-md-3">
                <h2>Aside</h2>
            </aside>
        
        </div>
    </div>
    
    <footer class="container-fluid py-2">
        <div class="container">
            <p class="m-0">&copy;2024 PCWK 웹 페이지 All rights reserved.</p>
        </div>
    </footer>
    
    
    



    <!-- Bootstrap JS -->
    <script src="/ehr/resources/js/bootstrap.bundle.js"></script>        
</body>
</html>   














