package net.board.db;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class BoardDAO {
	Connection con = null;
	PreparedStatement pstmt = null;
	String sql = "";
	ResultSet rs=null;
	int num=0;


	// 디비연결메서드 getConnection()
	private Connection getConnection() throws Exception {

		// 2단계 디비연결=>Connection con 객체 저장
//		String dbUrl = "jdbc:mysql://localhost:3306/jspdb2";
//		String dbId = "jspid";
//		String dbPass = "jsppass";
//		Connection con = null;
//		// 1단계 드라이버로더
//		Class.forName("com.mysql.jdbc.Driver");
//		// 2단계 디비연결
//		con = DriverManager.getConnection(dbUrl, dbId, dbPass);
//		return con;
//		
		
		
		//=================================================
		//커넥션 풀(Connection Pool): 미리 연결정보를 저장
		//데이터베이스와 연결된 Connection 객체를 미리 생성하여
		//폴 (Pool)속에 저장해 두고 필요할때마다 풀에 접근하여 Connection 객체 사용
		//작업이 끝나면 다시반환
		
		//자카르타 DBCP API 이용한 커넥션 풀
		//http://commons.apache.org/	다운
		//WebContent- WEB_INF - lib
		//commons-collections4-4.1.jar
		//commons-dbcp-1.4.jar
		//commons-pool-1.6.jar
		//(http://blog.naver.com/noranlemon84/10110802400)이거참고
		
		//1. WebContent -META-INF - context.xml 만들기
		// 1단계, 2단계 기술 -> 디비연동 이름정의
		//2. WebContent -WEB-INF - web.xml 수정
		// context.xml에 디비연동 해놓은 이름을 모든 페이지에 알려줌
		//3. DB작업(DAO) - 이름을 불러서 사용
		Connection con=null;
		// Context 객체 생성		
		Context init=new InitialContext();
		// DataSource =디비연동 이름 불러오기
		DataSource ds=(DataSource)init.lookup("java:comp/env/jdbc/mysqlDB");
		// con = DataSource
		con=ds.getConnection();
		return con;
		//=================================================
	}


	public void insertBoard(boardBean bb) {

		try {
			// 1,2디비연결 메서드 호출
			 con = getConnection();
			// num 게시판 글번호 구하기
			 sql="select max(num) from board";
			 pstmt=con.prepareStatement(sql);
			 
			 rs=pstmt.executeQuery();
			 
			 //rs.getInt(1)+1 이번에 입력될 값
			 if(rs.next()){num=rs.getInt(1)+1;}//1번열값

//			 System.out.println("num="+num);//콘솔창에 출력확인 
			// 3sql insert 디비날짜 데이터베이스 명령어: now()
	         sql = "insert into board(num,name,pass,subject,content,readcount,re_ref,re_lev,re_seq,date,ip,file) values(?,?,?,?,?,?,?,?,?,now(),?,?)";
	         

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1,num);
			pstmt.setString(2, bb.getName());
			pstmt.setString(3, bb.getPass());
			pstmt.setString(4, bb.getSubject());
			pstmt.setString(5, bb.getContent());
			pstmt.setInt(6,0);//readcount , 처음조회수는0
			pstmt.setInt(7, num);//re_ref 답변글 그룹==일반글의 글번호
			pstmt.setInt(8, 0);//re_lev 답변글 들여쓰기,0:맨위, 일반글 들여쓰기 없음
			pstmt.setInt(9, 0);//re_seq 답변글 순서 일반글순서 맨위
			//date는 위에서 now()로 생성됨
			pstmt.setString(10, bb.getIp());
			pstmt.setString(11, bb.getFile());
			// 4 실행
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null)
				try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null)
				try {pstmt.close();} catch (SQLException ex) {}
			if (con != null)
				try {con.close();} catch (SQLException ex) {}
		}//finally

	}//insertBoard 메소드
	
	
	public int getBoardcount(){
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "";
		ResultSet rs=null;
		int count=0;
		try{
			//1,2 디비연결 메서드 호출
			 con = getConnection();		
			 //3  sql 함수 count(*) 이용
			 sql="select count(*) from board";
			 
			 pstmt=con.prepareStatement(sql);
			 
			 //4 rs실행저장
			 rs=pstmt.executeQuery();
			 //5 데이터있으면 count저장
			 
			 //select에서 검색한 첫번째 열에 값이 있는지 없는지 확인하는 작업
			  if(rs.next()){
				 count=rs.getInt(1);}		 
		}catch(Exception e){
			e.printStackTrace();}
		finally {
			if(rs!=null)
				try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null)
				try {pstmt.close();} catch (SQLException ex) {}
			if (con != null)
				try {con.close();} catch (SQLException ex) {}
		}//finally
		return count;		
	}//getBoardcount
	

	
	
	public List getBoardList(int startrow, int pageSize) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "";
		ResultSet rs = null;

		// 배열(컬렉션)객체 생성- 여러개의 기억공간 사용+기억공간 추가해서 사용
		List boardList = new ArrayList();
		try {
			// 1,2 디비연결 메서드 호출
			con = getConnection();
			// 3 sql member모든 데이터 가져오기
			//sql select*from board
			//최근글 위로 re_ref 그룹별 내림차순 정렬 re_seq 오름차순
			//re_ref desc, re_seq asc
			//글 잘라 오기 limit(시작행-1, 개수)
			sql = "select *from board order by re_ref desc, re_seq asc limit ?,?";
		
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startrow-1);
			pstmt.setInt(2, pageSize);
			// 4 rs 실행저장
			rs = pstmt.executeQuery();
			
			
			// 5 rs while 첫행(다음행) 데이터 있으면
			// 자바빈 객체 생성 BoardBean bb
			// bb 멤버변수 <=rs열데이터 가져와서 저장
			// bb게시판 글 하나 =>boardList 한칸 저장
			
			while (rs.next()) {
				boardBean bb=new boardBean();
				bb.setNum(rs.getInt("num"));
				bb.setName(rs.getString("name"));
				bb.setPass(rs.getString("pass"));
				bb.setSubject(rs.getString("subject"));
				bb.setContent(rs.getString("content"));
				bb.setReadcount(rs.getInt("readcount"));
				bb.setRe_ref(rs.getInt("re_ref"));
				bb.setRe_lev(rs.getInt("re_lev"));
				bb.setRe_seq(rs.getInt("re_seq"));
				bb.setDate(rs.getDate("date"));
				bb.setIp(rs.getString("ip"));
				bb.setFile(rs.getString("file"));
				
				//boardList 한칸 저장
				boardList.add(bb);
			}
	

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {rs.close();} catch (SQLException ex) {	}
			if (pstmt != null)
				try {pstmt.close();} catch (SQLException ex) {}
			if (con != null)
				try {con.close();} catch (SQLException ex) {}

		}//finally
		return boardList;
	}
	
	
	public boardBean getBoard(int num) {
		//bb의기억공간은 아직 생성안됨
		boardBean bb=null;
		try{
		//1,2디비연결 메서드 호출
		con = getConnection();
		//3 sql 객체 생성 조건 num값에 해당하는 게시판글 전체 가져오기
		//num은프라이머리키로  기준이되는 키이다
		sql = "select *from board where num=?";
		pstmt = con.prepareStatement(sql);
		pstmt.setInt(1,num);
		//4 rs=실행저장
		//5 rs 데이터 있으면 자바빈 bb
		rs = pstmt.executeQuery();

		if (rs.next()) {
			//rs 데이터가 있으면 자바빈 bb객체 생성
			bb=new boardBean();
			//bb set메서드 멤버변수 저장 <=rs 열내용
			
			bb.setNum(rs.getInt("num"));
			bb.setName(rs.getString("name"));
			bb.setPass(rs.getString("pass"));
			bb.setSubject(rs.getString("subject"));
			bb.setContent(rs.getString("content"));
			bb.setReadcount(rs.getInt("readcount"));
			bb.setRe_ref(rs.getInt("re_ref"));
			bb.setRe_lev(rs.getInt("re_lev"));
			bb.setRe_seq(rs.getInt("re_seq"));
			bb.setDate(rs.getDate("date"));
			bb.setIp(rs.getString("ip"));
			bb.setFile(rs.getString("file"));
		}
	
	
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		if (rs != null)
			try {rs.close();} catch (SQLException ex) {	}
		if (pstmt != null)
			try {pstmt.close();} catch (SQLException ex) {}
		if (con != null)
			try {con.close();} catch (SQLException ex) {}

	}//finally
	return bb;
}
	
	
	public void updateReadCount(int num){
		try{
			//1,2디비연결 메서드 호출
			con = getConnection();			
			//3 sql 객체 생성 조건 num값에 해당하는 게시판글 전체 가져오기
			sql = "update board set readcount=readcount+1 where num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,num);

			//4 rs=실행저장			
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {rs.close();} catch (SQLException ex) {	}
			if (pstmt != null)
				try {pstmt.close();} catch (SQLException ex) {}
			if (con != null)
				try {con.close();} catch (SQLException ex) {}

		}//finally	
		
	}
	
	public int updateBoard(boardBean bb){
		int check=0;
		try{
			//1,2디비연결 메서드 호출
			con = getConnection();
			//3 sql num 에 해당하는 pass 가져오기
			sql = "select pass from board where num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,bb.getNum());
			//4 rs 실행저장
			rs=pstmt.executeQuery();//select
			//5 rs 데이터있으면
			//				비밀번호 비교 맞으면 check=1
			//						틀리면 check=0
			//		      없으면 check=-1
			if(rs.next()){
				if(bb.getPass().equals(rs.getString("Pass"))){
					sql = "update board set name=?, subject=?, content=? where num=? and pass=?";
					pstmt=con.prepareStatement(sql);
					
					pstmt.setString(1, bb.getName());
					pstmt.setString(2, bb.getSubject());
					pstmt.setString(3, bb.getContent());
					pstmt.setInt(4,bb.getNum());
					pstmt.setString(5,bb.getPass());
					
					pstmt.executeUpdate();
					System.out.println("정보수정성공");					
					check=1;
				}else
					check=0;							
			}else
				check=-1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {rs.close();} catch (SQLException ex) {	}
			if (pstmt != null)
				try {pstmt.close();} catch (SQLException ex) {}
			if (con != null)
				try {con.close();} catch (SQLException ex) {}
		}//finally	
		return check;
	}
	
	

	public int deleteboard(boardBean bb){
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "";
		ResultSet rs = null;
		int check = -1;
		try {
			// 1 드라이버로더 //2 디비연결 메서드 호출
			con = getConnection();
			// 3 sql 객체 생성 id에 해당하는 pass값 가져오기
			sql = "select pass from board where num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,bb.getNum());
			// 4 rs 실행 저장
			rs = pstmt.executeQuery();// select
			if (rs.next()) {
				// 아이디있음
				if (bb.getPass().equals(rs.getString("pass"))) {
					// 3 sql delete
					sql = "delete from board where num=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, bb.getNum());
					// 4 실행
					pstmt.executeUpdate();
					System.out.println("정보삭제됨");

					check = 1;
				} else
					check = 0;
			} else
				check = -1;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {rs.close();} catch (SQLException ex) {	}
			if (pstmt != null)
				try {pstmt.close();} catch (SQLException ex) {}
			if (con != null)
				try {con.close();} catch (SQLException ex) {}
		}//finally	
		return check;
		
	}
	
	public void reInsertBoard(boardBean bb){
		int num=0;
		try {
			// 1,2디비연결 메서드 호출
			 con = getConnection();
			// sql select 최대 num (게시판 글번호) 구하기
			 sql="select max(num) from board";
			 pstmt=con.prepareStatement(sql);
			 //4 rs=실행저장
			 rs=pstmt.executeQuery();
			 
			 //5 rs 데이터 있으면 1번째 열을 가져와서 +1 //rs.getInt(1)+1 이번에 입력될 값
			 if(rs.next()){num=rs.getInt(1)+1;}//1번열값
//			 System.out.println("num="+num);//콘솔창에 출력확인 

			 
			 
			 //답글 순서 재배치
			 //3 update 조건 re_ref 같은그룹 re_seq 기존값보다 큰 값이 있으면
			 //순서 바꾸기 re_seq 1증가
			 sql="update board set re_seq=re_seq+1 where re_ref=? and re_seq>?";			
			 pstmt = con.prepareStatement(sql);
			 pstmt.setInt(1,bb.getRe_ref());
			 pstmt.setInt(2, bb.getRe_seq());
			 //4실행
			 pstmt.executeUpdate();

			 
			 //3 sql insert         num구한값        re_ref 그대로
			 //						re_lev+1  re_seq+1			 
			 //4실행		 			 
			 			 
			 
			 
			 // 3sql insert 디비날짜 데이터베이스 명령어: now()
	         sql = "insert into board(num,name,pass,subject,content,readcount,re_ref,re_lev,re_seq,date,ip,file) values(?,?,?,?,?,?,?,?,?,now(),?,?)";
	         
			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1,num);
			pstmt.setString(2, bb.getName());
			pstmt.setString(3, bb.getPass());
			pstmt.setString(4, bb.getSubject());
			pstmt.setString(5, bb.getContent());
			pstmt.setInt(6,0);//readcount , 처음조회수는0
//			===================================================
			pstmt.setInt(7, bb.getRe_ref());//re_ref 기존글 그룹번호 같게함
			pstmt.setInt(8, bb.getRe_lev()+1);//re_lev 답변글 들여쓰기,0:맨위, 일반글 들여쓰기 없음// 기존글+1
			pstmt.setInt(9, bb.getRe_seq()+1);//re_seq 답변글 순서 일반글순서 맨위// 기존글 +1
//			====================================================

			//date는 위에서 now()로 생성됨
			pstmt.setString(10, bb.getIp());
			pstmt.setString(11, bb.getFile());
			// 4 실행
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null)
				try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null)
				try {pstmt.close();} catch (SQLException ex) {}
			if (con != null)
				try {con.close();} catch (SQLException ex) {}
		}//finally	
		
		
	}
	

	public int GetLikeList(int num) {

		int like_num=0;
		try {
			
			
			// 1,2디비연결 메서드 호출
			 con = getConnection();
			// num 게시판 글번호 구하기

	         sql = "update board set like_num=like_num+1 where num=?";
	         

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1,num);
	
			// 4 실행
			pstmt.executeUpdate();
			
			sql="select like_num from board where num=?";
			

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1,num);
			rs=pstmt.executeQuery();
			like_num=rs.getInt("like_num");

		
			
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null)
				try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null)
				try {pstmt.close();} catch (SQLException ex) {}
			if (con != null)
				try {con.close();} catch (SQLException ex) {}
			
			
			
		}//finally
		
		return like_num;

	}//insertBoard 메소드
	
		
		
		
	
	
}//Class end

