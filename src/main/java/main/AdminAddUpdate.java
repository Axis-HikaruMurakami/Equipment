package main;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import been.Location;
import been.User;
import dao.AdminDisplayDao;

@WebServlet("/AdminAddUpdate")
public class AdminAddUpdate extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idStr = request.getParameter("id");
		String mode = request.getParameter("mode");

		List<Location> locationList = AdminDisplayDao.locationName();
		request.setAttribute("locationList", locationList);

		if ("add".equals(mode)) {

			// 登録用画面へ
			request.setAttribute("mode", "add");
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/adminUpdate.jsp");
			rd.forward(request, response);

		} else if (idStr != null && !idStr.isEmpty()) {

			// 修正用画面へs
			// DAO から該当の備品情報を取得
			String id = idStr;
			User update = AdminDisplayDao.update(id);

			request.setAttribute("mode", "adminUpdate");
			request.setAttribute("update", update);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/adminUpdate.jsp");
			rd.forward(request, response);
		} else {
			// 不正アクセス時などの処理
			response.sendRedirect("/Equipment/main.jsp");
		}
	}

}
