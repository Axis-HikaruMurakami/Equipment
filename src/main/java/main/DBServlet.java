package main;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.DBManager;

@WebServlet("/dbtest") // アクセスURL: http://localhost:8080/プロジェクト名/dbtest
public class DBServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<html><body>");
        out.println("<h2>データベース接続テスト</h2>");

        try (Connection conn = DBManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM user")) {

            out.println("<ul>");
            while (rs.next()) {
                String name = rs.getString("user_id");
                out.println("<li>" + name + "</li>");
            }
            out.println("</ul>");

        } catch (Exception e) {
            out.println("<p>接続またはクエリに失敗しました。</p>");
            e.printStackTrace(out); // エラーもブラウザに表示
        }

        out.println("</body></html>");
    }
}