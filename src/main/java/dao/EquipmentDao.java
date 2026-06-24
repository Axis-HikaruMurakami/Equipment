package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import db.DBManager;

public class EquipmentDao {


	
    public static void updateEquipment(
            String equipmentId,
            int assetNumber,
            String maker,
            String model,
            String type,
            String serialnumber,
            String sp,
            String macAd,
            Date purchaseDate,
            int purchasePrice,
            String currentuser,
            String location,
            String previousUser,
            Date startDate,
            Date appCompletionDate,
            String equipmentStatus,
            String notes) {

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DBManager.getConnection();

            // ---------- equipment 更新 ----------
            String sql =
                "UPDATE equipment SET " +
                "asset_number = ?, " +
                "maker = ?, " +
                "model = ?, " +
                "TYPE = ?, " +
                "serialnumber = ?, " +
                "sp = ?, " +
                "mac_ad = ?, " +
                "purchase_date = ?, " +
                "purchase_price = ?, " +
                "equipment_status = ?, " +
                "notes = ?, " +
                "delete_equipment = ?, " +
                "location_cd = ? " +
                "WHERE equipment_id = ?";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, assetNumber);
            ps.setString(2, maker);
            ps.setString(3, model);
            ps.setString(4, type);
            ps.setString(5, serialnumber);
            ps.setString(6, sp);
            ps.setString(7, macAd);
            ps.setDate(8, purchaseDate);
            ps.setInt(9, purchasePrice);
            ps.setString(10, equipmentStatus);
            ps.setString(11, notes);
            ps.setBoolean(12, false);     // delete_equipment
            ps.setString(13, location);   // location_cd
            ps.setString(14, equipmentId);
            ps.executeUpdate();
            ps.close();

            // ---------- u_sege 更新 ----------
            sql =
                "UPDATE u_sege SET " +
                "currentuser = ?, " +
                "previous_user = ?, " +
                "location = ?, " +
                "start_date = ?, " +
                "application_completion_date = ? " +
                "WHERE equipment_id = ?";

            ps = conn.prepareStatement(sql);
            ps.setString(1, currentuser);
            ps.setString(2, previousUser);
            ps.setString(3, location);
            ps.setDate(4, startDate);
            ps.setDate(5, appCompletionDate);
            ps.setString(6, equipmentId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBManager.close(ps);
            DBManager.close(conn);
        }
    }



    // 新規登録
    public static void insertEquipment(
    		String	equipmentId,
            int		assetNumber,
            String	maker,
            String	model,
            String	type,
            String	serialnumber,
            String	sp,
            String	macAd,
            Date	purchaseDate,
            int		purchasePrice,
            String	currentuser,
            String	location,
            String	previousUser,
            Date	startDate,
            Date	appCompletionDate,
            String	equipmentStatus,
            String	notes) {

        Connection conn			= null;
        PreparedStatement ps 	= null;

        try {
            conn = DBManager.getConnection();

            String sql =
				"INSERT INTO equipment ( " +
				"equipment_id, " +
				"asset_number, " +
				"maker, " +
				"model, " +
				"TYPE, " +
				"serialnumber, " +
				"sp, " +
				"mac_ad, " +
				"purchase_date, " +
				"purchase_price, " +
				"equipment_status, " +
				"notes, " +
				"delete_equipment, " +
				"location_cd " +
				") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

            ps = conn.prepareStatement(sql);
            ps.setString(1, equipmentId);
            ps.setInt(2, assetNumber);
            ps.setString(3, maker);
            ps.setString(4, model);
            ps.setString(5, type);
            ps.setString(6, serialnumber);
            ps.setString(7, sp);
            ps.setString(8, macAd);
            ps.setDate(9, purchaseDate);
            ps.setInt(10, purchasePrice);
            ps.setString(11, notes);
            ps.setString(12, equipmentStatus);
            ps.setBoolean(13, false);
            ps.setString(14, location);
            
            ps.executeUpdate();

            ps.close();

            sql =
				"INSERT INTO u_sege (" +
				"equipment_id, " +
				"currentuser, " +
				"previous_user, " +
				"location, " +
				"start_date, " +
				"application_completion_date" +
				") VALUES (?, ?, ?, ?, ?, ?) ";

            ps = conn.prepareStatement(sql);
            ps.setString(1, equipmentId);
            ps.setString(2, currentuser);
            ps.setString(3, previousUser);
            ps.setString(4, location);
            ps.setDate(5, startDate);
            ps.setDate(6, appCompletionDate);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBManager.close(ps);
            DBManager.close(conn);
        }
    }


    // equipment_id 生成


	public static String generateEquipmentIdNew(String location, int assetNumber) {
	
	    // AX+拠点+資産分類
	    String prefix = generateEquipmentIdPrefix(location, assetNumber);
	
	    // DBからmax+1検索
	    int nextNo = maxEquipmentId(location, assetNumber);
	
	    // 3桁連番付与
	    return prefix + String.format("%03d", nextNo);
	}
    
    
	
    // 自動採番（末尾3桁）

    public static int maxEquipmentId(String location, int assetNumber) {

        Connection conn			= null;
        PreparedStatement ps	= null;
        ResultSet rs			= null;

        int nextNumber 	= 1;
        String prefix 	= generateEquipmentIdPrefix(location, assetNumber);

        try {
            conn = DBManager.getConnection();

            String sql =
				"SELECT MAX(CAST(RIGHT(equipment_id, 3) AS UNSIGNED)) AS max_no " +
				"FROM equipment.equipment " +
				"WHERE equipment_id LIKE ?";

            ps = conn.prepareStatement(sql);
            ps.setString(1, prefix + "%");
            rs = ps.executeQuery();

            if (rs.next()) {
            	
                int maxNo = rs.getInt("max_no");
                
                if (!rs.wasNull()) {
                    nextNumber = maxNo + 1;
                }
            }
        } catch (Exception e) {
        	
            e.printStackTrace();
            
        } finally {
            DBManager.close(rs);
            DBManager.close(ps);
            DBManager.close(conn);
        }

        return nextNumber;
    }
    
    

    // 拠点 + 資産分類 判定処理

    public static String generateEquipmentIdPrefix(String location, int assetNumber) {

    	// 拠点判定
        String location_id_new = "";
        
			if ("1".equals(location)) {
				// 何もしない。
    		} else if ("2".equals(location)) {
            	location_id_new = "S";
            } else if ("3".equals(location)) {
            	location_id_new = "OK";
            } else if ("4".equals(location)) {
            	location_id_new = "F";
            } else if ("5".equals(location)) {
            	location_id_new = "OS";
            } else {
                throw new IllegalArgumentException("不正な拠点コード: " + location);
            }


        // 資産分類判定
        String asset_id = "";

        if (assetNumber == 1) {
            asset_id = "PCL";   		// ノートPC
        } else if (assetNumber == 2) {
        	asset_id = "PCD";   		// デスクトップPC
        } else if (assetNumber == 3) {
        	asset_id = "PCK";   		// キーボード
        } else if (assetNumber == 4) {
        	asset_id = "DSP";  		 	// モニター
        } else if (assetNumber == 5) {
        	asset_id = "PCM";   		// マウス
        } else if (assetNumber == 6) {
        	asset_id = "PCN";   		// プリンタ
        } else if (assetNumber == 7) {
        	asset_id = "PJT";   		// プロジェクター
        } else {
            throw new IllegalArgumentException("不正な資産分類番号: " + assetNumber);
        }


        return "AX" + location_id_new + asset_id;
    }

}



