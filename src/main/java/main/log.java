package main;

import java.io.IOException;
import java.util.Collections;
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

@WebServlet("/log")
public class log extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    String flgStr = request.getParameter("flg");
	    Boolean flg = Boolean.parseBoolean(flgStr);

	    if (!flg) {

	        // deledis() が null の場合、空のリストを使用
	        List<Display> deletedisplayList = Displaydao.deledis();
	        if (deletedisplayList == null) {
	            deletedisplayList = Collections.emptyList(); // 空のリストにする
	        }

	        request.setAttribute("displayList", deletedisplayList);
	        List<Asset> assetname = Displaydao.assetName();

	        request.setAttribute("assetname", assetname);
	        HttpSession session = request.getSession();
	        session.setAttribute("displayList", deletedisplayList);

	        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/main.jsp");
	        rd.forward(request, response);

	    } else {

		    HttpSession session = request.getSession(false);
		    User user = (User) session.getAttribute("user");
		    
		    // ★ ログイン後に location_cd を取得
		    String location_cd = Userdao.getLocation_cd(user.getUser_id());
		    user.setLocation_cd(location_cd);
		    
	        List<Display> displayList = Displaydao.listdisplay(location_cd);

	        request.setAttribute("displayList", displayList);
	        List<Asset> assetname = Displaydao.assetName();

	        request.setAttribute("assetname", assetname);
	        session = request.getSession();
	        session.setAttribute("displayList", displayList);

	        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/main.jsp");
	        rd.forward(request, response);

	    }
	}

}
