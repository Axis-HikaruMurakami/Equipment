package main;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import been.Display;
import been.User;
import dao.Displaydao;
import dao.Userdao;

@WebServlet("/ExportCSVServlet")
public class ExportCSVServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

	    HttpSession session = request.getSession(false);
	    User user = (User) session.getAttribute("user");
	    
	    // ★ ログイン後に location_cd を取得
	    String location_cd = Userdao.getLocation_cd(user.getUser_id());
	    user.setLocation_cd(location_cd);
        
        List<Display> displayList = Displaydao.listdisplay(location_cd);
        
        if (displayList == null || displayList.isEmpty()) {
            request.setAttribute("errorMessage", "検索条件に該当する備品がありません。");
            request.getRequestDispatcher("/WEB-INF/main.jsp").forward(request, response);
            return;
        }

        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"equipment_list.csv\"");

        // UTF-8 BOMの書き込み（Excelでの文字化け防止）
        final byte[] bom = {(byte)0xEF, (byte)0xBB, (byte)0xBF};
        response.getOutputStream().write(bom);

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8))) {

            // CSVヘッダー
            writer.write("備品番号,資産分類,メーカー,機種,TYPE,S/N,スペック,MACアドレス,購入日,経過年,購入金額,使用者,使用場所,前使用者,現在使用開始日,現在申請完了日,備品ステータス,その他");
            writer.newLine();

            // 各行の出力
            for (Display item : displayList) {
            	writer.write(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
            	        item.getEquipmentId(),
            	        safe(item.getAssetName()),
            	        safe(item.getMaker()),
            	        safe(item.getModel()),
            	        safe(item.getType()),
            	        safe(item.getSerialnumber()),
            	        safe(item.getSp()),
            	        safe(item.getMacAddress()),
            	        formatDate(item.getPurchaseDate()),
            	        safe(String.valueOf(item.getOld())),
            	        formatNumber(item.getPurchasePrice()),
            	        safe(item.getCurrentuser()),
            	        item.getLocation(),
            	        safe(item.getPreviousUser()),
            	        formatDate(item.getUseStartDate()),
            	        formatDate(item.getApplyCompleteDate()),
            	        safe(item.getEquipmentStatusName()),
            	        safe(item.getNote())
            	));

                writer.newLine();
            }

            writer.flush();
        }
    }

    // 文字列がnullのときは空文字列に。CSVルールに基づき、カンマや改行、ダブルクォーテーションが含まれている場合は"で囲む
    private String safe(String str) {
    	
        if (str == null) return "";
        
        if (str.contains(",") || str.contains("\"") || str.contains("\n")) {
            
        	str = str.replace("\"", "\"\""); // ダブルクォーテーションを2つに
            
            return "\"" + str + "\"";
            
        }
        return str;
    }

    
    // 日付フォーマット変換（nullは空文字）
    private String formatDate(Date date) {
    	
        return date == null ? "" : new SimpleDateFormat("yyyy/MM/dd").format(date);
        
    }

    
    // 数値フォーマット（nullまたは0のときは空文字）
    private String formatNumber(Integer number) {
    	
        if (number == null || number == 0) {
            return "";
        }
        
        return String.valueOf(number);  // ← カンマなし（150000）
    }

}