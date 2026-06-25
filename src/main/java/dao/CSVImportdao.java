package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CSVImportdao {

	public static int getOrInsertAsset(Connection conn, String assetName) throws SQLException {
		String selectSql = "SELECT asset_number FROM asset WHERE asset_name = ?";
		try (PreparedStatement ps = conn.prepareStatement(selectSql)) {
			
			ps.setString(1, assetName);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				return rs.getInt("asset_number");
			}
		}
		String insertSql = "INSERT INTO asset (asset_name) VALUES (?)";
		
		try (PreparedStatement ps = conn.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, assetName);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			
			if (rs.next()) {
				return rs.getInt(1);
			} else {
				throw new SQLException("資産登録に失敗しました。");
			}
			
		}
	}

	public static Date parseDate(String dateStr) {
		
		if (dateStr == null || dateStr.isEmpty()) {
			return null;
		}

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			sdf.setLenient(false);
			return new java.sql.Date(sdf.parse(dateStr).getTime());
		} catch (ParseException e) {
			System.err.println("不正な日付: " + dateStr); // ログ出力だけして
			return null; // または throw して呼び出し元で処理
		}
	}

	public static void insertEquipment(Connection conn, 
			String equipmentId, 
			String assetNumber, 
			String maker, 
			String model, 
			String type, 
			String serialNumber, 
			String sp, 
			Date purchaseDate, 
			int purchasePrice, 
			String equipment_status, 
			String notes, 
			String macAddress, 
			String locationCode
			) throws SQLException {
		
		
		String insertQuery = "INSERT INTO equipment ("
				+ "equipment_id, "
				+ "asset_number, "
				+ "maker, "
				+ "model, "
				+ "type, "
				+ "serialnumber, "
				+ "sp, "
				+ "mac_ad, "
				+ "purchase_date, "
				+ "purchase_price, "
				+ "equipment_status, "
				+ "notes, "
				+ "location_cd) "
							+"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
			insertStmt.setString(1, equipmentId);
			insertStmt.setString(2, assetNumber);
			insertStmt.setString(3, maker);
			insertStmt.setString(4, model);
			insertStmt.setString(5, type);
			insertStmt.setString(6, serialNumber);
			insertStmt.setString(7, sp);
			insertStmt.setString(8, macAddress);
			insertStmt.setDate(9, purchaseDate);
			insertStmt.setInt(10, purchasePrice);
			insertStmt.setString(11, equipment_status);
			insertStmt.setString(12, notes);
			insertStmt.setString(13,locationCode);

			insertStmt.executeUpdate();
		}
	}

	public static void insertUsage(Connection conn, String equipmentId, String currentUser, String previousUser, Date startDate, Date applicationCompletionDate) throws SQLException {
		
		String sql = "INSERT INTO u_sege (equipment_id, currentuser, previous_user, start_date, application_completion_date) "
					+"VALUES (?, ?, ?, ?, ?)";
		
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, equipmentId);
			ps.setString(2, currentUser);
			ps.setString(3, previousUser);
			ps.setDate(4, startDate);
			ps.setDate(5, applicationCompletionDate);
			ps.executeUpdate();
		}
	}
}