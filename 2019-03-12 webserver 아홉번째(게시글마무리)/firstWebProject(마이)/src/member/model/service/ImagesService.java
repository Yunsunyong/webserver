package member.model.service;

import static common.JDBCTemplate.*;

import java.sql.Connection;

import member.model.dao.ImagesDao;

public class ImagesService {
	private ImagesDao idao = new ImagesDao();
	
	
	public ImagesService() {}
	
	public int insertImage(String fileName) {
		Connection conn = getConnection();
		int result = idao.insertIamge(conn, fileName);
		close(conn);
		return result;
	}
}
