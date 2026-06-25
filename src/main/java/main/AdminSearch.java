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

@WebServlet("/AdminSearch")
public class AdminSearch extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");

		String userId = request.getParameter("userId");
		String userName = request.getParameter("userName");
		String locationName = request.getParameter("locationName");
		String deleteFlag = request.getParameter("deleteFlag");
		//空文字を判定してnullを入れる
		if (userId != null && userId.isEmpty()) {
		    userId = null;
		}

		if (userName != null && userName.isEmpty()) {
		    userName = null;
		}

		if (locationName == null || locationName.isEmpty()) {
			HttpSession session = request.getSession(false);
		    User user = (User) session.getAttribute("user");

		    // ログインユーザーの location_cd
		    String userLocation_id = user.getLocation_cd();

		    switch (userLocation_id) {//今後支店が増え
		        case "1":
		            locationName = "本社";
		            break;

		        case "2":
		            locationName = "仙台支店";
		            break;

		        case "3":
		            locationName = "沖縄支店";
		            break;

		        case "4":
		            locationName = "福岡支店";
		            break;

		        case "5":
		            locationName = "大阪支店";
		            break;
		    }
		    
		}
		
		try {

			// 削除フラグ判定（null or 空ならnullに）
			Integer deleteFlagInt = null;
			if (deleteFlag != null && !deleteFlag.isEmpty()) {
				deleteFlagInt = Integer.parseInt(deleteFlag);
			}

			// DAOを呼び出し（削除フラグ, ステータスも渡す）
			List<User> searchList = AdminDisplayDao.searchDisplay(userId, userName, locationName, deleteFlagInt);
			List<Location> locationList = AdminDisplayDao.locationName();
			
			
			// 検索結果と拠点名リストをセット
			request.setAttribute("displayList", searchList);
			request.setAttribute("locationList", locationList);
			

	        
			// 検索条件の保持
			request.setAttribute("userId", userId);
			request.setAttribute("userName", userName);
			request.setAttribute("locationName", locationName);
			request.setAttribute("deleteFlag", deleteFlag);
			
			
//			HttpSession session = request.getSession();//いらないかも。セッションに入れてる意味がどこかにあるのかも
//			session.setAttribute("displayList", searchList);

			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/admin.jsp");
			rd.forward(request, response);

		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
}
