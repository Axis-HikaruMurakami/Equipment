package main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Logout")
public class Logout extends HttpServlet {
	 /**
     * セッション情報を破棄し、ログイン画面へリダイレクトするメソッド
     */
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {

        // HttpSessionのインスタンス化
        HttpSession session = request.getSession();

        // セッションスコープの破棄
        session.invalidate();

        // ログイン画面にリダイレクト
        response.sendRedirect("/Equipment/login.jsp");
    }
}
