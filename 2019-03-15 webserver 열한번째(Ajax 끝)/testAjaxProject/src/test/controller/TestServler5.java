package test.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import test.model.vo.User;

/**
 * Servlet implementation class TestServler5
 */
@WebServlet("/t5.do")
public class TestServler5 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestServler5() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// test5 버튼 클릭시 요청되는 컨트롤러
		int no = Integer.parseInt(request.getParameter("no"));
		System.out.println("t5 : " + no);
		
		ArrayList<User> userList = new ArrayList<>();
		userList.add(new User("u1234","p1234","홍길동",27,"h1234@kh.org","010-1234-5678"));
		userList.add(new User("u1235","p1235","홍길당",26,"h1235@kh.org","010-1234-5677"));
		userList.add(new User("u1236","p1236","홍길덩",25,"h1236@kh.org","010-1234-5676"));
		userList.add(new User("u1237","p1237","홍길똥",24,"h1237@kh.org","010-1234-5675"));
		userList.add(new User("u1238","p1238","홍길땅",23,"h1238@kh.org","010-1234-5674"));
		
		//전송할 json 객체 준비
		JSONObject sendJson = new JSONObject();
		//리스트 객체들을 저장할 json 배열 객체 준비
		JSONArray jsonArr = new JSONArray();
		
		//리스트(또는 맵)에서 객체를 하나씩 꺼냄
		for(User user : userList) {
			//꺼낸 user 객체 정보를 저장할 json 객체 준비
			JSONObject userJson = new JSONObject();
			//user 객체가 가진 필드값 한개씩 꺼내서 
			//jsonUser 에 옮겨 기록 저장하기 
			userJson.put("userid", user.getUserId());
			userJson.put("userpwd", user.getUserPwd());
			userJson.put("username", URLEncoder.encode(user.getUserName(),"UTF-8"));
			userJson.put("age", user.getAge());
			userJson.put("email", user.getEmail());
			userJson.put("phone", user.getPhone());

			//json 객체를 json 배열에 기록 저장
			jsonArr.add(userJson);
		}
		
		//json 배열은 전송할 수 없음
		//전송용 json 객체에 json 배열을 저장함
		sendJson.put("list", jsonArr);
		System.out.println("t5 : " + sendJson.toJSONString());
		
		//내보내기 : ajax 통신은 출력스트림 필요
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(sendJson.toJSONString());
		out.flush();
		out.close();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
