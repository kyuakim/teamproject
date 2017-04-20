<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>톰캣의여행커뮤니티</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet" href="../css/assets/css/main.css" />


		
	</head>
	<body>
		<jsp:include page="../header.jsp" />
	
		<!-- Main -->
			<section id="main" class="wrapper">
				<div class="container">
				
					<!-- 회원가입 -->
					<div class="join_div">
						<h2>회원가입</h2>
						<form action="./MemberJoinAction.me" method="post" name="fr" onsubmit="return validateEncryptedForm()" >
							
							<label for="id">아이디</label> 
								<input type="email" name="id" id="id" placeholder="이메일을 입력해주세요." >
							<label for="pass">비밀번호</label> 
								<input type="password" name="pass" id="pass" >
							<label for="">비밀번호 확인</label> 
								<input type="password" name="pass2" id="pass2" >
							<label for="name">이름</label> 
								<input type="text" name="name" id="name" >
							<label for="nick">닉네임</label> 
								<input type="text" name="nick" id="nick" >
							
							<label for="gender">성별</label> 
								<input type="radio" id="priority-normal man" name="pregender" value="남"  checked="checked">
									<label for="priority-normal man">남</label>
								<input type="radio" id="priority-normal woman" name="pregender" value="여" >
									<label for="priority-normal woman">여</label><br>
								<input type="hidden" name="gender" id="gender" value="" >	<!-- 암호화 후 넘길때 여기로 덮어쓰고 넘김 -->
								
							<label for="tel" >연락처</label> 
								<input type="text" name="tel" id="tel" >
							
							<!-- 공개키 -->
							<input type="hidden" id="rsaPublicKeyModulus" value="<%=request.getAttribute("publicKeyModulus") %>">
							<input type="hidden" id="rsaPublicKeyExponent" value="<%=request.getAttribute("publicKeyExponent") %>">
							
							<!-- 버튼 -->
							<div class="join_btn">	
								<input type="submit" class="button special" value="가입" >
								<input type="reset" class="button" value="다시쓰기" >
							</div>
							
						</form> 
					
					</div>	<!-- join_div -->
					
				</div>	<!-- container -->
		</section>


		<jsp:include page="../footer.jsp" />
	
	</body>
</html>