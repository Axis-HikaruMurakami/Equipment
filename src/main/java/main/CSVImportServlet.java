package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import been.Display;
import dao.CSVImportdao;
import dao.EquipmentDao;
import db.DBManager;

@WebServlet("/CSVImportServlet")
@MultipartConfig
public class CSVImportServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        Part filePart = request.getPart("csvfile");

        try (
                InputStream csvFile 	= filePart.getInputStream();
                BufferedReader reader 	= new BufferedReader(new InputStreamReader(csvFile, StandardCharsets.UTF_8));
                Connection conn 		= DBManager.getConnection()
        ) {
            conn.setAutoCommit(false); // トランザクション開始

            reader.readLine(); // ヘッダー行をスキップ

            
            //一括登録（CSV取り込み）では「備品番号」列の入力内容問わず、新ルールで採番するように処理が組まれている。
            //CSVの行ごとに毎回maxEquipmentId() を呼んでいるので、同じ「拠点」+「資産分類」の場合、同じ連番（max値）が返ってきてしまう。
            //問題解消のためにMAPの変換処理を使って、連番を管理しMax+1をできるように修正。
            
            Map<String, Integer> seqMap = new HashMap<>();

            String line;
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                String[] fields = line.split(",", -1);

                if (fields.length < 18) {
                    throw new IllegalArgumentException(
                        "CSVフォーマットエラー（" + lineNumber + "行目）: 列数が不足しています");
                }

                // 購入金額
                int purchasePrice;
                
                try {
                    purchasePrice = parseIntSafe(fields[10]);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException(
                        "CSVエラー（" + lineNumber + "行目）: 購入金額が整数でありません");
                }

                // 日付チェック
                if (!fields[8].isEmpty() && CSVImportdao.parseDate(fields[8]) == null) {
                    throw new IllegalArgumentException("CSVエラー（" + lineNumber + "行目）: 購入日の形式が不正です（" + fields[8] + "）");
                }
                if (!fields[14].isEmpty() && CSVImportdao.parseDate(fields[14]) == null) {
                    throw new IllegalArgumentException("CSVエラー（" + lineNumber + "行目）: 現在使用開始日の形式が不正です（" + fields[14] + "）");
                }
                if (!fields[15].isEmpty() && CSVImportdao.parseDate(fields[15]) == null) {
                    throw new IllegalArgumentException("CSVエラー（" + lineNumber + "行目）: 現在申請完了日の形式が不正です（" + fields[15] + "）");
                }
                
                // Display に格納
                Display display = new Display();
                display.setAssetName(fields[1]);
                display.setMaker(fields[2]);
                display.setModel(fields[3]);
                display.setType(fields[4]);
                display.setSerialnumber(fields[5]);
                display.setSp(fields[6]);
                display.setMacAddress(fields[7]);
                display.setPurchaseDate(CSVImportdao.parseDate(fields[8]));
                display.setPurchasePrice(purchasePrice);
                display.setCurrentuser(fields[11]);
                display.setLocation(fields[12]);
                display.setPreviousUser(fields[13]);
                display.setStartDate(CSVImportdao.parseDate(fields[14]));
                display.setApplicationCompletionDate(CSVImportdao.parseDate(fields[15]));

				String statusName = fields[16];
				display.setEquipmentStatusName(statusName);
				String statusCode = convertStatusNameToCode(statusName);
				display.setEquipmentStatus(statusCode);

                display.setNote(fields[17]);

                int assetNumber = CSVImportdao.getOrInsertAsset(conn, display.getAssetName());
                display.setAssetNumber(assetNumber);
                
                // ===== ID生成=====

				String locationName = display.getLocation();
				String locationCode = convertLocationNameToCd(locationName);

                
                String prefix = EquipmentDao.generateEquipmentIdPrefix(display.getLocation(), display.getAssetNumber());


				int seq;
				if (seqMap.containsKey(prefix)) {
				    seq = seqMap.get(prefix) + 1;
				} else {
				    seq = EquipmentDao.maxEquipmentId(locationCode, assetNumber);
				}
				seqMap.put(prefix, seq);


                String equipmentId = prefix + String.format("%03d", seq);
                display.setEquipmentId(equipmentId);

                // equipment 登録
                CSVImportdao.insertEquipment(
                        conn,
                        equipmentId,
                        String.valueOf(assetNumber),
                        display.getMaker(),
                        display.getModel(),
                        display.getType(),
                        display.getSerialnumber(),
                        display.getSp(),
                        display.getPurchaseDate(),
                        display.getPurchasePrice(),
                        display.getEquipmentStatus(),
                        display.getNote(),
                        display.getMacAddress()
                );
               
                
                // usage 登録
                CSVImportdao.insertUsage(
                        conn,
                        equipmentId,
                        display.getCurrentuser(),
                        display.getPreviousUser(),
                        locationCode,
                        display.getStartDate(),
                        display.getApplicationCompletionDate()
                );
            }

            conn.commit();

            request.setAttribute("successMessage", "CSVファイルのインポートに成功しました。");

            RequestDispatcher rd =request.getRequestDispatcher("/WEB-INF/file_Registration.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();

            request.setAttribute("errorMessage", "CSVファイルのインポートに失敗しました: " + e.getMessage());

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/file_Registration.jsp");
            rd.forward(request, response);
        }
    }

    
    // 整数変換
    private int parseIntSafe(String s) throws NumberFormatException {
        if (s == null || s.trim().isEmpty()) {
            return 0;
        }
        return Integer.parseInt(s.trim());
    }
    
    
    // CSVの備品ステータス名称をDB用コードに変換
    public String convertStatusNameToCode(String statusName) {
    	
        if (statusName == null || statusName.trim().isEmpty()) {
            return null;
        }
        
        switch (statusName.trim()) {
            case "未使用":
                return "1";
            case "使用中":
                return "2";
            case "故障中":
                return "3";
            case "廃棄":
                return "4";
            default:
                throw new IllegalArgumentException("不正な備品ステータスが指定されました: " + statusName);
        }
    }
    
 // CSVの使用場所（拠点名）をDB用コードに変換
    private String convertLocationNameToCd(String locationName) {

        if (locationName == null || locationName.trim().isEmpty()) {
            return null;
        }

        if (locationName.contains("本社")) {
            return "1";
        } else if (locationName.contains("仙台")) {
            return "2";
        } else if (locationName.contains("沖縄")) {
            return "3";
        } else if (locationName.contains("福岡")) {
            return "4";
        } else if (locationName.contains("大阪")) {
            return "5";
        }

        throw new IllegalArgumentException("不正な使用場所が指定されました: " + locationName);
    }
    
}