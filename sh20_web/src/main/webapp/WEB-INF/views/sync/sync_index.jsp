<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Haha-HoHo</title>
</head>
<body>
    <h2>sync</h2>
    
    <form action="/ehr/sync/sync_result.do" method="GET">
        <label for="name">이름:</label>    
        <input type="text" id="name" name="name">
        <input type="submit" value="전송">
    </form>
    
</body>
</html>


