package net.board.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import net.board.db.BoardDAO;
import net.board.db.boardBean;

public class BoardWriteAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardWriteAction execute()");

		request.setCharacterEncoding("utf-8");
		String realPath=request.getRealPath("/upload");
		int maxSize=5*1024*1024;	//5MB


		MultipartRequest multi = new MultipartRequest(request, realPath, maxSize, "utf-8", new DefaultFileRenamePolicy());


		
		BoardDAO bdao=new BoardDAO();
		boardBean bb=new boardBean();
		
		bb.setContent(multi.getParameter("content"));		
		bb.setFile(multi.getParameter("file"));
		bb.setName(multi.getParameter("name"));
		bb.setPass(multi.getParameter("pass"));
		bb.setSubject(multi.getParameter("subject"));
		bb.setFile(multi.getFilesystemName("file"));	
		multi.getOriginalFileName("file");

		

		bdao.insertBoard(bb);
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();		
		out.println("<script>");
		out.println("alert('인생샷이 업로드 되었습니다')");
		out.println("location.href='./BoardList.bo'");
		out.println("</script>");
		out.close(); 
		
//		ActionForward forward = new ActionForward();
//		forward.setPath("./BoardList.bo");
//		forward.setRedirect(true);
//		return forward;

		return null;
	}	
	
}