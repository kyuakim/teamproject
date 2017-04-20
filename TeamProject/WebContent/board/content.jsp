
<%@page import="net.board.db.boardBean"%>
<%@page import="net.board.db.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet" href="assets/css/main.css" />

</head>
<body>
	<jsp:include page="../header.jsp" />

<%

boardBean bb=(boardBean)request.getAttribute("bb");

int num=Integer.parseInt(request.getParameter("num"));
String pageNum = request.getParameter("pageNum");

%>

	<h1>WebContent/board/content.jsp</h1>
<h1>글 내용보기</h1>
<table border="1">
<tr><td>글번호</td><td><%=bb.getNum()%> </td><td>조회수</td><td><%=bb.getReadcount() %></td></tr>
<tr><td>글쓴이</td><td><%=bb.getName() %></td><td>작성일</td><td><%=bb.getDate() %></td></tr>
<tr><td>글제목</td><td colspan="3"><%=bb.getSubject()%></td></tr>
<%if(bb.getFile()!=null){ %>
<tr><td>첨부파일</td><td colspan="3">
<!--  상대적경로 위치 ".."는 한단계위로라는 의미 -->
<a href="./upload/<%=bb.getFile()%>">
<%=bb.getFile() %></a>
</td></tr>
<tr><td colspan="3"><img src="./upload/<%=bb.getFile()%>" width=400 height=400>
</td></tr>
<%} %>
<tr><td>글내용</td><td colspan="3"><%=bb.getContent() %></td></tr>
<tr><td colspan="4">

<input type="button" value="글수정"
onclick="location.href='./BoardUpdate.bo?num=<%=bb.getNum()%>&pageNum=<%=pageNum%>'">
<!-- http://localhost:8080/Study.jsp/board/////////updateForm.jsp?num=21&pageNum=1 -->

<input type="button" value="글삭제"
onclick="location.href='./BoardDelete.bo?num=<%=bb.getNum()%>&pageNum=<%=pageNum%>'">

<!-- =========================================================================== -->
<!-- boardDAO의 getboard에서 re_xxx값들 가져와야되 -->
<input type="button" value="답글쓰기"
onclick="location.href='./BoardRewrite.bo?num=<%=num%>&re_ref=<%=bb.getRe_ref()%>&re_lev=<%=bb.getRe_lev() %>&re_seq=<%=bb.getRe_seq() %>'">
<!-- =========================================================================== -->


<input type="button" value="글목록 " 
onclick="location.href='BoardList.bo?pageNum=<%=pageNum%>'"></td></tr>


</table>


<div id="fb-root"></div>
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/ko_KR/sdk.js#xfbml=1&version=v2.8";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));</script>



<div class="fb-comments" 
data-href="http://localhost:8080/TeamProject/BoardContent.bo?num=<%=bb.getNum() %>&pageNum=<%=pageNum %>" 
data-numposts="5"></div>
	<jsp:include page="../footer.jsp" />

</body>
</html>






















