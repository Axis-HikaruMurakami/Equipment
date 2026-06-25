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
import dao.Userdao;

@WebServlet("/AdminDeleteServlet")
public class AdminDelete extends HttpServlet {
	 // POSTリクエストが送られてきたときに呼ばれるメソッド
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

    	    HttpSession session = request.getSession(false);
    	    User user = (User) session.getAttribute("user");
    	    
    	    // ★ ログイン後に location_cd を取得
    	    String location_cd = Userdao.getLocation_cd(user.getUser_id());
    	    user.setLocation_cd(location_cd);
            // フロントエンドから送信されるパラメータを取得
            String userId = request.getParameter("userId");

            // ここでAdminDisplayDao.deleteメソッドを呼び出し、ユーザを論理削除
            int deletepoint = AdminDisplayDao.delete(userId);  // userIDを指定して削除

            // 削除が成功した場合（1以上が返る場合）
            if (deletepoint > 0) {
                // 削除後、更新された表示リストを取得
                List<User> displayList = AdminDisplayDao.listLocationDisplay(location_cd);
                
                // 取得したリストをリクエスト属性にセット
                request.setAttribute("displayList", displayList);
                
                List<Location> locationList = AdminDisplayDao.locationName();
    			
    			request.setAttribute("locationList", locationList);

                // admin.jsp にリクエストを転送
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/admin.jsp");
                rd.forward(request, response);
                
            } else {
                // 削除が失敗した場合、エラーメッセージをセット
                request.setAttribute("error", "削除に失敗しました。");

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/admin.jsp");
                
                rd.forward(request, response);
            }
            
        } catch (Exception e) {
            e.printStackTrace();  // エラーメッセージをコンソールに出力
            // エラー時のエラーメッセージをリクエスト属性にセット
            request.setAttribute("error", "処理中にエラーが発生しました。");

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/admin.jsp");
            rd.forward(request, response);
        }
    }
}
