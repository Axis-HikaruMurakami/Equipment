package main;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import been.Asset;
import been.Display;
import dao.Displaydao;

@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {
	 // POSTリクエストが送られてきたときに呼ばれるメソッド
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // フロントエンドから送信されるパラメータを取得
            String equipmentIdStr = request.getParameter("equipmentId");

            // ここでDisplaydao.deleteメソッドを呼び出し、備品を削除
            // 使用者IDも削除する場合は、displaydao.deleteで両方を削除する処理を追加すること
            int deletepoint = Displaydao.delete(equipmentIdStr);  // 備品IDを指定して削除

            // 削除が成功した場合（1以上が返る場合）
            if (deletepoint > 0) {
                // 削除後、更新された表示リストを取得
                List<Display> displayList = Displaydao.listdisplay();
                
                // 取得したリストをリクエスト属性にセット
                request.setAttribute("displayList", displayList);
                
                List<Asset> assetname = Displaydao.assetName();
    			
    			request.setAttribute("assetname", assetname);

                // main.jsp にリクエストを転送
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/main.jsp");
                rd.forward(request, response);
                
            } else {
                // 削除が失敗した場合、エラーメッセージをセット
                request.setAttribute("error", "削除に失敗しました。");

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/main.jsp");
                
                rd.forward(request, response);
            }
            
        } catch (Exception e) {
            e.printStackTrace();  // エラーメッセージをコンソールに出力
            // エラー時のエラーメッセージをリクエスト属性にセット
            request.setAttribute("error", "処理中にエラーが発生しました。");

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/main.jsp");
            rd.forward(request, response);
        }
    }
}