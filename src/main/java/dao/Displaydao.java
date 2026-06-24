package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import been.Asset;
import been.Display;
import db.DBManager;

public class Displaydao {
	//一覧表示用
	public static List<Display> listdisplay(String location_cd) {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Display> displayList = new ArrayList<>();

		try {
			//データベース接続
			conn = DBManager.getConnection();
			
			//trueかつ自分の所属のものだけ一覧表示
			String sql =
				    "SELECT " +
				    "eq.equipment_id AS equipment_id, " +
				    "a.asset_name AS asset_name, " +
				    "eq.maker AS maker, " +
				    "eq.model AS model, " +
				    "eq.TYPE AS TYPE, " +
				    "eq.serialnumber AS serialnumber, " +
				    "eq.sp AS spec, " +
				    "eq.mac_ad AS mac_address, " +
				    "eq.purchase_date AS purchase_date, " +
				    "TIMESTAMPDIFF(YEAR, eq.purchase_date, CURDATE()) AS years_since, " +
				    "eq.purchase_price AS purchase_price, " +
				    "u.currentuser AS currentuser, " +
				    "l.location_name AS location, " +  // ← equipment.location_cd を参照
				    "u.start_date AS use_start_date, " +
				    "u.application_completion_date AS apply_complete_date, " +
				    "u.previous_user AS previous_user, " +
				    "eq.equipment_status AS equipment_status, " +
				    "es.equipment_status_name AS equipment_status_name, " +
				    "eq.notes AS notes " +
				    "FROM equipment eq " +
				    "LEFT JOIN asset a ON eq.asset_number = a.asset_number " +
				    "LEFT JOIN u_sege u ON eq.equipment_id = u.equipment_id AND u.delete_u_sege = FALSE " +
				    "LEFT JOIN location l ON eq.location_cd = l.location_cd " + 
				    "LEFT JOIN status es ON eq.equipment_status = es.equipment_status " +
				    "WHERE eq.delete_equipment = FALSE "+
					"AND eq.location_cd = ? " ;



			ps = conn.prepareStatement(sql);
			ps.setString(1,location_cd);
			rs = ps.executeQuery();

			while (rs.next()) {
				Display display = new Display();
				display.setEquipmentId(rs.getString("equipment_id"));
				display.setAssetName(rs.getString("asset_name"));
				display.setMaker(rs.getString("maker"));
				display.setModel(rs.getString("model"));
				display.setType(rs.getString("TYPE"));
				display.setSerialnumber(rs.getString("serialnumber"));
				display.setSp(rs.getString("spec"));
				display.setMacAddress(rs.getString("mac_address"));
				display.setPurchaseDate(rs.getDate("purchase_date"));
				display.setOld(rs.getInt("years_since"));
				display.setPurchasePrice(rs.getInt("purchase_price"));
				display.setCurrentuser(rs.getString("currentuser"));
				display.setLocation(rs.getString("location"));
				display.setUseStartDate(rs.getDate("use_start_date"));
				display.setApplyCompleteDate(rs.getDate("apply_complete_date"));
				display.setPreviousUser(rs.getString("previous_user"));
				display.setEquipmentStatus(rs.getString("equipment_status"));
				display.setEquipmentStatusName(rs.getString("equipment_status_name"));
				display.setNote(rs.getString("notes"));
				displayList.add(display);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(rs);
			DBManager.close(ps);
			DBManager.close(conn);
		}

		return displayList;
	}

	// 検索機能：備品情報を検索する
	public static List<Display> searchDisplay(String location_cd, String assetName, Date startDate, Date endDate, String currentuser, Boolean deleteFlag, String equipmentStatus) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Display> displayList = new ArrayList<>();
		try {
			conn = DBManager.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ")
					.append("e.equipment_id, ")
					.append("a.asset_name, ")
					.append("e.maker, ")
					.append("e.model, ")
					.append("e.TYPE, ")
					.append("e.serialnumber, ")
					.append("e.sp AS spec, ")
					.append("e.mac_ad, ")
					.append("e.purchase_date, ")
					.append("TIMESTAMPDIFF(YEAR, e.purchase_date, CURDATE()) AS years_since, ")
					.append("e.purchase_price, ")
					.append("u.currentuser, ")
					.append("l.location_name AS location, ")
					.append("u.start_date AS use_start_date, ")
					.append("u.application_completion_date AS apply_complete_date, ")
					.append("u.previous_user, ")
					.append("e.equipment_status AS equipment_status, ")
					.append("s.equipment_status_name AS equipment_status_name, ")
					.append("e.notes AS note ")
					.append("FROM equipment e ")
					.append("LEFT JOIN asset a ON e.asset_number = a.asset_number ")
					.append("LEFT JOIN u_sege u ON e.equipment_id = u.equipment_id AND u.delete_u_sege = FALSE ")
					.append("LEFT JOIN location l ON e.location_cd = l.location_cd ")
					.append("LEFT JOIN status s ON e.equipment_status = s.equipment_status ")
					.append("WHERE 1=1 ")
					.append("AND e.location_cd = ? ");


			List<Object> params = new ArrayList<>();

			params.add(location_cd);
			
			// 削除フラグの追加
			if (deleteFlag != null) {
				sql.append(" AND e.delete_equipment = ? ");
				params.add(deleteFlag);
			}
			
			//ステータスの追加
			if (equipmentStatus != null && !equipmentStatus.isEmpty()) {
			    sql.append(" AND e.equipment_status = ? ");
			    params.add(equipmentStatus);
			}

			if (assetName != null && !assetName.isEmpty()) {
				sql.append(" AND a.asset_name = ? ");
				params.add(assetName);
			}

			if (startDate != null && endDate != null) {
				sql.append(" AND e.purchase_date BETWEEN ? AND ? ");
				params.add(new java.sql.Date(startDate.getTime()));
				params.add(new java.sql.Date(endDate.getTime()));
			} else if (startDate != null) {
				sql.append(" AND e.purchase_date >= ? ");
				params.add(new java.sql.Date(startDate.getTime()));
			} else if (endDate != null) {
				sql.append(" AND e.purchase_date <= ? ");
				params.add(new java.sql.Date(endDate.getTime()));
			}

			if (currentuser != null && !currentuser.isEmpty()) {
				sql.append(" AND u.currentuser = ? ");
				params.add(currentuser);
			}

			ps = conn.prepareStatement(sql.toString());

			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i + 1, params.get(i));
			}

