 
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
        doRetrieve();
    });
    
    //검색어 enter event: "searchWord"
    searchWordInput.addEventListener("keydown",function(event){
        event.stopPropagation();// 이벤트 버블링 방지
        
        const inputValue = searchWordInput.value;

        console.log("inputValue:"+inputValue);
        console.log("Key pressed:",event.key, event.keyCode);

        if(event.key === 'Enter'  && event.keyCode === 13 ){
            console.log("searchWordInput keydown");
            doRetrieve();
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
                   doRetrieve();
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
                     doRetrieve();
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
                   doRetrieve();
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
 
