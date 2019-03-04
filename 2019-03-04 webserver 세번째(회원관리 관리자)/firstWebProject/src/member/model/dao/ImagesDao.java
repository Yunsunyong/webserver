package member.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import static common.JDBCTemplate.*;

public class ImagesDao {

	public int insertIamge(Connection conn, String fileName) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query = "insert into images values (sq_imageid.nextval, ?)";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, fileName);
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

}