			rs = ps.executeQuery();

			while (rs.next()) {
				Display display = new Display();
				display.setEquipmentId(rs.getString("equipment_id"));
				display.setAssetName(rs.getString("asset_name"));
				display.setMaker(rs.getString("maker"));
				display.setModel(rs.getString("model"));
				display.setType(rs.getString("TYPE"));
				display.setSerialnumber(rs.getString("serialnumber"));
				display.setSp(rs.getString("spec"));
				display.setMacAddress(rs.getString("mac_ad"));
				display.setPurchaseDate(rs.getDate("purchase_date"));
				display.setOld(rs.getInt("years_since"));
				display.setPurchasePrice(rs.getInt("purchase_price"));
				display.setCurrentuser(rs.getString("currentuser"));
				display.setLocation(rs.getString("location"));
				display.setUseStartDate(rs.getDate("use_start_date"));
				display.setApplyCompleteDate(rs.getDate("apply_complete_date"));
				display.setPreviousUser(rs.getString("previous_user"));
				display.setEquipmentStatus(rs.getString("equipment_status"));
				display.setEquipmentStatusName(rs.getString("equipment_status_name"));
				display.setNote(rs.getString("note"));
				displayList.add(display);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(rs);
			DBManager.close(ps);
			DBManager.close(conn);
		}

