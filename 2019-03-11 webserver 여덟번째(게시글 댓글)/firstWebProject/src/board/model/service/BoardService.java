package board.model.service;

import board.model.dao.BoardDao;
import board.model.vo.Board;

import java.sql.Connection;
import java.util.ArrayList;

import static common.JDBCTemplate.*;
public class BoardService {
	private BoardDao bdao = new BoardDao();
	
	public BoardService() {}
	
	public int getListCount() {
		Connection conn = getConnection();
		int listCount = bdao.getListCount(conn);
		close(conn);
		return listCount;
	}

	public ArrayList<Board> selectList(int currentPage, int limit) {
		Connection conn = getConnection();
		ArrayList<Board> list = bdao.selectList(conn, currentPage, limit);
		close(conn);
		return list;
	}

	public void addReadCount(int boardNum) {
		Connection conn = getConnection();
		int result = bdao.addReadCount(boardNum, conn);
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
	}

	public Board selectBoard(int boardNum) {
		Connection conn = getConnection();
		Board board = bdao.selectBoard(boardNum, conn);
		close(conn);
		return board;
	}

	public int insertBoard(Board board) {
		Connection conn = getConnection();
		int result = bdao.insertBoard(board, conn);
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public void updateReplySeq(Board replyBoard) {
		Connection conn = getConnection();
		int result = bdao.updateRelySeq(replyBoard, conn);
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		System.out.println(replyBoard.getBoardReplySeq());
		close(conn);
	}

	public int insertReply(Board replyBoard) {
		Connection conn = getConnection();
		int result = bdao.insertReply(replyBoard, conn);
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return result;
	}
	
}
