package board.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import board.model.vo.Board;

import static common.JDBCTemplate.*;

public class BoardDao {
	public BoardDao() {}

	public int getListCount(Connection conn) {
		int listCount = 0;
		Statement stmt = null;
		ResultSet rset = null;
		
		String query = "select count(*) from board";
		
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			if(rset.next()) {
				listCount = rset.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(stmt);
		}

		return listCount;
	}

	public ArrayList<Board> selectList(Connection conn, int currentPage, int limit) {
		ArrayList<Board> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		//해당 페이지에 출력할 목록의 시작행과 끝행 계산
		int startRow = (currentPage - 1) * limit + 1;
		int endRow = startRow + limit -1;
		String query = "select * "+ 
				"from (select rownum rnum, board_num, board_writer,board_title,board_content,board_original_filename,board_rename_filename,board_ref,board_reply_ref,board_reply_lev,board_reply_seq,board_readcount,board_date " + 
				"from (select * from board order by board_ref desc, board_reply_ref desc, board_reply_lev asc, board_reply_seq asc))" + 
				"where rnum >= ? and rnum <= ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Board b = new Board();
				
				b.setBoardNum(rset.getInt("board_num"));
				b.setBoardWriter(rset.getString("board_writer"));
				b.setBoardTitle(rset.getString("board_content"));
				b.setBoardContent(rset.getString("board_content"));
				b.setBoardOriginalFileName(rset.getString("board_original_filename"));
				b.setBoardRenameFileName(rset.getString("board_rename_filename"));
				b.setBoardRef(rset.getInt("board_ref"));
				b.setBoardReplyRef(rset.getInt("board_reply_lev"));
				b.setBoardReplySeq(rset.getInt("board_reply_seq"));
				b.setBoardReadCount(rset.getInt("board_readcount"));
				b.setBoardDate(rset.getDate("board_date"));
				
				list.add(b);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	public int addReadCount(int boardNum, Connection conn) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query = "update board set board_readcount = board_readcount + 1 where board_num = ? ";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNum);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}
	
	/*public int addReadCount(int boardNum, Connection conn) {
	      int result = 0;
	      PreparedStatement pstmt = null;
	      //System.out.println(boardNum);
	      String query = "update board set board_readcount = board_readcount + 1 where board_num = ?";
	      
	      try {
	         pstmt = conn.prepareStatement(query);
	         pstmt.setInt(1, boardNum);
	         
	         result = pstmt.executeUpdate();
	      } catch (Exception e) {
	         e.printStackTrace();
	      }finally {
	         close(pstmt);
	      }
	      
	      return result;
	   }*/
	
	public Board selectBoard(int boardNum, Connection conn) {
		Board b = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String query = "select * from board where board_num = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNum);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				b = new Board();
				
				b.setBoardNum(rset.getInt("board_num"));
				b.setBoardWriter(rset.getString("board_writer"));
				b.setBoardTitle(rset.getString("board_content"));
				b.setBoardContent(rset.getString("board_content"));
				b.setBoardOriginalFileName(rset.getString("board_original_filename"));
				b.setBoardRenameFileName(rset.getString("board_rename_filename"));
				b.setBoardRef(rset.getInt("board_ref"));
				b.setBoardReplyRef(rset.getInt("board_reply_lev"));
				b.setBoardReplySeq(rset.getInt("board_reply_seq"));
				b.setBoardReadCount(rset.getInt("board_readcount"));
				b.setBoardDate(rset.getDate("board_date"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return b;
	}
}
