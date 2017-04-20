package net.board.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.BoardDAO;
import net.board.db.boardBean;

public class BoardListAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardListAction execute()");

		request.setCharacterEncoding("utf-8");		
		
		BoardDAO bdao = new BoardDAO();
		
		int count = bdao.getBoardcount();
		int pageSize = 10;

		String pageNum = request.getParameter("pageNum");
		if (pageNum == null) {//pageNum 이 없으면
			pageNum = "1";//무조건 1페이지
		}
		int currentPage = Integer.parseInt(pageNum);
		int startrow = (currentPage - 1) * pageSize + 1;
		int endRow=currentPage*pageSize;
		
		//list를 사용하여 list.jsp에 넘겨줄 값들 setAtrribute에 담기
		List boardList=null;
		if(count!=0){
			boardList=bdao.getBoardList(startrow, pageSize);
		}
		
		request.setAttribute("bl", boardList);
		request.setAttribute("count", count);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("startrow", startrow);
		request.setAttribute("endRow", endRow);		
		
				
			
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("./board/list.jsp");
		return forward;
	}

}
