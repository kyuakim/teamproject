package net.board.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.board.db.BoardDAO;
import net.board.db.boardBean;



public class BoardDeleteAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardDeleteAction execute()");


		String pass=request.getParameter("pass");
		
		BoardDAO bdao=new BoardDAO();
		boardBean bb=new boardBean();
		bb.setPass(request.getParameter("pass"));
		bb.setNum(Integer.parseInt(request.getParameter("num")));
		
		int check=bdao.deleteboard(bb);
		
		
		
		
		if (check == 1) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();		
			out.println("<script>");
			out.println("alert('삭제성공')");
			out.println("location.href='./BoardList.bo'");
			out.println("</script>");
			out.close();				
		} else {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('삭제실패');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
			return null;
			}				
						

				return null;

				
	}
}
		
	
