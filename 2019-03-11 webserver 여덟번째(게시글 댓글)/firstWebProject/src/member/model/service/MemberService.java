package member.model.service;

import member.model.dao.MemberDao;
import member.model.vo.Member;

import static common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.ArrayList;

public class MemberService {
	private MemberDao mdao = new MemberDao();
	
	public MemberService() {}
	
	public Member selectLogin(String userId, String userPwd) {
		Connection conn = getConnection();
		Member loginUser = mdao.selectLogin(userId, userPwd, conn);
		close(conn);
		return loginUser;
	}

	public int selectCheckId(String userId) {
		Connection conn = getConnection();
		int result = mdao.selectCheckId(userId, conn);
		close(conn);
		return result;
	}

	public int insertMember(Member member) {
		Connection conn = getConnection();
		int result = mdao.insertMember(member, conn);
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public Member selectMember(String userId) {
		Connection conn = getConnection();
		Member member = mdao.selectMember(conn, userId);
		close(conn);
		return member;
	}

	public int updateMember(Member member) {
		Connection conn = getConnection();
		int result = mdao.updateMember(member, conn);
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public int deleteMember(String userId) {
		Connection conn = getConnection();
		int result = mdao.deleteMember(userId, conn);
		if(result > 0 ) {
			commit(conn);
		}else {
			rollback(conn);
		}
		return result;
	}

	public ArrayList<Member> selectList() {
		Connection conn = getConnection();
		ArrayList<Member> list = mdao.selectList(conn);
		close(conn);
		return list;
	}
}
