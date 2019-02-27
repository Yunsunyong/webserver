package member.model.service;

import member.model.dao.MemberDao;
import member.model.vo.Member;

import static common.JDBCTemplate.*;

import java.sql.Connection;

public class MemberService {
	private MemberDao mdao = new MemberDao();
	
	public MemberService() {}
	
	public Member selectLogin(String userId, String userPwd) {
		Connection conn = getConnection();
		Member loginUser = mdao.selectLogin(userId, userPwd, conn);
		close(conn);
		return loginUser;
	}
}
