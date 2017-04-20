<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>WebContent/board/deleteForm.jsp</h1>
	<%	
	String pageNum = request.getParameter("pageNum");
	int num=Integer.parseInt(request.getParameter("num"));

	%>
	
	
	<h1>게시판 글 삭제</h1>
		<form action="./BoardDeleteAction.bo?pageNum=<%=pageNum%>" method="post" name="fr">
<!-- 		hidden으로 숨겨서 num값 넘겨주기 -->
		<input type="hidden" name="num" value="<%=num%>">

		비밀번호:<input type="password" name="pass"><br>
		<input type="submit" value="글삭제"><br> 
	</form>

</body>
</html>