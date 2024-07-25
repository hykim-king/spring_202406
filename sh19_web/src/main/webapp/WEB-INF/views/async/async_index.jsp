<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<meta charset="UTF-8">
<title>Haha-HoHo</title>
<script>
document.addEventListener("DOMContentLoaded", function(){
	console.log('DOMContentLoaded')
	
	//button객체 생성
	const sendBtn = document.querySelector("#sendButton");
	const jsSendBtn = document.querySelector("#jsSendButton");
	
	
	const nameInput = document.querySelector("#name");
	
    const resultDiv = document.querySelector("#result");
    
    
	console.log('sendBtn:',sendBtn);
	console.log('jsSendBtn:',jsSendBtn);
	console.log('nameInput:',nameInput);
	
	//이벤트 등록
	jsSendBtn.addEventListener("click",function(event){
		event.stopPropagation();
		console.log('jsSendBtn: click',event);
		asyncJsSend();
	});
	
	//이벤트 등록
	sendBtn.addEventListener("click",function(event){
		event.stopPropagation();
		
		console.log('sendBtn: click',event);
		//asyncSend();
		//asyncJsSend();
		
		asyncSendCallAjax();		
	});
	
	
	
	
	
	function asyncJsSend(){
        console.log('asyncJsSend');
        console.log('name:'+ nameInput.value);		

        //XMLHttpRequest객체를 생성
        let xhr = new XMLHttpRequest();
        
        //url
        let url = "/ehr/async/asyncJs.do";
        
        let name = nameInput.value;
        
        //비동기 방식으로 POST전송
        xhr.open('POST',url,true);
        
        //요청 헤더를 설정하여 전송할 데이터의 형식을 지정합니다.:POST, encoding 
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8');
        // 요청 상태가 변경될 때마다 호출되는 이벤트 핸들러를 정의합니다.
        xhr.onreadystatechange = function(){
           // 요청이 완료된 상태인 경우	
    	   if(xhr.readyState === XMLHttpRequest.DONE){
    		   //요청 성공
    		   if(xhr.status === 200){
    			   
    			   resultDiv.innerHTML = xhr.responseText;
    			   console.log("┌──────────────────────────────────────────┐");
    			   console.log("│ asyncJsSend()                            │");
    			   console.log("└──────────────────────────────────────────┘");
    		   //요청 실패	   
    		   }else{
    			   
    			   console.error("요청 실패:",xhr.status)
    		   }
    		   
    	   }              
        }; 
        
        //서버로 전송
        xhr.send('name='+name);   
        
	}

	function ajaxCall(url, params,dataType="html",type="GET",async = true, _callback){
		//code
        console.log("┌──────────────────┐"); 
        console.log("│ ajaxCall()       │");   
        console.log("└──────────────────┘");		
		
        console.log("1. url:"+url);
        console.log("2. dataType:"+dataType);
        console.log("3. type:"+type);  
        
        params.url = url;
        
        let paramArray = Object.keys(params);
        if(paramArray.length > 0){
        	console.log("4. param -----");  
        	for(let i =0; i<paramArray.length;i++){
        		console.log(paramArray[i]+": "+params[paramArray[i]]);
        	}
        	console.log("param end-----");  
        }
        
        
        return  $.ajax({
		            type: type, 
		            url:url,
		            asyn:async,
		            dataType:dataType,
		            data: params,
		            success:function(response){//통신 성공
		                console.log("success response:"+response);
		                _callback(response)
		                
		            },
		            error:function(response){//실패시 처리
		                console.error("error:"+response);
		            }
		        });
		
	}
	
	function asyncSendCallAjax(){
		let type= "POST";
		let url = "/ehr/async/async.do";
		let async = "true";
		let dataType = "html";
		
		let params = {
			name : 	nameInput.value,
			id :    nameInput.value
		};
		
		let response=ajaxCall(url,params,dataType,type,async,function(data){
			console.log('response99 data:'+data);	
			resultDiv.innerHTML = data;
		});
		
		
	}

	
	function asyncSend(){
		console.log('asyncSend');
		console.log('name:'+ nameInput.value);
		//XMLHttpRequest
		$.ajax({
		    type: "POST", 
		    url:"/ehr/async/async.do",
		    asyn:"true",
		    dataType:"html",
		    data:{
		        "name":nameInput.value  
		    },
		    success:function(response){//통신 성공
		        console.log("success response:"+response);
		        resultDiv.innerHTML = response;
		        
		    },
		    error:function(response){//실패시 처리
		        console.log("error:"+response);
		    }
		});
		
		
	}
	
});
</script>
</head>
<body>
	<h2>async</h2>
	<form id="asyncForm">
		<label for="name">이름:</label> <input type="text" id="name" name="name">
		<input type="button" id="sendButton" value="전송(jQuery)"> <input
			type="button" id="jsSendButton" value="전송(XMLHttpRequest)">
	</form>

	<div id="result"></div>
</body>
</html>
