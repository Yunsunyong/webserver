package member.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import member.model.vo.Member;
import static common.JDBCTemplate.*;

public class MemberDao {
	
	public MemberDao() {}

	public Member selectLogin(String userId, String userPwd, Connection conn) {
		Member loginUser = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String query = "select * from member where userid = ? and userpwd = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userId);
			pstmt.setString(2, userPwd);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				loginUser = new Member();
				
				loginUser.setUserId(userId);
				loginUser.setUserPwd(userPwd);
				loginUser.setUserName(rset.getString("username"));
				loginUser.setGender(rset.getString("gender"));
				loginUser.setAge(rset.getInt("age"));
				loginUser.setPhone(rset.getString("phone"));
				loginUser.setEmail(rset.getString("email"));
				loginUser.setHobby(rset.getString("hobby"));
				loginUser.setEtc(rset.getString("etc"));
				loginUser.setEnrollDate(rset.getDate("enroll_date"));
				loginUser.setLastModified(rset.getDate("lastmodified"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return loginUser;
	}
}
