package main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.Userdao;

@WebServlet("/AddUser")
public class AddUser extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		// 入力値の取得
		String user_id = request.getParameter("user_id");

		String user_name = request.getParameter("user_name");

		String password = request.getParameter("password");

		String location_cd = request.getParameter("location_cd");

		String adminFlgStr = request.getParameter("admin_flg");

		// 必須チェック

				if(user_id == null || user_id.trim().isEmpty()) {

					request.setAttribute("userIdError", "ユーザIDは必須です。");

					request.getRequestDispatcher("/WEB-INF/adminAddUpdate.jsp")
					.forward(request,response);

					return;
				}



				if(user_name == null || user_name.trim().isEmpty()) {

					request.setAttribute("userNameError", "ユーザ名は必須です。");

					request.getRequestDispatcher("/WEB-INF/adminAddUpdate.jsp")
					.forward(request,response);

					return;
				}



				if(password == null || password.trim().isEmpty()) {

					request.setAttribute("passwordError", "パスワードは必須です。");

					request.getRequestDispatcher("/WEB-INF/adminAddUpdate.jsp")
					.forward(request,response);

					return;
				}



				if(location_cd == null || location_cd.trim().isEmpty()) {

					request.setAttribute("locationError", "所属は必須です。");

					request.getRequestDispatcher("/WEB-INF/adminAddUpdate.jsp")
					.forward(request,response);

					return;
				}



				if(adminFlgStr == null || adminFlgStr.trim().isEmpty()) {

					request.setAttribute("adminFlgError", "管理者権限は必須です。");

					request.getRequestDispatcher("/WEB-INF/adminAddUpdate.jsp")
					.forward(request,response);

					return;
				}

				Integer admin_flg = Integer.parseInt(adminFlgStr);

		// DAOで登録
		Userdao.insertUser(user_id,user_name,password,location_cd,admin_flg);

		// main.jsp にリダイレクト
		response.sendRedirect("/Equipment/AdminHome");
	}
}
