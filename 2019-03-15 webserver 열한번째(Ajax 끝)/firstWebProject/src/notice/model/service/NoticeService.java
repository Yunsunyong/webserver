package notice.model.service;

import static common.JDBCTemplate.*;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import notice.model.dao.NoticeDao;
import notice.model.vo.Notice;

public class NoticeService {
	private NoticeDao ndao = new NoticeDao();
	
	public NoticeService() {}
	
	// 공지사항 전체조회
	public HashMap<Integer, Notice> selectMap() {
		Connection conn = getConnection();
		HashMap<Integer, Notice> noticeMap = ndao.selectMap(conn);
		close(conn);
		return noticeMap;
	}

	// 상세보기
	public Notice selectOne(int noticeNo) {
		Connection conn = getConnection();
		Notice notice = ndao.selectOne(conn, noticeNo);
		close(conn);
		return notice;
	}

	// 글 등록
	public int insertNotice(Notice notice) {
		Connection conn = getConnection();
		int result = ndao.insertNotice(conn, notice);
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		return result;
	}

	// 글 수정
	public int updateNotice(Notice notice) {
		Connection conn = getConnection();
		int result = ndao.updateNotice(conn, notice);
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		return result;
	}

	// 글 삭제
	public int deleteNotcie(int noticeNo) {
		Connection conn = getConnection();
		int result = ndao.deleteNotcie(conn, noticeNo);
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		return result;
	}

	public HashMap<Integer, Notice> selectSearchTitle(String noticeTitle) {
		Connection conn = getConnection();
		HashMap<Integer, Notice> map = ndao.seletSearchTitle(noticeTitle, conn);
		close(conn);
		return map;
	}

	public HashMap<Integer, Notice> selectSearchWriter(String noticeWriter) {
		Connection conn = getConnection();
		HashMap<Integer, Notice> map = ndao.seletSearchWriter(noticeWriter, conn);
		close(conn);
		return map;
	}

	public HashMap<Integer, Notice> selectSearchDate(Date beginDate, Date endDate) {
		Connection conn = getConnection();
		HashMap<Integer, Notice> map = ndao.seletSearchDate(beginDate,endDate, conn);
		close(conn);
		return map;
	}

	public ArrayList<Notice> selectTop5Write() {
		Connection conn = getConnection();
		ArrayList<Notice> list = ndao.selectTop5Write(conn);
		close(conn);
		return list;
	}
	
}
