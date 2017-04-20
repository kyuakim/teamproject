
<%@page import="net.board.db.boardBean"%>
<%@page import="net.board.db.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
//int num,String pageNum 파라미터 가져오기
String pageNum = request.getParameter("pageNum");
int num=Integer.parseInt(request.getParameter("num"));
//BoardDAO bdao 객체 생성
BoardDAO bdao=new BoardDAO();
//BoardBean bb=메서드호출 getBoard(num)
boardBean bb=bdao.getBoard(num);
%>

	<h1>WebContent/board/updateForm.jsp</h1>
	<h1>게시판 글수정</h1>
		<form action="./BoardUpdateAction.bo?pageNum=<%=pageNum%>" method="post" name="fr">
<!-- 		hidden으로 숨겨서 num값 넘겨주기 -->
		<input type="hidden" name="num" value="<%=num%>">
		글쓴이:<input type="text" name="name" value="<%=bb.getName()%>"><br> 
<!-- 		비밀번호가 맞으면 수정가능하도록 -->
		비밀번호:<input type="password" name="pass"><br>
		제목:<input type="text" name="subject" value="<%=bb.getSubject()%>"><br>
<!-- 		textarea는 value값이 없움 -->
		내용:<textarea rows="10" cols="20" name="content"><%=bb.getContent()%></textarea><br>
		<input type="submit" value="글수정"><br> 
	</form>


</body>
</html>