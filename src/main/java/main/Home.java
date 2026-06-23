package main;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import been.Asset;
import been.Display;
import been.User;
import dao.Displaydao;
import dao.Userdao;

@WebServlet("/Home")
public class Home extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		

	    HttpSession session = request.getSession(false);
	    User user = (User) session.getAttribute("user");
	    
	    // ★ ログイン後に location_cd を取得
	    String location_cd = Userdao.getLocation_cd(user.getUser_id());
	    user.setLocation_cd(location_cd);

	    // セッションに保存
	    session.setAttribute("user", user);

	    // ログインユーザーの location_cd
	    String userLocation = user.getLocation_cd();
	    session.setAttribute("user",user);
	    
	   
	    List<Display> displayList = Displaydao.listdisplay(location_cd);
	    
//	    // location_cd が一致するものだけに絞る
//	    List<Display> filteredList = new ArrayList<>();
//
//	    for (Display d : displayList) {
//	        if (userLocation.equals(d.getLocation())) {
//	            filteredList.add(d);
//	        }
//	    }

	    request.setAttribute("displayList", displayList);

	    List<Asset> assetname = Displaydao.assetName();
	    request.setAttribute("assetname", assetname);

	    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/main.jsp");
	    rd.forward(request, response);
	}



	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String 	id 			= request.getParameter("id");
		String 	password 	= request.getParameter("password");
		User 	user 		= Userdao.login(id, password);
		
		
		if(user!=null) {

		    // ★ ログイン後に location_cd を取得
		    String location_cd = Userdao.getLocation_cd(user.getUser_id());
		    user.setLocation_cd(location_cd);
			
			List<Display> displayList = Displaydao.listdisplay(location_cd);
			
			request.setAttribute("displayList", displayList);
			
			
			List<Asset> assetname = Displaydao.assetName();
			
			request.setAttribute("assetname", assetname);
			
			
			//成功時セッションを保存してmain.jspへ
			HttpSession session1 = request.getSession();
			session1.setAttribute("user", user);
			
			HttpSession session = request.getSession();
			session.setAttribute("displayList", displayList);
			
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/main.jsp");
			rd.forward(request, response);
			
		} else {
			
			//nullの場合はログインエラーでログイン画面に遷移
		    request.setAttribute("error", "true");
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);
		}
	}
}
