<%-- <%@page import="java.sql.Statement"%> --%>

<%@page import="net.board.db.boardBean"%>
<%@page import="net.board.db.BoardDAO"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>톰캣의여행커뮤니티</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet" href="assets/css/main.css" />

</head>
</head>
<body>
	<jsp:include page="../header.jsp" />

	<%
	
	List boardList=(List)request.getAttribute("bl");
	
	int count=((Integer)request.getAttribute("count")).intValue();
	int pageSize=((Integer)request.getAttribute("pageSize")).intValue();
	String pageNum=(String)request.getAttribute("pageNum");
	int currentPage=((Integer)request.getAttribute("currentPage")).intValue();
	int startrow=((Integer)request.getAttribute("startrow")).intValue();
	int endRow=((Integer)request.getAttribute("endRow")).intValue();

	BoardDAO bdao = new BoardDAO();		
			 %>
			 <div class="photo_update">
		<h3><a href="./BoardWrite.bo">인생샷 공유해요~</a></h3>
		</div>
		<div class="table-wrapper">
			<table class="alt_width">
		<%
			for(int i=0;i<boardList.size();i++){
				//자바빈(boardBean) 변수=배열한칸 접근 배열변수.get()
			boardBean bb=(boardBean)boardList.get(i);
			%>
		<tr>
			<td colspan="2"><%=bb.getSubject() %></td>
			</tr>			
			<tr>
			<td colspan="2">
			<a href="./BoardContent.bo?num=<%=bb.getNum()%>&pageNum=<%=pageNum%>">
			<img src="./upload/<%=bb.getFile()%>" width=300 height=300></a><tr>			
			<tr><td colspan="2">업로드날짜:<%=bb.getDate() %></td><tr>
			<tr><td colspan="2">조회수:<%=bb.getReadcount() %></td></tr>
	
			<tr><td class="underline">
<!-- 			좋아요 -->

<div id="fb-root"></div>
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/ko_KR/sdk.js#xfbml=1&version=v2.8";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));</script>

<div class="fb-like" 
data-href="http://localhost:8080/TeamProject/BoardContent.bo?num=<%=bb.getNum()%>&pageNum=<%=pageNum %>" 
data-layout="standard" 
data-action="like" 
data-size="small" 
data-show-faces="true" 
data-share="true"></div>

			
			</td></tr>			
			
		<%
			}
			%>
			
	</table>
	</div>
	<%
	//페이지 출력
	if(count!=0){
		//전체 페이지수 구하기 게시판 글 50개 한화면에 보여줄 글개수 10 =>5  전체페이지+0=>5
					//게시판 글 56개 한화면에 보여줄 글개수 10 =>5 전체페이지+1(나머지)=>6 
		int pageCount=count/pageSize+(count%pageSize==0?0:1);
		//한 화면에 보여줄 페이지 번호 개수
		int pageBlock=10;
		//시작페이지 번호 구하기 1~10=>1  11~20=>11  21~30=>21
		int startPage=((currentPage-1)/pageBlock)*pageBlock+1;
		//끝페이지 번호 구하기
		int endPage=startPage+pageBlock-1;
		if(endPage>pageCount){
			endPage=pageCount;}
		//이전
		if(startPage>pageBlock){
			%><a href="./BoardList.bo?pageNum=<%=startPage-pageBlock%>">[이전]</a>
	<%
		}
		//1...10
		for(int i=startPage;i<=endPage;i++){
			%><a href="./BoardList.bo?pageNum=<%=i%>">[<%=i%>]
	</a>
	<%
		}
		//다음
		if(endPage< pageCount){
			%><a href="./BoardList.bo?pageNum=<%=startPage+pageBlock %>">[다음]</a>
	<%
			}		
	}
	%>

	<jsp:include page="../footer.jsp" />

</body>
</html>