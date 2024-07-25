     function doSelectOneAjax(userId){
        console.log("doSelectOneAjax()",userId);
        //비동기 통신
        let type= "GET";
        let url = "/ehr/user/doSelectOne.do";
        let async = "true";
        let dataType = "html";
        
        let params = {
            "userId" :  userId
        };

        PClass.pAjax(url,params,dataType,type,async,function(data){

            if(data){
                try{
                    //JSON문자열을 JSON Object로 변환
                    const jsonObj =JSON.parse(data)

                    //메시지
                    const message = jsonObj.message;

                    if(isEmpty(message) === false && 1 === message.messagId){
                        alert(message.messageContents);
                        
                        const user = jsonObj.user;

					    const userIdInput = document.querySelector("#userId");
					    const nameInput = document.querySelector("#name");
					    const passwordInput = document.querySelector("#password");
					    const birthdayInput = document.querySelector("#birthday");
					    const levelSelect = document.querySelector("#level");
					    const loginInput = document.querySelector("#login");
					    const recommendInput = document.querySelector("#recommend");
					    const emailInput = document.querySelector("#email");
					    const regDtInput = document.querySelector("#regDt");
                        

                        userIdInput.value  = user.userId;
                        console.log("user.userId:"+user.userId);
                        console.log("user.level:"+user.level);
                        
                        let levelValue = 0;
                        if( "BASIC" == user.level){
                           levelValue = 1;
                        }else if("SILVER" == user.level){
                           levelValue = 2;
                        }else if( "GOLD"==user.level){
                           levelValue = 3;
                        }
                        
                        nameInput.value  = user.name;
                        passwordInput.value = user.password;
                        birthdayInput.value = user.birthday;
                        levelSelect.value   = user.level;
                        loginInput.value    = user.login;
                        recommendInput.value= user.recommend;
                        emailInput.value    = user.email;
                        regDtInput.value    = user.regDt;
                        levelSelect.value   = levelValue;

                        //disable속성값 부여 
                        userIdInput.disabled = true;
                        regDtInput.disabled = true;
                    }
  
  
                }catch(e){
                   console.error("data가 null혹은, undefined 입니다.");
                   alert("data가 null혹은, undefined 입니다.");     
                }

            }
        });

    }
     
     
     function doRetrieveAjax(inURL,pageNo){
       console.log("doRetrieveAjax()"+inURL+","+pageNo);
       //비동기 통신
       
       let type = "GET";
       let url  = inURL;
       let async = "true";
       let dataType = "html";
       
       const userForm = document.querySelector("#userForm");
       
       let params = {
         "searchDiv"  : userForm.searchDiv.value,
         "searchWord" : userForm.searchWord.value,
         "pageSize"   : userForm.pageSize.value,
         "pageNo"     : pageNo
       
       }
       
       
       PClass.pAjax(url,params,dataType,type,async,function(data){
            if(data){
                try{
                    //문자열 to JSON
                    const jsonObj  = JSON.parse(data);
                    const userList = jsonObj.user; 
                    const totalCnt = jsonObj.totalCnt;
                    console.log(" userList :",userList); 
                    console.log(" totalCnt :",totalCnt);
                    
                    
                    //userTable> tbody 데이터 삭제
                    //동적으로 데이터 추가
                    
                    const userTableTbody = document.querySelector("#userTable > tbody");
                    //userTable> tbody 데이터 삭제
                    userTableTbody.innerHTML = "";  
                    
                    //data가 있는 경우
                    console.log(" userList.length :",userList.length);
                    
                    let html = "";
                    
                    //데이터가 있는 경우
                    if(userList.length > 0){
                        userList.forEach(function(value,i){
                            //console.log(value.userId,i);  
                            html += "<tr>";
                            html +=  "<td class='text-center' >"+value.no+"</td>";    
                            html +=  "<td class='text-left' >"+value.userId+"</td>";
                            html +=  "<td class='text-center' >"+value.name+"</td>";
                            html +=  "<td class='text-left' >"+value.password+"</td>";
                            html +=  "<td class='text-center' >"+value.birthday+"</td>";
                            html +=  "<td class='text-left' >"+value.email+"</td>";
                            html +=  "<td class='text-center' >"+value.regDt+"</td>";
                            html += "</tr>";
                        });
                    //데이터가 없는 경우
                    }else{
                            html += "<tr>";
                            html +=  "<td class='text-center' colspan='99' >No data found!</td>";                             
                            html += "</tr>";                                                
                    }
                    
                    userTableTbody.innerHTML = html;
                    
                    //단건조회 이벤트 처리
                    userTableTbody.querySelectorAll("tr").forEach(function(row){
                        row.addEventListener('dblclick',function(event){
                            console.log('*dblclick click*');
                            
                            let userId=this.querySelector('td:nth-child(2)').textContent.trim();
                            console.log('userId: ' + userId);
                            
                            doSelectOneAjax(userId);
                        });
                    });
                    
                    
                    //paging
                    
                    //기존 paging은 지운다.
                    //paging을 새로운 데이터로 그려 준다.
                    
                    const pagerDiv = document.querySelector("#page-selection");
                    pagerDiv.innerHTML = "";
                    
                    pagerDiv.innerHTML = pager(totalCnt,pageNo,userForm.pageSize.value,10,url,"doRetrieveAjax");
                    
                    iniControl();
                }catch(e){
                  console.error("data가 null혹은, undefined 입니다.",e);
                  alert("data가 null혹은, undefined 입니다.");                      
                }
            }
       
       
       });
       
       
    }
 function pageRetrieve(url,pageNo){
     console.log("pageRetrieve()");
     const  frm = document.querySelector("#userForm");
     
     //검색 조건
     let searchDiv = frm.searchDiv.value;
     console.log("searchDiv:"+searchDiv);
    
     let searchWord = frm.searchWord.value; 
     console.log("searchWord:"+searchWord);
     
     let pageSize = frm.pageSize.value;  
     console.log("pageSize:"+pageSize);
     //시작 페이지 번호:1      
     frm.pageNo.value = pageNo;
     
     let actionUrl = url;
     
     frm.action = actionUrl;
     frm.submit();    
}
 document.addEventListener("DOMContentLoaded", function(){
    console.log("DOMContentLoaded~~");
    
    //객체 생성
    const doRetrieveBtn=document.querySelector("#doRetrieve");
    console.log("doRetrieveBtn",doRetrieveBtn);
    
    const searchWordInput = document.querySelector("#searchWord");
    console.log("searchWordInput",searchWordInput);

    const searchDivSelect = document.querySelector("#searchDiv");
    console.log("searchDivSelect",searchDivSelect);

    const pageSizeSelect = document.querySelector("#pageSize");
    console.log("pageSizeSelect",pageSizeSelect);

    const  frm = document.querySelector("#userForm");
    console.log("frm",frm);
    
    //관리 버튼 : 
    const doUpdateBtn = document.querySelector("#doUpdate");
    console.log("doUpdateBtn",doUpdateBtn);

    const doDeleteBtn = document.querySelector("#doDelete");
    console.log("doDeleteBtn",doDeleteBtn);

    const initBtn = document.querySelector("#initBtn");
    console.log("initBtn",initBtn);   

    const doSaveBtn = document.querySelector("#doSave");
    console.log("doSaveBtn",doSaveBtn);
    
    //idDuplicateCheck
    const idDuplicateCheckBtn = document.querySelector("#idDuplicateCheck");
    console.log("idDuplicateCheckBtn",idDuplicateCheckBtn);    
    
    //관리 컨트롤
    const userIdInput = document.querySelector("#userId");
    const nameInput = document.querySelector("#name");
    const passwordInput = document.querySelector("#password");
    const birthdayInput = document.querySelector("#birthday");
    const levelSelect = document.querySelector("#level");
    const loginInput = document.querySelector("#login");
    const recommendInput = document.querySelector("#recommend");
    const emailInput = document.querySelector("#email");
    const regDtInput = document.querySelector("#regDt");


    //컨트롤 초기화
    iniControl();
    
    doUpdateBtn.addEventListener("click",function(event){
        event.stopPropagation();// 이벤트 버블링 방지
        console.log('doUpdateBtn click');
        
        doUpdate();
    });

    idDuplicateCheckBtn.addEventListener("click",function(event){
        console.log('idDuplicateCheckBtn click');
        idDuplicateCheck();
    });


    doDeleteBtn.addEventListener("click",function(event){
        console.log('doDeleteBtn click');
        event.stopPropagation();// 이벤트 버블링 방지
        doDelete();
    });
    
    doSaveBtn.addEventListener("click",function(event){
        console.log('doSaveBtn click');
        event.stopPropagation();// 이벤트 버블링 방지


        doSave();

    });
    //조회 결과 : 다건
    const rows = document.querySelectorAll("#userTable tbody tr");

    //이벤트 감지 및 처리  
    rows.forEach(function(row){
        //dblclick
        row.addEventListener('click',function(event){
            console.log('row click');
            // 두 번째 td
            let userId = this.querySelector('td:nth-child(2)').textContent.trim();
            console.log('두 번째 td 값: ' + userId);
            doSelectOne(userId);
        });  
    });



    initBtn.addEventListener("click",function(event){
        event.stopPropagation();// 이벤트 버블링 방지
		console.log('initBtn click');
		
		const initString = "";  
		
		userIdInput.value   = initString;
		nameInput.value     = initString;
		passwordInput.value = initString; 
		birthdayInput.value = initString; 
		levelSelect.value   = initString; 
		loginInput.value    = initString; 
		recommendInput.value= initString; 
		emailInput.value    = initString; 
		regDtInput.value    = initString; 
		levelSelect.value   = 1;
		
		//disable속성값 부여 
		userIdInput.disabled = false;
		regDtInput.disabled  = false;          
    });


    searchDivSelect.addEventListener("change",function(event){
        console.log("searchDivSelect change ");
        console.log("searchDivSelect.value:"+searchDivSelect.value);
       
        //전체
        if( "" === searchDivSelect.value ){
            event.stopPropagation();// 이벤트 버블링 방지
            searchWordInput.value = "";//검색어 초기화
            pageSizeSelect.value = 10;//페이지 사이즈 초기화
            
        }
    });


    doRetrieveBtn.addEventListener("click",function(event){
        event.stopPropagation();// 이벤트 버블링 방지
        console.log("doRetrieveBtn click");
        //doRetrieve();
        
        doRetrieveAjax("/ehr/user/doRetrieveAjax.do", 1);
    });
    
    //검색어 enter event: "searchWord"
    searchWordInput.addEventListener("keydown",function(event){
        event.stopPropagation();// 이벤트 버블링 방지
        
        const inputValue = searchWordInput.value;

        console.log("inputValue:"+inputValue);
        console.log("Key pressed:",event.key, event.keyCode);

        if(event.key === 'Enter'  && event.keyCode === 13 ){
            console.log("searchWordInput keydown");
            doRetrieveAjax("/ehr/user/doRetrieveAjax.do", 1);
        }
    });




    function idDuplicateCheck(){
        console.log("idDuplicateCheck()");
        if(isEmpty(userIdInput.value) == true){
            alert("아이디를 입력 하세요.");
            userIdInput.focus();
            return;
        }
        
        //비동기 통신
        let type= "GET";  
        let url = "/ehr/user/idDuplicateCheck.do";
        let async = "true";
        let dataType = "html";        

        let params = { 
            "userId"   :userIdInput.value
        };        
        
          
        PClass.pAjax(url,params,dataType,type,async,function(data){
        
            if(data){
              try{
                  //JSON문자열을 JSON Object로 변환
                  const message =JSON.parse(data)       
                  console.log("message.messagId:"+message.messagId);
                  console.log("message.messageContents:"+message.messageContents);
                  if(isEmpty(message) === false && 1 === message.messagId){ 
                     alert(message.messageContents);
                     userIdInput.focus();   
                  }else{
                     alert(message.messageContents);
                  }  
                     
              }catch(e){
                 console.error("data가 null혹은, undefined 입니다.",e);
                 alert("data가 null혹은, undefined 입니다.");     
              }           
            }
  
         });
                         
    }

    function doUpdate(){
        console.log("doUpdate()");
        //필수 입력 처리

        if(isEmpty(userIdInput.value) == true){
            alert("아이디를 입력 하세요.");
            userIdInput.focus();
            return;
        }

        if(isEmpty(nameInput.value) == true){
            alert("이름을 입력 하세요.");
            nameInput.focus();
            return;
        }
 
        if(isEmpty(passwordInput.value) == true){
            alert("비밀번호를 입력 하세요.");
            passwordInput.focus();
            return;
        }        

        if(isEmpty(birthdayInput.value) == true){
            alert("생년월일을 입력 하세요.");
            birthdayInput.focus();
            return;
        }    
        
        if(isEmpty(loginInput.value) == true){
            alert("로그인 회수를 입력 하세요.");
            loginInput.focus();
            return;
        }          
        
        if(isEmpty(recommendInput.value) == true){
            alert("추천 회수를 입력 하세요.");
            recommendInput.focus();
            return;
        } 
        
        if(isEmpty(emailInput.value) == true){
            alert("이메일을 입력 하세요.");
            emailInput.focus();
            return;
        }         
        //비동기 통신
        let type= "POST";  
        let url = "/ehr/user/doUpdate.do";
        let async = "true";
        let dataType = "html";
        
        let levelValue = ""; 
        if( "1" == levelSelect.value){
           levelValue = "BASIC";
        }else if("2" == levelSelect.value){
           levelValue = "SILVER";
        }else if( "3"==levelSelect.value){
           levelValue = "GOLD";
        }
                                
        let params = { 
            "userId"   :userIdInput.value,
            "name"     :nameInput.value,
            "password" :passwordInput.value,
            "birthday" :birthdayInput.value,
            "level"    :levelValue,
            "login"    :loginInput.value ,
            "recommend":recommendInput.value,
            "email"    :emailInput.value
        };        
        
        if(confirm("수정 하시겠습니까?")===false)return;        
      
        PClass.pAjax(url,params,dataType,type,async,function(data){
          if(data){
            try{ 
                //JSON문자열을 JSON Object로 변환
                const message =JSON.parse(data)     
                console.log("message.messagId:"+message.messagId);
                console.log("message.messageContents:"+message.messageContents);
                if(isEmpty(message) === false && 1 === message.messagId){   
                   alert(message.messageContents);
                   doRetrieveAjax("/ehr/user/doRetrieveAjax.do", 1);
                }else{
                   alert(message.messageContents);
                }          
            }catch(e){
               console.error("data가 null혹은, undefined 입니다.",e);
               alert("data가 null혹은, undefined 입니다.");     
            }         
          }        
        
        });
        
    } 
    
    function doDelete(){
        //필수 입력 처리
        if(isEmpty(userIdInput.value) == true){
            alert("삭제 대상을 선택 하세요.");
            userIdInput.focus();
            return;
        }

        //비동기 통신
		let type= "GET";  
		let url = "/ehr/user/doDelete.do";
		let async = "true";
		let dataType = "html";        

		let params = { 
			"userId"   :userIdInput.value
		};        
		
		if(confirm("삭제 하시겠습니까?")===false)return;
		
		PClass.pAjax(url,params,dataType,type,async,function(data){
		
            if(data){
              try{
                  //JSON문자열을 JSON Object로 변환
                  const message =JSON.parse(data)		
                  console.log("message.messagId:"+message.messagId);
                  console.log("message.messageContents:"+message.messageContents);
                  if(isEmpty(message) === false && 1 === message.messagId){	
                     alert(message.messageContents);
                     doRetrieveAjax("/ehr/user/doRetrieveAjax.do", 1);
                  }else{
                     alert(message.messageContents);
                  }		   
              }catch(e){
                 console.error("data가 null혹은, undefined 입니다.",e);
                 alert("data가 null혹은, undefined 입니다.");     
              }     	  
            }
  
         });
    }



    function doSave(){
        console.log("doSave()");
        //필수 입력 처리

        if(isEmpty(userIdInput.value) == true){
            alert("아이디를 입력 하세요.");
            userIdInput.focus();
            return;
        }

        if(isEmpty(nameInput.value) == true){
            alert("이름을 입력 하세요.");
            nameInput.focus();
            return;
        }
 
        if(isEmpty(passwordInput.value) == true){
            alert("비밀번호를 입력 하세요.");
            passwordInput.focus();
            return;
        }        

        if(isEmpty(birthdayInput.value) == true){
            alert("생년월일을 입력 하세요.");
            birthdayInput.focus();
            return;
        }    
        
        if(isEmpty(loginInput.value) == true){
            alert("로그인 회수를 입력 하세요.");
            loginInput.focus();
            return;
        }          
        
        if(isEmpty(recommendInput.value) == true){
            alert("추천 회수를 입력 하세요.");
            recommendInput.focus();
            return;
        } 
        
        if(isEmpty(emailInput.value) == true){
            alert("이메일을 입력 하세요.");
            emailInput.focus();
            return;
        }         
        //비동기 통신
		let type= "POST";  
		let url = "/ehr/user/doSave.do";
		let async = "true";
		let dataType = "html";
		
        let levelValue = ""; 
        if( "1" == levelSelect.value){
           levelValue = "BASIC";
        }else if("2" == levelSelect.value){
           levelValue = "SILVER";
        }else if( "3"==levelSelect.value){
           levelValue = "GOLD";
        }
                        		
		let params = { 
			"userId"   :userIdInput.value,
            "name"     :nameInput.value,
            "password" :passwordInput.value,
            "birthday" :birthdayInput.value,
            "level"    :levelValue,
            "login"    :loginInput.value ,
            "recommend":recommendInput.value,
            "email"    :emailInput.value
		};        
		
		if(confirm("등록 하시겠습니까?")===false)return;
		
		PClass.pAjax(url,params,dataType,type,async,function(data){
		
		  if(data){
			try{
	            //JSON문자열을 JSON Object로 변환
	            const message =JSON.parse(data)		
                console.log("message.messagId:"+message.messagId);
                console.log("message.messageContents:"+message.messageContents);
                if(isEmpty(message) === false && 1 === message.messagId){	
                   alert(message.messageContents);
                   //doRetrieve();
                   doRetrieveAjax("/ehr/user/doRetrieveAjax.do", 1);
                }else{
                   alert(message.messageContents);
                }		   
			}catch(e){
			   console.error("data가 null혹은, undefined 입니다.",e);
			   alert("data가 null혹은, undefined 입니다.");     
			}     	  
		  }

       });
    }
    
    
    function doSelectOne(userId){
        console.log("doSelectOne(userId):"+userId);
        //비동기 통신
		let type= "GET";
		let url = "/ehr/user/doSelectOne.do";
		let async = "true";
		let dataType = "html";
		
		let params = {
			"userId" : 	userId
		};


        PClass.pAjax(url,params,dataType,type,async,function(data){
            //console.log("data:"+data);
            // //사용자 정보
            // const user = jsonObj.user;
            // console.log("user.userId:"+user.userId);

            //null, undefined
            if(data){
                try{
                    //JSON문자열을 JSON Object로 변환
                    const jsonObj =JSON.parse(data)

                    //메시지
                    const message = jsonObj.message;

                    if(isEmpty(message) === false && 1 === message.messagId){
                        alert(message.messageContents);
                        
                        const user = jsonObj.user;
                        
                        userIdInput.value  = user.userId;
                        console.log("user.userId:"+user.userId);
                        console.log("user.level:"+user.level);
                        
                        let levelValue = 0;
                        if( "BASIC" == user.level){
                           levelValue = 1;
                        }else if("SILVER" == user.level){
                           levelValue = 2;
                        }else if( "GOLD"==user.level){
                           levelValue = 3;
                        }
                        
                        nameInput.value  = user.name;
                        passwordInput.value = user.password;
                        birthdayInput.value = user.birthday;
                        levelSelect.value   = user.level;
                        loginInput.value    = user.login;
                        recommendInput.value= user.recommend;
                        emailInput.value    = user.email;
                        regDtInput.value    = user.regDt;
                        levelSelect.value   = levelValue;

                        //disable속성값 부여 
                        userIdInput.disabled = true;
                        regDtInput.disabled = true;
                    }
  
  
                }catch(e){
                   console.error("data가 null혹은, undefined 입니다.");
                   alert("data가 null혹은, undefined 입니다.");     
                }

            }
        });

    }
 
    function iniControl(){
        const initString = "";  
        
        userIdInput.value   = initString;
        nameInput.value     = initString;
        passwordInput.value = initString; 
        birthdayInput.value = initString; 
        levelSelect.value   = initString; 
        loginInput.value    = initString; 
        recommendInput.value= initString; 
        emailInput.value    = initString; 
        regDtInput.value    = initString; 
        levelSelect.value   = 1;
        
        //disable속성값 부여 
        userIdInput.disabled = true;
        regDtInput.disabled  = true;      
    }
     


     
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
 
