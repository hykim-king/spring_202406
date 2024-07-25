



PClass = {
    pAjax : function(_url, params,_dataType="html",_type="GET",_async = true, _callback){
        //code
        console.log("┌──────────────────┐"); 
        console.log("│ ajaxCall()       │");   
        console.log("└──────────────────┘");        
        
        console.log("1. url:"+_url);
        console.log("2. dataType:"+_dataType);
        console.log("3. type:"+_type);  
        
        params.url = _url;
        
        let paramArray = Object.keys(params);
        if(paramArray.length > 0){
            console.log("4. param -----");  
            for(let i =0; i<paramArray.length;i++){
                console.log(paramArray[i]+": "+params[paramArray[i]]);
            }
            console.log("param end-----");  
        }
        
        
        return  $.ajax({
                    type: _type, 
                    url:_url,
                    asyn:_async,
                    dataType:_dataType,
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


}

/**
 * 입력 값이 비어있는지 확인하는 함수 
 * @param {any} value : 입력값
 * @returns {boolean} 비어 있으면 true, 그렇치 않으면 false
 */ 
let isEmpty  = function(value){
  
  if(null === value || value == undefined){
    return true;
  }
  
  if(typeof value === 'string' && value.trim() === ''){
    return true;
  }
  
  if(Array.isArray(value) && value.length === 0){
    return true;
  }
  
  return false;
}


let pager = function (maxNum, currentPageNo, rowPerPage, bottomCount, url, scriptName) {
    let html = [];
    

    let maxPageNo = Math.floor((maxNum - 1) / rowPerPage) + 1;
    let startPageNO = Math.floor((currentPageNo - 1) / bottomCount) * bottomCount + 1;
    let endPageNo = Math.floor((currentPageNo - 1) / bottomCount + 1) * bottomCount;
    
    let nowBlockNo = Math.floor((currentPageNo - 1) / bottomCount) + 1;
    let maxBlockNo = Math.floor((maxNum - 1) / bottomCount) + 1;

    if (currentPageNo > maxPageNo) {
        return '';
    }

    html.push('<ul class="pagination justify-content-center">');
    
    // <<
    if (nowBlockNo > 1 && nowBlockNo <= maxBlockNo) {
        html.push('<li class="page-item">');
        html.push('<a class="page-link" href="javascript:' + scriptName + '(\'' + url + '\', 1);">');
        html.push('<span>&laquo;</span>');
        html.push('</a>');
        html.push('</li>');
    }
    
    // <
    if (startPageNO > bottomCount) {
        html.push('<li class="page-item">');
        html.push('<a class="page-link" href="javascript:' + scriptName + '(\'' + url + '\',' + (startPageNO - bottomCount) + ');">');
        html.push('<span>&lt;</span>');
        html.push('</a>');
        html.push('</li>');
    }
    
    // 1 2 3 ... 10
    for (let inx = startPageNO; inx <= maxPageNo && inx <= endPageNo; inx++) {
        if (inx == currentPageNo) {
            html.push('<li class="page-item">');
            html.push('<a class="page-link active" href="#">' + inx + '</a>');
            html.push('</li>');
        } else {
            html.push('<li class="page-item">');
            html.push('<a class="page-link" href="javascript:' + scriptName + '(\'' + url + '\',' + inx + ');">' + inx + '</a>');
            html.push('</li>');
        }
    }
    
    // >
    if (maxPageNo > endPageNo) {
        html.push('<li class="page-item">');
        html.push('<a class="page-link" href="javascript:' + scriptName + '(\'' + url + '\',' + ((nowBlockNo * bottomCount) + 1) + ');">');
        html.push('<span>&gt;</span>');
        html.push('</a>');
        html.push('</li>');
    }
    
    // >>
    if (maxPageNo > endPageNo) {
        html.push('<li class="page-item">');
        html.push('<a class="page-link" href="javascript:' + scriptName + '(\'' + url + '\',' + maxPageNo + ');">');
        html.push('<span>&raquo;</span>');
        html.push('</a>');
        html.push('</li>');
    }
    
    html.push('</ul>');

    return html.join('');
}