 document.addEventListener("DOMContentLoaded", function(){
    console.log("DOMContentLoaded~~");
        
    //객체 생성
    const doRetrieveBtn=document.querySelector("#doRetrieve");
    console.log("doRetrieveBtn",doRetrieveBtn);
    
    const  frm = document.querySelector("#userForm");
    console.log("frm",frm);
 
    //이벤트 감지 및 처리
    doRetrieveBtn.addEventListener("click",function(event){
        event.stopPropagation();// 이벤트 버블링 방지
        console.log("doRetrieveBtn click");
        doRetrieve();
    });
 
 
    function doRetrieve(){
     console.log("doRetrieve()");
     
     
     //검색 조건
     let searchDiv = frm.searchDiv.value;
     console.log("searchDiv:"+searchDiv);
    
     let searchWord = frm.searchWord.value; 
     console.log("searchWord:"+searchWord);
     
     let pageSize = frm.pageSize.value;  
     console.log("pageSize:"+pageSize);
     //시작 페이지 번호:1      
     frm.pageNo.value = 1;
     
     let actionUrl = "/ehr/user/doRetrieve.do";
     
     frm.action = actionUrl;
     frm.submit();
    }
    
 
 });