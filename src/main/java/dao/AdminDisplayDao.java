package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import been.Location;
import been.User;
import db.DBManager;

public class AdminDisplayDao {
	//全件一覧表示用
	public static List<User> listdisplay() {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<User> displayList = new ArrayList<>();

		try {
			//データベース接続
			conn = DBManager.getConnection();
			
			//true	のものだけ一覧表示
			String sql =
				    "SELECT user_id,user_name,password,location_cd,admin_flg,delete_flg "
				    + "FROM user";

			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				User user = new User();
				user.setUser_id(rs.getString("user_id"));
				user.setUser_name(rs.getString("user_name"));
				user.setPassword(rs.getString("password"));
				user.setLocation_cd(rs.getString("location_cd"));
				user.setAdmin_flg(rs.getInt("admin_flg"));
				user.setDelete_flg(rs.getInt("delete_flg"));
				displayList.add(user);
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
	
	//ログインした人の支店の一覧表示用
		public static List<User> listLocationDisplay(String location_cd) {

			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			List<User> displayList = new ArrayList<>();

			try {
				//データベース接続
				conn = DBManager.getConnection();
				
				//true	のものだけ一覧表示
				String sql =
					    "SELECT user_id,user_name,password,location_cd,admin_flg,delete_flg "
					    + "FROM user "
					    + "WHERE location.cd = ?";

				ps = conn.prepareStatement(sql);
				ps.setString(1, location_cd);
				rs = ps.executeQuery();

				while (rs.next()) {
					User user = new User();
					user.setUser_id(rs.getString("user_id"));
					user.setUser_name(rs.getString("user_name"));
					user.setPassword(rs.getString("password"));
					user.setLocation_cd(rs.getString("location_cd"));
					user.setAdmin_flg(rs.getInt("admin_flg"));
					user.setDelete_flg(rs.getInt("delete_flg"));
					displayList.add(user);
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
	
	// 検索機能：ユーザー情報を検索する
	public static List<User> searchDisplay(String userId,String userName,String locationName,Integer deleteFlagInt) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<User> displayList = new ArrayList<>();
		
		
		
		try {
			conn = DBManager.getConnection();

			String sql =
				    "SELECT u.user_id,u.user_name,u.password,u.location_cd,u.admin_flg,u.delete_flg "
				    + "FROM user u "
				    + "INNER JOIN location l "
				    + "ON u.location_cd = l.location_cd "
				    + "WHERE (? IS NULL OR u.user_id = ?) "
				    + "AND (? IS NULL OR u.user_name LIKE ?) "
				    + "AND (? IS NULL OR l.location_name = ?) "
				    + "AND (? IS NULL OR u.delete_flg = ?)";

			 ps = conn.prepareStatement(sql);
	            ps.setString(1, userId);
	            ps.setString(2, userId);
	            ps.setString(3, userName);
	            ps.setString(4, userName);
	            ps.setString(5, locationName);
	            ps.setString(6, locationName);
	            ps.setInt(7, deleteFlagInt);
	            ps.setInt(8, deleteFlagInt);
			
			rs = ps.executeQuery();

			while (rs.next()) {
				User user = new User();
				user.setUser_id(rs.getString("user_id"));
				user.setUser_name(rs.getString("user_name,"));
				user.setPassword(rs.getString("password"));
				user.setLocation_cd(rs.getString("location_cd"));
				user.setAdmin_flg(rs.getInt("admin_flg"));
				user.setDelete_flg(rs.getInt("delete_flg"));
				displayList.add(user);
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

	//拠点を取得
	public static List<Location> locationName() {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Location> locationList = new ArrayList<>();

		try {

			conn = DBManager.getConnection();

			String sql = "SELECT location_id,location_name FROM location";

			ps = conn.prepareStatement(sql);

			rs = ps.executeQuery();

			//拠点名を全件取得
			while (rs.next()) {

				Location location = new Location();

				location.setLocation_name(rs.getString("location_name"));

				locationList.add(location);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(rs);
			DBManager.close(ps);
			DBManager.close(conn);
		}

		return locationList;
	}

	/*// 削除履歴：削除された備品の情報を取得する
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
	}*/

	//選択された行の削除フラグをtrueに変更(user)
	public static int delete(String userId) {
		Connection conn = null;
		PreparedStatement ps = null;
		int updated = 0;
		
		try {
			conn = DBManager.getConnection();

			// equipmentテーブルの更新
			String sql = "UPDATE user SET delete_flg = TRUE WHERE user_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, userId);
			updated = ps.executeUpdate();

		} catch (SQLException e) {

	        e.printStackTrace();

	    } finally {

	        DBManager.close(ps);

	        if (conn != null) {
	            DBManager.close(conn);
	        }
	    }

		return updated;
	}

	public static User update(String id) {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		User update = null;

		try {
			conn = DBManager.getConnection();

			String sql ="SELECT user_id,user_name,password,location_cd,admin_flg "
					+ "FROM user "
					+ "WHERE user_id = ?";


			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				update = new User();
				update.setUser_id(rs.getString("user_id"));
				update.setUser_name(rs.getString("user_name"));
				update.setPassword(rs.getString("password"));
				update.setLocation_cd(rs.getString("location_cd"));
				update.setAdmin_flg(rs.getInt("admin_flg"));
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

	
	}