		return displayList;
	}

	//資産名を取得
	public static List<Asset> assetName() {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Asset> assetsList = new ArrayList<>();

		try {

			conn = DBManager.getConnection();

			String sql = "SELECT asset_name FROM asset";

			ps = conn.prepareStatement(sql);

			rs = ps.executeQuery();

			//資産分類を全件取得
			while (rs.next()) {

				Asset assets = new Asset();

				assets.setAssetName(rs.getString("asset_name"));

				assetsList.add(assets);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(rs);
			DBManager.close(ps);
			DBManager.close(conn);
		}

		return assetsList;
	}

	// 削除履歴：削除された備品の情報を取得する
	public static List<Display> deledis() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		// 検索結果を格納するリスト
		List<Display> displayList = new ArrayList<>();

		try {
			conn = DBManager.getConnection();

			// 削除された備品の履歴を取得するためのSQLクエリ
			String sql =
				"SELECT " +
                "eq.equipment_id AS equipment_id, " +
                "a.asset_name AS asset_name, " +
                "eq.maker AS maker, " +
                "eq.model AS model, " +
                "eq.TYPE AS TYPE, " +
                "eq.serialnumber AS S_N, " +
                "eq.sp AS spec, " +
                "eq.purchase_date AS purchase_date, " +
                "YEAR(CURDATE()) - YEAR(eq.purchase_date) AS years_since, " +
                "eq.purchase_price AS purchase_price, " +
                "us.currentuser AS currentuser, " +
                "l.location_name AS location, " +
                "FROM equipment eq " +
                "LEFT JOIN asset a ON eq.asset_number = a.asset_number " +
                "LEFT JOIN u_sege us ON eq.equipment_id = us.equipment_id " +
                "LEFT JOIN location l ON eq.location_cd = l.location_cd " +
                "WHERE eq.delete_equipment = true " +
                "AND us.delete_u_sege = true";


			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			// 検索結果を取得してDisplayオブジェクトにセット
			while (rs.next()) {
				Display display = new Display();
				display.setEquipmentId(rs.getString("equipment_id"));
				display.setAssetName(rs.getString("asset_name"));
				display.setMaker(rs.getString("maker"));
				display.setModel(rs.getString("model"));
				display.setType(rs.getString("TYPE"));
				display.setSerialnumber(rs.getString("S_N"));
				display.setSp(rs.getString("spec"));
				display.setPurchaseDate(rs.getDate("purchase_date"));
				display.setOld(rs.getInt("years_since"));
				display.setPurchasePrice(rs.getInt("purchase_price"));
				display.setCurrentuser(rs.getString("currentuser"));
				display.setLocation(rs.getString("location"));

				displayList.add(display);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(rs);
			DBManager.close(ps);
			DBManager.close(conn);
		}

		return displayList;
	}

	//選択された行の削除フラグをtrueに変更
	public static int delete(String equipmentId) {
		Connection conn = null;
		PreparedStatement psEquipment = null;
		PreparedStatement psUsage = null;
		int totalUpdated = 0;

		try {
			conn = DBManager.getConnection();
			conn.setAutoCommit(false); // トランザクション開始

			// equipmentテーブルの更新
			String sqlEquipment = "UPDATE equipment SET delete_equipment = TRUE WHERE equipment_id = ?";
			psEquipment = conn.prepareStatement(sqlEquipment);
			psEquipment.setString(1, equipmentId);
			int updatedEquipment = psEquipment.executeUpdate();

			// u_segeテーブルの更新
			String sqlUsage = "UPDATE u_sege SET delete_u_sege = TRUE WHERE equipment_id = ?";
			psUsage = conn.prepareStatement(sqlUsage);
			psUsage.setString(1, equipmentId);
			int updatedUsage = psUsage.executeUpdate();

			conn.commit();
			totalUpdated = updatedEquipment + updatedUsage;

		} catch (SQLException e) {
			e.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		} finally {
			DBManager.close(psEquipment);
			DBManager.close(psUsage);
			if (conn != null) {
				try {
					conn.setAutoCommit(true);
					DBManager.close(conn);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return totalUpdated;
	}

	public static Display update(String id) {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Display update = null;

		try {
			conn = DBManager.getConnection();

			String sql =
				"SELECT " +
				"eq.equipment_id, " +
				"a.asset_name, " +
				"eq.maker, " +
				"eq.model, " +
				"eq.`TYPE`, " +
				"eq.serialnumber, " +
				"eq.sp, " +
				"eq.mac_ad, " +
				"eq.purchase_date, " +
				"eq.purchase_price, " +
				"us.currentuser, " +
				"us.previous_user, " +
				"eq.location_cd AS location, " +
				"us.start_date, " +
				"us.application_completion_date, " +
				"eq.equipment_status AS equipment_status, " +
				"es.equipment_status_name AS equipment_status_name, " +
				"eq.notes " +
				"FROM equipment eq " +
				"LEFT JOIN asset a ON eq.asset_number = a.asset_number " +
				"LEFT JOIN u_sege us ON eq.equipment_id = us.equipment_id " +
				"LEFT JOIN location l ON eq.location_cd = l.location_cd " +
				"LEFT JOIN status es " +
				"  ON eq.equipment_status = es.equipment_status " +
				"WHERE eq.delete_equipment = false " +
				"  AND us.delete_u_sege = false " +
				"  AND eq.equipment_id = ?" ;


			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				update = new Display();
				update.setEquipmentId(rs.getString("equipment_id"));
				update.setAssetName(rs.getString("asset_name"));
				update.setMaker(rs.getString("maker"));
				update.setModel(rs.getString("model"));
				update.setType(rs.getString("TYPE"));
				update.setSerialnumber(rs.getString("serialnumber"));
				update.setSp(rs.getString("sp"));
				update.setMacAddress(rs.getString("mac_ad"));
				update.setPurchaseDate(rs.getDate("purchase_date"));
				update.setPurchasePrice(rs.getInt("purchase_price"));
				update.setCurrentuser(rs.getString("currentuser"));
				update.setPreviousUser(rs.getString("previous_user"));
				update.setLocation(rs.getString("location"));
				update.setStartDate(rs.getDate("start_date"));
				update.setApplicationCompletionDate(rs.getDate("application_completion_date"));
				update.setEquipmentStatusName(rs.getString("equipment_status_name"));
				update.setEquipmentStatus(rs.getString("equipment_status"));
				update.setNotes(rs.getString("notes"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(rs);
			DBManager.close(ps);
			DBManager.close(conn);
		}

		return update;
	}

	// 備品ステータスマスタ取得
	public static List<Display> equipmentStatusList() {
	    List<Display> list = new ArrayList<>();
	    Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        conn = DBManager.getConnection();
	        String sql =
	        	"SELECT equipment_status, equipment_status_name " +
	            "FROM status " +
	            "ORDER BY disp_order";

	        ps = conn.prepareStatement(sql);
	        rs = ps.executeQuery();

	        while (rs.next()) {
	            Display d = new Display();
	            d.setEquipmentStatus(rs.getString("equipment_status"));       // コード
	            d.setEquipmentStatusName(rs.getString("equipment_status_name"));// 表示名
	            list.add(d);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        DBManager.close(rs);
	        DBManager.close(ps);
	        DBManager.close(conn);
	    }
	    return list;
	}

}
