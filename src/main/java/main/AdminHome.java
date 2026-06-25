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

import been.Location;
import been.User;
import dao.AdminDisplayDao;

@WebServlet("/AdminHome")
public class AdminHome extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		
		String flg = request.getParameter("flg");

		if ("true".equals(flg)) {
			List<User> displayList = AdminDisplayDao.listdisplay();

		    request.setAttribute("displayList", displayList);

		    List<Location> locationList = AdminDisplayDao.locationName();
		    request.setAttribute("locationList", locationList);
			
			
		} else {
			HttpSession session = request.getSession(false);
		    User user = (User) session.getAttribute("user");
		    
		    // ログインユーザーの location_cd
		    String userLocation_id = user.getLocation_cd();
		   
		    List<User> displayList = AdminDisplayDao.listLocationDisplay(userLocation_id);

		    request.setAttribute("displayList", displayList);

		    List<Location> locationList = AdminDisplayDao.locationName();
		    request.setAttribute("locationList", locationList);
		}
		
	    
	    
	    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/admin.jsp");
	    rd.forward(request, response);
	}



	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
	}

}

