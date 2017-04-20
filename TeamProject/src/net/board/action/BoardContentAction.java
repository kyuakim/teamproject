package net.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.BoardDAO;
import net.board.db.boardBean;

public class BoardContentAction implements Action{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardContentAction");	
		request.setCharacterEncoding("utf-8");		
		
		int num=Integer.parseInt(request.getParameter("num"));
		String pageNum=request.getParameter("pageNum");

		
		BoardDAO bdao=new BoardDAO();
				
		bdao.updateReadCount(num);
		boardBean bb=bdao.getBoard(num);
		
		if(bb==null){
			System.out.println("상세보기 실패");
			return null;
		}
		System.out.println("상세보기 성공");
		
		request.setAttribute("bb", bb);
		request.setAttribute("num", num);
		request.setAttribute("pageNum", pageNum);
		
		
		ActionForward forward=new ActionForward();
		forward.setPath("./board/content.jsp");
		forward.setRedirect(false);
		return forward;

		
			}

}
