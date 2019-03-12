package board.model.dao;

import java.sql.Connection;
import java.sql.Date;
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
				"from (select * from board order by board_ref desc, board_reply_ref asc, board_reply_lev asc, board_reply_seq asc))" + 
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
				b.setBoardTitle(rset.getString("board_title"));
				b.setBoardContent(rset.getString("board_content"));
				b.setBoardOriginalFileName(rset.getString("board_original_filename"));
				b.setBoardRenameFileName(rset.getString("board_rename_filename"));
				b.setBoardRef(rset.getInt("board_ref"));
				b.setBoardReplyRef(rset.getInt("board_reply_ref"));
				b.setBoardReplyLev(rset.getInt("board_reply_lev"));
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
				b.setBoardTitle(rset.getString("board_title"));
				b.setBoardContent(rset.getString("board_content"));
				b.setBoardOriginalFileName(rset.getString("board_original_filename"));
				b.setBoardRenameFileName(rset.getString("board_rename_filename"));
				b.setBoardRef(rset.getInt("board_ref"));
				b.setBoardReplyLev(rset.getInt("board_reply_lev"));
				b.setBoardReplySeq(rset.getInt("board_reply_seq"));
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

	public int insertBoard(Board board, Connection conn) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query = "insert into board values "
				+ "((select max(board_num) + 1 from board), "
				+ "?, ?, ?, ?, ?, "
				+ "(select max(board_num) + 1 from board),"
				+ "0, 0, 0, default, default)";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, board.getBoardWriter());
			pstmt.setString(2, board.getBoardTitle());
			pstmt.setString(3, board.getBoardContent());
			pstmt.setString(4, board.getBoardOriginalFileName());
			pstmt.setString(5, board.getBoardRenameFileName());
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int updateRelySeq(Board replyBoard, Connection conn) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = null;
		if(replyBoard.getBoardReplyLev() == 1) {
			query = "update board set board_reply_seq = board_reply_seq + 1 where board_ref = ? and board_reply_lev = ?";
		}
		if(replyBoard.getBoardReplyLev() == 2){
			query = "update board set board_reply_seq = board_reply_seq + 1 where board_ref = ? and board_reply_lev = ? and board_reply_ref = ?";
		}
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, replyBoard.getBoardRef());
			pstmt.setInt(2, replyBoard.getBoardReplyLev());
			if(replyBoard.getBoardReplyLev() == 2) {
				pstmt.setInt(3, replyBoard.getBoardReplyRef());
			}
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int insertReply(Board replyBoard, Connection conn) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query = null;
		//원글의 댓글일 때
		if(replyBoard.getBoardReplyLev() == 1) {
			query = "insert into board values "
					+ "((select max(board_num) + 1 from board), "
					+ "?, ?, ?, null, null,?, "
					+ "(select max(board_num) + 1 from board),"
					+ "1, ?, default, default)";
		} 
		if(replyBoard.getBoardReplyLev() == 2) {
			//댓글의 댓글일 때
			query = "insert into board values "
					+ "((select max(board_num) + 1 from board), "
					+ "?, ?, ?, null, null, "
					+ "?, ?, 2, ?, default, default)";
		}
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, replyBoard.getBoardWriter());
			pstmt.setString(2, replyBoard.getBoardTitle());
			pstmt.setString(3, replyBoard.getBoardContent());
			pstmt.setInt(4, replyBoard.getBoardRef());
			if(replyBoard.getBoardReplyLev() == 1) {
				pstmt.setInt(5, replyBoard.getBoardReplySeq());
			} 
			if(replyBoard.getBoardReplyLev() == 2) {
				pstmt.setInt(5, replyBoard.getBoardReplyRef());
				pstmt.setInt(6, replyBoard.getBoardReplySeq());
			}
		
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int updateReply(Board board, Connection conn) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query = "update board set board_title = ?, board_content = ? where board_num = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, board.getBoardTitle());
			pstmt.setString(2, board.getBoardContent());
			pstmt.setInt(3, board.getBoardNum());
			System.out.println("title"+board.getBoardTitle());
			System.out.println("content"+board.getBoardContent());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int updateBoard(Board board, Connection conn) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query = "update board set board_title = ?, board_content = ?, board_original_filename = ?, board_rename_filename = ? where board_num = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, board.getBoardTitle());
			pstmt.setString(2, board.getBoardContent());
			pstmt.setString(3, board.getBoardOriginalFileName());
			pstmt.setString(4, board.getBoardRenameFileName());
			pstmt.setInt(5, board.getBoardNum());
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int deleteBoard(int boardNum, Connection conn) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query = "delete from board where board_num = ?";
		
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

	public ArrayList<Board> selectTitleList(String title, int currentPage, int limit, Connection conn) {
		ArrayList<Board> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		int startRow = (currentPage - 1) * limit + 1;
		int endRow = startRow + limit -1;
		String query = "select * "+ 
				"from (select rownum rnum, board_num, board_writer,board_title,board_content,board_original_filename,board_rename_filename,board_ref,board_reply_ref,board_reply_lev,board_reply_seq,board_readcount,board_date " + 
				"from (select * from board  where board_title like ? order by board_ref desc, board_reply_ref asc, board_reply_lev asc, board_reply_seq asc))" + 
				"where rnum >= ? and rnum <= ?";
		
		try {
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%"+title+"%");
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Board b = new Board();
				
				b.setBoardNum(rset.getInt("board_num"));
				b.setBoardWriter(rset.getString("board_writer"));
				b.setBoardTitle(rset.getString("board_title"));
				b.setBoardContent(rset.getString("board_content"));
				b.setBoardOriginalFileName(rset.getString("board_original_filename"));
				b.setBoardRenameFileName(rset.getString("board_rename_filename"));
				b.setBoardRef(rset.getInt("board_ref"));
				b.setBoardReplyRef(rset.getInt("board_reply_ref"));
				b.setBoardReplyLev(rset.getInt("board_reply_lev"));
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

	public ArrayList<Board> selectWriterList(String writer, int currentPage, int limit, Connection conn) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Board> selectDateList(Date begin, Date end, int currentPage, int limit, Connection conn) {
		// TODO Auto-generated method stub
		return null;
	}
}
