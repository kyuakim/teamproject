<%@page import="net.board.db.boardBean"%>
<%@page import="net.board.db.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>톰캣의여행커뮤니티</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet" href="assets/css/main.css" />

<script type="text/javascript">

function func1(){
if(document.fr.file.value==""){
	alert("당신의 인생샷을 첨부해주세요");
	document.fr.file.focus();
	return false;}

if(document.fr.pass.value==""){
	alert("비번을 입력해주세요");
	document.fr.pass.focus();		
	return false;}
	
document.fr.submit();

}

</script>

</head>
<body>
	<jsp:include page="../header.jsp" />



	
	<h1>글쓰기</h1>
		<form action="./BoardWriteAction.bo" method="post" name="fr" onsubmit="return func1()" enctype="multipart/form-data">
		글쓴이:<input type="text" name="name"><br> 
		비밀번호:<input type="password" name="pass"><br>
		제목:<input type="text" name="subject"><br>
		내용:<textarea rows="10" cols="20" name="content"></textarea><br>
		파일첨부:<input type="file" name="file"><br>
		
		<input type="submit" value="글쓰기"><br> 
	</form>

	<jsp:include page="../footer.jsp" />

</body>

</body>
</html>