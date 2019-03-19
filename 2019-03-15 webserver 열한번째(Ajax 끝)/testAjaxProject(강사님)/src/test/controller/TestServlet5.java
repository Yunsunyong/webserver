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
 * Servlet implementation class TestServlet5
 */
@WebServlet("/t5.do")
public class TestServlet5 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestServlet5() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int no =Integer.parseInt(request.getParameter("no"));
		System.out.println("no : " + no);
		
		ArrayList<User> list = new ArrayList<>();
		list.add(new User("u1234","p1234","홍길동",27,"h1234@kh.org","010-1234-5678"));
		list.add(new User("u2345","p2345","박문수",33,"h2345@kh.org","010-2345-6789"));
		list.add(new User("u3456","p3456","장영실",45,"h3456@kh.org","010-3456-7890"));
		list.add(new User("u4567","p4567","이순신",52,"h4567@kh.org","010-4567-8901"));
		list.add(new User("u5678","p5678","유관순",19,"h5678@kh.org","010-5678-9012"));
		
		//전송할 json 객체 준비
		JSONObject sendJson = new JSONObject();
		// list 처리할 json 배열객체준비
		JSONArray jsonArr = new JSONArray();
		
		//리스트(또는 맵)에서 객체를 하나씩 꺼냄
		for(User user : list) {
			//꺼낸 user 객체 정보를 저장할 json 객체 준비
			JSONObject userJson = new JSONObject();
			//user 객체가 가진 필드값 한개씩 꺼내서 
			//jsonUser 에 옮겨 기록 저장하기
			userJson.put("userid", user.getUserId());
			userJson.put("userpwd", user.getUserPwd());
			userJson.put("username", URLEncoder.encode(user.getUserName(), "utf-8"));
			userJson.put("age", user.getAge());
			userJson.put("email", user.getEmail());
			userJson.put("phone", user.getPhone());
			
			//json 객체를 json 배열에 기록저장
			jsonArr.add(userJson);
		}
		
		//json 배열은 전송할 수 없음.
		//전송용 json 객체에 json 배열을 저장함
		sendJson.put("list", jsonArr);
		System.out.println("t5 + : " + sendJson);
		
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(sendJson.toJSONString());
		out.flush();
		out.close();
		//내보내기 : ajax 통신은 출력스트림 필요
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
