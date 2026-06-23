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

@WebServlet("/UserSearch")
public class AdminSearch extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String userId = request.getParameter("userId");
		String userName = request.getParameter("userName");
		String locationName = request.getParameter("locationName");
		String deleteFlag = request.getParameter("deleteFlag");

		try {

			// 削除フラグ判定（null or 空ならnullに）
			Integer deleteFlagInt = null;
			if (deleteFlag != null && !deleteFlag.isEmpty()) {
				deleteFlagInt = Integer.parseInt(deleteFlag);
			}

			// DAOを呼び出し（削除フラグ, ステータスも渡す）
			List<User> searchList = AdminDisplayDao.searchDisplay(userId, userName, locationName, deleteFlagInt);
			List<Location> locationList = AdminDisplayDao.locationName();

			// 検索結果と資産名リストをセット
			request.setAttribute("displayList", searchList);
			request.setAttribute("assetname", locationList);

			// 検索条件の保持
			request.setAttribute("userId", userId);
			request.setAttribute("userName", userName);
			request.setAttribute("locationName", locationName);
			request.setAttribute("deleteFlag", deleteFlag);

			HttpSession session = request.getSession();//いらないかも。セッションに入れてる意味がどこかにあるのかも
			session.setAttribute("displayList", searchList);

			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/main.jsp");
			rd.forward(request, response);

		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
}
